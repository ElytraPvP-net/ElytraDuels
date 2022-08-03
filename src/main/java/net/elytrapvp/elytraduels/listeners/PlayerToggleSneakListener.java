package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

public class PlayerToggleSneakListener implements Listener {
    private final ElytraDuels plugin;

    public PlayerToggleSneakListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerToggleSneakEvent event) {
        // Prevents the code from running twice.
        if(!event.isSneaking()) {
            return;
        }

        Player player = event.getPlayer();
        Game game = plugin.gameManager().getGame(player);

        if(game == null) {
            return;
        }

        // Return if the kit does not have repulsors.
        if(game.getKit().getRepulsors() == 0) {
            return;
        }

        // Spectators cannot use repulsors.
        if(game.getSpectators().contains(player)) {
            return;
        }

        if(game.getRepulsors(player) < 1) {
            return;
        }

        for(Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if(entity instanceof TNTPrimed) {
                continue;
            }

            // Prevent repulsors from pushing spectators.
            if(entity instanceof Player) {
                if(game.getSpectators().contains((Player) entity)) {
                    continue;
                }
            }

            Location location = player.getLocation();
            location.setY(player.getLocation().getY() - 2.5);
            Vector direction = entity.getLocation().toVector().subtract(location.toVector()).normalize().multiply(new Vector(0.48, 1.15, 0.48));
            entity.setVelocity(entity.getVelocity().add(direction));
        }

        game.removeRepulsor(player);
    }
}
