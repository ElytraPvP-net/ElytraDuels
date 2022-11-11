package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BowFightKit extends Kit {
    private final ElytraDuels plugin;

    public BowFightKit(ElytraDuels plugin) {
        super(plugin, "Bow Fight");
        this.plugin = plugin;
        setIconMaterial(Material.ENCHANTED_BOOK);
        setGameMode(GameMode.SURVIVAL);

        setNaturalRegen(false);
        setDoDamage(false);

        ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setUnbreakable(true)
                .build();
        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setUnbreakable(true)
                .build();
        ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setUnbreakable(true)
                .build();
        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setUnbreakable(true)
                .build();

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
                .setUnbreakable(true)
                .build();
        ItemStack arrows = new ItemBuilder(Material.ARROW, 1).build();

        ItemStack wool = new ItemBuilder(Material.WOOL, 32).build();

        addItem(39, helmet);
        addItem(38, chestplate);
        addItem(37, leggings);
        addItem(36, boots);

        addItem(0, bow);
        addItem(35, arrows);
        addItem(1, wool);
    }

    @Override
    public void onBlockPlace(Game game, BlockPlaceEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> event.getBlock().setType(Material.AIR), 100);
    }
}