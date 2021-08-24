package net.elytrapvp.elytraduels.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerItemConsumeListener implements Listener {
    @EventHandler
    public void onEvent(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.GOLDEN_APPLE) {
            PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 100, 2);
            event.getPlayer().addPotionEffect(effect);
        }
    }
}
