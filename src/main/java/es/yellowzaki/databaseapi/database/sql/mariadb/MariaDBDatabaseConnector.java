package es.yellowzaki.databaseapi.database.sql.mariadb;

import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.sql.SQLDatabaseConnector;

/**
 * @author barpec12
 * @since 1.1
 */
public class MariaDBDatabaseConnector extends SQLDatabaseConnector {

    /**
     * Class for MariaDB database connections using the settings provided
     * @param dbSettings - database settings
     */
    MariaDBDatabaseConnector(DatabaseSettings dbSettings) {
        super(dbSettings, "jdbc:mysql://" + dbSettings.getDatabaseHost() + ":" + dbSettings.getDatabasePort() + "/" + dbSettings.getDatabaseName()
        + "?autoReconnect=true&useSSL=" + dbSettings.isUseSSL() + "&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8");
    }

}
