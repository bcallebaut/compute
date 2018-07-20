/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import be.belgiplast.utilities.namespaces.support.AbstractNamespaceSupport;
import be.belgiplast.utilities.namespaces.support.MutableNamespace;
import java.util.Collection;

/**
 * Allows the creation of names dynamically.
 * Useful for uses cases like mind maps or variables cration in namespace
 * @author benoit
 */
public class DynamicNamespace implements Namespace {

    private Namespace backend;

    public DynamicNamespace(Namespace backend) {
        this.backend = backend;
    }
    
    public DynamicNamespace(String name, Namespace parent) {
        this(new AbstractNamespaceSupport(name, parent));
    }
    
    public DynamicNamespace(String name) {
        this(name, null);
    }

    @Override
    public <E extends NamedItem> E findByName(String name) {
        E result = backend.findByName(name);
        if (result == null){            
            addName((Name)(result = ((E)new DynamicName(name,this))));
            return result;
        }
        return result;
    }
    
    
    public Namespace findNamespaceByName(String name) {
        Namespace result = backend.findByName(name);
        if (result == null){            
            addNamespace(result = new DynamicNamespace(name,this));
            return result;
        }
        return result;
    }

    public void addNamespace(Namespace name) throws UnsupportedOperationException{
        try{
            ((MutableNamespace)backend).addNamespace(name); //To change body of generated methods, choose Tools | Templates.
        }catch (Exception e){
            throw new UnsupportedOperationException("Backend is not a mutable Namespace",e);
        }
    }
    
    public void addName(Name name) throws UnsupportedOperationException{
        try{
            ((MutableNamespace)backend).addName(name);
        }catch (Exception e){
            throw new UnsupportedOperationException("Backend is not a mutable Namespace",e);
        }
    } 

    @Override
    public Collection<? extends NamedItem> getChildren() {
        return backend.getChildren();
    }

    @Override
    public Collection<Namespace> getNamespaces() {
        return backend.getNamespaces();
    }

    @Override
    public Collection<? extends Name> getNames() {
        return backend.getNames();
    }

    @Override
    public Name findNameByName(String name) {
        return backend.findNameByName(name);
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
