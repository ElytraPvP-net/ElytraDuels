package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityRegainhealthListener implements Listener {
    private final ElytraDuels plugin;

    public EntityRegainhealthListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(EntityRegainHealthEvent event) {
        // Exit if not a player.
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        // Allow regeneration to work.
        if(player.hasPotionEffect(PotionEffectType.REGENERATION)) {
            return;
        }

        Game game = plugin.getGameManager().getGame(player);

        if(game == null) {
            return;
        }

        if(!game.getKit().naturalRegen()) {
            event.setCancelled(true);
        }
    }
}