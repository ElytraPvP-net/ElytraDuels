package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.party.Party;
import net.elytrapvp.elytraduels.runnables.AFKTimer;
import net.elytrapvp.elytraduels.utils.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final ElytraDuels plugin;

    public PlayerQuitListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        event.setQuitMessage(ChatUtils.translate("&8[&c-&8] &c" + event.getPlayer().getName()));

        Player player = event.getPlayer();
        Game game = plugin.gameManager().getGame(player);

        if(game != null) {
            game.playerDisconnect(player);
        }

        plugin.queueManager().removePlayer(player);
        AFKTimer.counter.remove(player.getUniqueId());

        Party party = plugin.partyManager().getParty(player);
        if(party != null) {
            party.removePlayer(player);
            party.sendMessage("&aParty &8» &f" + player.getName() + " &adisconnected.");
        }

        plugin.customPlayerManager().removePlayer(player);
        plugin.duelManager().duelRequests().remove(player);
    }
}