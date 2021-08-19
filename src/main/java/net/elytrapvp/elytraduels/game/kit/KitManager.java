package net.elytrapvp.elytraduels.game.kit;

import net.elytrapvp.elytraduels.ElytraDuels;

import java.util.LinkedHashSet;
import java.util.Set;

public class KitManager {
    private final ElytraDuels plugin;
    private final Set<Kit> kits = new LinkedHashSet<>();

    public KitManager(ElytraDuels plugin) {
        this.plugin = plugin;
    }

    public void addKit(Kit kit) {
        kits.add(kit);
    }

    public Set<Kit> getKits() {
        return kits;
    }

    public void removeKit(Kit kit) {
        kits.remove(kit);
    }
}