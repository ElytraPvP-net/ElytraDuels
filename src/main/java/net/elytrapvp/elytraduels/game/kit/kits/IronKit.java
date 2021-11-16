package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IronKit extends Kit {

    public IronKit() {
        super("Iron");
        setIconMaterial(Material.IRON_SWORD);
        setHunger(true);
        setStartingSaturation(20);

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

        ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .setUnbreakable(true)
                .build();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .setUnbreakable(true)
                .build();
        ItemStack arrows = new ItemBuilder(Material.ARROW, 32).build();

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64).build();

        addItem(39, helmet);
        addItem(38, chestplate);
        addItem(37, leggings);
        addItem(36, boots);

        addItem(0, sword);
        addItem(1, bow);
        addItem(8, steak);
        addItem(7, arrows);
    }
}