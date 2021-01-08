package es.yellowzaki.databaseapi.database.sql.postgresql;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;

/**
 * @since 1.6.0
 * @author Poslovitch
 */
public class PostgreSQLDatabase implements DatabaseSetup {

    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> dataObjectClass, DatabaseSettings settings) {
        JavaPlugin plugin = settings.getPlugin();

        PostgreSQLDatabaseConnector connector = new PostgreSQLDatabaseConnector(settings);

        return new PostgreSQLDatabaseHandler<>(plugin, dataObjectClass, connector, settings);
    }
}
