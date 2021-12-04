package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.Material;

public class SumoKit extends Kit {

    public SumoKit(ElytraDuels plugin) {
        super(plugin, "Sumo");
        setKnockback("nospeed");

        setIconMaterial(Material.SLIME_BALL);

        setDoDamage(false);
        setWaterKills(true);
    }
}