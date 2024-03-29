package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class DuelGUI extends CustomGUI {

    public DuelGUI(ElytraDuels plugin, Player player, Player target) {
        super(27, "Duel " + target.getName());

        int i = 0;
        // Displays enabled kits.
        for(Kit kit : plugin.kitManager().kits()) {
            int playing = plugin.queueManager().getPlaying(kit);
            int queue = plugin.queueManager().getQueueing(kit, GameType.UNRANKED);

            int count = playing + queue;
            if(count == 0) count = 1;

            ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                    .setDisplayName("&a" + kit.getName())
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();

            setItem(i, item, (p, a) -> {
                p.performCommand("duel " + target.getName() + " " + kit.getName().toLowerCase());
                p.closeInventory();

                // Remove player from queue.
                plugin.queueManager().removePlayer(p);
            });
            i++;
        }

        // Displays disabled kits if the player has permission.
        if(player.hasPermission("duels.disabled")) {
            for(Kit kit : plugin.kitManager().disabledKits()) {
                int playing = plugin.queueManager().getPlaying(kit);
                int queue = plugin.queueManager().getQueueing(kit, GameType.UNRANKED);

                int count = playing + queue;
                if(count == 0) count = 1;

                ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                        .setDisplayName("&a" + kit.getName())
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .build();

                setItem(i, item, (p, a) -> {
                    p.performCommand("duel " + target.getName() + " " + kit.getName().toLowerCase());
                    p.closeInventory();

                    // Remove player from queue.
                    plugin.queueManager().removePlayer(p);
                });
                i++;
            }
        }
    }
}