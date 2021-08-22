package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {
    private final ElytraDuels plugin;

    public ProjectileLaunchListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if(!(event.getEntity() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();

        if(!(arrow.getShooter() instanceof Player)) {
            return;
        }

        Player shooter = (Player) arrow.getShooter();
        Game game = plugin.getGameManager().getGame(shooter);

        if(game == null) {
            return;
        }

        if(game.getGameState() == GameState.RUNNING) {
            return;
        }

        event.setCancelled(true);
    }
}