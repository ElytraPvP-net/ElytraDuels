package net.elytrapvp.elytraduels.customplayer;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages all CustomPlayer objects.
 */
public class CustomPlayerManager {
    private final ElytraDuels plugin;
    private final Map<UUID, CustomPlayer> players = new HashMap<>();

    public CustomPlayerManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), new CustomPlayer(plugin, player.getUniqueId()));
    }

    public CustomPlayer getPlayer(Player player) {
        if(players.containsKey(player.getUniqueId())) {
            return players.get(player.getUniqueId());
        }

        return null;
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }
}
