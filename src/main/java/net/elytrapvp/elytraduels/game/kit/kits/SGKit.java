package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SGKit extends Kit {

    public SGKit() {
        super("SG");
        setHunger(true);
    }

    public void apply(Player p) {
        p.getInventory().clear();
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setSaturation(10);
        p.setGameMode(GameMode.SURVIVAL);

        ItemStack helmet = new ItemBuilder(Material.IRON_HELMET)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.IRON_CHESTPLATE)
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

        ItemStack sword = new ItemBuilder(Material.STONE_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .setUnbreakable(true)
                .build();

        ItemStack rod = new ItemBuilder(Material.FISHING_ROD).build();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setUnbreakable(true)
                .build();

        ItemStack arrows = new ItemBuilder(Material.ARROW, 6).build();
        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 10).build();
        ItemStack gapple = new ItemBuilder(Material.GOLDEN_APPLE, 1).build();

        ItemStack fns = new ItemStack(Material.FLINT_AND_STEEL);
        fns.setDurability((short) 60);

        ItemStack cobwebs = new ItemBuilder(Material.WEB, 1).build();

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, rod);
        p.getInventory().setItem(2, bow);
        p.getInventory().setItem(4, arrows);
        p.getInventory().setItem(5, gapple);
        p.getInventory().setItem(6, steak);
        p.getInventory().setItem(7, fns);
        p.getInventory().setItem(8, cobwebs);
    }

    public Material getIconMaterial() {
        return Material.FLINT_AND_STEEL;
    }
}