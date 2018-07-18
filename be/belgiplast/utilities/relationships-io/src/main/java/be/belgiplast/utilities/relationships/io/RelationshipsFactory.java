/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.io;

import be.belgiplast.utilities.relationships.Entities;
import be.belgiplast.utilities.relationships.Entity;
import be.belgiplast.utilities.relationships.Relationship;

/**
 *
 * @author benoit
 */
public interface RelationshipsFactory {
    Entity createEntity(String type, String name);
    Relationship createRelationship(String type,String id);
    Entities createEntities();
}
