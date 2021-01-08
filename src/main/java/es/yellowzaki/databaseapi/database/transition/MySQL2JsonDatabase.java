package es.yellowzaki.databaseapi.database.transition;

import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;
import es.yellowzaki.databaseapi.database.json.JSONDatabase;
import es.yellowzaki.databaseapi.database.sql.mysql.MySQLDatabase;

/**
 * @author tastybento
 * @since 1.5.0
 */
public class MySQL2JsonDatabase implements DatabaseSetup {

    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> type, DatabaseSettings settings) {
        return new TransitionDatabaseHandler<>(type, new MySQLDatabase().getHandler(type, settings), new JSONDatabase().getHandler(type, settings));
    }

}
