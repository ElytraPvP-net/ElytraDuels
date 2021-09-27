package net.elytrapvp.elytraduels.listeners;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.Game;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerItemConsumeListener implements Listener {
    private ElytraDuels plugin;

    public PlayerItemConsumeListener(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        Game game = plugin.getGameManager().getGame(player);

        if(game == null) {
            return;
        }

        if(event.getItem().getType() == Material.GOLDEN_APPLE) {
            if(!game.getKit().hasStrongGapple()) {
                return;
            }

            PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);
            event.getPlayer().addPotionEffect(effect);
        }
    }
}
