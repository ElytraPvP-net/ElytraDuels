package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
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
        Player player = event.getPlayer();
        Party party = plugin.getPartyManager().getParty(player);

        if(party == null) {
            return;
        }

        if(!party.hasPartyChatToggled(player)) {
            return;
        }

        event.setCancelled(true);
        party.sendMessage("&a&lParty &8» &f" + player.getName() + "&8: &a" + event.getMessage());
    }
}
