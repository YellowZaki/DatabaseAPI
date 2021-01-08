package es.yellowzaki.databaseapi.database.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.mongodb.MongoClientException;
import com.mongodb.MongoNamespace;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.util.JSON;

import org.bukkit.plugin.java.JavaPlugin;
import es.yellowzaki.databaseapi.database.DatabaseConnector;
import es.yellowzaki.databaseapi.database.DatabaseLogger;
import es.yellowzaki.databaseapi.database.DatabaseSettings;
import es.yellowzaki.databaseapi.database.json.AbstractJSONDatabaseHandler;
import es.yellowzaki.databaseapi.database.objects.DataObject;
import es.yellowzaki.databaseapi.database.objects.Table;

/**
 *
 * Class that inserts a <T> into the corresponding database-table.
 *
 * @author tastybento
 *
 * @param <T>
 */
@SuppressWarnings("deprecation")
public class MongoDBDatabaseHandler<T> extends AbstractJSONDatabaseHandler<T> {

    private static final String UNIQUEID = "uniqueId";
    private static final String MONGO_ID = "_id";

    private MongoCollection<Document> collection;
    private DatabaseConnector dbConnecter;

    /**
     * Handles the connection to the database and creation of the initial database schema (tables) for
     * the class that will be stored.
     * @param plugin - plugin object
     * @param type - the type of class to be stored in the database. Must inherit DataObject
     * @param dbConnecter - authentication details for the database
     */
    MongoDBDatabaseHandler(JavaPlugin plugin, Class<T> type, DatabaseConnector dbConnecter, DatabaseSettings settings) {
        super(plugin, type, dbConnecter, settings);
        this.dbConnecter = dbConnecter;

        boolean connected = true; // if it is set to false, it will consider there has been an error upon connecting.
        try {
            // Connection to the database
            MongoDatabase database = (MongoDatabase) dbConnecter.createConnection(dataObject);
            if (database == null) {
                DatabaseLogger.logError(settings.getPlugin(), "Could not connect to the database. Are the credentials in the config.yml file correct?");
                connected = false;
            } else {
                // Check for old collections
                String oldName = settings.getDatabasePrefix() + type.getCanonicalName();
                String newName = getName(plugin, dataObject);
                if (!oldName.equals((newName)) && collectionExists(database, oldName) && !collectionExists(database, newName)){
                    collection = database.getCollection(oldName);
                    collection.renameCollection(new MongoNamespace(database.getName(), newName));
                } else {
                    collection = database.getCollection(newName);
                }
                IndexOptions indexOptions = new IndexOptions().unique(true);
                collection.createIndex(Indexes.text(UNIQUEID), indexOptions);
            }
        } catch (MongoTimeoutException e) {
            DatabaseLogger.logError(settings.getPlugin(), "Could not connect to the database. MongoDB timed out.");
            DatabaseLogger.logError(settings.getPlugin(), "Error code: " + e.getCode());
            //DatabaseLogger.logError(settings.getPlugin(), "Errors: " + String.join(", ", e.getErrorLabels()));
            connected = false;
        } catch (MongoClientException e) {
            DatabaseLogger.logError(settings.getPlugin(), "Could not connect to the database. An unhandled error occurred.");
            DatabaseLogger.logStacktrace(e);
            connected = false;
        }

        if (!connected) {
            DatabaseLogger.logError(settings.getPlugin(), "Disabling JavaPlugin...");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    private boolean collectionExists(MongoDatabase database, final String collectionName) {
        for (final String name : database.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }

    private String getName(JavaPlugin plugin, Class<T> type) {
        return settings.getDatabasePrefix() +
                (type.getAnnotation(Table.class) == null ?
                        type.getCanonicalName()
                        : type.getAnnotation(Table.class)
                        .name());
    }

    @Override
    public List<T> loadObjects() {
        List<T> list = new ArrayList<>();
        Gson gson = getGson();
        for (Document document : collection.find(new Document())) {
            // The deprecated serialize option does not have a viable alternative without involving a huge amount of custom code
            String json = JSON.serialize(document);
            json = json.replaceFirst(MONGO_ID, UNIQUEID);
            try {
                list.add(gson.fromJson(json, dataObject));
            } catch (Exception e) {
                DatabaseLogger.logError(settings.getPlugin(), "Could not load object :" + e.getMessage());
            }
        }
        return list;
    }

    @Override
    public T loadObject(String uniqueId) {
        Document doc = collection.find(new Document(MONGO_ID, uniqueId)).limit(1).first();
        Gson gson = getGson();
        String json = JSON.serialize(doc).replaceFirst(MONGO_ID, UNIQUEID);
        // load single object
        return gson.fromJson(json, dataObject);
    }

    @Override
    public CompletableFuture<Boolean> saveObject(T instance) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        // Null check
        if (instance == null) {
            DatabaseLogger.logError(settings.getPlugin(), "MongoDB database request to store a null. ");
            completableFuture.complete(false);
            return completableFuture;
        }
        if (!(instance instanceof DataObject)) {
            DatabaseLogger.logError(settings.getPlugin(), "This class is not a DataObject: " + instance.getClass().getName());
            completableFuture.complete(false);
            return completableFuture;
        }
        DataObject dataObj = (DataObject)instance;
        try {
            Gson gson = getGson();
            String toStore = gson.toJson(instance);
            // Change uniqueId to _id
            toStore = toStore.replaceFirst(UNIQUEID, MONGO_ID);
            // This parses JSON to a Mongo Document
            Document document = Document.parse(toStore);
            // Filter based on the id
            Bson filter = new Document(MONGO_ID, dataObj.getUniqueId());
            // Set the options to upsert (update or insert if doc is not there)
            FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().upsert(true);
            // Do the deed
            collection.findOneAndReplace(filter, document, options);
            completableFuture.complete(true);
        } catch (Exception e) {
            DatabaseLogger.logError(settings.getPlugin(), "Could not save object " + instance.getClass().getName() + " " + e.getMessage());
            completableFuture.complete(false);
        }
        return completableFuture;
    }

    @Override
    public void deleteID(String uniqueId) {
        try {
            collection.findOneAndDelete(new Document(MONGO_ID, uniqueId));
        } catch (Exception e) {
            DatabaseLogger.logError(settings.getPlugin(), "Could not delete object " + getName(plugin, dataObject) + " " + uniqueId + " " + e.getMessage());
        }
    }

    @Override
    public void deleteObject(T instance) {
        // Null check
        if (instance == null) {
            DatabaseLogger.logError(settings.getPlugin(), "MondDB database request to delete a null. ");
            return;
        }
        if (!(instance instanceof DataObject)) {
            DatabaseLogger.logError(settings.getPlugin(), "This class is not a DataObject: " + instance.getClass().getName());
            return;
        }
        deleteID(((DataObject)instance).getUniqueId());
    }

    @Override
    public boolean objectExists(String uniqueId) {
        return collection.find(new Document(MONGO_ID, uniqueId)).first() != null;
    }

    @Override
    public void close() {
        dbConnecter.closeConnection(dataObject);
    }
}
