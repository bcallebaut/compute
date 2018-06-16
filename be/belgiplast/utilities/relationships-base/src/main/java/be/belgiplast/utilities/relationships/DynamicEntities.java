/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.DynamicName;
import be.belgiplast.utilities.namespaces.DynamicNamespace;
import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.namespaces.NamespaceSystem;
import be.belgiplast.utilities.namespaces.StringPathIterator;
import be.belgiplast.utilities.namespaces.support.AbstractNamespaceSupport;
import be.belgiplast.utilities.relationships.factories.EntityFactory;
import be.belgiplast.utilities.relationships.factories.RelationshipFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author benoit
 */
public class DynamicEntities extends AbstractNamespaceSupport implements Entities<DynamicName>{

    private String name = "entities";
    private final DynamicEntities.DynamicEntityFactory eFactory;
    private final RelationshipFactory<Relationship<DynamicName>> rFactory;
    private final List<Entity<DynamicName>> entities = new ArrayList<>();
    private DynamicNamespaceSystem namespaceSystem;

    public DynamicEntities(String name) {
        if (name != null)
            this.name = name;
        eFactory = new DynamicEntityFactory(this);
        rFactory = new DynamicRelationshipFactory(this);
        namespaceSystem = new DynamicNamespaceSystem();
    }
    
    public Entity<DynamicName> createEntity(String name,String type){
        eFactory.setType((Name)namespaceSystem.findName(type));
        return eFactory.createEntity(name);
    }
    
    public Relationship<DynamicName> createRelationship(String name,String type){
        eFactory.setType((Name)namespaceSystem.findName(type));
        return rFactory.createRelationship(name);
    }
    
    @Override
    public EntityFactory<Entity<DynamicName>> getEntityFactory() {
        return eFactory;
    }

    @Override
    public RelationshipFactory<Relationship<DynamicName>> getRelationshipFactory() {
        return rFactory;
    }

    @Override
    public Collection<Entity<DynamicName>> getEntities() {
        return entities;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Namespace getParent() {
        return null;
    }
    
    private class DynamicEntityFactory extends EntityFactory<Entity<DynamicName>>{

        private Name type;

        public DynamicEntityFactory(Namespace namespace) {
            super(namespace);
        }

        public Name getType() {
            return type;
        }

        public void setType(Name type) {
            this.type = type;
        }
        
        @Override
        protected Entity<DynamicName> newInstance(String name,Namespace namespace) {
            DynamicEntity entity = new DynamicEntity(type,name,namespace);
            return entity;
        }
    }
    
    private class DynamicRelationshipFactory extends RelationshipFactory<Relationship<DynamicName>>{

        private Name type;

        public DynamicRelationshipFactory(Namespace namespace) {
            super(namespace);
        }

        public Name getType() {
            return type;
        }

        public void setType(Name type) {
            this.type = type;
        }
        
        @Override
        protected Relationship<DynamicName> newInstance(String name,Namespace namespace) {
            DynamicRelationship entity = new DynamicRelationship(namespace,name,type);
            return entity;
        }
    }
    
    private class DynamicNamespaceSystem extends NamespaceSystem{

        @Override
        protected Namespace createRoot() {
            return new DynamicNamespace(DynamicEntities.this.name);
        }

        @Override
        protected void registerPathIterators(List iterators) {
            iterators.add(new StringPathIterator("."));
        }
    }
}
