package net.elytrapvp.elytraduels.game;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DuelManager {
    private final ElytraDuels plugin;
    private final Map<Player, Player> duelRequests = new HashMap<>();
    private final Map<Player, Kit> duelRequestKits = new HashMap<>();

    public DuelManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    /**
     * Adds a duel requests.
     * @param player Player sending the request.
     * @param target Who the request is being sent to.
     */
    public void addDuelRequest(Player player, Player target, Kit kit) {
        duelRequests.put(player, target);
        duelRequestKits.put(player, kit);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if(duelRequests.get(player).equals(target)) {
                duelRequests.remove(player);
                duelRequestKits.remove(player);
            }
        }, 1200);
    }

    /**
     * Gets all current duel requests.
     * @return All current duel requests.
     */
    public Map<Player, Player> getDuelRequests() {
        return duelRequests;
    }

    /**
     * Get the kit of a duel request.
     * @param player Player who sent the duel request.
     * @return Kit
     */
    public Kit getDuelKit(Player player) {
        if(duelRequestKits.containsKey(player)) {
            return duelRequestKits.get(player);
        }

        for(Player p : duelRequests.keySet()) {
            if(duelRequests.get(p).equals(player)) {
                return  duelRequestKits.get(p);
            }
        }

        return null;
    }
}