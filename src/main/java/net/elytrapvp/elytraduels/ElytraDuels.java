package net.elytrapvp.elytraduels;

import net.elytrapvp.elytraduels.game.GameManager;
import net.elytrapvp.elytraduels.game.arena.ArenaManager;
import net.elytrapvp.elytraduels.game.kit.KitManager;
import net.elytrapvp.elytraduels.game.queue.QueueManager;
import net.elytrapvp.elytraduels.party.PartyManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraDuels extends JavaPlugin {
    private ArenaManager arenaManager;
    private GameManager gameManager;
    private KitManager kitManager;
    private PartyManager partyManager;
    private QueueManager queueManager;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        settingsManager = new SettingsManager(this);
        kitManager = new KitManager(this);
        partyManager = new PartyManager();
        arenaManager = new ArenaManager(this);
        gameManager = new GameManager(this);
        queueManager = new QueueManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public GameManager getGameManager() {
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