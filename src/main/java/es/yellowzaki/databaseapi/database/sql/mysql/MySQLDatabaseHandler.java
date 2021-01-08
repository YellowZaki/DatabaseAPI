package es.yellowzaki.databaseapi.database.sql.mysql;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.DatabaseConnector;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.sql.SQLConfiguration;
import es.yellowzaki.databaseapi.database.sql.SQLDatabaseHandler;

/**
 *
 * Class that inserts a <T> into the corresponding database-table.
 *
 * @author tastybento
 *
 * @param <T>
 */
public class MySQLDatabaseHandler<T> extends SQLDatabaseHandler<T> {

    /**
     * Handles the connection to the database and creation of the initial database schema (tables) for
     * the class that will be stored.
     * @param plugin - plugin object
     * @param type - the type of class to be stored in the database. Must inherit DataObject
     * @param dbConnecter - authentication details for the database
     */
    MySQLDatabaseHandler(JavaPlugin plugin, Class<T> type, DatabaseConnector dbConnecter, DatabaseSettings settings) {
        super(plugin, type, dbConnecter, settings, new SQLConfiguration(plugin, type, settings)
                .schema("CREATE TABLE IF NOT EXISTS `[tableName]` (json JSON, uniqueId VARCHAR(255) GENERATED ALWAYS AS (json->\"$.uniqueId\"), UNIQUE INDEX i (uniqueId) ) ENGINE = INNODB"));
    }
}
