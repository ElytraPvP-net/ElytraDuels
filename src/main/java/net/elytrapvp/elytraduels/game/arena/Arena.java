package net.elytrapvp.elytraduels.game.arena;

import net.elytrapvp.elytraduels.ElytraDuels;
import net.elytrapvp.elytraduels.game.arena.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an arena that a game is held in.
 */
public class Arena {
    private final Map map;
    private final List<Location> spawns = new ArrayList<>();

    /**
     * Creates an arena object.
     * @param plugin ElytraDuels instance.
     * @param map Map the arena uses.
     * @param id Id of the arena.
     */
    public Arena(ElytraDuels plugin, Map map, String id) {
        this.map = map;

        // Setting these two variables makes it easier to get information.
        FileConfiguration maps = plugin.getSettingsManager().getMaps();
        String path = "Maps." + getMap().getId() + ".arenas." + id + ".";

        // Loops though all spawns for this arena in maps.yml
        ConfigurationSection section = maps.getConfigurationSection(path + "spawns");
        for(String spawn : section.getKeys(false)) {
            String world = maps.getString(path + spawn + ".World");
            double x = maps.getDouble(path + spawn + ".X");
            double y = maps.getDouble(path + spawn + ".Y");
            double z = maps.getDouble(path + spawn + ".Z");
            float pitch = (float) maps.getDouble(path + spawn + ".Pitch");
            float yaw = (float) maps.getDouble(path + spawn + ".Yaw");

            spawns.add(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch));
        }
    }

    /**
     * Get the map that this arena uses.
     * @return Map the arena uses.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Get all spawns of the arena.
     * @return All spawns of the arena.
     */
    public List<Location> getSpawns() {
        return spawns;
    }
}