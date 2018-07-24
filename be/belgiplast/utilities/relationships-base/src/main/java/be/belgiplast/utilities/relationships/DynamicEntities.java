/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.DynamicNamespace;
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
public class DynamicEntities extends AbstractNamespaceSupport implements Entities<DynamicEntityName,DynamicRelationshipName>{

    private String name = "entities";
    private final DynamicEntities.DynamicEntityFactory eFactory;
    private final RelationshipFactory<Relationship<DynamicRelationshipName>> rFactory;
    private final List<Entity<DynamicEntityName>> entities = new ArrayList<>();
    private DynamicNamespaceSystem namespaceSystem;

    public DynamicEntities(String name) {
        if (name != null)
            this.name = name;
        eFactory = new DynamicEntityFactory(this);
        rFactory = new DynamicRelationshipFactory(this);
        namespaceSystem = new DynamicNamespaceSystem();
    }
    
    public Entity<DynamicEntityName> createEntity(String name,String type){
        eFactory.setType((EntityType)namespaceSystem.findName(type));
        return eFactory.createEntity(name);
    }
    
    public Relationship<DynamicRelationshipName> createRelationship(String name,String type){
        //rFactory.setType((RelationshipType)namespaceSystem.findName(type));
        return rFactory.createRelationship(name);
    }
    
    @Override
    public EntityFactory<Entity<DynamicEntityName>> getEntityFactory() {
        return eFactory;
    }

    @Override
    public RelationshipFactory<Relationship<DynamicRelationshipName>> getRelationshipFactory() {
        return rFactory;
    }

    @Override
    public Collection<Entity<DynamicEntityName>> getEntities() {
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
    
    private class DynamicEntityFactory extends EntityFactory<Entity<DynamicEntityName>>{

        private EntityType type;

        public DynamicEntityFactory(Namespace namespace) {
            super(namespace);
        }

        public EntityType getType() {
            return type;
        }

        public void setType(EntityType type) {
            this.type = type;
        }
        
        @Override
        protected Entity<DynamicEntityName> newInstance(String name,Namespace namespace) {
            DynamicEntity entity = new DynamicEntity(type,name,namespace);
            return entity;
        }
    }
    
    private class DynamicRelationshipFactory extends RelationshipFactory<Relationship<DynamicRelationshipName>>{

        private RelationshipType type;

        public DynamicRelationshipFactory(Namespace namespace) {
            super(namespace);
        }

        public RelationshipType getType() {
            return type;
        }

        public void setType(RelationshipType type) {
            this.type = type;
        }
        
        @Override
        protected Relationship<DynamicRelationshipName> newInstance(String name,Namespace namespace) {
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
