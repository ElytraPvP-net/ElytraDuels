package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class ProjectileLaunchListener implements Listener {
    private final ElytraDuels plugin;

    public ProjectileLaunchListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {
        if(event.getEntity() instanceof Egg) {
            if(!(event.getEntity().getShooter() instanceof Player)) {
                return;
            }

            Player shooter = (Player) event.getEntity().getShooter();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if(plugin.getGameManager().getGame(shooter) != null) {
                    shooter.getInventory().setItem(1, new ItemStack(Material.EGG));
                }
            }, 50);
            return;
        }


        if(event.getEntity() instanceof Arrow) {
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
}