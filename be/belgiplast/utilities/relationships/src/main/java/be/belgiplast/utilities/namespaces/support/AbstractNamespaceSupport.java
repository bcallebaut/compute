/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces.support;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.NamedItem;
import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.util.JoinList;
import be.belgiplast.utilities.util.JoinMap;
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
    private JoinMap<String,NamedItem> items = new JoinMap<>();
    private Map<String,Name> names = new HashMap<>();
    private Map<String,Namespace> namespaces = new HashMap<>();

    public AbstractNamespaceSupport() {
        items.addMap(names);
        items.addMap(namespaces);
    }

    protected AbstractNamespaceSupport(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
        items.addMap(names);
        items.addMap(namespaces);
    }
    
    public String getName() {
        return name;
    }

    @Override
    public Namespace getParent() {
        return parent;
    }
    
    protected void addName(Name name){
        names.put(name.getName(), name);
    }
    
    protected void addNamespace(Namespace name){
        namespaces.put(name.getName(), name);
    }
    
    protected void removeName(Name name){
        names.remove(name.getName());
    }
    
    protected void removeNamespace(Namespace name){
        names.remove(name.getName());
    }

    @Override
    public Collection<NamedItem> getChildren() {
        return items.values();
    }

    @Override
    public Collection<Namespace> getNamespaces() {
        return namespaces.values();
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
    
    @Override
    public Name findNameByName(String name) {
        return names.get(name);
    }
    
    @Override
    public Namespace findNamespaceByName(String name) {
        return namespaces.get(name);
    }
}
