/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.factories;

import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.Entities;
import be.belgiplast.utilities.relationships.Relationship;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author benoit
 * @param <R>
 */
public abstract class RelationshipFactory<R extends Relationship> {
    private final Map<String,R> relationships = new HashMap<>();
    private Namespace namespace;
    
    protected abstract R newInstance(String name,Namespace namespace);

    public RelationshipFactory(Namespace namespace) {
        this.namespace = namespace;
    }
    
    public final R createRelationship(String name){
        if (relationships.containsKey(name))
            return relationships.get(name);
        R instance = newInstance(name, namespace);
        if (instance == null)
            return null;
        relationships.put(name, instance);
        return instance;
    }
    
    public final void discardRelationship(String name){
        relationships.remove(name);
    }
    
    public final void discardRelationship(R rel){
        relationships.remove(rel.getName());
    }
}
