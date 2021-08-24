package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BlockBreakListener implements Listener {
    private final ElytraDuels plugin;

    public BlockBreakListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onEvent(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        if(game == null) {
            return;
        }

        // Get the drops from the block and add them to the inventory.
        Collection<ItemStack> drops = event.getBlock().getDrops();
        drops.forEach(drop -> player.getInventory().addItem(drop));
    }
}
