package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
    private final ElytraDuels plugin;

    public EntityExplodeListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.setCancelled(true);

        if(!(e.getEntity() instanceof TNTPrimed)) {
            return;
        }

        TNTPrimed tnt = (TNTPrimed) e.getEntity();

        if(tnt.getSource() == null) {
            tnt.getSourceLoc().getWorld().getBlockAt(tnt.getSourceLoc()).setType(Material.TNT);
            return;
        }

        if(!tnt.getSource().isValid()) {
            return;
        }

        /*
        if((tnt.getSource() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) tnt.getSource();

        if(arrow.getShooter() == null) {
            return;
        }

        if((arrow.getShooter() instanceof Player)) {
            return;
        }

        Player shooter = (Player) arrow.getShooter();

        if(gameManager.getGame(shooter) == null) {
            return;
        }

        Game game = gameManager.getGame(shooter);

        game.addBlock(tnt.getSourceLoc(), Material.TNT);

         */

        if(!(tnt.getSource() instanceof Player)) {
            return;
        }

        Player shooter = (Player) tnt.getSource();

        if(plugin.getGameManager().getGame(shooter) == null) {
            return;
        }

        Game game = plugin.getGameManager().getGame(shooter);

        if(tnt.getSourceLoc() == null) {
            return;
        }

        game.addBlock(tnt.getSourceLoc(), Material.TNT);

        tnt.remove();
    }

}