/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces.support;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;

/**
 *
 * @author benoit
 */
public class AbstractNameSupport implements Name{
    private String name;
    private Namespace parent;

    public AbstractNameSupport(String name) {
        this.name = name;
    }

    public AbstractNameSupport(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
    }
    
    public void rename(String name){
        this.name = name;
    }
    
    public final void hook(Namespace parent){
        this.parent = parent;
    }
    
    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final Namespace getParent() {
        return parent;
    }
}
