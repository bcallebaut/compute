/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import be.belgiplast.utilities.namespaces.support.AbstractNamespaceSupport;
import java.util.Collection;

/**
 * Allows the creation of names dynamically.
 * Useful for uses cases like mind maps or variables cration in namespace
 * @author benoit
 */
public class DynamicNamespace extends AbstractNamespaceSupport implements Namespace {

    public DynamicNamespace(String name, Namespace parent) {
        super(name, parent);
    }
    
    public DynamicNamespace(String name) {
        super(name, null);
    }

    @Override
    public <E extends NamedItem> E findByName(String name) {
        E result = super.findByName(name);
        if (result == null){            
            addName((Name)(result = ((E)new DynamicName(name,this))));
            return result;
        }
        return result;
    }
    
    
    public Namespace findNamespaceByName(String name) {
        Namespace result = super.findByName(name);
        if (result == null){            
            addNamespace(result = new DynamicNamespace(name,this));
            return result;
        }
        return result;
    }

    @Override
    public void addNamespace(Namespace name) {
        super.addNamespace(name); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addName(Name name) {
        super.addName(name); //To change body of generated methods, choose Tools | Templates.
    } 
}
