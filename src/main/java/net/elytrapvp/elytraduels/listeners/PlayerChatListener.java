package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.team.TeamColor;
import net.elytrapvp.elytraduels.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final ElytraDuels plugin;

    public PlayerChatListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        partyChat(event);
        gameChat(event);
    }

    public void partyChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Party party = plugin.partyManager().getParty(player);

        if(party == null) {
            return;
        }

        if(!party.hasPartyChatToggled(player)) {
            return;
        }

        event.setCancelled(true);
        party.sendMessage("&a&lParty &8» &f" + player.getName() + "&8: &a" + event.getMessage());
    }

    public void gameChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Game game = plugin.gameManager().getGame(player);

        if(game == null) {
            return;
        }

        event.setCancelled(true);

        if(game.getSpectators().contains(player)) {
            game.broadcast("&7[SPECTATOR] " + player.getName() + " &8» &7" + event.getMessage());
            return;
        }

        TeamColor teamColor = game.getTeam(player).teamColor();
        game.broadcast(teamColor.chatColor() + "[" + teamColor + "] &7" + player.getName() + " &8» &7" + event.getMessage());
    }
}
