package net.elytrapvp.elytraduels.game.kit;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewKit {
    // Kit metadata
    private final String name;

    // Maps
    private final Map<Integer, ItemStack> items = new HashMap<>();
    private final List<PotionEffect> potionEffects = new ArrayList<>();

    // Settings
    private GameMode gameMode;
    private double maxHealth = 20.0;
    private double rodMultiplier = 1.5;
    private double startingHealth = 20.0;
    private int startingHunger = 20;
    private float startingSaturation = 10;
    private int voidLevel = 51;

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
     * Add a potion effect to the kit.
     * @param effect Potion effect to add.
     */
    public void addPotionEffect(PotionEffect effect) {
        potionEffects.add(effect);
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

        // Set game mode/health/hunger/saturation.
        player.setGameMode(gameMode);
        player.setMaxHealth(maxHealth);
        player.setHealth(startingHealth);
        player.setFoodLevel(startingHunger);
        player.setSaturation(startingSaturation);

        // Apply potion effects to the kit.
        for(PotionEffect effect : potionEffects) {
            player.addPotionEffect(effect);
        }
    }

    /**
     * Get the kit's game mode.
     * @return Game mode of the kit.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Get the items in the kit.
     * @return Items in the kit.
     */
    public Map<Integer, ItemStack> getItems() {
        return items;
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
     * Get the rod multiplier of the kit.
     * @return Rod multiplier of the kit.
     */
    public double getRodMultiplier() {
        return rodMultiplier;
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

    /**
     * Get the void level of the kit.
     * @return Void level of the kit.
     */
    public int getVoidLevel() {
        return voidLevel;
    }

    /**
     * Set the kit's game mode.
     * @param gameMode Game mode of the kit.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Set the kit's maximum health.
     * @param maxHealth Maximum health of the kit.
     */
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Set the rod multiplier of the kit.
     * @param rodMultiplier The rod multiplier of the kit.
     */
    public void setRodMultiplier(double rodMultiplier) {
        this.rodMultiplier = rodMultiplier;
    }

    /**
     * Set the kit's starting health.
     * @param startingHealth The starting health of the kit.
     */
    public void setStartingHealth(double startingHealth) {
        this.startingHealth = startingHealth;
    }

    /**
     * Set the kit's starting hunger.
     * @param startingHunger Starting hunger of the kit.
     */
    public void setStartingHunger(int startingHunger) {
        this.startingHunger = startingHunger;
    }

    /**
     * Set the kit's starting saturation.
     * @param startingSaturation Set the starting saturation of the kit.
     */
    public void setStartingSaturation(float startingSaturation) {
        this.startingSaturation = startingSaturation;
    }

    /**
     * Set the void level of the kit.
     * @param voidLevel Void level of the kit.
     */
    public void setVoidLevel(int voidLevel) {
        this.voidLevel = voidLevel;
    }
}