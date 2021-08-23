package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StratusKit extends Kit {

    public StratusKit() {
        super("Stratus");
    }

    @Override
    public void apply(Player player) {
        player.getInventory().clear();
        player.setHealth(20);
        player.setGameMode(GameMode.SURVIVAL);

        ItemStack helmet = new ItemBuilder(Material.CHAINMAIL_HELMET)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.IRON_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2)
                .setUnbreakable(true)
                .build();

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        ItemStack sword = new ItemBuilder(Material.STONE_SWORD).setUnbreakable(true).build();
        ItemStack bow = new ItemBuilder(Material.BOW).setUnbreakable(true).build();
        ItemStack axe = new ItemBuilder(Material.IRON_AXE).setUnbreakable(true).build();
        ItemStack blocks1 = new ItemBuilder(Material.WOOD, 32).build();
        ItemStack blocks2 = new ItemBuilder(Material.GLASS, 32).build();
        ItemStack gapple = new ItemBuilder(Material.GOLDEN_APPLE, 1).build();
        ItemStack carrot = new ItemBuilder(Material.GOLDEN_CARROT, 16).build();

        ItemStack arrows = new ItemBuilder(Material.ARROW, 12).build();

        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, bow);
        player.getInventory().setItem(2, axe);
        player.getInventory().setItem(3, blocks1);
        player.getInventory().setItem(4, blocks2);
        player.getInventory().setItem(7, gapple);
        player.getInventory().setItem(8, carrot);
        player.getInventory().setItem(35, arrows);

    }

    @Override
    public Material getIconMaterial() {
        return Material.STONE_SWORD;
    }

}