/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author benoit
 */
public class Context {
    private final Map<String, NamespaceDef> namespaces = new HashMap<>();
    private final Map<String, EntityDef> entities = new HashMap<>();
    private final Map<String, RelationshipDef> relationships = new HashMap<>();

    public Map<String, NamespaceDef> getNamespaces() {
        return namespaces;
    }

    public Map<String, EntityDef> getEntities() {
        return entities;
    }

    public Map<String, RelationshipDef> getRelationships() {
        return relationships;
    }
    
    
}
