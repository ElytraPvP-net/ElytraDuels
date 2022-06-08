package net.elytrapvp.elytraduels.runnables;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import net.elytrapvp.elytraduels.game.GameType;
import net.elytrapvp.elytraduels.scoreboards.LobbyScoreboard;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.LocationUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AFKTimer extends BukkitRunnable {
    private final ElytraDuels plugin;
    public static Map<UUID, Integer> counter = new HashMap<>();
    private Map<UUID, Location> locations = new HashMap<>();

    public AFKTimer(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Game game = plugin.getGameManager().getGame(player);

            if(game == null) {
                counter.remove(player.getUniqueId());
                locations.remove(player.getUniqueId());
                continue;
            }

            if(game.getSpectators().contains(player)) {
                return;
            }

            if(game.getGameState() != GameState.RUNNING) {
                return;
            }

            if(game.getGameType() != GameType.UNRANKED) {
                return;
            }

            if(!counter.containsKey(player.getUniqueId())) {
                counter.put(player.getUniqueId(), 1);
                locations.put(player.getUniqueId(), player.getLocation());
            }
            else {
                if(locations.get(player.getUniqueId()).equals(player.getLocation())) {
                    counter.put(player.getUniqueId(), counter.get(player.getUniqueId()) + 1);
                }
                else {
                    locations.put(player.getUniqueId(), player.getLocation());
                    counter.put(player.getUniqueId(), 1);
                }
            }

            if(counter.get(player.getUniqueId()) == 4) {
                ChatUtils.chat(player, "&c&lYou will be AFK Kicked in &f&l10 &c&lSeconds!");
            }

            if(counter.get(player.getUniqueId()) == 6) {
                player.teleport(LocationUtils.getSpawn(plugin));
                ItemUtils.givePartyItems(plugin.getPartyManager(), player);
                ChatUtils.chat(player, "&c&lYou have been kicked for being AFK!");
                new LobbyScoreboard(plugin, player);
                game.playerDisconnect(player);
            }
        }
    }
}