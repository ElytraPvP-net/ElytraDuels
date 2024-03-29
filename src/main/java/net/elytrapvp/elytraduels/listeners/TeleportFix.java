package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TeleportFix implements Listener {
    private final Server server;
    private final ElytraDuels plugin;

    private final int TELEPORT_FIX_DELAY = 15; // ticks

    public TeleportFix(ElytraDuels plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        // Prevent players from "respawning" when using ender pearls.
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }

        final Player player = event.getPlayer();
        final int visibleDistance = server.getViewDistance() * 16;

        Game game = plugin.gameManager().getGame(player);
        if(game != null) {
            if(game.getSpectators().contains(player)) {
                return;
            }
        }

        // Fix the visibility issue one tick later
        server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            // Refresh nearby clients
            final List<Player> nearby = getPlayersWithin(player, visibleDistance);

            // Hide every player
            updateEntities(player, nearby, false);

            // Then show them again
            server.getScheduler().scheduleSyncDelayedTask(plugin, () -> updateEntities(player, nearby, true), 1);
        }, TELEPORT_FIX_DELAY);
    }

    private void updateEntities(Player tpedPlayer, List<Player> players, boolean visible) {
        // Hide or show every player to tpedPlayer
        // and hide or show tpedPlayer to every player.
        for (Player player : players) {
            // Fix spectators being seen after teleport.
            Game game = plugin.gameManager().getGame(player);
            if(game != null && game.getSpectators().contains(player)) {
                continue;
            }

            if (visible) {
                tpedPlayer.showPlayer(player);
                player.showPlayer(tpedPlayer);
            }
            else {
                tpedPlayer.hidePlayer(player);
                player.hidePlayer(tpedPlayer);
            }
        }
    }

    private List<Player> getPlayersWithin(Player player, int distance) {
        List<Player> res = new ArrayList<>();
        int d2 = distance * distance;
        for (Player p : server.getOnlinePlayers()) {
            if (p != player && p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                res.add(p);
            }
        }
        return res;
    }
}