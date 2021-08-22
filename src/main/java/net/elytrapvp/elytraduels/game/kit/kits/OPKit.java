package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class OPKit extends Kit {

    public OPKit() {
        super("OP");
    }

    public void apply(Player p) {
        p.getInventory().clear();

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .setUnbreakable(true)
                .build();

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .setUnbreakable(true)
                .build();

        ItemStack rod = new ItemBuilder(Material.FISHING_ROD).build();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 4)
                .setUnbreakable(true)
                .build();

        ItemStack goldenApples = new ItemBuilder(Material.GOLDEN_APPLE, 6).build();

        Potion speedPot = new Potion(PotionType.SPEED);
        speedPot.setSplash(true);
        speedPot.setLevel(2);
        ItemStack speed = speedPot.toItemStack(1);

        Potion regenPot = new Potion(PotionType.REGEN);
        regenPot.setSplash(true);
        regenPot.setLevel(1);
        ItemStack regen = regenPot.toItemStack(1);

        ItemStack arrows = new ItemBuilder(Material.ARROW, 20).build();

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, rod);
        p.getInventory().setItem(2, bow);
        p.getInventory().setItem(3, goldenApples);
        p.getInventory().setItem(4, speed);
        p.getInventory().setItem(5, regen);
        p.getInventory().setItem(9, arrows);
        p.getInventory().setItem(6, speed);
        p.getInventory().setItem(7, regen);
    }

    public Material getIconMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }
}