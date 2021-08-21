package net.elytrapvp.elytraduels.game.kit;

import net.elytrapvp.elytraduels.ElytraDuels;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Manages all existing kits.
 */
public class KitManager {
    private final ElytraDuels plugin;
    private final Set<Kit> kits = new LinkedHashSet<>();

    public KitManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    public Set<Kit> getKits() {
        return kits;
    }

}