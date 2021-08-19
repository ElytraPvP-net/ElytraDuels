package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.game.team.Team;
import net.elytrapvp.elytraduels.game.team.TeamManager;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a singular match.
 */
public class Game {
    private final GameManager gameManager;
    private final TeamManager teamManager = new TeamManager();

    private GameState gameState;

    private final Set<Player> spectators = new HashSet<>();

    // TODO: Create a Game object
    public Game(GameManager gameManager) {
        this.gameManager = gameManager;

        gameState = GameState.WAITING;
    }

    // TODO: Complete addSpectator() method.
    public void addSpectator(Player player) {

    }

    /**
     * Get the current state of the Game.
     * @return State of the Game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Get all players in the game.
     * This includes spectators.
     * @return All players in the game.
     */
    public Set<Player> getPlayers() {
        Set<Player> players = new HashSet<>();

        for(Team team : teamManager.getTeams()) {
            players.addAll(team.getPlayers());
        }
        players.addAll(getSpectators());

        return players;
    }

    /**
     * Get all current spectators.
     * @return All current spectators.
     */
    public Set<Player> getSpectators() {
        return spectators;
    }

    // TODO: Complete removeSpectator() method.
    public void removeSpectator(Player player) {

    }
}