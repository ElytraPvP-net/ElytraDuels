package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SpleefKit extends Kit {

    public SpleefKit(ElytraDuels plugin) {
        super(plugin, "Spleef");

        setIconMaterial(Material.SNOW_BALL);
        setGameMode(GameMode.SURVIVAL);
        addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200000, 100, true));
        setVoidLevel(50);

        ItemStack shovel = new ItemBuilder(Material.DIAMOND_SPADE)
                .addEnchantment(Enchantment.DIG_SPEED, 5)
                .setUnbreakable(true)
                .build();
        addItem(0, shovel);
    }

    @Override
    public void onBlockBreak(Game game, BlockBreakEvent event) {
        if(event.getBlock().getType() != Material.SNOW_BLOCK) {
            return;
        }

        if(new Random().nextInt(4) == 0) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.SNOW_BALL));
        }

        game.addBlock(event.getBlock().getLocation(), Material.SNOW_BLOCK);
        event.getBlock().setType(Material.AIR);
        event.setCancelled(true);
    }
}