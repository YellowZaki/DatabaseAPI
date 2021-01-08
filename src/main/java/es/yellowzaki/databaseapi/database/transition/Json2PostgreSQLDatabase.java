package es.yellowzaki.databaseapi.database.transition;

import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;
import es.yellowzaki.databaseapi.database.json.JSONDatabase;
import es.yellowzaki.databaseapi.database.sql.postgresql.PostgreSQLDatabase;

/**
 * @author Poslovitch
 * @since 1.6.0
 */
public class Json2PostgreSQLDatabase implements DatabaseSetup {
    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> dataObjectClass, DatabaseSettings settings) {
        return new TransitionDatabaseHandler<>(dataObjectClass, new JSONDatabase().getHandler(dataObjectClass, settings), new PostgreSQLDatabase().getHandler(dataObjectClass, settings));
    }
}
