package net.elytrapvp.elytraduels.utils;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * A collection of tools to help deal with locations.
 */
public class LocationUtils {

    /**
     * Gets a location from a config file.
     * @param config File to get location from.
     * @param path Path to the location.
     * @return Location stored in the file.
     */
    public static Location fromConfig(FileConfiguration config, String path) {
        String world = config.getString(path + ".World");
        double x = config.getDouble(path + ".X");
        double y = config.getDouble(path + ".Y");
        double z = config.getDouble(path + ".Z");

        // Exists if there are no rotation values.
        if(!config.isSet(path + ".Yaw")) {
            return new Location(Bukkit.getWorld(world), x, y, z);
        }

        float yaw = (float) config.getDouble(path + ".Yaw");
        float pitch = (float) config.getDouble(path + ".Pitch");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    /**
     * Get the spawn Location from the Config
     * @return Spawn Location
     */
    public static Location getSpawn(ElytraDuels plugin) {
        String world = plugin.getSettingsManager().getConfig().getString("Spawn.World");
        double x = plugin.getSettingsManager().getConfig().getDouble("Spawn.X");
        double y = plugin.getSettingsManager().getConfig().getDouble("Spawn.Y");
        double z = plugin.getSettingsManager().getConfig().getDouble("Spawn.Z");
        float pitch = (float) plugin.getSettingsManager().getConfig().getDouble("Spawn.Pitch");
        float yaw = (float) plugin.getSettingsManager().getConfig().getDouble("Spawn.Yaw");

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    /**
     * Set the server spawn to the current location.
     * @param loc Location
     */
    public static void setSpawn(ElytraDuels plugin, Location loc) {
        String world = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float pitch = loc.getPitch();
        float yaw = loc.getYaw();

        plugin.getSettingsManager().getConfig().set("Spawn.World", world);
        plugin.getSettingsManager().getConfig().set("Spawn.X", x);
        plugin.getSettingsManager().getConfig().set("Spawn.Y", y);
        plugin.getSettingsManager().getConfig().set("Spawn.Z", z);
        plugin.getSettingsManager().getConfig().set("Spawn.Pitch", pitch);
        plugin.getSettingsManager().getConfig().set("Spawn.Yaw", yaw);
        plugin.getSettingsManager().getConfig().set("Spawn.Set", true);

        plugin.getSettingsManager().reloadConfig();
    }
}