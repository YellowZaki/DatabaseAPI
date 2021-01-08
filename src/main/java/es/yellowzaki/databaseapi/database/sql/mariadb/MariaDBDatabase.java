package es.yellowzaki.databaseapi.database.sql.mariadb;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;

/**
 * @author barpec12
 * @since 1.1
 */
public class MariaDBDatabase implements DatabaseSetup {

    /* (non-Javadoc)
     * @see es.yellowzaki.databaseapi.database.DatabaseSetup#getHandler(java.lang.Class)
     */
    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> type, DatabaseSettings settings) {
        JavaPlugin plugin = settings.getPlugin();

        MariaDBDatabaseConnector connector = new MariaDBDatabaseConnector(settings);

        return new MariaDBDatabaseHandler<>(plugin, type, connector, settings);
    }

}
