/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.factories;

import be.belgiplast.utilities.relationships.Entity;
import java.util.HashMap;

/**
 *
 * @author benoit
 * @param <E>
 */
public abstract class EntityFactory <E extends Entity> {
    private final HashMap<String,E> entities = new HashMap<>();
    
    protected abstract E newInstance(String name);
    
    public final E createEntity(String name){
        if (entities.containsKey(name))
            return entities.get(name);
        E instance = newInstance(name);
        if (instance == null)
            return null;
        entities.put(name, instance);
        return instance;
    }
    
    public final void discardEntity(String name){
        entities.remove(name);
    }
    
    public final void discardEntity(E entity){
        discardEntity(entity.getName());
    }
}
