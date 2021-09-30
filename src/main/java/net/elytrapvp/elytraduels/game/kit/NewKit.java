package net.elytrapvp.elytraduels.game.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class NewKit {
    // Kit metadata
    private final String name;

    // Maps
    private final Map<Integer, ItemStack> items = new HashMap<>();

    // Settings
    private double startingHealth = 20.0;
    private double maxHealth = 20.0;
    private int startingHunger = 20;
    private float startingSaturation = 10;

    /**
     * Create a kit.
     * @param name Name of the kit.
     */
    public NewKit(String name) {
        this.name = name;
    }

    /**
     * Add an item to the kit.
     * @param slot Slot item is in.
     * @param item Item to add.
     */
    public void addItem(int slot, ItemStack item) {
        items.put(slot, item);
    }

    /**
     * Apply a kit to a player.
     * @param player Player to apply kit to.
     */
    public void apply(Player player) {
        // Clear inventory.
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        // Clear potion effects.
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        // Give items
        for(int i : items.keySet()) {
            player.getInventory().setItem(i, items.get(i));
        }

        // TODO: Set Health/Max Health, Hunger, Saturation, Potion Effects
        player.setMaxHealth(maxHealth);
        player.setHealth(startingHealth);
        player.setFoodLevel(startingHunger);
        player.setSaturation(startingSaturation);
    }

    /**
     * Get the max health.
     * @return Max health.
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the name of the kit.
     * @return Name of the kit.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the starting health.
     * @return Starting health.
     */
    public double getStartingHealth() {
        return startingHealth;
    }

    /**
     * Get the starting hunger.
     * @return Starting hunger.
     */
    public int getStartingHunger() {
        return startingHunger;
    }

    /**
     * Get the starting saturation.
     * @return Starting saturation.
     */
    public float getStartingSaturation() {
        return startingSaturation;
    }
}
