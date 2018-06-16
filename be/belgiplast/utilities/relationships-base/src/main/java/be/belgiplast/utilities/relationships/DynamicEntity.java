/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.support.EntitySupport;

/**
 *
 * @author benoit
 */
public class DynamicEntity extends EntitySupport{
    
    public DynamicEntity(Name type, String name, Namespace parent) {
        super(type, name, parent);
    }
    
}
