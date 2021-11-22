package net.elytrapvp.elytraduels.game.kit.kits;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SumoKit extends Kit {

    public SumoKit(ElytraDuels plugin) {
        super(plugin, "Sumo");

        setIconMaterial(Material.SLIME_BALL);

        setDoDamage(false);
        setWaterKills(true);
    }
}