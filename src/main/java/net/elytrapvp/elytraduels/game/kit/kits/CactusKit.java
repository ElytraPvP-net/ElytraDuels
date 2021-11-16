package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CactusKit extends Kit {
    public CactusKit() {
        super("Cactus");
        setIconMaterial(Material.CACTUS);
        setGameMode(GameMode.SURVIVAL);
        setMaxHealth(1);

        addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200000, 100, true));
        addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200000, 0, true));

        setNaturalRegen(false);
        setVoidLevel(50);

        ItemStack cactus = new ItemBuilder(Material.CACTUS, 16).build();
        ItemStack egg = new ItemStack(Material.EGG);

        addItem(0, cactus);
        addItem(1, egg);
    }
}