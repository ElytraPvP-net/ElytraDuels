package net.elytrapvp.elytraduels.game.kit;

import net.elytrapvp.elytraduels.game.kit.kits.ArcherKit;
import net.elytrapvp.elytraduels.game.kit.kits.BowKit;
import net.elytrapvp.elytraduels.game.kit.kits.IronKit;
import net.elytrapvp.elytraduels.game.kit.kits.UHCKit;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Manages all existing kits.
 */
public class KitManager {
    private final Set<Kit> kits = new LinkedHashSet<>();

    public KitManager() {
        kits.add(new ArcherKit());
        kits.add(new BowKit());
        kits.add(new UHCKit());
        kits.add(new IronKit());
    }

    /**
     * Get a kit from its name,
     * @param str Name of the kit.
     * @return Kit from name.
     */
    public Kit getKit(String str) {
        for(Kit kit : getKits()) {
            if(kit.getName().equalsIgnoreCase(str)) {
                return kit;
            }
        }

        return null;
    }

    /**
     * Get all existing kits.
     * @return All kits.
     */
    public Set<Kit> getKits() {
        return kits;
    }

}