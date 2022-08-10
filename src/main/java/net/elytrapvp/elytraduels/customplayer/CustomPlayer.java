package net.elytrapvp.elytraduels.customplayer;

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
    private final Map<String, Map<Integer, Integer>> kitEditor = new HashMap<>();
    private final Map<String, Integer> elo = new HashMap<>();

    private String title = "";
    private String teamColor = "NONE";

    // Settings
    private boolean showScoreboard;
    private boolean partyInvites;
    private boolean duelRequests;

    /**
     * Creates the CustomPlayer object.
     * @param plugin Plugin instance.
     * @param uuid UUID of player.
     */
    public CustomPlayer(ElytraDuels plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;

        wins.put("global", 0);
        losses.put("global", 0);
        winStreak.put("global", 0);
        bestWinStreak.put("global", 0);
        elo.put("global", 0);

        // Run everything async to prevent lag.
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            // Create a list of all kits to add.
            List<String> kits = new ArrayList<>();
            for(Kit kit : plugin.kitManager().getRankedKits()) {
                kits.add(kit.getName().toLowerCase());
            }
            kits.add("global");

            try {
                // Loops through each kit to
                for(String kit : kits) {
                    PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * from duels_statistics WHERE uuid = ? AND kit = ?");
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
                        PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("INSERT INTO duels_statistics (uuid, kit) VALUES (?, ?)");
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

                for(Kit kit : plugin.kitManager().kits()) {
                    kitEditor.put(kit.getName() ,new HashMap<>());
                }

                for(Kit kit : plugin.kitManager().disabledKits()) {
                    kitEditor.put(kit.getName() ,new HashMap<>());
                }

                PreparedStatement statement3 = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_kit_editor WHERE uuid = ?");
                statement3.setString(1, uuid.toString());
                ResultSet results3 = statement3.executeQuery();

                while(results3.next()) {
                    kitEditor.get(results3.getString(2)).put(results3.getInt(3), results3.getInt(4));
                }

                PreparedStatement statement4 = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_settings WHERE uuid = ?");
                statement4.setString(1, uuid.toString());
                ResultSet results4 = statement4.executeQuery();
                if(results4.next()) {
                    showScoreboard = results4.getBoolean(2);
                    partyInvites = results4.getBoolean(3);
                    duelRequests = results4.getBoolean(4);
                    teamColor = results4.getString(5);
                }
                else {
                    PreparedStatement statement5 = plugin.mySQL().getConnection().prepareStatement("INSERT INTO duels_settings (uuid) VALUES (?)");
                    statement5.setString(1, uuid.toString());
                    statement5.executeUpdate();

                    showScoreboard = true;
                    partyInvites = true;
                    duelRequests = true;
                }

                PreparedStatement statement5 = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM tournament_settings WHERE uuid = ?");
                statement5.setString(1, uuid.toString());
                ResultSet results5 = statement4.executeQuery();
                if(results5.next()) {
                    if(results5.getString(3) == null) {
                        setTitle("");
                    }
                    else {
                        title = results5.getString(3);
                    }
                }
            }
            catch (SQLException exception) {
                ChatUtils.chat(Bukkit.getPlayer(uuid), "&cError &8» &cSomething went wrong loading your data! Please reconnect or your data could be lost.");
                exception.printStackTrace();

                for(Kit kit : plugin.kitManager().getRankedKits()) {
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

        setWinStreak(kit, getWinStreak(kit) + 1);
        setWinStreak("global", getWinStreak("global") + 1);

    }

    /**
     * Clear the kit editor of a kit.
     * @param kit Kit to clear.
     */
    private void cleanKitEditor(String kit) {
        try {
            PreparedStatement statement3 = plugin.mySQL().getConnection().prepareStatement("DELETE FROM duels_kit_editor WHERE uuid = ? AND kit = ?");
            statement3.setString(1, uuid.toString());
            statement3.setString(2, kit);
            statement3.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
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
     * Get if duel requests should be sent.
     * @return if duel requests should be sent.
     */
    public boolean getDuelRequests() {
        return duelRequests;
    }

    /**
     * Get the ELO of the player.
     * @param kit Kit to get ELO of.
     * @return The ELO.
     */
    public int getElo(String kit) {
        return elo.get(kit);
    }

    public String getTeamColor() {
        return teamColor;
    }

    /**
     * Get the player's current title.
     * @return Current title.
     */
    public String getTitle() {
        return title;
    }

    public Map<Integer, Integer> getKitEditor(String kit) {
        return kitEditor.get(kit);
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
     * Get if party invites should be sent.
     * @return if party invites should be sent.
     */
    public boolean getPartyInvites() {
        return partyInvites;
    }

    /**
     * Get if the scoreboard should be shown.
     * @return if the scoreboard should be shown.
     */
    public boolean getShowScoreboard() {
        return showScoreboard;
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
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET bestWinStreak = ? WHERE uuid = ? AND kit = ?");
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
     * Set if duels requests should be sent.
     * @param duelRequests Whether duel requests should be sent.
     */
    public void setDuelRequests(boolean duelRequests) {
        this.duelRequests = duelRequests;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_settings SET duelRequests = ? WHERE uuid = ?");
                statement.setBoolean(1, duelRequests);
                statement.setString(2, uuid.toString());
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
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET elo = ? WHERE uuid = ? AND kit = ?");
                statement.setInt(1, elo);
                statement.setString(2, uuid.toString());
                statement.setString(3, kit);
                statement.executeUpdate();

                if(kit.equals("global")) {
                    return;
                }

                int globalEloChange = 0;
                for(Kit rankedKit : plugin.kitManager().getRankedKits()) {
                    globalEloChange += (getElo(rankedKit.getName().toLowerCase()) - 1000);
                }

                setElo("global", (1000 + (globalEloChange / 3)));
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
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET losses = ? WHERE uuid = ? AND kit = ?");
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
     * Set if party invites can be sent.
     * @param partyInvites Whether party invites can be sent.
     */
    public void setPartyInvites(boolean partyInvites) {
        this.partyInvites = partyInvites;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_settings SET partyInvites = ? WHERE uuid = ?");
                statement.setBoolean(1, partyInvites);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Set if the scoreboard should be shown.
     * @param showScoreboard Whether the scoreboard should be shown.
     */
    public void setShowScoreboard(boolean showScoreboard) {
        this.showScoreboard = showScoreboard;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_settings SET showScoreboard = ? WHERE uuid = ?");
                statement.setBoolean(1, showScoreboard);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_settings SET teamColor = ? WHERE uuid = ?");
                statement.setString(1, teamColor);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Set the player's current title.
     * @param title New title.
     */
    public void setTitle(String title) {
        this.title = title;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE tournament_settings SET title = ? WHERE uuid = ?");
                statement.setString(1, title);
                statement.setString(2, uuid.toString());
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
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET wins = ? WHERE uuid = ? AND kit = ?");
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
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET winStreak = ? WHERE uuid = ? AND kit = ?");
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

    /**
     * Update the kit editor.
     * @param kit Kit to update.
     */
    public void updateKitEditor(String kit) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            cleanKitEditor(kit);

            try {
                Map<Integer, Integer> map = getKitEditor(kit);

                for(int item : map.keySet()) {
                    int slot = map.get(item);

                    PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("INSERT INTO duels_kit_editor (uuid,kit,item,slot) VALUES (?,?,?,?)");
                    statement.setString(1, uuid.toString());
                    statement.setString(2, kit);
                    statement.setInt(3, item);
                    statement.setInt(4, slot);
                    statement.executeUpdate();
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
}