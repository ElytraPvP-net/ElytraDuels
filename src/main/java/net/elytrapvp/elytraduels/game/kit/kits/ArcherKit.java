package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArcherKit extends Kit {

    public ArcherKit() {
        super("Archer");

        setRangedDamage(true);
        setNaturalRegen(false);
    }

    @Override
    public void apply(Player player) {
        player.getInventory().clear();

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

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 2)
                .setUnbreakable(true)
                .build();
        ItemStack arrows = new ItemBuilder(Material.ARROW, 1).build();

        ItemStack pearl = new ItemBuilder(Material.ENDER_PEARL).build();

        player.getInventory().setItem(0, bow);
        player.getInventory().setItem(1, pearl);
        player.getInventory().setItem(35, arrows);

        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 999999, 0);
        player.addPotionEffect(effect);
    }

    @Override
    public Material getIconMaterial() {
        return Material.BOW;
    }

}
