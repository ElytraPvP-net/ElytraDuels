package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BowSpleefKit extends Kit {

    public BowSpleefKit() {
        super("Bow Spleef");

        setArrowPickup(true);
        setTakeDamage(false);
        setVoidLevel(54);

        setDoubleJumps(10);
        setRepulsors(10);
        setTripleShots(10);
    }

    @Override
    public void apply(Player p) {
        p.getInventory().clear();
        p.setHealth(20);
        p.setGameMode(GameMode.ADVENTURE);
        ItemStack bow = new ItemBuilder(Material.BOW)
                .addEnchantment(Enchantment.ARROW_FIRE, 1)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .setUnbreakable(true)
                .build();
        p.getInventory().setItem(0, bow);

        ItemStack arrows = new ItemBuilder(Material.ARROW, 1).build();
        p.getInventory().setItem(35, arrows);
    }

    @Override
    public Material getIconMaterial() {
        return Material.TNT;
    }
}