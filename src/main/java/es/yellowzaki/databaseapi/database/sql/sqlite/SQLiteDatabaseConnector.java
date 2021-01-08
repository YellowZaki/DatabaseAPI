package es.yellowzaki.databaseapi.database.sql.sqlite;

import es.yellowzaki.databaseapi.database.DatabaseLogger;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.eclipse.jdt.annotation.NonNull;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.sql.SQLDatabaseConnector;

/**
 * @since 1.6.0
 * @author Poslovitch
 */
public class SQLiteDatabaseConnector extends SQLDatabaseConnector {

    private static final String DATABASE_FOLDER_NAME = "database";

    SQLiteDatabaseConnector(@NonNull JavaPlugin plugin) {
        super(null, ""); // Not used by SQLite
        File dataFolder = new File(plugin.getDataFolder(), DATABASE_FOLDER_NAME);
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            DatabaseLogger.logError(plugin, "Could not create database folder!");
            return;
        }
        connectionUrl = "jdbc:sqlite:" + dataFolder.getAbsolutePath() + File.separator + "database.db";
    }


    /* (non-Javadoc)
     * @see es.yellowzaki.databaseapi.database.sql.SQLDatabaseConnector#createConnection(java.lang.Class)
     */
    @Override
    public Object createConnection(Class<?> type) {
        types.add(type);
        // Only make one connection at a time
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(connectionUrl);
            } catch (SQLException e) {
                Bukkit.getLogger().severe("Could not connect to the database! " + e.getMessage());
            }
        }
        return connection;
    }
}
