package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import net.elytrapvp.elytraduels.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private final ElytraDuels plugin;

    public EntityDamageListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        // Cancel damage from specific causes.
        switch (event.getCause()) {
            case LIGHTNING:
            case ENTITY_EXPLOSION:
            case FALL:
                event.setCancelled(true);
                return;
        }

        Player player = (Player) event.getEntity();
        Game game = plugin.gameManager().getGame(player);

        if(game == null) {
            return;
        }

        // Prevent spectators from taking damage.
        if(game.getSpectators().contains(player)) {
            event.setCancelled(true);
            return;
        }

        // Players can only take damage when the game is running.
        if(game.getGameState() != GameState.RUNNING) {
            event.setCancelled(true);
            return;
        }

        // Prevents "killing" a player twice.
        if(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }

        // Kill player if they won't survive.
        if(event.getFinalDamage() >= player.getHealth()) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> game.playerKilled(player), 1);
        }
    }
}