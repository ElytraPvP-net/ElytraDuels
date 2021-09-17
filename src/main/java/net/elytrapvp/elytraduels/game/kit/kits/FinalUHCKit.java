package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import net.elytrapvp.elytraduels.utils.item.SkullBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FinalUHCKit extends Kit {

    public FinalUHCKit() {
        super("Final UHC");
        setNaturalRegen(false);
    }

    @Override
    public void apply(Player player) {
        player.getInventory().clear();
        player.setHealth(20);
        player.setGameMode(GameMode.SURVIVAL);

        ItemStack helmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .build();
        helmet.setDurability((short) 121);

        ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3)
                .build();
        chestplate.setDurability((short) 176);

        ItemStack leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .build();
        leggings.setDurability((short) 165);

        ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .build();
        boots.setDurability((short) 143);

        ItemStack spareHelmet = new ItemBuilder(Material.DIAMOND_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .build();
        spareHelmet.setDurability((short) 121);

        ItemStack spareChestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 2)
                .build();
        spareChestplate.setDurability((short) 176);

        ItemStack spareLeggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .build();
        spareLeggings.setDurability((short) 165);

        ItemStack spareBoots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .build();
        spareBoots.setDurability((short) 143);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 4)
                .setUnbreakable(true).build();
        ItemStack fishingRod = new ItemBuilder(Material.FISHING_ROD)
                .build();

        ItemStack axe = new ItemBuilder(Material.DIAMOND_AXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .setUnbreakable(true)
                .build();

        ItemStack pickaxe = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .setUnbreakable(true)
                .build();

        ItemStack gapple = new ItemBuilder(Material.GOLDEN_APPLE, 24).build();
        ItemStack ghead = new SkullBuilder("dedcd92b0e210cfe98892c4334be462b3b9e725ddbd009c2783fcf88f0ffdc53")
                .setDisplayName("&fGolden Head")
                .build();
        ghead.setAmount(4);

        ItemStack lava = new ItemBuilder(Material.LAVA_BUCKET).build();
        ItemStack water = new ItemBuilder(Material.WATER_BUCKET).build();

        ItemStack planks = new ItemBuilder(Material.WOOD, 64).build();
        ItemStack cobblestone = new ItemBuilder(Material.COBBLESTONE, 64).build();

        ItemStack fns = new ItemStack(Material.FLINT_AND_STEEL);
        fns.setDurability((short) 49);

        ItemStack steak = new ItemBuilder(Material.COOKED_BEEF, 64).build();

        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, fishingRod);
        player.getInventory().setItem(2, lava);
        player.getInventory().setItem(3, pickaxe);
        player.getInventory().setItem(4, cobblestone);
        player.getInventory().setItem(5, gapple);
        player.getInventory().setItem(6, ghead);
        player.getInventory().setItem(7, fns);
        player.getInventory().setItem(8, water);

        player.getInventory().setItem(9, spareHelmet);
        player.getInventory().setItem(10, spareChestplate);
        player.getInventory().setItem(11, spareLeggings);
        player.getInventory().setItem(12, spareBoots);
        player.getInventory().setItem(13, planks);
        player.getInventory().setItem(20, lava);
        player.getInventory().setItem(22, planks);
        player.getInventory().setItem(26, water);
        player.getInventory().setItem(29, lava);
        player.getInventory().setItem(30, axe);
        player.getInventory().setItem(31, cobblestone);
        player.getInventory().setItem(33, steak);
        player.getInventory().setItem(35, water);
    }

    @Override
    public Material getIconMaterial() {
        return Material.DIAMOND_HELMET;
    }

}