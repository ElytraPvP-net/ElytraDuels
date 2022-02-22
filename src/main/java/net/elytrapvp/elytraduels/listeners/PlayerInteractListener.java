package net.elytrapvp.elytraduels.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import net.elytrapvp.elytraduels.gui.*;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.scoreboards.LobbyScoreboard;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.MathUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class PlayerInteractListener implements Listener {
    private final ElytraDuels plugin;
    private final Set<Player> pearlCooldown = new HashSet<>();

    public PlayerInteractListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        // Prevent using items during game countdown.
        if(game != null && game.getGameState() != GameState.RUNNING) {
            event.setCancelled(true);

            // Fixes visual glitch with throwables during countdown.
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), player.getItemInHand());
            return;
        }

        // Checks for the ender pearl cooldown.
        if(event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if(pearlCooldown.contains(player)) {
                ChatUtils.chat(player, "&cThat item is currently on cooldown.");
                event.setCancelled(true);
                return;
            }

            if(game.getKit().hasPearlCooldown()) {
                pearlCooldown.add(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> pearlCooldown.remove(player), 200);
            }
        }

        if(game != null) {
            if (game.getGameState() == GameState.RUNNING) {
                if (game.getKit().getTripleShots() > 0) {
                    if (!game.getSpectators().contains(player)) {
                        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                            if (event.getItem() != null) {
                                if (event.getItem().getType() == Material.BOW) {
                                    if (game.getTripleShots(player) > 0) {
                                        Arrow arrow = player.launchProjectile(Arrow.class);
                                        arrow.setVelocity(player.getLocation().getDirection().multiply(1.9));
                                        arrow.setFireTicks(100);

                                        Arrow arrow2 = player.launchProjectile(Arrow.class);
                                        arrow2.setVelocity(MathUtils.rotateVector(arrow.getVelocity().clone(), 0.21816616).multiply(1.1));
                                        arrow2.setFireTicks(100);

                                        Arrow arrow3 = player.launchProjectile(Arrow.class);
                                        arrow3.setVelocity(MathUtils.rotateVector(arrow.getVelocity().clone(), -0.21816616).multiply(1.1));
                                        arrow3.setFireTicks(100);

                                        game.removeTripleShot(player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Exit if the item is null.
        if(event.getItem() == null)
            return;

        // Exit if item meta is null.
        if(event.getItem().getItemMeta() == null)
            return;

        String item = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());

        if(item == null) {
            return;
        }

        switch (item) {
            case "Golden Head":
                if(pearlCooldown.contains(player)) {
                    ChatUtils.chat(player, "&cThat item is currently on cooldown.");
                    event.setCancelled(true);
                    return;
                }

                pearlCooldown.add(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> pearlCooldown.remove(player), 60);

                if(event.getItem().getAmount() == 1) {
                    player.getInventory().remove(event.getItem());
                }
                else {
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                }

                // Fix absorption from not resetting.
                player.removePotionEffect(PotionEffectType.ABSORPTION);

                PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 100, 0);
                PotionEffect absorption = new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0);
                PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);

                player.addPotionEffect(speed);
                player.addPotionEffect(absorption);
                player.addPotionEffect(regen);

                ChatUtils.chat(player, "&aYou ate a Golden Head!");
                player.playSound(player.getLocation(), Sound.EAT, 1, 1);

                event.setCancelled(true);

                break;
            case "Kits":
                new RankedGUI(plugin, player).open(player);
                event.setCancelled(true);
                break;

            case "Leave Queue":
                plugin.getQueueManager().removePlayer(player);
                ItemUtils.giveLobbyItems(player);
                new LobbyScoreboard(plugin, player);
                event.setCancelled(true);
                break;

            case "Leave Match":
                if(game != null && game.getSpectators().contains(player)) {
                    game.removeSpectator(player);
                    ItemUtils.giveLobbyItems(player);
                }
                event.setCancelled(true);
                break;

            case "Spectate":
                new SpectateGUI(plugin).open(player);
                event.setCancelled(true);
                break;

            case "Create a party":
                event.setCancelled(true);
                plugin.getPartyManager().createParty(player);
                ItemUtils.givePartyItems(plugin.getPartyManager(), player);
                break;

            case "Leave Party":
                event.setCancelled(true);

                Party party = plugin.getPartyManager().getParty(player);
                if(party == null) {
                    return;
                }

                if(party.getLeader().equals(player)) {
                    party.disband();
                }
                else {
                    party.removePlayer(player);
                }

                ItemUtils.giveLobbyItems(player);

                break;

            case "Duel another party":
                new PartyListGUI(plugin, player).open(player);
                event.setCancelled(true);
                break;

            case "Duel Party Members":
                new PartyDuelGUI(plugin).open(player);
                event.setCancelled(true);
                break;

            case "FFA Duel":
                new PartyFFAGUI(plugin).open(player);
                event.setCancelled(true);
                break;


            case "Spectate Current Game":
                event.setCancelled(true);

                Party party1 = plugin.getPartyManager().getParty(player);
                if(party1 == null) {
                    return;
                }

                Game game1 = plugin.getGameManager().getGame(party1.getLeader());
                if(game1 == null) {
                    ChatUtils.chat(player, "&cError &8» &cYour party is not currently in a game.");
                    return;
                }

                game1.addSpectator(player);
                game1.broadcast("&a" + player.getName() + " is now spectating.");
                break;

            case "Leaderboards":
                event.setCancelled(true);
                new LeaderboardGUI(plugin).open(player);
                break;
            case "Settings":
                event.setCancelled(true);
                new SettingsGUI(plugin, player).open(player);
                break;
            case "Double Jump":
                if(game == null) {
                    return;
                }

                if(game.getGameState() != GameState.RUNNING) {
                    return;
                }

                // Exit if game does not have double jumps.
                if(game.getKit().getDoubleJumps() == 0) {
                    return;
                }

                // Spectators can't double jump
                if(game.getSpectators().contains(player)) {
                    return;
                }

                // Prevents players from double jumping too often.
                if(PlayerToggleFlightListener.getDelay().contains(player)) {
                    return;
                }

                // Prevents players from "flying" when having no double jumps left.
                if(game.getDoubleJumps(player) == 0) {
                    event.setCancelled(true);
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    return;
                }

                PlayerToggleFlightListener.getDelay().add(player);
                player.setAllowFlight(false);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    PlayerToggleFlightListener.getDelay().remove(player);
                    player.setAllowFlight(true);
                }, 15);
                game.removeDoubleJump(player);
                player.setFlying(false);

                Vector vector = player.getLocation().getDirection().normalize().multiply(0.5).add(new Vector(0, 0.8, 0));
                player.setVelocity(vector);

                player.getLocation().getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE,0, 20);
                break;
        }

    }
}
