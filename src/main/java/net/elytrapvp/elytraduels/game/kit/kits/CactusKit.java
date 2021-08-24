package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CactusKit extends Kit {
    public CactusKit() {
        super("Cactus");

        setNaturalRegen(false);
    }


    @Override
    public void apply(Player player) {
        player.setHealth(1);
        player.getInventory().clear();

        ItemStack cactus = new ItemBuilder(Material.CACTUS, 16).build();
        ItemStack egg = new ItemStack(Material.EGG);

        player.getInventory().setItem(0, cactus);
        player.getInventory().setItem(1, egg);

        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200000, 100));
    }

    @Override
    public Material getIconMaterial() {
        return Material.CACTUS;
    }
}
