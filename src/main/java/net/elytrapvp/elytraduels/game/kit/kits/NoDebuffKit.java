package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class NoDebuffKit extends Kit {

    public NoDebuffKit() {
        super("No Debuff");
    }

    public void apply(Player p) {
        p.getInventory().clear();
        p.setHealth(20);

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.FIRE_ASPECT, 2)
                .addEnchantment(Enchantment.DAMAGE_ALL, 3)
                .setUnbreakable(true)
                .build();
        ItemStack pearls = new ItemBuilder(Material.ENDER_PEARL, 5).build();

        Potion speedPot = new Potion(PotionType.SPEED);
        speedPot.setLevel(2);
        ItemStack speed = speedPot.toItemStack(1);

        Potion fireResPot = new Potion(PotionType.FIRE_RESISTANCE);
        fireResPot.setHasExtendedDuration(true);
        ItemStack fireRes = fireResPot.toItemStack(1);

        Potion healingPot = new Potion(PotionType.INSTANT_HEAL);
        healingPot.setSplash(true);
        healingPot.setLevel(2);
        ItemStack healing = healingPot.toItemStack(1);

        p.getInventory().setHelmet(helmet);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setBoots(boots);

        p.getInventory().setItem(0, sword);
        p.getInventory().setItem(1, pearls);
        p.getInventory().setItem(2, speed);
        p.getInventory().setItem(3, fireRes);
        p.getInventory().setItem(17, speed);
        p.getInventory().setItem(26, speed);
        p.getInventory().setItem(35, speed);

        for(int i = 0; i < p.getInventory().getContents().length; i++) {
            if(p.getInventory().getItem(i) == null) {
                p.getInventory().setItem(i, healing);
            }
        }
    }

    public Material getIconMaterial() {
        return Material.POTION;
    }
}