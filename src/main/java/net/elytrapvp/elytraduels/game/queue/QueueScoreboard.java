package net.elytrapvp.elytraduels.game.queue;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.game.kit.Kit;
import net.elytrapvp.elytraduels.utils.Timer;
import net.elytrapvp.elytraduels.utils.scoreboard.CustomScoreboard;
import net.elytrapvp.elytraduels.utils.scoreboard.ScoreHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QueueScoreboard extends CustomScoreboard {
    private final ElytraDuels plugin;
    private final Kit kit;
    private final Timer timer;
    private final GameType gameType;

    public QueueScoreboard(ElytraDuels plugin, Player player, Kit kit, Timer timer, GameType gameType) {
        super(player);
        this.plugin = plugin;
        this.kit = kit;
        this.timer = timer;
        this.gameType = gameType;

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

        helper.setTitle("&a&lDuels");
        helper.setSlot(12, "&7&m------------------");
        helper.setSlot(11, "&aOnline: &f" + Bukkit.getOnlinePlayers().size());
        helper.setSlot(10, "&aPlaying: &f" + plugin.getQueueManager().getPlaying());
        helper.setSlot(9, "&aQueue: &f" + plugin.getQueueManager().getQueueing());
        helper.setSlot(8, "");
        helper.setSlot(7, "&aQueue");
        helper.setSlot(6, "  &aLadder: &f" + kit.getName());
        helper.setSlot(5, "  &aElapsed: &f" + timer.toString());
        if(gameType == GameType.UNRANKED) {
            helper.setSlot(4, "  &aType: &fUnranked");
        }
        else {
            helper.setSlot(4, "  &aType: &fRanked");
        }
        helper.setSlot(3, "");
        helper.setSlot(2, "&7&m------------------");
        helper.setSlot(1, "&aplay.elytrapvp.net");
    }
}