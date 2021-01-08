package es.yellowzaki.databaseapi.database.json;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;

public class JSONDatabase implements DatabaseSetup {


    /* (non-Javadoc)
     * @see es.yellowzaki.databaseapi.database.DatabaseSetup#getHandler(java.lang.Class)
     */
    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> dataObjectClass, DatabaseSettings settings) {
        JSONDatabaseConnector connector = new JSONDatabaseConnector(settings.getPlugin());
        return new JSONDatabaseHandler<>(settings.getPlugin(), dataObjectClass, connector, settings);
    }
}
