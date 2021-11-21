package net.elytrapvp.elytraduels.game.kit;

import net.elytrapvp.elytraduels.game.kit.kits.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Manages all existing kits.
 */
public class KitManager {
    private final Set<Kit> kits = new LinkedHashSet<>();
    private final Set<Kit> rankedKits = new LinkedHashSet<>();

    public KitManager() {
        Kit speedArcher = new SpeedArcherKit();
        Kit bow = new BowKit();
        Kit iron = new IronKit();
        Kit diamond = new DiamondKit();
        Kit bowSpleef = new BowSpleefKit();
        Kit classic = new ClassicKit();
        Kit noDebuff = new NoDebuffKit();
        Kit op = new OPKit();
        Kit blitz = new BlitzKit();
        Kit buildUHC = new BuildUHCKit();
        Kit cactus = new CactusKit();
        Kit sg = new SGKit();
        Kit finalUHC = new FinalUHCKit();
        Kit sumo = new SumoKit();

        kits.add(speedArcher);
        kits.add(bow);
        kits.add(iron);
        kits.add(diamond);
        kits.add(bowSpleef);
        kits.add(classic);
        kits.add(noDebuff);
        kits.add(op);
        kits.add(blitz);
        kits.add(buildUHC);
        kits.add(cactus);
        kits.add(sg);
        kits.add(finalUHC);
        kits.add(sumo);

        rankedKits.add(bow);
        rankedKits.add(bowSpleef);
        rankedKits.add(noDebuff);
        rankedKits.add(op);
        rankedKits.add(blitz);
        rankedKits.add(buildUHC);
        rankedKits.add(cactus);
        rankedKits.add(sg);
        rankedKits.add(sumo);
        rankedKits.add(iron);
        rankedKits.add(diamond);
        rankedKits.add(finalUHC);
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


    public Set<Kit> getRankedKits() {
        return kits;
    }
}