package es.yellowzaki.databaseapi.database.sql.mysql;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;

public class MySQLDatabase implements DatabaseSetup {


    /* (non-Javadoc)
     * @see es.yellowzaki.databaseapi.database.DatabaseSetup#getHandler(java.lang.Class)
     */
    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> type, DatabaseSettings settings) {
        JavaPlugin plugin = settings.getPlugin();

        MySQLDatabaseConnector connector = new MySQLDatabaseConnector(settings);

        return new MySQLDatabaseHandler<>(plugin, type, connector, settings);
    }

}
