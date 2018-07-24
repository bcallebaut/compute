/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.support;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.Entity;
import be.belgiplast.utilities.relationships.EntityType;
import be.belgiplast.utilities.relationships.Relationship;
import be.belgiplast.utilities.relationships.RelationshipType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author benoit
 */
public class EntitySupport implements Entity{

    private EntityType type;
    private String name;
    private Namespace parent;
    private final Map<Name,List<Relationship<RelationshipType>>> relationships;

    public EntitySupport(EntityType type, String name, Namespace parent) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        relationships = new HashMap<>();
    }
    
    protected EntitySupport(EntityType type, String name, Namespace parent, List<Relationship<RelationshipType>> relationships) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        this.relationships = new HashMap<>();
        for (Relationship<RelationshipType> rel : relationships)
            addRelationship(rel);
    }

    protected void setName(String name) {
        this.name = name;
    }
    
    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public List<Relationship<RelationshipType>> getRelationships(RelationshipType type) {
        List<Relationship<RelationshipType>> result = relationships.get(type);
        if (result == null)
            return Collections.emptyList();
        return result;
    }
    
    public List<Relationship<RelationshipType>> getRelationships() {
        List<Relationship<RelationshipType>> result = new ArrayList<>();
        for (List<Relationship<RelationshipType>> l : relationships.values())
            result.addAll(l);
        return result;
    }
    
    protected void addRelationship(Relationship rel){
        List<Relationship<RelationshipType>> result = relationships.get(rel.getId());
        if (result == null){
            result = new ArrayList<Relationship<RelationshipType>>();
            relationships.put(rel.getId(),result);
        }
        result.add(rel);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Namespace getParent() {
        return parent;
    }
}
