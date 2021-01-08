package es.yellowzaki.databaseapi.database.objects;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Contains fields that must be in any data object
 * DataObject's canonical name must be no more than 62 characters long otherwise it may not fit in a database table name
 * @author tastybento
 *
 */
public interface DataObject {

//    default JavaPlugin getPlugin() {
//        return JavaPlugin.getInstance();
//    }

    /**
     * @return the uniqueId
     */
    String getUniqueId();

    /**
     * @param uniqueId - unique ID the uniqueId to set
     */
    void setUniqueId(String uniqueId);

}
