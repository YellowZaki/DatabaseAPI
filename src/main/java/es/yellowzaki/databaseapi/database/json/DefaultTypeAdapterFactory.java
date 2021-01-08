package es.yellowzaki.databaseapi.database.json;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import es.yellowzaki.databaseapi.database.json.adapters.BukkitObjectTypeAdapter;
import es.yellowzaki.databaseapi.database.json.adapters.ItemStackTypeAdapter;
import es.yellowzaki.databaseapi.database.json.adapters.LocationTypeAdapter;
import es.yellowzaki.databaseapi.database.json.adapters.PotionEffectTypeAdapter;
import es.yellowzaki.databaseapi.database.json.adapters.VectorTypeAdapter;
import es.yellowzaki.databaseapi.database.json.adapters.WorldTypeAdapter;

/**
 * Allocates type adapters based on class type.
 *
 * @author tastybento
 *
 */
public class DefaultTypeAdapterFactory implements TypeAdapterFactory {

    /**
     * @param plugin plugin
     */
    public DefaultTypeAdapterFactory() {

    }

    /* (non-Javadoc)
     * @see com.google.gson.TypeAdapterFactory#create(com.google.gson.Gson, com.google.gson.reflect.TypeToken)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<?> rawType = type.getRawType();
        if (Location.class.isAssignableFrom(rawType)) {
            // Use our current location adapter for backward compatibility
            return (TypeAdapter<T>) new LocationTypeAdapter();
        } else if (ItemStack.class.isAssignableFrom(rawType)) {
            // Use our current location adapter for backward compatibility
            return (TypeAdapter<T>) new ItemStackTypeAdapter();
        } else if (PotionEffectType.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new PotionEffectTypeAdapter();
        } else if (World.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new WorldTypeAdapter();
        } else if (Vector.class.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) new VectorTypeAdapter();
        } else if (ConfigurationSerializable.class.isAssignableFrom(rawType)) {
            // This covers a lot of Bukkit objects
            return (TypeAdapter<T>) new BukkitObjectTypeAdapter(gson.getAdapter(Map.class));
        }
        return null;
    }

}
