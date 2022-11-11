package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BoxingKit extends Kit {

    public BoxingKit(ElytraDuels plugin) {
        super(plugin, "Boxing");
        setIconMaterial(Material.SKULL_ITEM);
        setGameMode(GameMode.ADVENTURE);
        setBoxingDamage(true);
        setNaturalRegen(false);

        addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD)
                .setUnbreakable(true)
                .build();
        addItem(0, sword);

        ItemStack fish = new ItemBuilder(Material.RAW_FISH)
                .build();
        addItem(1, fish);
    }

}
