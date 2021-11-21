package net.elytrapvp.elytraduels.utils;

import net.elytrapvp.elytraduels.ElytraDuels;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Creates a timer that counts up.
 * Used in queue and in match.
 */
public class Timer {
    private final ElytraDuels plugin;
    private int seconds;
    private int minutes;
    private final BukkitRunnable task;

    /**
     * Create a timer.
     */
    public Timer(ElytraDuels plugin) {
        this.plugin = plugin;
        seconds = 0;
        minutes = 0;

        task = new BukkitRunnable() {
            @Override
            public void run() {
                seconds++;

                if(seconds == 60) {
                    seconds = 0;
                    minutes ++;
                }
            }
        };
    }

    /**
     * Create a timer from seconds.
     * @param plugin Plugin instance.
     * @param seconds Amount of seconds.
     */
    public Timer(ElytraDuels plugin, int seconds) {
        this.plugin = plugin;
        task = null;

        this.minutes = seconds / 60;
        this.seconds = seconds % 60;
    }

    /**
     * Reset the timer.
     */
    public void reset() {
        seconds = 0;
        minutes = 0;
    }

    /**
     * Start the timer.
     */
    public void start() {
        task.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        task.cancel();
    }

    /**
     * Convert the timer to seconds.
     * @return Seconds.
     */
    public int toSeconds() {
        return (60 * minutes) + seconds;
    }

    /**
     * Converts the timer into a String
     * @return String version of timer.
     */
    public String toString() {
        String minute = "";
        if(minutes < 10) {
            minute += "0";
        }
        minute += "" + minutes;

        String second = "";

        if(seconds < 10) {
            second += "0";
        }
        second += "" + seconds;

        return minute + ":" + second;
    }
}