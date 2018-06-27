/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.annotations.Name;

/**
 *
 * @author T0194671
 */
public class NameDef {
    private NamespaceDef parent;
    private String name;
    private Class implementation;

    public NameDef() {
    }

    public NameDef(Name n) {
        name = n.name();
    }
    
    public NamespaceDef getParent() {
        return parent;
    }

    public void setParent(NamespaceDef parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getImplementation() {
        return implementation;
    }

    public void setImplementation(Class implementation) {
        this.implementation = implementation;
    }
    
    
}
