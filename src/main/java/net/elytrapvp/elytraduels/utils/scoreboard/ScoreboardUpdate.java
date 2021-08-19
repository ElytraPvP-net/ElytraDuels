package net.elytrapvp.elytraduels.utils.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A repeating task to update all active
 * Custom Scorebords.
 */
public class ScoreboardUpdate extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(ScoreHelper.hasScore(p)) {
                CustomScoreboard.getPlayers().get(p.getUniqueId()).update(p);
            }
        }
    }
}