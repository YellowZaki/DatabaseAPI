package es.yellowzaki.databaseapi.database;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

import es.yellowzaki.databaseapi.database.json.JSONDatabase;
import es.yellowzaki.databaseapi.database.mongodb.MongoDBDatabase;
import es.yellowzaki.databaseapi.database.sql.mariadb.MariaDBDatabase;
import es.yellowzaki.databaseapi.database.sql.mysql.MySQLDatabase;
import es.yellowzaki.databaseapi.database.sql.postgresql.PostgreSQLDatabase;
import es.yellowzaki.databaseapi.database.sql.sqlite.SQLiteDatabase;
import es.yellowzaki.databaseapi.database.transition.Json2MariaDBDatabase;
import es.yellowzaki.databaseapi.database.transition.Json2MongoDBDatabase;
import es.yellowzaki.databaseapi.database.transition.Json2MySQLDatabase;
import es.yellowzaki.databaseapi.database.transition.Json2PostgreSQLDatabase;
import es.yellowzaki.databaseapi.database.transition.Json2SQLiteDatabase;
import es.yellowzaki.databaseapi.database.transition.MariaDB2JsonDatabase;
import es.yellowzaki.databaseapi.database.transition.MongoDB2JsonDatabase;
import es.yellowzaki.databaseapi.database.transition.MySQL2JsonDatabase;
import es.yellowzaki.databaseapi.database.transition.PostgreSQL2JsonDatabase;
import es.yellowzaki.databaseapi.database.transition.SQLite2JsonDatabase;

/**
 * @author Poslovitch, tastybento
 */
public interface DatabaseSetup {

    /**
     * Gets the type of database being used.
     * Currently supported options are YAML, JSON, MYSQL, MARIADB and MONGODB.
     * Default is JSON.
     * @return Database type
     */
    static DatabaseSetup getDatabase(DatabaseSettings settings) {

        return Arrays.stream(DatabaseType.values())
                .filter(settings.getDatabaseType()::equals)
                .findFirst()
                .map(t -> t.database)
                .orElse(DatabaseType.JSON.database);
    }

    /**
     * Database types
     *
     */
    enum DatabaseType {


        JSON(new JSONDatabase()),
        /**
         * Transition database, from JSON to MySQL
         * @since 1.5.0
         */
        JSON2MYSQL(new Json2MySQLDatabase()),
        /**
         * Transition database, from JSON to MySQL (MariaDB)
         * @since 1.5.0
         */
        JSON2MARIADB(new Json2MariaDBDatabase()),

        /**
         * Transition database, from JSON to MongoDB
         * @since 1.6.0
         */
        JSON2MONGODB(new Json2MongoDBDatabase()),

        /**
         * Transition database, from JSON to SQLite
         * @since 1.6.0
         */
        JSON2SQLITE(new Json2SQLiteDatabase()),

        /**
         * Transition database, from JSON to PostgreSQL
         * @since 1.6.0
         */
        JSON2POSTGRESQL(new Json2PostgreSQLDatabase()),

        MYSQL(new MySQLDatabase()),

        /**
         * Transition database, from MySQL to JSON
         * @since 1.5.0
         */
        MYSQL2JSON(new MySQL2JsonDatabase()),
        /**
         * @since 1.1
         */
        MARIADB(new MariaDBDatabase()),

        /**
         * Transition database, from MariaDB to JSON
         * @since 1.6.0
         */
        MARIADB2JSON(new MariaDB2JsonDatabase()),

        MONGODB(new MongoDBDatabase()),

        /**
         * Transition database, from MongoDB to JSON
         * @since 1.6.0
         */
        MONGODB2JSON(new MongoDB2JsonDatabase()),

        /**
         * @since 1.6.0
         */
        SQLITE(new SQLiteDatabase()),

        /**
         * Transition database, from SQLite to JSON
         * @since 1.6.0
         */
        SQLITE2JSON(new SQLite2JsonDatabase()),

        /**
         * @since 1.6.0
         */
        POSTGRESQL(new PostgreSQLDatabase()),

        /**
         * Transition database, from PostgreSQL to JSON
         * @since 1.6.0
         */
        POSTGRESQL2JSON(new PostgreSQL2JsonDatabase());

        DatabaseSetup database;

        DatabaseType(DatabaseSetup database){
            this.database = database;
        }
    }

    /**
     * Gets a database handler that will store and retrieve classes of type dataObjectClass
     * @param <T> - Class type
     * @param dataObjectClass - class of the object to be stored in the database
     * @return handler for this database object
     */
    <T> AbstractDatabaseHandler<T> getHandler(Class<T> dataObjectClass, DatabaseSettings settings);

}