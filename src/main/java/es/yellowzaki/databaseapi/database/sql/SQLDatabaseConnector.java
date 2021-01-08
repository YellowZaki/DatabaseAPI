package es.yellowzaki.databaseapi.database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.eclipse.jdt.annotation.NonNull;

import es.yellowzaki.databaseapi.database.DatabaseConnector;
import es.yellowzaki.databaseapi.database.DatabaseSettings;

public abstract class SQLDatabaseConnector implements DatabaseConnector {

    protected String connectionUrl;
    private DatabaseSettings dbSettings;
    protected static Connection connection = null;
    protected static Set<Class<?>> types = new HashSet<>();

    public SQLDatabaseConnector(DatabaseSettings dbSettings, String connectionUrl) {
        this.dbSettings = dbSettings;
        this.connectionUrl = connectionUrl;
    }

    @Override
    public String getConnectionUrl() {
        return connectionUrl;
    }

    @Override
    @NonNull
    public String getUniqueId(String tableName) {
        // Not used
        return "";
    }

    @Override
    public boolean uniqueIdExists(String tableName, String key) {
        // Not used
        return false;
    }

    @Override
    public void closeConnection(Class<?> type) {
        types.remove(type);
        if (types.isEmpty() && connection != null) {
            try {
                connection.close();
                Bukkit.getLogger().info("Closed database connection");
            } catch (SQLException e) {
                Bukkit.getLogger().severe("Could not close database connection");
            }
        }
    }

    @Override
    public Object createConnection(Class<?> type) {
        types.add(type);
        // Only make one connection to the database
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(connectionUrl, dbSettings.getDatabaseUsername(), dbSettings.getDatabasePassword());
            } catch (SQLException e) {
                Bukkit.getLogger().severe("Could not connect to the database! " + e.getMessage());
            }
        }
        return connection;
    }

}
