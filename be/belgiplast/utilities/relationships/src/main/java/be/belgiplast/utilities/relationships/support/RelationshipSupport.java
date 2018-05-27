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

/**
 *
 * @author benoit
 */
public class RelationshipSupport<N extends Name> implements Relationship<Name>{

    private Namespace parent;
    private String name;
    private N id;
    private Entity from;
    private Entity to;
    private Relationship inverse;

    public RelationshipSupport(Namespace parent, String name, N id) {
        this.parent = parent;
        this.name = name;
        this.id = id;
        this.inverse = null;
    }
    
    public RelationshipSupport(Relationship rel, String name, N id) {
        this.parent = rel.getParent();
        this.name = name;
        this.id = id;
        this.inverse = rel;
    }

    protected void setName(String name) {
        this.name = name;
    }
    
    @Override
    public N getId() {
        return id;
    }

    @Override
    public Entity from() {
        return from;
    }

    @Override
    public Entity to() {
        return to;
    }

    public void setFrom(Entity from) {
        this.from = from;
    }

    public void setTo(Entity to) {
        this.to = to;
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
    public final Relationship getInverse() {
        if (inverse == null)
            inverse = createInverse();
        return inverse;
    }
    
    protected Relationship createInverse(){
        return null;
    };
}
