DatabaseAPI
--------------------
This is a database API plugin extracted directly from BentoBox (https://github.com/BentoBoxWorld/BentoBox/tree/develop/src/main/java/world/bentobox/bentobox/database).
It has been adapted to be usable on any plugin.


How to use
--------------------

Example of the persistent class that must implements "DataObject":
```
public class PersistentObject implements DataObject {
    @Expose
    private String uniqueId;
    @Expose
    long attribute1;
}
```


Example of the manager:
```
public class PersistentObjectManager {
    private Database<PersistentObject> handler;
    
    public PersistentObjectManager(JavaPlugin plugin) {
        DatabaseSettings dbSettings = new DatabaseSettings.DatabaseSettingsBuilder(plugin)
                .databaseHost(databaseHost)
                .databaseType(databaseType)
                .databaseName(databaseName)
                .databasePort(databasePort)
                .databasePassword(databasePassword)
                .databaseUsername(databaseUsername)
                .databasePrefix(databasePrefix)
                .useSSL(useSSL)
                .mongodbConnectionUri(mongodbConnectionUri)
                .build();   
        handler = new Database<>(dbSettings, PersistentObject.class);
    }
}
```

Examples of methods of Database class:
```
List<PersistentObject> persistentObjectList = handler.loadObjects();
boolean exists = handler.objectExists(uniqueID);
PersistentObject persistentObject = handler.loadObject(uniqueID);
handler.saveObjectAsync(persistentObject);
handler.deleteObject(persistentObject);


```



