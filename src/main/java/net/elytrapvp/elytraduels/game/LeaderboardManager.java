package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LeaderboardManager {
    private final ElytraDuels plugin;
    private final Map<String, Map<String, Map<String, Integer>>> leaderboard = new HashMap<>();

    public LeaderboardManager(ElytraDuels plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 0, 20*60*20);
    }

    public Map<String, Integer> getLeaderboard(String kit, String type) {
        return leaderboard.get(kit).get(type);
    }

    private void update() {
        leaderboard.clear();

        List<String> kits = new ArrayList<>();
        plugin.kitManager().getRankedKits().forEach(kit -> kits.add(kit.getName().toLowerCase()));
        kits.add("global");

        for(String kit : kits) {
            Map<String, Map<String, Integer>> types = new LinkedHashMap<>();
            leaderboard.put(kit, types);
            String[] leaderboardTypes = new String[]{"wins", "losses", "winStreak", "bestWinStreak", "elo"};

            int row = 3;
            for(String type : leaderboardTypes) {
                Map<String, Integer> data = new LinkedHashMap<>();
                types.put(type, data);

                try {
                    PreparedStatement statement1 = plugin.mySQL().getConnection().prepareStatement("SELECT * from duels_statistics WHERE kit = ? ORDER BY " + type + " DESC LIMIT 10");
                    statement1.setString(1, kit);
                    ResultSet results = statement1.executeQuery();

                    while(results.next()) {
                        PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("SELECT * from player_info WHERE uuid = ? LIMIT 1");
                        statement2.setString(1, results.getString(1));
                        ResultSet results2 = statement2.executeQuery();

                        if(results2.next()) {
                            data.put(results2.getString(2), results.getInt(row));
                        }
                        else {
                            data.put("null", results.getInt(row));
                        }
                    }
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
                row++;
            }
        }
    }
}
