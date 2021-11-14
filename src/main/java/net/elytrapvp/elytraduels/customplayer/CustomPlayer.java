package net.elytrapvp.elytraduels.customplayer;

import net.elytrapvp.elytradb.ElytraDB;
import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Stores all duels data about a player.
 */
public class CustomPlayer {
    private final ElytraDuels plugin;
    private final UUID uuid;

    private final Map<String, Integer> wins = new HashMap<>();
    private final Map<String, Integer> losses = new HashMap<>();
    private final Map<String, Integer> winStreak = new HashMap<>();
    private final Map<String, Integer> bestWinStreak = new HashMap<>();
    private final Map<String, Integer> elo = new HashMap<>();

    /**
     * Creates the CustomPlayer object.
     * @param plugin Plugin instance.
     * @param uuid UUID of player.
     */
    public CustomPlayer(ElytraDuels plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;

        // Run everything async to prevent lag.
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            // Create a list of all kits to add.
            List<String> kits = new ArrayList<>();
            for(Kit kit : plugin.getKitManager().getRankedKits()) {
                kits.add(kit.getName().toLowerCase());
            }
            kits.add("global");

            try {
                // Loops through each kit to
                for(String kit : kits) {
                    PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("SELECT * from duels_statistics WHERE uuid = ? AND kit = ?");
                    statement.setString(1, uuid.toString());
                    statement.setString(2, kit);
                    ResultSet results = statement.executeQuery();

                    // Makes sure data exists before grabbing it.
                    if(results.next()) {
                        wins.put(kit, results.getInt(3));
                        losses.put(kit, results.getInt(4));
                        winStreak.put(kit, results.getInt(5));
                        bestWinStreak.put(kit, results.getInt(6));
                        elo.put(kit, results.getInt(7));
                    }
                    else {
                        // If it doesn't exist, create it.
                        PreparedStatement statement2 = ElytraDB.getDatabase().prepareStatement("INSERT INTO duels_statistics (uuid, kit) VALUES (?, ?)");
                        statement2.setString(1, uuid.toString());
                        statement2.setString(2, kit);
                        statement2.executeUpdate();

                        wins.put(kit, 0);
                        losses.put(kit, 0);
                        winStreak.put(kit, 0);
                        bestWinStreak.put(kit, 0);
                        elo.put(kit, 1000);
                    }
                }

            }
            catch (SQLException exception) {
                ChatUtils.chat(Bukkit.getPlayer(uuid), "&c&l(&7!&c&l) &cSomething went wrong loading your data! Please reconnect or your data could be lost.");
                exception.printStackTrace();
                for(Kit kit : plugin.getKitManager().getRankedKits()) {
                    elo.put(kit.getName().toLowerCase(), 1000);
                }
            }
        });
    }

    /**
     * Add a loss to the player.
     * @param kit Kit to add loss to.
     */
    public void addLoss(String kit) {
        setLosses(kit, getLosses(kit) + 1);
        setLosses("global", getLosses("global") + 1);

        setWinStreak(kit, 0);
        setWinStreak("global", 0);
    }

    /**
     * Add a win to the player.
     * @param kit Kit to add win to.
     */
    public void addWin(String kit) {
        setWins(kit, getWins(kit) + 1);
        setWins("global", getWins("global") + 1);

        setWinStreak(kit, getWinStreak("kit") + 1);
        setWinStreak("global", getWinStreak("global") + 1);

    }

    /**
     * Get the best win streak of the player.
     * @param kit Kit to get best win streak of.
     * @return The best win streak.
     */
    public int getBestWinStreak(String kit) {
        return bestWinStreak.get(kit);
    }

    /**
     * Get the ELO of the player.
     * @param kit Kit to get ELO of.
     * @return The ELO.
     */
    public int getElo(String kit) {
        return elo.get(kit);
    }

    /**
     * Get the loss count of the player.
     * @param kit Kit to get loss count of.
     * @return The loss count.
     */
    public int getLosses(String kit) {
        return losses.get(kit);
    }

    /**
     * Get the win count of the player.
     * @param kit Kit to get win count of.
     * @return The win count.
     */
    public int getWins(String kit) {
        return wins.get(kit);
    }

    /**
     * Get the player's win streak.
     * @param kit Kit the win streak is in.
     * @return The win streak.
     */
    public int getWinStreak(String kit) {
        return winStreak.get(kit);
    }

    /**
     * Set the best win streak of the player.
     * @param kit The kit the best win streak should be set in.
     * @param bestWinStreak What the best win streak should be set to.
     */
    public void setBestWinStreak(String kit, int bestWinStreak) {
        this.bestWinStreak.put(kit, bestWinStreak);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("UPDATE duels_statistics SET bestWinStreak = ? WHERE uuid = ? AND kit = ?");
                statement.setInt(1, bestWinStreak);
                statement.setString(2, uuid.toString());
                statement.setString(3, kit);
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Set the ELO of the player.
     * @param kit Kit to set ELO in.
     * @param elo What the ELO should be set to.
     */
    public void setElo(String kit, int elo) {
        this.elo.put(kit, elo);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("UPDATE duels_statistics SET elo = ? WHERE uuid = ? AND kit = ?");
                statement.setInt(1, elo);
                statement.setString(2, uuid.toString());
                statement.setString(3, kit);
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Set the losses of the player.
     * @param kit Kit to set the losses in.
     * @param losses What the losses should be set to.
     */
    public void setLosses(String kit, int losses) {
        this.losses.put(kit, losses);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("UPDATE duels_statistics SET losses = ? WHERE uuid = ? AND kit = ?");
                statement.setInt(1, losses);
                statement.setString(2, uuid.toString());
                statement.setString(3, kit);
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Set the wins of the player.
     * @param kit Kit to set the wins in.
     * @param wins What the wins should be set to.
     */
    public void setWins(String kit, int wins) {
        this.wins.put(kit, wins);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("UPDATE duels_statistics SET wins = ? WHERE uuid = ? AND kit = ?");
                statement.setInt(1, wins);
                statement.setString(2, uuid.toString());
                statement.setString(3, kit);
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Set the win streak of the player.
     * @param kit Kit to the set the win streak in.
     * @param winStreak What the win streak should be set to.
     */
    public void setWinStreak(String kit, int winStreak) {
        this.winStreak.put(kit, winStreak);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("UPDATE duels_statistics SET winStreak = ? WHERE uuid = ? AND kit = ?");
                statement.setInt(1, winStreak);
                statement.setString(2, uuid.toString());
                statement.setString(3, kit);
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        // If the current win streak is the best, update the best.
        if(getBestWinStreak(kit) < winStreak) {
            setBestWinStreak(kit, winStreak);
        }
    }
}