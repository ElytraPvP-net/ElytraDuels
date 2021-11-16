package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DuelGUI extends CustomGUI {

    public DuelGUI(ElytraDuels plugin, Player target) {
        super(18, "Duel " + target.getName());

        int i = 0;
        for(Kit kit : plugin.getKitManager().getKits()) {
            int playing = plugin.getQueueManager().getPlaying(kit);
            int queue = plugin.getQueueManager().getQueueing(kit, GameType.UNRANKED);

            int count = playing + queue;
            if(count == 0) count = 1;

            ItemStack item = new ItemBuilder(kit.getIconMaterial(), count)
                    .setDisplayName("&a" + kit.getName())
                    .build();

            setItem(i, item, (p, a) -> {
                p.performCommand("duel " + target.getName() + " " + kit.getName().toLowerCase());
                p.closeInventory();
                ChatUtils.chat(p, "&a&l(&7!&a&l) &aDuel request sent.");

                // Remove player from queue.
                plugin.getQueueManager().removePlayer(p);
            });
            i++;
        }
    }

}