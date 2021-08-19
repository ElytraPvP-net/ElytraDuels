package net.elytrapvp.elytraduels.utils.gui;

import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Reoresebts a simplified way to create
 * interactive GUIs.
 */
public abstract class CustomGUI {
    private static Map<UUID, CustomGUI> inventories = new HashMap<>();
    private static Map<UUID, UUID> openInventories = new HashMap<>();

    private Inventory inventory;
    private UUID uuid;
    private Map<Integer, ClickAction> actions;

    /**
     * Creates a new custom GUI
     * @param size Size of the GUI
     * @param name Name of the GUI
     */
    public CustomGUI(int size, String name) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, ChatUtils.translate(name));
        actions = new HashMap<>();

        inventories.put(uuid, this);
    }

    public static Map<UUID, CustomGUI> getInventories() {
        return inventories;
    }

    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public void delete() {
        inventories.remove(getUUID());
    }

    public Map<Integer, ClickAction> getActions() {
        return actions;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Deprecated
    public UUID getUuid() {
        return getUUID();
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setItem(int slot, ItemStack item, ClickAction action) {
        inventory.setItem(slot, item);

        if(actions != null) {
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack item) {
        setItem(slot, item, null);
    }

    public void open(Player p) {
        p.openInventory(inventory);
        openInventories.put(p.getUniqueId(), getUuid());
    }

    public void onClose(Player p) {}

    public interface ClickAction {
        void click(Player player, ClickType type);
    }
}