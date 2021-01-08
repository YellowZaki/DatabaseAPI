/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.yellowzaki.databaseapi;

import com.google.gson.TypeAdapterFactory;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.json.TypeAdapterFactories;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Alberto
 */
public class DatabaseAPI extends JavaPlugin {

    private static DatabaseAPI instance;

    private boolean shutdown;

    public static DatabaseAPI getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Stop all async database tasks
        shutdown = true;
    }

    //Methods for users
    public static void addTypeAdapterFactory(TypeAdapterFactory type) {
        TypeAdapterFactories.addTypeAdapterFactory(type);
    }

    public boolean isShutdown() {
        return shutdown;
    }

}
