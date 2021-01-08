/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.yellowzaki.databaseapi.database.json;

import com.google.gson.TypeAdapterFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alberto
 */
public class TypeAdapterFactories {
    
    private static List<TypeAdapterFactory> factories = new ArrayList();
    
    static {
        List<TypeAdapterFactory> factories = new ArrayList();
        factories.add(new DefaultTypeAdapterFactory());
        TypeAdapterFactories.factories = factories;
    }
    
    
    public static List<TypeAdapterFactory> getTypeAdapterFactories() {
        return factories;
    }
    
    public static void addTypeAdapterFactory(TypeAdapterFactory type) {
        factories.add(type);
    }
}
