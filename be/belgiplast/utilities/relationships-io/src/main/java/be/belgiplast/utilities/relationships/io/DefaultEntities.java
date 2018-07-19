/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.io;

import be.belgiplast.utilities.namespaces.DynamicNamespace;
import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.namespaces.support.AbstractNamespaceSupport;
import be.belgiplast.utilities.relationships.DynamicEntity;
import be.belgiplast.utilities.relationships.DynamicRelationship;
import be.belgiplast.utilities.relationships.Entities;
import be.belgiplast.utilities.relationships.Entity;
import be.belgiplast.utilities.relationships.Relationship;
import be.belgiplast.utilities.relationships.factories.EntityFactory;
import be.belgiplast.utilities.relationships.factories.RelationshipFactory;
import java.util.Collection;

/**
 *
 * @author benoit
 */
public class DefaultEntities extends AbstractNamespaceSupport implements Entities<Name>{

    private String name;
    private DynamicNamespace entities;
    private DynamicNamespace relationships;
    private DynamicNamespace names;
    
    private final DefaultEntityFactory entityFactory;
    private final DefaultRelationshipFactory relationshipFactory;

    public DefaultEntities() {
        this("default");
    }

    public DefaultEntities(String name) {
        super(name,null);
        entities = new DynamicNamespace("entities",this);
        relationships = new DynamicNamespace("relationships",this);
        names = new DynamicNamespace("names",this);
        entityFactory = new DefaultEntityFactory(this);
        relationshipFactory = new DefaultRelationshipFactory(this);
    }

    public DynamicNamespace getRelationships() {
        return relationships;
    }

    @Override
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    @Override
    public RelationshipFactory getRelationshipFactory() {
        return relationshipFactory;
    }

    @Override
    public Collection<Entity<Name>> getEntities() {
        return (Collection<Entity<Name>>)entities.getNames();
    }
    
    public Entity createEntity(String name){
        return entityFactory.createEntity(name);
    }

    public final Relationship createRelationship(String name) {
        return relationshipFactory.createRelationship(name);
    }
    
    private class DefaultEntityFactory extends EntityFactory{

        public DefaultEntityFactory(Namespace namespace) {
            super(namespace);
        }

        @Override
        protected Entity newInstance(String name, Namespace namespace) {
            Entity entity = new DynamicEntity(null,name,namespace);
            DefaultEntities.this.entities.addName(entity);
            return entity;
        }
    }
    
    private class DefaultRelationshipFactory extends RelationshipFactory{

        public DefaultRelationshipFactory(Namespace namespace) {
            super(namespace);
        }
        
        @Override
        protected Relationship newInstance(String name, Namespace namespace) {
            Relationship relationship = new DynamicRelationship(namespace,name,null);
            DefaultEntities.this.relationships.addName(relationship);
            return relationship;
        }
        
    }
}
