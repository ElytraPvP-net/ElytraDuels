package net.elytrapvp.elytraduels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the connection process to MySQL.
 */
public class MySQL {
    private final ElytraDuels plugin;
    private Connection connection;
    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int port;

    /**
     * Loads the MySQL database connection info.
     * @param plugin Instance of the plugin.
     */
    public MySQL(ElytraDuels plugin) {
        this.plugin = plugin;
        host = plugin.getSettingsManager().getConfig().getString("MySQL.host");
        database = plugin.getSettingsManager().getConfig().getString("MySQL.database");
        username = plugin.getSettingsManager().getConfig().getString("MySQL.username");
        password = plugin.getSettingsManager().getConfig().getString("MySQL.password");
        port = plugin.getSettingsManager().getConfig().getInt("MySQL.port");
    }

    /**
     * Close a connection.
     */
    public void closeConnection() {
        if(isConnected()) {
            try {
                connection.close();
            }
            catch(SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Get the connection.
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Get if plugin is connected to the database.
     * @return Connected
     */
    private boolean isConnected() {
        return (connection != null);
    }

    /**
     * Open a MySQL Connection
     */
    public void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            synchronized(ElytraDuels.class) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false&characterEncoding=utf8", username, password);
            }

            // Prevents losing connection to MySQL.
            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, ()-> {
                try {
                    connection.isValid(0);
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }, 504000, 504000);
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}