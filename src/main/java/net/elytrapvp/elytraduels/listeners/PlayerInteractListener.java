package net.elytrapvp.elytraduels.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import net.elytrapvp.elytraduels.gui.KitGUI;
import net.elytrapvp.elytraduels.gui.SpectateGUI;
import net.elytrapvp.elytraduels.scoreboards.LobbyScoreboard;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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

        // Checks for the ender pearl cooldown.
        if(event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if(pearlCooldown.contains(player)) {
                event.setCancelled(true);
                return;
            }

            pearlCooldown.add(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> pearlCooldown.remove(player), 100);
        }

        Game game = plugin.getGameManager().getGame(player);
        //
        if(game != null) {
            if (game.getGameState() == GameState.RUNNING) {
                if (game.getKit().getTripleShots() > 0) {
                    if (!game.getSpectators().contains(player)) {
                        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                            if (event.getItem() != null) {
                                if (event.getItem().getType() == Material.BOW) {
                                    if (game.getTripleShots(player) > 0) {
                                        Arrow arrow = player.launchProjectile(Arrow.class);
                                        arrow.setVelocity(player.getLocation().getDirection().multiply(1.8));
                                        arrow.setFireTicks(100);

                                        Arrow arrow2 = player.launchProjectile(Arrow.class);
                                        arrow2.setVelocity(MathUtils.rotateVector(arrow.getVelocity().clone(), 0.26179939).multiply(1.1));
                                        arrow2.setFireTicks(100);

                                        Arrow arrow3 = player.launchProjectile(Arrow.class);
                                        arrow3.setVelocity(MathUtils.rotateVector(arrow.getVelocity().clone(), -0.26179939).multiply(1.1));
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
            case "Kits":
                new KitGUI(plugin).open(player);
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

            case "Back to Lobby":
                event.setCancelled(true);

                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("lobby");

                player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                break;
        }
    }
}
