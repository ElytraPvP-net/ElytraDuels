package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        if(event.getToBlock().getType() == Material.STATIONARY_LAVA || event.getToBlock().getType() == Material.OBSIDIAN) {
            //Bukkit.getScheduler().runTaskLater(plugin, () -> event.getBlock().setType(Material.AIR), 200);
        }

        /*
        if(!event.getToBlock().isLiquid() && event.getToBlock().getType() != Material.AIR) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getToBlock().setType(Material.AIR), 200);
            return;
        }

         */

        /*
        if(event.getToBlock().getType() == Material.COBBLESTONE || event.getToBlock().getType() == Material.STONE) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getToBlock().setType(Material.AIR), 200);
            return;
        }

         */


        Material type = event.getBlock().getType();
        if (type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA){
            Block b = event.getToBlock();
            if (b.getType() == Material.AIR){
                generatesCobble(type, b);
            }
        }


    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b) {
        Material mirrorID1 = (type == Material.WATER || type == Material.STATIONARY_WATER ? Material.LAVA : Material.WATER);
        Material mirrorID2 = (type == Material.WATER || type == Material.STATIONARY_WATER ? Material.STATIONARY_LAVA : Material.STATIONARY_WATER);
        for (BlockFace face : faces) {
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1 || r.getType() == mirrorID2) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    r.setType(Material.AIR);
                }, 200);
                return true;
            }
        }
        return false;
    }
}