package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UHCKit extends Kit {

    public UHCKit() {
        super("UHC");

        setNaturalRegen(false);
    }

    public void apply(Player p) {
        p.getInventory().clear();
        p.setHealth(20);

        ItemStack helmet = new ItemBuilder(Material.IRON_HELMET)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.IRON_BOOTS)
                .setUnbreakable(true)
                .build();

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .setUnbreakable(true)
                .build();
        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 2).build();

        ItemStack arrows = new ItemBuilder(Material.ARROW, 1).build();

        p.getInventory().setItem(0, bow);
        p.getInventory().setItem(1, goldenApples);
        p.getInventory().setItem(35, arrows);
    }

    public Material getIconMaterial() {
        return Material.GOLDEN_APPLE;
    }
}