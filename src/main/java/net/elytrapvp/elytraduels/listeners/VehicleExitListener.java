package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class VehicleExitListener implements Listener {
    private final ElytraDuels plugin;

    public VehicleExitListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExit(VehicleExitEvent event) {

        if(!(event.getExited() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getExited();
        Game game = plugin.gameManager().getGame(player);

        if(game == null) {
            return;
        }

        if(!event.getExited().isValid()) {
            return;
        }

        if(!game.getKit().hasExitVehicle()) {
            event.setCancelled(true);
        }
    }
}
