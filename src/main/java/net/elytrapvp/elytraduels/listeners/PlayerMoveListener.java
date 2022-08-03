package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        Game game = plugin.gameManager().getGame(player);

        if(game == null) {
            return;
        }

        if(game.getKit().waterKills()) {
            Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
            Block block2 = player.getLocation().getBlock();

            if(block.getType() == Material.STATIONARY_WATER || block2.getType() == Material.STATIONARY_WATER) {
                if(game.getSpectators().contains(player)) {
                    player.teleport(game.getArena().spawns().get(0));
                    return;
                }

                if(game.getGameState() == GameState.COUNTDOWN) {
                    player.teleport(game.getArena().spawns().get(0));
                    return;
                }

                if(game.getGameState() == GameState.END) {
                    return;
                }

                game.playerKilled(player);
                return;
            }
        }

        if(player.getLocation().getY() < game.getKit().getVoidLevel()) {
            if(game.getGameState() == GameState.COUNTDOWN) {
                player.teleport(game.getArena().spawns().get(0));
                return;
            }

            if(game.getSpectators().contains(player)) {
                player.teleport(game.getArena().spawns().get(0));
                player.setAllowFlight(true);
                player.setFlying(true);
                return;
            }

            game.playerKilled(player);
            player.teleport(game.getArena().spawns().get(0));
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }
}