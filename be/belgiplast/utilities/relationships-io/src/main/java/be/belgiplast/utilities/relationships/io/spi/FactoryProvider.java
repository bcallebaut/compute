/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.io.spi;

import be.belgiplast.utilities.relationships.io.RelationshipsFactory;
import java.util.Collection;

/**
 *
 * @author benoit
 */
public interface FactoryProvider {
    Collection<String>   getRelationshipsNames();
    Collection<String>   getEntitiesNames();
    RelationshipsFactory getFactory();
}
