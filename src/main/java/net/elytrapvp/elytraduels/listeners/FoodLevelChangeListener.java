package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    private final ElytraDuels plugin;

    public FoodLevelChangeListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(FoodLevelChangeEvent event) {
        // Makes sure we are dealing with a player.
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Game game = plugin.gameManager().getGame(player);

        // Hunger won't lower outside of games
        if(game == null || !game.getKit().hasHunger()) {
            event.setCancelled(true);
        }
    }
}
