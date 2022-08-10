package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HorseKit extends Kit {

    public HorseKit(ElytraDuels plugin) {
        super(plugin, "Horse");
        setIconMaterial(Material.DIAMOND_BARDING);
        setExitVehicle(false);

        addItem(0, new ItemStack(Material.IRON_SWORD));
        addItem(3, new ItemStack(Material.BOW));
        addItem(5, new ItemStack(Material.GOLDEN_APPLE, 2));
        addItem(6, new ItemStack(Material.COOKED_BEEF, 64));

        addItem(34, new ItemStack(Material.ARROW, 64));
        addItem(35, new ItemStack(Material.ARROW, 64));

        addItem(39, new ItemStack(Material.IRON_HELMET));
        addItem(38, new ItemStack(Material.IRON_CHESTPLATE));
        addItem(37, new ItemStack(Material.IRON_LEGGINGS));
        addItem(36, new ItemStack(Material.IRON_BOOTS));
    }

    @Override
    public void onGameStart(Game game) {
        for(Player player : game.getAlivePlayers()) {
            Horse horse = (Horse) player.getWorld().spawnCreature(player.getLocation(), EntityType.HORSE); // Spawns the horse
            horse.setTamed(true); // Sets horse to tamed
            horse.setOwner(player); // Makes the horse the players
            horse.setVariant(Horse.Variant.HORSE);
            horse.setMaxHealth(40);
            horse.setHealth(40);
            horse.setAdult();
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1)); // Gives horse saddle
            horse.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING)); // Gives the horse armor
            horse.teleport(player);
            horse.setPassenger(player);
            game.addEntity(horse);
        }
    }
}