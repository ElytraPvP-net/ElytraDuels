package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClassicKit extends Kit {

    public ClassicKit() {
        super("Classic");
    }

    public void apply(Player p) {
        p.getInventory().clear();
        p.setHealth(20);

        ItemStack helmet = new ItemBuilder(Material.IRON_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.IRON_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.IRON_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .setUnbreakable(true)
                .build();
        ItemStack fishingRod = new ItemBuilder(Material.FISHING_ROD)
                .build();
        ItemStack bow = new ItemBuilder(Material.BOW)
                .setUnbreakable(true)
                .build();
        ItemStack arrows = new ItemBuilder(Material.ARROW, 4).build();

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, fishingRod);
        p.getInventory().setItem(2, bow);
        p.getInventory().setItem(8, arrows);
    }

    public Material getIconMaterial() {
        return Material.FISHING_ROD;
    }
}