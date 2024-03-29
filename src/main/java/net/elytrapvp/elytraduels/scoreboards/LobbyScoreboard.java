package net.elytrapvp.elytraduels.scoreboards;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import net.elytrapvp.elytraduels.utils.scoreboard.CustomScoreboard;
import net.elytrapvp.elytraduels.utils.scoreboard.ScoreHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LobbyScoreboard extends CustomScoreboard {
    private final ElytraDuels plugin;

    public LobbyScoreboard(ElytraDuels plugin, Player player) {
        super(player);

        this.plugin = plugin;

        update(player);
    }

    public void update(Player p) {
        ScoreHelper helper;

        if(ScoreHelper.hasScore(p)) {
            helper = ScoreHelper.getByPlayer(p);
        }
        else {
            helper = ScoreHelper.createScore(p);
        }

        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(p);

        helper.setTitle("&a&lDuels");
        helper.setSlot(10, "&7&m------------------");
        helper.setSlot(9, "&aOnline: &f" + Bukkit.getOnlinePlayers().size());
        helper.setSlot(8, "&aPlaying: &f" + plugin.queueManager().getPlaying());
        helper.setSlot(7, "&aQueue: &f" + plugin.queueManager().getQueueing());
        helper.setSlot(6, "");
        helper.setSlot(5, "&aWins: &f" + customPlayer.getWins("global"));
        helper.setSlot(4, "&aWin Streak: &f" + customPlayer.getWinStreak("global"));
        helper.setSlot(3, "&aBest Win Streak: &f" + customPlayer.getBestWinStreak("global"));
        helper.setSlot(2, "&7&m------------------");
        helper.setSlot(1, "&aplay.elytrapvp.net");
    }
}