package net.elytrapvp.elytraduels;

import net.elytrapvp.elytraduels.commands.AbstractCommand;
import net.elytrapvp.elytraduels.customplayer.CustomPlayerManager;
import net.elytrapvp.elytraduels.game.GameManager;
import net.elytrapvp.elytraduels.game.arena.ArenaManager;
import net.elytrapvp.elytraduels.game.kit.KitManager;
import net.elytrapvp.elytraduels.game.queue.QueueManager;
import net.elytrapvp.elytraduels.listeners.*;
import net.elytrapvp.elytraduels.party.PartyManager;
import net.elytrapvp.elytraduels.runnables.AFKTimer;
import net.elytrapvp.elytraduels.game.LeaderboardManager;
import net.elytrapvp.elytraduels.utils.gui.GUIListeners;
import net.elytrapvp.elytraduels.utils.scoreboard.ScoreboardUpdate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraDuels extends JavaPlugin {
    private ArenaManager arenaManager;
    private CustomPlayerManager customPlayerManager;
    private GameManager gameManager;
    private KitManager kitManager;
    private PartyManager partyManager;
    private QueueManager queueManager;
    private SettingsManager settingsManager;
    private LeaderboardManager leaderboardManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        settingsManager = new SettingsManager(this);
        kitManager = new KitManager();
        partyManager = new PartyManager(this);
        arenaManager = new ArenaManager(this);
        gameManager = new GameManager(this);
        queueManager = new QueueManager(this);
        customPlayerManager = new CustomPlayerManager(this);
        leaderboardManager = new LeaderboardManager(this);

        AbstractCommand.registerCommands(this);

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockFromToListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityRegainhealthListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityShootBowListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBucketEmptyListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerItemConsumeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToggleFlightListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToggleSneakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchListener(this), this);
        Bukkit.getPluginManager().registerEvents(new TeleportFix(this), this);

        new ScoreboardUpdate().runTaskTimer(this, 20L, 20L);
        new AFKTimer(this).runTaskTimer(this, 5 * 20, 5 * 20);

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public CustomPlayerManager getCustomPlayerManager() {
        return customPlayerManager;
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

    public LeaderboardManager getLeaderboardManager() {
        return leaderboardManager;
    }
}