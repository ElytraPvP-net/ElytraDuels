package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetELOCMD extends AbstractCommand {
    private final ElytraDuels plugin;

    public ResetELOCMD(ElytraDuels plugin) {
        super("resetelo", "duels.resetelo", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            ChatUtils.chat(sender, "&a&lUsage &8» &c/resetelo [player]");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String target;

            try {
                // -------------------------------------- Get player to reset
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM player_info WHERE username = ?");
                statement.setString(1, args[0]);
                ResultSet results = statement.executeQuery();

                if(results.next()) {
                    target = results.getString(1);
                }
                else {
                    ChatUtils.chat(sender, "&cError &8» &cThat player has not played.");
                    return;
                }

                // -------------------------------------- Loop through all the matches they won.
                PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_match_history WHERE winner = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement2.setString(1, target);
                ResultSet results2 = statement2.executeQuery();

                while(results2.next()) {
                    String victim = results2.getString(5);
                    int eloChange = results2.getInt(7);
                    String kit = results.getString(1);

                    // Updates ELO
                    updateElo(target, kit, -eloChange);
                    updateElo(victim, kit, eloChange);

                    // Updates losses
                    removeLoss(victim, kit);
                    removeLoss(victim, "global");

                    // Updates wins
                    removeWin(target, kit);
                    removeWin(target, "global");

                    notifyVictim(victim);

                    // Updates win streak.
                    //resetWinStreak(target, kit);
                    //resetWinStreak(target, "global");

                    results2.deleteRow();
                }

                // -------------------------------------- Loop through all the matches they lost.
                PreparedStatement statement3 = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_match_history WHERE loser = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement3.setString(1, target);
                ResultSet results3 = statement3.executeQuery();

                while(results3.next()) {
                    String victim = results3.getString(3);
                    int eloChange = results3.getInt(7);
                    String kit = results.getString(1);

                    // Notifies the victim
                    notifyVictim(victim);

                    // Updates ELO
                    updateElo(target, kit, eloChange);
                    updateElo(victim, kit, -eloChange);

                    // Updates losses
                    removeLoss(target, kit);
                    removeLoss(target, "global");

                    // Updates wins
                    removeWin(victim, kit);
                    removeWin(victim, "global");

                    results3.deleteRow();
                }

                // -------------------------------------- Clear Win Streaks
                for(Kit kit : plugin.kitManager().kits()) {
                    resetWinStreak(target, kit.getName());
                    resetWins(target, kit.getName());
                    resetLosses(target, kit.getName());
                    resetElo(target, kit.getName());
                }

                resetWinStreak(target, "global");
                resetWins(target, "global");
                resetElo(target, "global");
                resetLosses(target, "global");

                ChatUtils.chat(sender, "&a&lSuccessfully reset data!");
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void updateElo(String uuid, String kit, int eloChange) {
        try {
            PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_statistics WHERE uuid = ? AND kit = ?");
            statement.setString(1, uuid);
            statement.setString(2, kit);
            ResultSet results = statement.executeQuery();

            if(results.next()) {
                int elo = results.getInt(7);

                PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET elo = ? WHERE uuid = ? AND kit = ?");
                statement2.setInt(1, elo + eloChange);
                statement2.setString(2, uuid);
                statement2.setString(3, kit);
                statement2.executeUpdate();
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void removeLoss(String uuid, String kit) {
        try {
            PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_statistics WHERE uuid = ? AND kit = ?");
            statement.setString(1, uuid);
            statement.setString(2, kit);
            ResultSet results = statement.executeQuery();

            if(results.next()) {
                int losses = results.getInt(4);

                PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET losses = ? WHERE uuid = ? AND kit = ?");
                statement2.setInt(1, losses - 1);
                statement2.setString(2, uuid);
                statement2.setString(3, kit);
                statement2.executeUpdate();
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void removeWin(String uuid, String kit) {
        try {
            PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM duels_statistics WHERE uuid = ? AND kit = ?");
            statement.setString(1, uuid);
            statement.setString(2, kit);
            ResultSet results = statement.executeQuery();

            if(results.next()) {
                int wins = results.getInt(3);

                PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET wins = ? WHERE uuid = ? AND kit = ?");
                statement2.setInt(1, wins - 1);
                statement2.setString(2, uuid);
                statement2.setString(3, kit);
                statement2.executeUpdate();
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void resetWinStreak(String uuid, String kit) {
        try {
            PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET winStreak = ?,bestWinStreak = ? WHERE uuid = ? AND kit = ?");
            statement2.setInt(1, 0);
            statement2.setInt(2, 0);
            statement2.setString(3, uuid);
            statement2.setString(4, kit);
            statement2.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void notifyVictim(String uuid) {
        try {
            PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("INSERT IGNORE INTO duels_notifications (uuid) VALUES (?)");
            statement.setString(1, uuid);
            statement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void resetElo(String uuid, String kit) {
        try {
            PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET elo = ? WHERE uuid = ? AND kit = ?");
            statement2.setInt(1, 1000);
            statement2.setString(2, uuid);
            statement2.setString(3, kit);
            statement2.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void resetWins(String uuid, String kit) {
        try {
            PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET wins = ? WHERE uuid = ? AND kit = ?");
            statement2.setInt(1, 0);
            statement2.setString(2, uuid);
            statement2.setString(3, kit);
            statement2.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void resetLosses(String uuid, String kit) {
        try {
            PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE duels_statistics SET losses = ? WHERE uuid = ? AND kit = ?");
            statement2.setInt(1, 0);
            statement2.setString(2, uuid);
            statement2.setString(3, kit);
            statement2.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}