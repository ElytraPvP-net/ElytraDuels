package net.elytrapvp.elytraduels.scoreboards;

import net.elytrapvp.elytraduels.ElytraDuels;
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

        helper.setTitle("&a&lDuels &b(Season 1)");
        helper.setSlot(6, "&7&m------------------");
        helper.setSlot(5, "&aOnline: &f" + Bukkit.getOnlinePlayers().size());
        helper.setSlot(4, "&aPlaying: &f" + plugin.getQueueManager().getPlaying());
        helper.setSlot(3, "&aQueue: &f" + plugin.getQueueManager().getQueueing());
        helper.setSlot(2, "&7&m------------------");
        helper.setSlot(1, "&aplay.elytrapvp.net");
    }
}