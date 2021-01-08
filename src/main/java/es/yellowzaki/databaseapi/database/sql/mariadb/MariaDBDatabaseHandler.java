package es.yellowzaki.databaseapi.database.sql.mariadb;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.DatabaseConnector;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.sql.SQLConfiguration;
import es.yellowzaki.databaseapi.database.sql.SQLDatabaseHandler;

/**
 *
 * Class that inserts a <T> into the corresponding database-table.
 *
 * @author tastybento, barpec12
 *
 * @param <T>
 */
public class MariaDBDatabaseHandler<T> extends SQLDatabaseHandler<T> {

    /**
     * Handles the connection to the database and creation of the initial database schema (tables) for
     * the class that will be stored.
     * @param plugin - plugin object
     * @param type - the type of class to be stored in the database. Must inherit DataObject
     * @param databaseConnector - authentication details for the database
     */
    MariaDBDatabaseHandler(JavaPlugin plugin, Class<T> type, DatabaseConnector databaseConnector, DatabaseSettings settings) {
        super(plugin, type, databaseConnector, settings,
                new SQLConfiguration(plugin, type, settings)
                .schema("CREATE TABLE IF NOT EXISTS `[tableName]` (json JSON, uniqueId VARCHAR(255) GENERATED ALWAYS AS (JSON_EXTRACT(json, \"$.uniqueId\")), UNIQUE INDEX i (uniqueId))"));
    }
}
