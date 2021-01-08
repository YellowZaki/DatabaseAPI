package es.yellowzaki.databaseapi.database.sql.sqlite;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;

/**
 * @since 1.6.0
 * @author Poslovitch
 */
public class SQLiteDatabase implements DatabaseSetup {



    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> dataObjectClass, DatabaseSettings settings) {
        SQLiteDatabaseConnector connector = new SQLiteDatabaseConnector(settings.getPlugin());
        return new SQLiteDatabaseHandler<>(settings.getPlugin(), dataObjectClass, connector, settings);
    }
}
