package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages all active games.
 */
public class GameManager {
    private ElytraDuels plugin;
    private final Set<Game> activeGames = new HashSet<>();

    public GameManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    // TODO: Complete createGame() method.
    public void createGame() {
        // Finds random available arena for the kit.
    }

    /**
     * Destroys an existing game object.
     * @param game Game to destroy.
     */
    public void destroyGame(Game game) {
        getActiveGames().remove(game);
    }

    /**
     * Get all currently active games.
     * @return All active games.
     */
    public Set<Game> getActiveGames() {
        return activeGames;
    }

    /**
     * Get the gane a player is currently in.
     * Returns null if no game found.
     * @param player Player to get game of.
     * @return Game player is currently in.
     */
    public Game getGame(Player player) {
        // TODO: Loop through all games to find player.
        return null;
    }
}