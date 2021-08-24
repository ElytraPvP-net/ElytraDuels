package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildUHCKit extends Kit {

    public BuildUHCKit() {
        super("Build UHC");
        setNaturalRegen(false);
    }

    @Override
    public void apply(Player player) {
        player.getInventory().clear();
        player.setHealth(20);
        player.setGameMode(GameMode.SURVIVAL);

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2)
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

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 3)
                .setUnbreakable(true).build();
        ItemStack fishingRod = new ItemBuilder(Material.FISHING_ROD)
                .setUnbreakable(true)
                .build();
        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 3)
                .setUnbreakable(true).build();
        ItemStack axe = new ItemBuilder(Material.DIAMOND_AXE).setUnbreakable(true).build();

        ItemStack gapple = new ItemBuilder(Material.GOLDEN_APPLE, 6).build();
        ItemStack ghead = new SkullBuilder("dedcd92b0e210cfe98892c4334be462b3b9e725ddbd009c2783fcf88f0ffdc53")
                .setDisplayName("&fGolden Head")
                .build();
        ghead.setAmount(3);

        ItemStack lava = new ItemBuilder(Material.LAVA_BUCKET).build();
        ItemStack water = new ItemBuilder(Material.WATER_BUCKET).build();

        ItemStack blocks = new ItemBuilder(Material.WOOD, 64).build();

        ItemStack arrows = new ItemBuilder(Material.ARROW, 24).build();

        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, fishingRod);
        player.getInventory().setItem(2, bow);
        player.getInventory().setItem(3, axe);
        player.getInventory().setItem(4, gapple);
        player.getInventory().setItem(5, ghead);
        player.getInventory().setItem(6, lava);
        player.getInventory().setItem(7, water);
        player.getInventory().setItem(8, blocks);
        player.getInventory().setItem(35, arrows);
        player.getInventory().setItem(34, lava);
        player.getInventory().setItem(33, water);
        player.getInventory().setItem(32, blocks);
    }

    @Override
    public Material getIconMaterial() {
        return Material.LAVA_BUCKET;
    }

}