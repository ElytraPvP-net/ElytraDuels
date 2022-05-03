package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytradb.ElytraDB;
import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.scoreboards.LobbyScoreboard;
import net.elytrapvp.elytraduels.utils.ItemUtils;
import net.elytrapvp.elytraduels.utils.LocationUtils;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    private final ElytraDuels plugin;

    public PlayerJoinListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new LobbyScoreboard(plugin, player);
        player.teleport(LocationUtils.getSpawn(plugin));
        player.setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage(ChatUtils.translate("&8[&a+&8] &a" + player.getName()));

        ItemUtils.giveLobbyItems(player);

        plugin.getCustomPlayerManager().addPlayer(player);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, ()-> {
            try {
                PreparedStatement statement = ElytraDB.getDatabase().prepareStatement("SELECT * FROM duels_notifications WHERE uuid = ? LIMIT 1", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();

                if(results.next()) {
                    ChatUtils.chat(player, "&c&lA player you have played against has been punished for violating our rules against unfair play. Your stats have been updated accordingly.");
                    results.deleteRow();
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
}