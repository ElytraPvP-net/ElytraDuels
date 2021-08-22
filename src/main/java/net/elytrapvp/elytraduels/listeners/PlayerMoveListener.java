package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private final ElytraDuels plugin;

    public PlayerMoveListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        if(game == null) {
            return;
        }

        if(player.getLocation().getY() < game.getKit().getVoidLevel()) {
            if(game.getSpectators().contains(player)) {
                player.teleport(game.getArena().getSpawns().get(0));
                player.setFlying(true);
                return;
            }

            game.playerKilled(player);
            player.teleport(game.getArena().getSpawns().get(0));
            player.setFlying(true);
        }
    }
}