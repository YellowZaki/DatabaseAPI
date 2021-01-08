package es.yellowzaki.databaseapi.database.sql.postgresql;

import es.yellowzaki.databaseapi.database.DatabaseSettings;
import org.eclipse.jdt.annotation.NonNull;
import org.postgresql.Driver;

import es.yellowzaki.databaseapi.database.sql.SQLDatabaseConnector;

/**
 * @since 1.6.0
 * @author Poslovitch
 */
public class PostgreSQLDatabaseConnector extends SQLDatabaseConnector {

    /*
     * Ensure the driver is loaded as JDBC Driver might be invisible to Java's ServiceLoader.
     * Usually, this is not required as {@link DriverManager} detects JDBC drivers
     * via {@code META-INF/services/java.sql.Driver} entries. However there might be cases when the driver
     * is located at the application level classloader, thus it might be required to perform manual
     * registration of the driver.
     */
    static {
        new Driver();
    }
    
    /**
     * Class for PostgreSQL database connections using the settings provided
     * @param dbSettings - database settings
     */
    PostgreSQLDatabaseConnector(@NonNull DatabaseSettings dbSettings) {
        super(dbSettings, "jdbc:postgresql://" + dbSettings.getDatabaseHost() + ":" + dbSettings.getDatabasePort() + "/" + dbSettings.getDatabaseName()
        + "?autoReconnect=true&useSSL=" + dbSettings.isUseSSL() + "&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8");
    }
}
