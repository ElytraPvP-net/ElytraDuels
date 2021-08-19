package net.elytrapvp.elytraduels;

import net.elytrapvp.elytraduels.game.GameManager;
import net.elytrapvp.elytraduels.game.kit.KitManager;
import net.elytrapvp.elytraduels.game.queue.QueueManager;
import net.elytrapvp.elytraduels.party.PartyManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraDuels extends JavaPlugin {
    private GameManager gameManager;
    private KitManager kitManager;
    private PartyManager partyManager;
    private QueueManager queueManager;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        settingsManager = new SettingsManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GameManager gameManager() {
        return gameManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}