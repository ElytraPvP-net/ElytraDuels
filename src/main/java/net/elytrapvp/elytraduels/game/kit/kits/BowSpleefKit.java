package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BowSpleefKit extends Kit {

    public BowSpleefKit(ElytraDuels plugin) {
        super(plugin, "Bow Spleef");
        setIconMaterial(Material.TNT);
        setGameMode(GameMode.ADVENTURE);

        setArrowPickup(true);
        setTakeDamage(false);
        setVoidLevel(51);

        setDoubleJumps(10);
        setRepulsors(10);
        setTripleShots(10);

        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_FIRE, 1)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .setUnbreakable(true)
                .build();
        addItem(0, bow);

        ItemStack arrows = new ItemBuilder(Material.ARROW, 1).build();
        addItem(35, arrows);
    }
}