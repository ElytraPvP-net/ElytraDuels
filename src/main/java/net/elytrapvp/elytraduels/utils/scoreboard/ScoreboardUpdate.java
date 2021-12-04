package net.elytrapvp.elytraduels.utils.scoreboard;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.customplayer.CustomPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A repeating task to update all active
 * Custom Scorebords.
 */
public class ScoreboardUpdate extends BukkitRunnable {
    private final ElytraDuels plugin;

    public ScoreboardUpdate(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            CustomPlayer customPlayer = plugin.getCustomPlayerManager().getPlayer(p);

            if(!customPlayer.getShowScoreboard()) {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                return;
            }

            if(ScoreHelper.hasScore(p)) {
                CustomScoreboard.getPlayers().get(p.getUniqueId()).update(p);
            }
        }
    }
}