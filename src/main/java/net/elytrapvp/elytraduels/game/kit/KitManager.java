package net.elytrapvp.elytraduels.game.kit;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.kit.kits.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Manages all existing kits.
 */
public class KitManager {
    private final Set<Kit> kits = new LinkedHashSet<>();

    public KitManager(ElytraDuels plugin) {
        Kit speedArcher = new SpeedArcherKit(plugin);
        Kit bow = new BowKit(plugin);
        Kit iron = new IronKit(plugin);
        Kit diamond = new DiamondKit(plugin);
        Kit bowSpleef = new BowSpleefKit(plugin);
        Kit classic = new ClassicKit(plugin);
        Kit noDebuff = new NoDebuffKit(plugin);
        Kit op = new OPKit(plugin);
        Kit blitz = new BlitzKit(plugin);
        Kit buildUHC = new BuildUHCKit(plugin);
        Kit cactus = new CactusKit(plugin);
        Kit sg = new SGKit(plugin);
        Kit finalUHC = new FinalUHCKit(plugin);
        Kit sumo = new SumoKit(plugin);
        Kit archer = new ArcherKit(plugin);
        Kit peal = new PearlKit(plugin);
        Kit blockSumo = new BlockSumoKit(plugin);
        Kit bowFight = new BowFightKit(plugin);
        Kit shortBow = new ShortBowKit(plugin);

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
        kits.add(archer);
        //kits.add(peal);
        //kits.add(blockSumo);
        //kits.add(bowFight);
        kits.add(shortBow);
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