package net.elytrapvp.elytraduels.game.queue;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.Timer;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the queue for each kit.
 */
public class QueueManager {
    private final ElytraDuels plugin;
    private final Map<Player, Kit> unrankedQueue = new HashMap<>();
    private final Map<Player, Kit> rankedQueue = new HashMap<>();
    private final Map<Player, Timer> timers = new HashMap<>();
    private final Map<Kit, Integer> playingKit = new HashMap<>();

    private int playing;

    public QueueManager(ElytraDuels plugin) {
        this.plugin = plugin;
        playing = 0;

        for(Kit kit : plugin.getKitManager().getKits()) {
            playingKit.put(kit, 0);
        }

        for(Kit kit : plugin.getKitManager().getDisabledKits()) {
            playingKit.put(kit, 0);
        }
    }

    /**
     * Add a player to the queue.
     * @param player Player ot add to the queue.
     * @param kit Kit to add them to.
     * @param gameType GameType to add them to.
     */
    public void addPlayer(Player player, Kit kit, GameType gameType) {
        if(unrankedQueue.containsKey(player) || rankedQueue.containsKey(player)) {
            ChatUtils.chat(player, "&cError &8» &cYou are already in queue!");
            return;
        }

        Player other = getPlayer(kit, gameType);

        // If no one else is queueing that kit and GameType.
        if(other == null) {
            getQueue(gameType).put(player, kit);

            player.getInventory().clear();
            ItemStack leave = new ItemBuilder(Material.REDSTONE)
                    .setDisplayName("&cLeave Queue")
                    .build();
            player.getInventory().setItem(8, leave);

            Timer timer = new Timer(plugin);
            timer.start();
            timers.put(player, timer);
            new QueueScoreboard(plugin, player, kit, timer, gameType);
        }
        // If someone else is queueing that kit and GameType.
        else {
            removePlayer(other);
            Game game = plugin.getGameManager().createGame(kit, gameType);
            game.addPlayer(player);
            game.addPlayer(other);
            game.start();
        }
    }

    /**
     * Add to the playing counter.
     * @param playing Amount to add.
     */
    public void addPlaying(int playing) {
        this.playing += playing;
    }

    public void addPlaying(Kit kit, int playing) {
        this.playingKit.put(kit, this.playingKit.get(kit) + playing);
    }

    /**
     * Get a player in a specific queue.
     * @param kit Kit of the queue.
     * @param gameType GameType of the queue
     * @return Player in it. Null if none.
     */
    public Player getPlayer(Kit kit, GameType gameType) {
        Map<Player, Kit> queue = getQueue(gameType);

        for(Player player : queue.keySet()) {
            if(queue.get(player).equals(kit)) {
                return player;
            }
        }

        return null;
    }

    /**
     * get the number of players currently playing.
     * @return Amount of players playing.
     */
    public int getPlaying() {
        return playing;
    }

    public int getPlaying(Kit kit) {
        return playingKit.get(kit);
    }

    /**
     * Get the queue HashMap of a specific GameType
     * @param gameType GameType to get queue of.
     * @return Queue
     */
    public Map<Player, Kit> getQueue(GameType gameType) {
        if(gameType == GameType.UNRANKED) {
            return unrankedQueue;
        }

        return rankedQueue;
    }

    /**
     * Get the amount of people queuing a specific kit.
     * @param kit Kit they could be queueing.
     * @param gameType GameType they could be queueing.
     * @return Amount of people queueing.
     */
    public int getQueueing(Kit kit, GameType gameType) {
        if(getQueue(gameType).containsValue(kit)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Get the amount of players currently queueing.
     * @return Number of players queueing.
     */
    public int getQueueing() {
        return unrankedQueue.size() + rankedQueue.size();
    }

    /**
     * Remove a player from the queue.
     * @param player Player to remove.
     */
    public void removePlayer(Player player) {
        unrankedQueue.remove(player);
        rankedQueue.remove(player);

        Timer timer = timers.get(player);

        if(timer != null) {
            timer.stop();
            timers.remove(player);
        }
    }

    /**
     * Remove from the playing count.
     * @param playing Amount to remove.
     */
    public void removePlaying(int playing) {
        this.playing -= playing;
    }

    public void removePlaying(Kit kit, int playing) {
        this.playingKit.put(kit, this.playingKit.get(kit) - playing);
    }
}