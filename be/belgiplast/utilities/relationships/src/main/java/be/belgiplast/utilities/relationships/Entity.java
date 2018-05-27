/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.Name;
import java.util.List;

/**
 *
 * @author benoit
 */
public interface Entity<N extends Name> extends Name {
    N getEntityType();
    <T extends Name> List<Relationship<T>> getRelationships(Name type);
}
