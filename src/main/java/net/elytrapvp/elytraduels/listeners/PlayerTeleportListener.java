package net.elytrapvp.elytraduels.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {
    @EventHandler
    public void onEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        // Prevents ender pearls from doing damage.
        if(event.getCause()== PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
            player.setNoDamageTicks(1);
            player.teleport(event.getTo());
        }
    }
}
