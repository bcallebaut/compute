/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.relationships.factories.EntityFactory;
import be.belgiplast.utilities.relationships.factories.RelationshipFactory;
import java.util.Collection;

/**
 *
 * @author benoit
 */
public interface Entities<N extends Name> extends Name{
    EntityFactory<Entity<N>> getEntityFactory();
    RelationshipFactory<Relationship<N>> getRelationshipFactory();
    Collection<Entity<N>> getEntities();
}
