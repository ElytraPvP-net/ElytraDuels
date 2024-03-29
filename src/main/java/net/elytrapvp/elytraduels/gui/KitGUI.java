package net.elytrapvp.elytraduels.gui;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitGUI extends CustomGUI {

    public KitGUI(ElytraDuels plugin) {
        super(45, "Kits");
        filler();

        int[] iconSlots = new int[]{10,11,12,13,14,15,16,28,29,30,31,32,33,34,21,23};
        int i = 0;
        for(Kit kit : plugin.kitManager().kits()) {

            ItemStack item = new ItemBuilder(kit.getIconMaterial(), 1)
                    .setDisplayName("&a" + kit.getName())
                    .addLore("&7Playing: " + plugin.queueManager().getPlaying(kit))
                    .addLore("&7Queuing: " + plugin.queueManager().getQueueing(kit, GameType.UNRANKED))
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .build();

            setItem(iconSlots[i], item, (pl, a) -> {
                pl.closeInventory();
                plugin.queueManager().addPlayer(pl, kit, GameType.UNRANKED);
            });
            i++;
        }
    }

    private void filler() {
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);

        int[] fillerSlots = new int[]{0,1,2,3,4,5,6,7,8,9,10,17,18,19,20,21,22,23,24,25,26,27,28,36,37,38,39,40,41,42,43,44};

        for(int slot : fillerSlots) {
            setItem(slot, filler);
        }
    }
}