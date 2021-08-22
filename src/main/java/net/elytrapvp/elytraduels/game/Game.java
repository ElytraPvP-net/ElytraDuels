package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.arena.Arena;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.game.team.Team;
import net.elytrapvp.elytraduels.game.team.TeamManager;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Represents a singular match.
 */
public class Game {
    private final ElytraDuels plugin;
    private final TeamManager teamManager = new TeamManager();

    private GameState gameState;
    private final Kit kit;
    private final Arena arena;

    private final Set<Player> spectators = new HashSet<>();

    private final HashMap<Player, Integer> tripleShot = new HashMap<>();
    private final HashMap<Player, Integer> repulsor = new HashMap<>();
    private final HashMap<Player, Integer> doubleJump = new HashMap<>();

    /**
     * Create a Game object,
     * @param plugin ElytraDuels instance.
     * @param kit Kit to use.
     * @param arena Arena to use.
     * @param gameType GameType to use.
     */
    // TODO: Create a Game object
    public Game(ElytraDuels plugin, Kit kit, Arena arena, GameType gameType) {
        this.plugin = plugin;
        this.kit = kit;
        this.arena = arena;

        gameState = GameState.WAITING;
    }

    // ----------------------------------------------------------------------------------------------------------------

    //TODO: Finish countdown
    private void countdown() {
        gameState = GameState.COUNTDOWN;
    }

    // TODO: Finish running
    private void running() {
        gameState = GameState.RUNNING;
    }

    // TODO: Finish end
    private void end() {
        gameState = GameState.END;

        plugin.getGameManager().destroyGame(this);
    }

    // ----------------------------------------------------------------------------------------------------------------

    public void addPlayer(Player player) {
        Team team = teamManager.getTeam(player);

        if(team == null) {
            Set<Player> members = new HashSet<>();
            members.add(player);

            team = teamManager.createTeam(members);
        }

        doubleJump.put(player, kit.getDoubleJumps());
        repulsor.put(player, kit.getRepulsors());
        tripleShot.put(player, kit.getTripleShots());
    }

    // TODO: Complete addSpectator() method.
    public void addSpectator(Player player) {
        spectators.add(player);

        // Doesn't teleport player if they were in the game before.
        if(getAlivePlayers().contains(player)) {
            player.teleport(arena.getSpawns().get(0));
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setHealth(20.0);

        // Prevents player from interfering.
        player.spigot().setCollidesWithEntities(false);

        ItemStack leave = new ItemBuilder(Material.BED)
                .setDisplayName("&cLeave Match")
                .build();
        player.getInventory().setItem(8, leave);

        // Delayed to prevent TeleportFix from making visible again.
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Player pl : getPlayers()) {
                pl.hidePlayer(player);
            }
        }, 2);
    }

    /**
     * Get all players in the arena.
     * @return All players in the arena.
     */
    public List<Player> getAlivePlayers() {
        List<Player> players = new ArrayList<>();

        for(Team team : teamManager.getTeams()) {
            players.addAll(team.getAlivePlayers());
        }

        return players;
    }

    /**
     * Get the current state of the Game.
     * @return State of the Game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Get the kit used in the game.
     * @return Kit being used.
     */
    public Kit getKit() {
        return kit;
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

    public void start() {
        countdown();
    }

    public int getRepulsors(Player player) {
        return repulsor.get(player);
    }

    public int getTripleShots(Player player) {
        return tripleShot.get(player);
    }

    public int getDoubleJumps(Player player) {
        return doubleJump.get(player);
    }

    public void removeRepulsor(Player player) {
        repulsor.put(player, getRepulsors(player) - 1);
    }

    public void removeTripleShot(Player player) {
        tripleShot.put(player, getTripleShots(player) - 1);
    }

    public void removeDoubleJump(Player player) {
        doubleJump.put(player, getDoubleJumps(player) - 1);
    }
}