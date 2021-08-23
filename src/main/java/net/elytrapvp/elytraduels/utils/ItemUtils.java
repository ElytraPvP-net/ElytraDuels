package net.elytrapvp.elytraduels.utils;

import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static void giveLobbyItems(Player player) {
        player.getInventory().clear();

        player.setHealth(20);
        player.setFoodLevel(20);

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        ItemStack spectate = new ItemBuilder(Material.EYE_OF_ENDER)
                .setDisplayName("&aSpectate")
                .build();
        player.getInventory().setItem(0, spectate);

        ItemStack unranked = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName("&aKits")
                .build();
        player.getInventory().setItem(4, unranked);

        ItemStack otherModes = new ItemBuilder(Material.BED)
                .setDisplayName("&cBack to Lobby")
                .build();
        player.getInventory().setItem(8, otherModes);
    }
}