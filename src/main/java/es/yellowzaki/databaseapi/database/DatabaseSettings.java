/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.yellowzaki.databaseapi.database;

import es.yellowzaki.databaseapi.database.DatabaseSetup.DatabaseType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Alberto
 */
public class DatabaseSettings {

    public static class DatabaseSettingsBuilder {

        private DatabaseSetup.DatabaseType databaseType = DatabaseSetup.DatabaseType.JSON;
        private int backupPeriod = 5;
        private String databaseHost = "localhost";
        private int databasePort = 3306;
        private String databaseName = "bentobox";
        private String databaseUsername = "username";
        private String databasePassword = "password";
        private boolean useSSL = false;
        private String mongodbConnectionUri = "";
        private String databasePrefix = "";

        private JavaPlugin plugin;

        public DatabaseSettingsBuilder(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        //In minutes
        public DatabaseSettingsBuilder backupPeriod(int backupPeriod) {
            this.backupPeriod = backupPeriod;
            return this;
        }

        public DatabaseSettingsBuilder databaseType(DatabaseSetup.DatabaseType databaseType) {
            this.databaseType = databaseType;
            return this;
        }

        public DatabaseSettingsBuilder databaseHost(String databaseHost) {
            this.databaseHost = databaseHost;
            return this;
        }

        public DatabaseSettingsBuilder useSSL(boolean useSSL) {
            this.useSSL = useSSL;
            return this;
        }

        public DatabaseSettingsBuilder databasePort(int databasePort) {
            this.databasePort = databasePort;
            return this;
        }

        public DatabaseSettingsBuilder databaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public DatabaseSettingsBuilder databaseUsername(String databaseUsername) {
            this.databaseUsername = databaseUsername;
            return this;
        }

        public DatabaseSettingsBuilder databasePassword(String databasePassword) {
            this.databasePassword = databasePassword;
            return this;
        }

        public DatabaseSettingsBuilder mongodbConnectionUri(String mongodbConnectionUri) {
            this.mongodbConnectionUri = mongodbConnectionUri;
            return this;
        }

        public DatabaseSettingsBuilder databasePrefix(String databasePrefix) {
            this.databasePrefix = databasePrefix;
            return this;
        }

        public DatabaseSettings build() {
            return new DatabaseSettings(databaseType, backupPeriod, databaseHost, databasePort, databaseName, databaseUsername, databasePassword, useSSL, mongodbConnectionUri, databasePrefix, plugin);
        }

    }

    private DatabaseType databaseType;
    private int backupPeriod;
    private String databaseHost;
    private int databasePort;
    private String databaseName;
    private String databaseUsername;
    private String databasePassword;
    private boolean useSSL;
    private String mongodbConnectionUri;
    private String databasePrefix;

    private JavaPlugin plugin;

    private DatabaseSettings(DatabaseType databaseType, int backupPeriod, String databaseHost, int databasePort, String databaseName, String databaseUsername, String databasePassword, boolean useSSL, String mongodbConnectionUri, String databasePrefix, JavaPlugin plugin) {
        this.databaseType = databaseType;
        this.backupPeriod = backupPeriod;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.useSSL = useSSL;
        this.mongodbConnectionUri = mongodbConnectionUri;
        this.databasePrefix = databasePrefix;
        this.plugin = plugin;
    }

    //In minutes
    public int getBackupPeriod() {
        return this.backupPeriod;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    /**
     * This method returns the useSSL value.
     *
     * @return the value of useSSL.
     * @since 1.12.0
     */
    public boolean isUseSSL() {
        return useSSL;
    }

    /**
     * This method sets the useSSL value.
     *
     * @param useSSL the useSSL new value.
     * @since 1.12.0
     */
    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getMongodbConnectionUri() {
        return mongodbConnectionUri;
    }

    /**
     * Set the MongoDB client connection URI.
     *
     * @param mongodbConnectionUri connection URI.
     * @since 1.14.0
     */
    public void setMongodbConnectionUri(String mongodbConnectionUri) {
        this.mongodbConnectionUri = mongodbConnectionUri;
    }

    /**
     * @return the databasePrefix
     */
    public String getDatabasePrefix() {
        if (databasePrefix == null) {
            databasePrefix = "";
        }
        return databasePrefix.isEmpty() ? "" : databasePrefix.replaceAll("[^a-zA-Z0-9]", "_");
    }

    /**
     * @param databasePrefix the databasePrefix to set
     */
    public void setDatabasePrefix(String databasePrefix) {
        this.databasePrefix = databasePrefix;
    }

}
