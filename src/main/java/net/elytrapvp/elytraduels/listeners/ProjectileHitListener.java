package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {
    private final ElytraDuels plugin;

    public ProjectileHitListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(ProjectileHitEvent event) {
        if(!(event.getEntity() instanceof Arrow)) {
            return;
        }

        if(!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();
        Game game = plugin.getGameManager().getGame(player);

        if(game == null) {
            return;
        }

        if(game.getKit().hasArrowPickup()) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        arrow.remove();
    }
}
