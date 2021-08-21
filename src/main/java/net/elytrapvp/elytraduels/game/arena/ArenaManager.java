package net.elytrapvp.elytraduels.game.arena;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.arena.map.Map;
import net.elytrapvp.elytraduels.game.kit.Kit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

/**
 * Manages all existing arenas.
 */
public class ArenaManager {
    private final Set<Arena> openArenas = new HashSet<>();

    public ArenaManager(ElytraDuels plugin) {
        FileConfiguration maps = plugin.getSettingsManager().getMaps();
        ConfigurationSection section = maps.getConfigurationSection("Maps");

        for(String map : section.getKeys(false)) {
            new Map(plugin, map);
        }
    }

    /**
     * Add an arena to the queue.
     * @param arena Arena to add.
     */
    public void addArena(Arena arena) {
        getOpenArenas().add(arena);
    }

    /**
     * Get a random open arena for a specific kit.
     * @param kit Kit to get arena for.
     * @return Random open arena.
     */
    public Arena getOpenArena(Kit kit) {
        List<Arena> open = new ArrayList<>();

        for(Arena arena : getOpenArenas()) {
            if(arena.getMap().getKits().contains(kit)) {
                open.add(arena);
            }
        }

        if(open.size() == 0) {
            return null;
        }

        Random random = new Random();
        return open.get(random.nextInt(open.size()));
    }

    /**
     * Get all arenas that are currently open.
     * @return All currently open arenas.
     */
    public Set<Arena> getOpenArenas() {
        return openArenas;
    }

    /**
     * Remove an arena from the queue.
     * @param arena Arena to remove.
     */
    public void removeArena(Arena arena) {
        getOpenArenas().remove(arena);
    }
}
