/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.annotations.Name;
import be.belgiplast.utilities.annotations.Relationship;
import be.belgiplast.utilities.util.Resolvable;
import be.belgiplast.utilities.util.Resolver;

/**
 *
 * @author T0194671
 */
public class RelationshipDef implements Resolvable<Context>{
    private NamespaceDef parent;
    private String name;
    private Name n;
    private String pkg;
    private Class implementation;

    public RelationshipDef() {
    }
    
    public RelationshipDef(Relationship n) {
        name = n.type();
    }

    public RelationshipDef(Name n) {
        name = n.name();
    }

    @Override
    public boolean resolve(Context context) {
        //n = context.
        return true;
    }
    
    public String getPackage() {
        return pkg;
    }

    public void setPackage(String pkg) {
        this.pkg = pkg;
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
