package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PearlKit extends Kit {
    public PearlKit(ElytraDuels plugin) {
        super(plugin, "Pearl");

        setDoDamage(false);
        setIconMaterial(Material.ENDER_PEARL);

        ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                .setUnbreakable(true)
                .build();

        addItem(39, helmet);
        addItem(38, chestplate);
        addItem(37, leggings);
        addItem(36, boots);

        ItemStack stick = new ItemBuilder(Material.STICK)
                .setDisplayName("&aKnockback Stick")
                .addEnchantment(Enchantment.KNOCKBACK, 2)
                .build();

        ItemStack pearl = new ItemBuilder(Material.ENDER_PEARL, 8).build();

        addItem(0, stick);
        addItem(1, pearl);
    }
}