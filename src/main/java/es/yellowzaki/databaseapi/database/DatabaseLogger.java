/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.yellowzaki.databaseapi.database;

import es.yellowzaki.databaseapi.DatabaseAPI;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Alberto
 */
public class DatabaseLogger {

    public static void logError(JavaPlugin plugin, String str) {
        plugin.getLogger().log(Level.SEVERE, str);
    }

    public static void logWarning(JavaPlugin plugin, String str) {
        plugin.getLogger().log(Level.WARNING, str);
    }

    public static void logStacktrace(Exception e) {
        e.printStackTrace();
    }

    public static void logError(String str) {
        DatabaseAPI.getInstance().getLogger().log(Level.SEVERE, str);
    }

    public static void logWarning(String str) {
        DatabaseAPI.getInstance().getLogger().log(Level.WARNING, str);
    }

}
