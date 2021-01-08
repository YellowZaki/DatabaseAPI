package es.yellowzaki.databaseapi.database.mongodb;

import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseLogger;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;

public class MongoDBDatabase implements DatabaseSetup {


    /* (non-Javadoc)
     * @see es.yellowzaki.databaseapi.database.DatabaseSetup#getHandler(java.lang.Class)
     */
    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> type, DatabaseSettings settings) {
        JavaPlugin plugin = settings.getPlugin();
        // Check if the MongoDB plugin exists
        if (Bukkit.getPluginManager().getPlugin("BsbMongo") == null) {
            DatabaseLogger.logError(settings.getPlugin(), "You must install BsbMongo plugin for MongoDB support!");
            DatabaseLogger.logError(settings.getPlugin(), "See: https://github.com/tastybento/bsbMongo/releases/");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return null;
        }
        MongoDBDatabaseConnector connector = new MongoDBDatabaseConnector(settings);
        return new MongoDBDatabaseHandler<>(plugin, type, connector, settings);
    }

}
