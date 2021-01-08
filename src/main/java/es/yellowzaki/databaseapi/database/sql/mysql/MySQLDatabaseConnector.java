package es.yellowzaki.databaseapi.database.sql.mysql;

import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.sql.SQLDatabaseConnector;

public class MySQLDatabaseConnector extends SQLDatabaseConnector {

    /**
     * Class for MySQL database connections using the settings provided
     * @param dbSettings - database settings
     */
    MySQLDatabaseConnector(DatabaseSettings dbSettings) {
        super(dbSettings, "jdbc:mysql://" + dbSettings.getDatabaseHost() + ":" + dbSettings.getDatabasePort() + "/" + dbSettings.getDatabaseName()
        + "?autoReconnect=true&useSSL=" + dbSettings.isUseSSL() + "&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8");
    }
}
