package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all active games.
 */
public class GameManager {
    private final ElytraDuels plugin;
    private final List<Game> activeGames = new ArrayList<>();

    public GameManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a new game with a specific kit.
     * @param kit Kit to create game with.
     * @param gameType GameType to use.
     * @return Game that was created.
     */
    public Game createGame(Kit kit, GameType gameType) {
        Game game = new Game(plugin, kit, plugin.getArenaManager().getOpenArena(kit), gameType);
        getActiveGames().add(game);
        return game;
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
    @Deprecated
    public List<Game> getActiveGames() {
        return activeGames;
    }

    /**
     * Get all currently active games.
     * @return All active games.
     */
    public List<Game> activeGames() {
        return activeGames;
    }

    /**
     * Get the gane a player is currently in.
     * Returns null if no game found.
     * @param player Player to get game of.
     * @return Game player is currently in.
     */
    public Game getGame(Player player) {
        for(Game game : getActiveGames()) {
            if(game.getPlayers().contains(player)) {
                return game;
            }
        }
        return null;
    }
}