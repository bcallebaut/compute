/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.dependencies;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.NamedItem;
import be.belgiplast.utilities.namespaces.Namespace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author benoit
 */
class DependencyNamespace implements Namespace{

    private List<Name> names = new ArrayList<>();
    private Namespace parent;

    public DependencyNamespace(Namespace parent) {
        names.add(new DependsOnName(this));
        this.parent = parent;
    }
    
    @Override
    public Collection<NamedItem> getChildren() {
        ArrayList<NamedItem> a = new ArrayList<>();
        a.addAll(names);
        return a;
    }

    @Override
    public Collection<Namespace> getNamespaces() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<Name> getNames() {
        return names;
    }

    @Override
    public <E extends NamedItem> E findByName(String name) {
        for (Name n : names)
            try{
                if (n.getName().equals(name))
                    return (E)n;
            }catch (Exception e){}
        return null;
    }

    @Override
    public String getName() {
        return "dependencies";
    }

    @Override
    public Namespace getParent() {
        return parent;
    }
    
}
