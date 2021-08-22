package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DiamondKit extends Kit {

    public DiamondKit() {
        super("Diamond");
        setHunger(true);
    }

    public void apply(Player p) {
        p.getInventory().clear();
        p.setHealth(20);

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
                .setUnbreakable(true)
                .build();

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .setUnbreakable(true)
                .build();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setUnbreakable(true)
                .build();
        ItemStack arrows = new ItemBuilder(Material.ARROW, 32).build();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64).build();

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, bow);
        p.getInventory().setItem(8, steak);
        p.getInventory().setItem(35, arrows);
    }

    public Material getIconMaterial() {
        return Material.DIAMOND_SWORD;
    }
}