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
import java.util.List;

/**
 *
 * @author benoit
 */
public class EntitySupport implements Entity{

    private Name type;
    private String name;
    private Namespace parent;
    private final List<Relationship<Name>> relationships;

    public EntitySupport(Name type, String name, Namespace parent) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        relationships = new ArrayList<>();
    }
    
    protected EntitySupport(Name type, String name, Namespace parent, List<Relationship<Name>> relationships) {
        this.type = type;
        this.name = name;
        this.parent = parent;
        this.relationships = relationships;
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
        return relationships;
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
