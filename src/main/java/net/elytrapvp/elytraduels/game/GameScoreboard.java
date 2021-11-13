package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.game.team.Team;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import net.elytrapvp.elytraduels.utils.scoreboard.CustomScoreboard;
import net.elytrapvp.elytraduels.utils.scoreboard.ScoreHelper;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class GameScoreboard extends CustomScoreboard {
    private final Game game;

    public GameScoreboard(Player player, Game game) {
        super(player);
        this.game = game;
        update(player);
    }

    public void update(Player player) {
        ScoreHelper helper;

        if(ScoreHelper.hasScore(player)) {
            helper = ScoreHelper.getByPlayer(player);
        }
        else {
            helper = ScoreHelper.createScore(player);
        }

        helper.setTitle("&a&lDuels &b(Beta)");

        if(game.getKit().hasAbilities()) {
            helper.setSlot(13, "&7&m------------------");
            helper.setSlot(12, "&aTime: &f" + game.getTimer().toString());
            helper.setSlot(11, "");
            helper.setSlot(10, "&aLadder");
            helper.setSlot(9, "  &f" + game.getKit().getName());
            helper.setSlot(8, " ");
            helper.setSlot(7, "&aAbilities");
            helper.setSlot(6, "  &aDouble Jump: &f" + game.getDoubleJumps(player));
            helper.setSlot(5, "  &aTriple Shot: &f" + game.getTripleShots(player));
            helper.setSlot(4, "  &aRepulsor: &f" + game.getRepulsors(player));
            helper.setSlot(3, "");
            helper.setSlot(2, "&7&m------------------");
            helper.setSlot(1, "&aplay.elytrapvp.net");
        }
        else {
            if(game.getGameType() == GameType.FFA) {
                helper.setSlot(14, "&7&m------------------");
                helper.setSlot(13, "&aTime: &f" + game.getTimer().toString());
                helper.setSlot(12, "");
                helper.setSlot(8, "&aOpponents");

                int i = 4;
                for(Team team : game.getTeamManager().getTeams()) {
                    if(team.equals(game.getTeam(player))) {
                        continue;
                    }

                    if(i == 8) {
                        break;
                    }

                    for(Player opponent : team.getAlivePlayers()) {
                        String opponentName = opponent.getName();

                        String line = "  &f" + opponentName + "" + ChatUtils.getFormattedHealthPercent(opponent);
                        int spaces = 25 - line.length();

                        if(spaces < 0) {
                            spaces = 0;
                        }

                        helper.setSlot(i, "  &f" + opponentName + StringUtils.repeat(' ', spaces) + ChatUtils.getFormattedHealthPercent(opponent));
                        i++;
                    }
                }

                helper.setSlot(3, "");
                helper.setSlot(2, "&7&m------------------");
                helper.setSlot(1, "&aplay.elytrapvp.net");
            }
            else {
                helper.setSlot(14, "&7&m------------------");
                helper.setSlot(13, "&aTime: &f" + game.getTimer().toString());
                helper.setSlot(12, "");
                helper.setSlot(11, "&aLadder");
                helper.setSlot(10, "  &f" + game.getKit().getName());
                helper.setSlot(9, " ");
                helper.setSlot(8, "&aOpponent");

                List<Player> opponents = game.getOpposingTeam(game.getTeam(player)).getPlayers();

                for(int i = 0; i < opponents.size(); i++) {
                    Player o = opponents.get(i);
                    String opponentName = o.getName();

                    helper.setSlot(4 + (i * 2) + 1, "  &f" + opponentName);

                    if(game.getTeam(player).getDeadPlayers().contains(player)) {
                        helper.setSlot(4 + (i * 2), "  " + "&c0%" + " &7- " + ChatUtils.getFormattedPing(o));
                    }
                    else {
                        helper.setSlot(4 + (i * 2), "  " + ChatUtils.getFormattedHealthPercent(o) + " &7- " + ChatUtils.getFormattedPing(o));
                    }
                }
                helper.setSlot(3, "");
                helper.setSlot(2, "&7&m------------------");
                helper.setSlot(1, "&aplay.elytrapvp.net");
            }
        }
    }
}
