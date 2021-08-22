package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerToggleFlightListener implements Listener {
    private final ElytraDuels plugin;
    private final Set<Player> delay = new HashSet<>();

    public PlayerToggleFlightListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        if(game == null) {
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
        if(delay.contains(player)) {
            return;
        }

        // Prevents players from "flying" when having no double jumps left.
        if(game.getDoubleJumps(player) == 0) {
            event.setCancelled(true);
            player.setFlying(false);
            player.setAllowFlight(false);
            return;
        }

        delay.add(player);
        player.setAllowFlight(false);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            delay.remove(player);
            player.setAllowFlight(true);
        }, 15);
        game.removeDoubleJump(player);
        player.setFlying(false);

        player.setVelocity(player.getLocation().getDirection().setY((Math.abs(player.getLocation().getDirection().getY()) + 0.3) / 1.3).multiply(1.3));
        player.getLocation().getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE,0, 20);
        event.setCancelled(true);
    }
}