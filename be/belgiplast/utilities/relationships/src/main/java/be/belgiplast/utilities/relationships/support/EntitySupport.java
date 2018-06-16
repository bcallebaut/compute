/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.support;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.Entity;
import be.belgiplast.utilities.relationships.Relationship;
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

    private Name type;
    private String name;
    private Namespace parent;
    private final Map<Name,List<Relationship<Name>>> relationships;

    public EntitySupport(Name type, String name, Namespace parent) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        relationships = new HashMap<>();
    }
    
    protected EntitySupport(Name type, String name, Namespace parent, List<Relationship<Name>> relationships) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        this.relationships = new HashMap<>();
        for (Relationship<Name> rel : relationships)
            addRelationship(rel);
    }

    protected void setName(String name) {
        this.name = name;
    }
    
    @Override
    public Name getEntityType() {
        return type;
    }

    @Override
    public List<Relationship<Name>> getRelationships(Name type) {
        List<Relationship<Name>> result = relationships.get(type);
        if (result == null)
            return Collections.emptyList();
        return result;
    }
    
    public List<Relationship<Name>> getRelationships() {
        List<Relationship<Name>> result = new ArrayList<>();
        for (List<Relationship<Name>> l : relationships.values())
            result.addAll(l);
        return result;
    }
    
    protected void addRelationship(Relationship rel){
        List<Relationship<Name>> result = relationships.get(rel.getId());
        if (result == null){
            result = new ArrayList<Relationship<Name>>();
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
