/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.DynamicName;
import be.belgiplast.utilities.namespaces.Namespace;

/**
 *
 * @author benoit
 */
public class DynamicEntityName extends DynamicName implements EntityType{
    
    public DynamicEntityName(String name, Namespace parent) {
        super(name, parent);
    }
    
}
