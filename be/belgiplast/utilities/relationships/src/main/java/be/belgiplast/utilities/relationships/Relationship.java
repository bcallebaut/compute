/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.Name;

/**
 *
 * @author benoit
 */
public interface Relationship<N extends Name> extends Name {
    N getId();
    Entity from();
    Entity to();
    
    default void discard(){
        if (from() != null){
            from().getRelationships(getId()).remove(this);
        }
        if (to() != null){
            to().getRelationships(getId()).remove(this);
        }
    }
    
    Relationship getInverse();
}
