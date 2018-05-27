/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import be.belgiplast.utilities.util.JoinList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 * @author benoit
 */
public final class SystemNamespace implements Namespace{

    private List<Name> names = new ArrayList<>();
    private List<Namespace> children = new ArrayList<>();
    private JoinList<NamedItem> items;

    private static SystemNamespace instance = null;

    public static SystemNamespace getInstance() {
        if (instance == null)
            instance = new SystemNamespace();
        return instance;
    }
    
    private SystemNamespace() {
        names = new ArrayList<>();
        children = new ArrayList<>();
        items = new JoinList<>();
        items.addList(names);
        items.addList(children);
        
        ServiceLoader<NamespaceProvider> loader = ServiceLoader.load(NamespaceProvider.class);
        for (NamespaceProvider provider : loader)
            children.add(provider.getNamespace(this));
    }
    
    @Override
    public Collection<? extends NamedItem> getChildren() {
        return items;
    }

    @Override
    public Collection<Namespace> getNamespaces() {
        return children;
    }

    @Override
    public Collection<Name> getNames() {
        return names;
    }

    @Override
    public <E extends NamedItem> E findByName(String name) {
        for (Name n : names)
            if (n.getName().equals(name))
                return (E)n;
        for (Namespace n : children)
            if (n.getName().equals(name))
                return (E)n;
        return null;
    }

    @Override
    public String getName() {
        return "system";
    }

    @Override
    public Namespace getParent() {
        return null;
    }
    
}
