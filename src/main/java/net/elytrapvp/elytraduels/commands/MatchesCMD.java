package net.elytrapvp.elytraduels.commands;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.Timer;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.gui.CustomGUI;
import net.elytrapvp.elytraduels.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchesCMD extends AbstractCommand {
    private final ElytraDuels plugin;

    public MatchesCMD(ElytraDuels plugin) {
        super("matches", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new MatchesGUI(player.getName(), player.getUniqueId().toString()).open(player));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM player_info WHERE username = ?");
                statement.setString(1, args[0]);
                ResultSet results = statement.executeQuery();

                if(results.next()) {
                    String target = results.getString(2);
                    String uuid = results.getString(1);
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new MatchesGUI(target, uuid).open(player));
                }
                else {
                    ChatUtils.chat(sender, "&cError &8» &cThat player has not played.");
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

    }

    private class MatchesGUI extends CustomGUI {
        public MatchesGUI(String player, String uuid) {
            super(54, "Matches");

            try {
                PreparedStatement statement2 = plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM duels_match_history WHERE winner = ? OR loser = ? ORDER BY time DESC");
                statement2.setString(1, uuid);
                statement2.setString(2, uuid);
                ResultSet results2 = statement2.executeQuery();

                int i = 0;
                while(results2.next()) {
                    if(i >= 53) {
                        break;
                    }

                    String opponent = "";
                    boolean winner = true;
                    if(results2.getString(3).equals(uuid)) {
                        opponent = results2.getString(5);
                    }
                    else {
                        opponent = results2.getString(3);
                        winner = false;
                    }

                    PreparedStatement statement3 = plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM player_info WHERE uuid = ? LIMIT 1");
                    statement3.setString(1, opponent);
                    ResultSet results3 = statement3.executeQuery();

                    String opponentName = "null";
                    if(results3.next()) {
                        opponentName = results3.getString(2);
                    }

                    Kit kit = plugin.getKitManager().getKit(results2.getString(1));
                    ItemBuilder builder = new ItemBuilder(kit.getIconMaterial())
                            .setDisplayName("&a" + player + " vs. " + opponentName)
                            .addLore("&aKit: &f" + kit.getName())
                            .addLore("&aMap: &f" + results2.getString(2))
                            .addLore("");

                    if(winner) {
                        builder.addLore("&aWinner: &f" + player + " &a(" + results2.getInt(4) +  " | +" + results2.getInt(7) + ")");
                        builder.addLore("&cLoser: &f" + opponentName + " &c(" + results2.getInt(6) + " | -" + results2.getInt(7) + ")");
                    }
                    else {
                        builder.addLore("&aWinner: &f" + opponentName + " &a(" + results2.getInt(4) +  " | +" + results2.getInt(7) + ")");
                        builder.addLore("&cLoser: &f" + player + " &c(" + results2.getInt(6) + " | -" + results2.getInt(7) + ")");
                    }

                    builder.addLore("")
                            .addLore("&aLength: &f" + new Timer(plugin, results2.getInt(8)))
                            .addLore("&aTime: &f" + results2.getString(9));

                    setItem(i, builder.build());
                    i++;
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}