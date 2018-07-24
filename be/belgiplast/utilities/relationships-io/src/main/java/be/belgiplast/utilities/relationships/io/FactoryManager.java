/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.io;

import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.DynamicEntity;
import be.belgiplast.utilities.relationships.DynamicRelationship;
import be.belgiplast.utilities.relationships.Entities;
import be.belgiplast.utilities.relationships.Entity;
import be.belgiplast.utilities.relationships.EntityType;
import be.belgiplast.utilities.relationships.Relationship;
import be.belgiplast.utilities.relationships.RelationshipType;
import be.belgiplast.utilities.relationships.io.spi.FactoryProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 *
 * @author benoit
 */
public abstract class FactoryManager implements RelationshipsFactory{
    private static FactoryManager instance = new DefaultFactoryManager();

    public static FactoryManager getInstance() {
        return instance;
    }
    
    protected Namespace namespace;
    protected Namespace homeNamespace;
    protected final Map<String,RelationshipsFactory> entitiesfactories = new HashMap<>();
    protected final Map<String,RelationshipsFactory> relationshipsfactories = new HashMap<>();

    public FactoryManager() {
    }

    @Override
    public Entity createEntity(String type, String name) {
        RelationshipsFactory factory = entitiesfactories.get(type);
        if (factory == null)
            return new DynamicEntity((EntityType)namespace.findNameByName(type),name,homeNamespace);
        return factory.createEntity(type, name);
    }
    
    public abstract Entities createEntities();

    @Override
    public Relationship createRelationship(String type,String id) {
        RelationshipsFactory factory = entitiesfactories.get(type);
        if (factory == null)
            return new DynamicRelationship(homeNamespace,id,(RelationshipType)namespace.findNameByName(type));
        return factory.createRelationship(type,id);
    }
    
    private static class DefaultFactoryManager extends FactoryManager{

        public DefaultFactoryManager() {
            ServiceLoader<FactoryProvider> loader = ServiceLoader.load(FactoryProvider.class);
            for (FactoryProvider fp : loader){
                RelationshipsFactory factory = fp.getFactory();
                fp.getEntitiesNames().forEach((name) -> {
                    entitiesfactories.put(name,factory);
                });
                fp.getRelationshipsNames().forEach((name) -> {
                    relationshipsfactories.put(name,factory);
                });
            }
        }

        @Override
        public Entities createEntities() {
            return new DefaultEntities();
        }

        public Namespace getNamespace() {
            return namespace;
        }

        public void setNamespace(Namespace namespace) {
            this.namespace = namespace;
        }

        public Namespace getHomeNamespace() {
            return homeNamespace;
        }

        public void setHomeNamespace(Namespace homeNamespace) {
            this.homeNamespace = homeNamespace;
        }
    }
}
