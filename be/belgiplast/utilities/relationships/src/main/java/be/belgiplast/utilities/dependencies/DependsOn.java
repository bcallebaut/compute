/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.dependencies;

import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.namespaces.SystemNamespace;
import be.belgiplast.utilities.relationships.Entity;
import be.belgiplast.utilities.relationships.Relationship;

/**
 *
 * @author benoit
 */
public class DependsOn implements Relationship<DependsOnName>{

    private Entity from;
    private Entity to;
    private final String name;
    private final Namespace parent;

    public DependsOn(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
    }

    public void setFrom(Entity from) {
        this.from = from;
    }

    public void setTo(Entity to) {
        this.to = to;
    }
    
    @Override
    public DependsOnName getId() {
        return ((Namespace)SystemNamespace.getInstance().findByName("dependencies")).findByName("dependsOn");
    }

    @Override
    public Entity from() {
        return from;
    }

    @Override
    public Entity to() {
        return to;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Namespace getParent() {
        return parent;
    }

    @Override
    public Relationship getInverse() {
        return null;
    }
    
}
