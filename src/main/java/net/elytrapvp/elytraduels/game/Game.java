package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.utils.EloUtils;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.Timer;
import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.arena.Arena;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.game.team.Team;
import net.elytrapvp.elytraduels.game.team.TeamManager;
import net.elytrapvp.elytraduels.scoreboards.LobbyScoreboard;
import net.elytrapvp.elytraduels.utils.LocationUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Represents a singular match.
 */
public class Game {
    private final ElytraDuels plugin;
    private final TeamManager teamManager;

    private int players;
    private GameState gameState;
    private final Kit kit;
    private final Arena arena;
    private final Timer timer;
    private final GameType gameType;

    private final Set<Player> spectators = new HashSet<>();
    private final HashMap<Location, Material> blocks = new HashMap<>();

    private final HashMap<Player, Integer> tripleShot = new HashMap<>();
    private final HashMap<Player, Integer> repulsor = new HashMap<>();
    private final HashMap<Player, Integer> doubleJump = new HashMap<>();
    private final List<Entity> entities = new ArrayList<>();

    /**
     * Create a Game object,
     * @param plugin ElytraDuels instance.
     * @param kit Kit to use.
     * @param arena Arena to use.
     * @param gameType GameType to use.
     */
    public Game(ElytraDuels plugin, Kit kit, Arena arena, GameType gameType) {
        this.plugin = plugin;
        this.kit = kit;
        this.arena = arena;
        this.gameType = gameType;

        teamManager = new TeamManager(plugin);

        gameState = GameState.WAITING;
        timer = new Timer(plugin);
        plugin.arenaManager().removeArena(arena);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Starts the game.
     */
    public void start() {
        players = getAlivePlayers().size();
        plugin.queueManager().addPlaying(kit, players);
        plugin.queueManager().addPlaying(players);

        int spawn = 0;
        for(Team team : teamManager.teams()) {
            if(spawn >= arena.spawns().size()) {
                spawn = 0;
            }

            for(Player p : team.players()) {
                p.teleport(arena.spawns().get(spawn));
                kit.apply(p, this);
                new GameScoreboard(p, this);
            }
            spawn++;
        }

        broadcast("&8&m+-----------------------***-----------------------+");
        for(Player player : getPlayers()) {
            ChatUtils.chat(player, "");
            ChatUtils.centeredChat(player, "&a&l" + kit.getName() + " Duel");
            ChatUtils.chat(player, "");

            if(getSpectators().contains(player)) {
                continue;
            }

            ChatUtils.centeredChat(player, "&aOpponents:");

            int i = 1;
            for(Team team : teamManager.teams()) {
                if(team.equals(getTeam(player))) {
                    continue;
                }

                if(i == 6) {
                    break;
                }

                for(Player opponent : team.alivePlayers()) {
                    String opponentName = opponent.getName();
                    ChatUtils.centeredChat(player, opponentName);
                    i++;
                }
            }
            ChatUtils.chat(player, "");
        }
        broadcast("&8&m+-----------------------***-----------------------+");

        countdown();
    }

    /**
     * Runs the game countdown.
     */
    private void countdown() {
        gameState = GameState.COUNTDOWN;
        BukkitRunnable countdown = new  BukkitRunnable() {
            int counter = 4;
            public void run() {
                counter--;

                if(gameState == GameState.END) {
                    cancel();
                }

                if(counter  != 0) {
                    broadcast("&aStarting in " + counter + "...");
                    for (Player p : getPlayers()) {
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
                    }
                }
                else {
                    for(Player p : getPlayers()) {
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 2);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> running(), 1);
                        cancel();
                    }
                }
            }
        };
        countdown.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Marks the game as running.
     */
    private void running() {
        if(gameState == GameState.RUNNING) {
           return;
        }

        gameState = GameState.RUNNING;
        timer.start();

        int spawn = 0;
        for(Team team : teamManager.teams()) {
            for(Player p : team.players()) {

                // Reset spawn point if not enough spawns
                if(spawn > arena.spawns().size() - 1) {
                    spawn = 0;
                }

                p.closeInventory();
                p.teleport(arena.spawns().get(spawn));
                new GameScoreboard(p, this);
                p.setFireTicks(0);
            }
            spawn++;
        }

        if(kit.getDoubleJumps() > 0) {
            getPlayers().forEach(player -> player.setAllowFlight(true));
        }

        kit.onGameStart(this);
    }

    /**
     * Ends the game.
     * @param winner Winning team.
     * @param loser Losing team.
     */
    private void end(Team winner, Team loser) {
        if(gameState == GameState.END) {
            return;
        }

        gameState = GameState.END;
        timer.stop();

        for(Team team : teamManager.teams()) {
            if(team == winner) {
                Title title = new Title(ChatUtils.translate("&aVictory!"), "", 20, 60, 20);
                team.players().forEach(player -> player.sendTitle(title));
            }
            else {
                Title title = new Title(ChatUtils.translate("&cYou lose!"));
                team.players().forEach(player -> player.sendTitle(title));
            }
        }

        if(gameType == GameType.RANKED) {
            Player w = winner.players().get(0);
            CustomPlayer wp = plugin.customPlayerManager().getPlayer(w);
            Player l = loser.players().get(0);
            CustomPlayer lp = plugin.customPlayerManager().getPlayer(l);

            int welo = wp.getElo(kit.getName().toLowerCase());
            int lelo = lp.getElo(kit.getName().toLowerCase());
            int[] newElo = EloUtils.eloRating(welo, lelo, 32, true);

            broadcast("&8&m+-----------------------***-----------------------+");
            broadcast(" ");
            broadcastCenter("&a&l" + kit.getName() + " Duel &7- &f&l" + timer.toString());
            broadcast(" ");
            broadcast("  &aWinner: &f" + w.getName() + " &a(+" + (newElo[0] - welo) + " | " + newElo[0] + ")");
            broadcast("  &cLoser: &f" + l.getName() + " &c(" + (newElo[1] - lelo) + " | " + newElo[1] + ")");
            broadcast(" ");
            broadcast("&8&m+-----------------------***-----------------------+");

            wp.setElo(kit.getName().toLowerCase(), newElo[0]);
            wp.addWin(kit.getName().toLowerCase());
            lp.setElo(kit.getName().toLowerCase(), newElo[1]);
            lp.addLoss(kit.getName().toLowerCase());

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("INSERT INTO duels_match_history (kit,map,winner,winnerElo,loser,loserElo,eloChange,length) VALUES (?,?,?,?,?,?,?,?)");
                    statement.setString(1, kit.getName());
                    statement.setString(2, arena.map().name());
                    statement.setString(3, w.getUniqueId().toString());
                    statement.setInt(4, newElo[0]);
                    statement.setString(5, l.getUniqueId().toString());
                    statement.setInt(6, newElo[1]);
                    statement.setInt(7, newElo[0] - welo);
                    statement.setInt(8, timer.toSeconds());
                    statement.executeUpdate();
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
        }
        else {
            broadcast("&8&m+-----------------------***-----------------------+");
            broadcast(" ");
            broadcastCenter("&a&l" + kit.getName() + " Duel &7- &f&l" + timer.toString());
            broadcast(" ");
            if(winner.players().size() > 1) {
                broadcastCenter("&aWinners:");
            }
            else {
                broadcastCenter("&aWinner:");
            }

            for(Player player : winner.players()) {
                if(teamManager.getTeam(player).deadPlayers().contains(player)) {
                    broadcastCenter("&f" + player.getName() + " &a(&c0%&a)");
                }
                else {
                    broadcastCenter("&f" + player.getName() + " &a(" + ChatUtils.getFormattedHealthPercent(player) + "&a)");
                }
            }
            broadcast(" ");
            broadcast("&8&m+-----------------------***-----------------------+");
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            for(Player player : getPlayers()) {
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setMaxHealth(20.0);
                player.setHealth(20.0);
                player.teleport(LocationUtils.getSpawn(plugin));
                player.spigot().setCollidesWithEntities(true);
                ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);

                ItemUtils.givePartyItems(plugin.partyManager(), player);
                new LobbyScoreboard(plugin, player);

                for(Player pl : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(pl);
                }

                for(PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
            }

            for(Team team : teamManager.teams()) {
                team.players().clear();
                team.alivePlayers().clear();
                team.deadPlayers().clear();
            }

            plugin.queueManager().removePlaying(kit, players);
            plugin.queueManager().removePlaying(players);

            spectators.clear();
            teamManager.teams().clear();
            plugin.arenaManager().addArena(arena);

            plugin.gameManager().destroyGame(this);
        }, 100);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            for(Entity entity : entities) {
                if(entity.isValid()) {
                    entity.remove();
                }
            }

            entities.clear();

            for(Location location : blocks.keySet()) {
                location.getWorld().getBlockAt(location).setType(blocks.get(location));

                if(!location.getChunk().isLoaded()) {
                    location.getChunk().load();
                    Bukkit.getScheduler().runTaskLater(plugin, () -> location.getChunk().unload(), 100);
                }
            }
        }, 100);
    }
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Add a block to the game cache.
     * Used for arena resetting.
     * @param location Location of the block.
     * @param material Material of the block.
     */
    public void addBlock(Location location, Material material) {
        blocks.put(location, material);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Add a player to the game.
     * @param player Player to add.
     */
    public void addPlayer(Player player) {
        Team team = teamManager.getTeam(player);

        if(team == null) {
            List<Player> members = new ArrayList<>();
            members.add(player);

            team = teamManager.createTeam(members);
        }

        doubleJump.put(player, kit.getDoubleJumps());
        repulsor.put(player, kit.getRepulsors());
        tripleShot.put(player, kit.getTripleShots());
    }

    /**
     * Add a player to the arena.
     * @param players Players to add.
     */
    public void addPlayers(List<Player> players) {
        Team team = teamManager.createTeam(players);

        for(Player player : players) {
            doubleJump.put(player, kit.getDoubleJumps());
            repulsor.put(player, kit.getRepulsors());
            tripleShot.put(player, kit.getTripleShots());
        }
    }

    /**
     * Add a spectator to the game.
     * @param player Spectator to add.
     */
    public void addSpectator(Player player) {
        spectators.add(player);

        // Doesn't teleport player if they were in the game before.
        if(getAlivePlayers().contains(player)) {
            player.teleport(arena.spawns().get(0));
        }

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);

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
     * Broadcast a message to the arena.
     * @param message Message to broadcast.
     */
    public void broadcast(String message) {
        for(Player player : getPlayers()) {
            ChatUtils.chat(player, message);
        }
    }

    /**
     * Broadcast a centered message to the arena.
     * @param message Message to broadcast.
     */
    public void broadcastCenter(String message) {
        for(Player player : getPlayers()) {
            ChatUtils.centeredChat(player, message);
        }
    }

    /**
     * Get all players in the arena.
     * @return All players in the arena.
     */
    public List<Player> getAlivePlayers() {
        List<Player> players = new ArrayList<>();

        for(Team team : teamManager.teams()) {
            players.addAll(team.alivePlayers());
        }

        return players;
    }

    /**
     * Get the arena being used in the game.
     * @return Arena being used.
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get all blocks that have been placed by players.
     * @return All blocks placed by players.
     */
    public Collection<Location> getBlocks() {
        return blocks.keySet();
    }

    /**
     * Get the amount of double jumps a player has left.
     * @param player Player to get double jumps of.
     * @return Amount of double jumps left.
     */
    public int getDoubleJumps(Player player) {
        return doubleJump.get(player);
    }

    /**
     * Get the current state of the Game.
     * @return State of the Game.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Get the current game type.
     * @return Game Type of the game.
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * Get the kit used in the game.
     * @return Kit being used.
     */
    public Kit getKit() {
        return kit;
    }

    /**
     * Get the opposing team.
     * @param team Team to get opposing team of.
     * @return Opposing team.
     */
    public Team getOpposingTeam(Team team) {
        if(team.equals(teamManager.teams().get(1))) {
            return teamManager.teams().get(0);
        }

        return teamManager.teams().get(1);
    }

    /**
     * Get all players in the game.
     * This includes spectators.
     * @return All players in the game.
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        for(Team team : teamManager.teams()) {
            players.addAll(team.alivePlayers());
        }
        players.addAll(getSpectators());

        return players;
    }

    /**
     * Get the amount of repulsors a player has left.
     * @param player Player to get repulsors of.
     * @return Amount of repulsors left.
     */
    public int getRepulsors(Player player) {
        return repulsor.get(player);
    }

    /**
     * Get all current spectators.
     * @return All current spectators.
     */
    public Set<Player> getSpectators() {
        return spectators;
    }

    /**
     * Get the team of a player.
     * @param player Player to get team of.
     * @return Team the player is in.
     */
    public Team getTeam(Player player) {
        for(Team team : teamManager.teams()) {
            if(team.players().contains(player)) {
                return team;
            }
        }

        return null;
    }

    /**
     * Get the game's team manager.
     * @return Team manager.
     */
    public TeamManager getTeamManager() {
        return teamManager;
    }

    /**
     * Get the game timer.
     * @return Game timer.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Get the amount of triple shots a player has left.
     * @param player Player to get triple shots of.
     * @return Amount of triple shots left.
     */
    public int getTripleShots(Player player) {
        return tripleShot.get(player);
    }

    /**
     * Runs when a played disconnects.
     * @param player Player who disconnected.
     */
    public void playerDisconnect(Player player) {
        if(spectators.contains(player)) {
            return;
        }

        broadcast("&a" + player.getName() + " disconnected.");
        teamManager.getTeam(player).killPlayer(player);
        player.getLocation().getWorld().strikeLightning(player.getLocation());

        for(Team team : teamManager.teams()) {
            if(team.alivePlayers().size() == 0) {
                teamManager.killTeam(team);

                if(teamManager.aliveTeams().size() == 1) {
                    Team winner = teamManager.aliveTeams().get(0);
                    end(winner, team);
                    break;
                }
            }
        }

        for(Player spectator : getSpectators()) {
            player.showPlayer(spectator);
        }
    }

    /**
     * Runs when a player is killed.
     * @param player Player who was killed.
     */
    public void playerKilled(Player player) {
        if(spectators.contains(player)) {
            return;
        }

        player.getLocation().getWorld().strikeLightning(player.getLocation());
        addSpectator(player);
        teamManager.getTeam(player).killPlayer(player);
        broadcast(getTeam(player).teamColor().chatColor()  + player.getName() + " &ahas died!");

        // Prevents stuff from breaking if the game is already over.
        if(gameState == GameState.END) {
            return;
        }

        for(Team team : teamManager.teams()) {
            if(team.alivePlayers().size() == 0) {
                teamManager.killTeam(team);

                if(teamManager.aliveTeams().size() == 1) {
                    Team winner = teamManager.aliveTeams().get(0);
                    end(winner, team);
                    break;
                }
            }
        }
    }

    /**
     * Runs when a player is killed.
     * @param player Player who was killed.
     * @param killer Player who killed the player.
     */
    public void playerKilled(Player player, Player killer) {
        if(spectators.contains(player)) {
            return;
        }

        player.getLocation().getWorld().strikeLightning(player.getLocation());
        addSpectator(player);
        teamManager.getTeam(player).killPlayer(player);
        broadcast(getTeam(player).teamColor().chatColor()  + player.getName() + " &awas killed by " + getTeam(killer).teamColor().chatColor() + killer.getName() + " &a(" + ChatUtils.getFormattedHealthPercent(killer) + "&a)");

        // Prevents stuff from breaking if the game is already over.
        if(gameState == GameState.END) {
            return;
        }

        for(Team team : teamManager.teams()) {
            if(team.alivePlayers().size() == 0) {
                teamManager.killTeam(team);

                if(teamManager.aliveTeams().size() == 1) {
                    Team winner = teamManager.aliveTeams().get(0);
                    end(winner, team);
                    break;
                }
            }
        }
    }

    /**
     * Remove a double jump from a player.
     * @param player Player to double jump from.
     */
    public void removeDoubleJump(Player player) {
        doubleJump.put(player, getDoubleJumps(player) - 1);
    }

    /**
     * Remove a repulsor from a player.
     * @param player Player to remove repulsor of.
     */
    public void removeRepulsor(Player player) {
        repulsor.put(player, getRepulsors(player) - 1);
    }

    /**
     * Remove a spectator from the game.
     * @param player Spectator to remove.
     */
    public void removeSpectator(Player player) {
        spectators.remove(player);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setMaxHealth(20.0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.teleport(LocationUtils.getSpawn(plugin));
        player.spigot().setCollidesWithEntities(true);

        // Clears arrows from the player. Requires craftbukkit.
        ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);

        new LobbyScoreboard(plugin, player);

        for(Player pl : Bukkit.getOnlinePlayers()) {
            pl.showPlayer(player);
        }
    }

    /**
     * Remove a triple shot from a player.
     * @param player Player to remove triple shot from.
     */
    public void removeTripleShot(Player player) {
        tripleShot.put(player, getTripleShots(player) - 1);
    }
}