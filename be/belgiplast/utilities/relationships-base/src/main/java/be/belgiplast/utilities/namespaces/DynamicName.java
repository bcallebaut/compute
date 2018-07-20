/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import be.belgiplast.utilities.namespaces.support.AbstractNameSupport;

/**
 *
 * @author benoit
 */
public class DynamicName implements Name{

    private Name backend;

    protected DynamicName(Name backend) {        
        this.backend = backend;
    }
    
    public DynamicName(String name, Namespace parent) {
        this(new AbstractNameSupport(name,parent));
    }

    @Override
    public String getName() {
        return backend.getName();
    }

    @Override
    public Namespace getParent() {
        return backend.getParent();
    }
}
