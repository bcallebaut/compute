/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces.support;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.NamedItem;
import be.belgiplast.utilities.namespaces.Namespace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author benoit
 */
public class AbstractNamespaceSupport implements Namespace{
    private String name;
    private Namespace parent;
    private Map<String,NamedItem> items = new HashMap<>();

    public AbstractNamespaceSupport() {
    }

    protected AbstractNamespaceSupport(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public Namespace getParent() {
        return parent;
    }
    
    public void addName(Name name){
        items.put(name.getName(), name);
    }
    
    public void addNamespace(Namespace name){
        items.put(name.getName(), name);
    }
    
    public void removeName(Name name){
        items.remove(name.getName());
    }
    
    public void removeNamespace(Namespace name){
        items.remove(name.getName());
    }

    @Override
    public Collection<NamedItem> getChildren() {
        return items.values();
    }

    @Override
    public Collection<Namespace> getNamespaces() {
        ArrayList<Namespace> ns = new ArrayList<>();
        for (NamedItem ni : items.values())
            if (ni instanceof Namespace) ns.add((Namespace)ni);
        return ns;
    }

    @Override
    public Collection<Name> getNames() {
        ArrayList<Name> ns = new ArrayList<>();
        for (NamedItem ni : items.values())
            if (ni instanceof Name) ns.add((Name)ni);
        return ns;
    }

    @Override
    public <E extends NamedItem> E findByName(String name) {
        return (E)items.get(name);
    }
}
