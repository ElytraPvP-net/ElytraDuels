package net.elytrapvp.elytraduels.game.arena.map;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.arena.Arena;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores all information about a specific map.
 *  This include:
 *    - name
 *    - builders
 *    - kits that can be used on it
 */
public class Map {
    private final String id;
    private final String name;
    private final List<String> builders;
    private final Set<Kit> kits = new HashSet<>();

    /**
     * Creates a new kit object
     * @param plugin ElytraDuels instance.
     * @param id ID of the map, as found in maps.yml.
     */
    public Map(ElytraDuels plugin, String id) {
        this.id = id;

        // Setting these two variables makes it easier to get information.
        FileConfiguration maps = plugin.getSettingsManager().getMaps();
        String path = "Maps." + id + ".";

        name = maps.getString(path + "name");
        builders = maps.getStringList(path + "builders");

        for(String kit : maps.getStringList(path + "kits")) {
            kits.add(plugin.getKitManager().getKit(kit));
        }

        ConfigurationSection section = maps.getConfigurationSection(path + "arenas");
        for(String arena : section.getKeys(false)) {
            plugin.getArenaManager().addArena(new Arena(plugin, this, arena));
        }
    }

    /**
     * Get a list of who built the map.
     * @return All builders.
     */
    public List<String> getBuilders() {
        return builders;
    }

    /**
     * Get the id of the map.
     * @return Id of the map.
     */
    public String getId() {
        return id;
    }

    /**
     * Get what kits can be used on the map.
     * @return All kits that can be used.
     */
    public Set<Kit> getKits() {
        return kits;
    }

    /**
     *  Get the name of the map.
     * @return Name of the map.
     */
    public String getName() {
        return name;
    }
}