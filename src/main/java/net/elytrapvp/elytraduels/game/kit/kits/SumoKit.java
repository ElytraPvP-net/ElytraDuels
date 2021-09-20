package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SumoKit extends Kit {

    public SumoKit() {
        super("Sumo");

        setDoDamage(false);
        setWaterKills(true);
    }

    public void apply(Player p) {
        p.getInventory().clear();
    }

    public Material getIconMaterial() {
        return Material.SLIME_BALL;
    }
}