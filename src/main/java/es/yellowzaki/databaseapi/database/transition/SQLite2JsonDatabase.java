package es.yellowzaki.databaseapi.database.transition;

import es.yellowzaki.databaseapi.database.AbstractDatabaseHandler;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.DatabaseSetup;
import es.yellowzaki.databaseapi.database.json.JSONDatabase;
import es.yellowzaki.databaseapi.database.sql.sqlite.SQLiteDatabase;


/**
 * @author BONNe
 * @since 1.6.0
 */
public class SQLite2JsonDatabase implements DatabaseSetup {

    @Override
    public <T> AbstractDatabaseHandler<T> getHandler(Class<T> type, DatabaseSettings settings) {
        return new TransitionDatabaseHandler<>(type, new SQLiteDatabase().getHandler(type, settings), new JSONDatabase().getHandler(type, settings));
    }

}
