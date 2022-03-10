package net.elytrapvp.elytraduels.runnables;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HealingRunnable extends BukkitRunnable {
    private final ElytraDuels plugin;

    public HealingRunnable(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            // Allow regeneration to work.
            if(player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                continue;
            }

            Game game = plugin.getGameManager().getGame(player);

            if(game == null) {
                return;
            }

            if(!game.getKit().naturalRegen()) {
                continue;
            }

            double health = player.getHealth() + 1;

            if(health > 20) {
                health = 20;
            }

            player.setHealth(health);
        }
    }
}