package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {
    private final ElytraDuels plugin;

    public BlockFromToListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(BlockFromToEvent event) {
        Bukkit.broadcastMessage("FROM:" + event.getBlock().getType());
        Bukkit.broadcastMessage("TO: " + event.getToBlock().getType());

        if(event.getToBlock().getType() == Material.STATIONARY_LAVA || event.getToBlock().getType() == Material.OBSIDIAN) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getBlock().setType(Material.AIR), 200);
        }

        if(event.getBlock().isLiquid()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getToBlock().setType(Material.AIR), 200);
            return;
        }
        /*
        if(event.getBlock().getType() == Material.WATER ||
            event.getBlock().getType() == Material.STATIONARY_WATER ||
            event.getBlock().getType() == Material.LAVA ||
            event.getBlock().getType() == Material.STATIONARY_LAVA) {

            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getToBlock().setType(Material.AIR), 200);
            return;
        }

        if(event.getToBlock().getType() == Material.STONE ||
            event.getToBlock().getType() == Material.COBBLESTONE ||
            event.getToBlock().getType() == Material.OBSIDIAN) {

            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getToBlock().setType(Material.AIR), 200);
            return;
        }

         */
    }
}