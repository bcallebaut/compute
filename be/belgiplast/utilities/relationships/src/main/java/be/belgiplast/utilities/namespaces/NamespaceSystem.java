/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benoit
 */
public abstract class NamespaceSystem<E> {
    private List<PathIterator> pathIterators = new ArrayList<>();
    private Namespace root;

    public NamespaceSystem() {
        root = createRoot();
    }
    
    protected abstract Namespace createRoot();
    
    protected abstract void registerPathIterators(List<PathIterator> iterators);
    
    public final NamedItem findName(E name){
        for (PathIterator iterator : pathIterators){
            if (iterator.accept(name)){
                return findName(name,iterator);
            }
        }
        return null;
    } 

    private NamedItem findName(E name, PathIterator iterator) {
        iterator.initialize(name);
        Namespace ns = root;
        if (root == null)
            return null;
        while (iterator.hasNext()){
            String element = iterator.next();
            NamedItem n = null;
            if (iterator.hasNext()){
                n = ns.findNamespaceByName(element);
            }else n = ns.findNameByName(element);
            //TODO : simplify next statement
            if (n != null){
                if (n instanceof Name)
                    if (iterator.hasNext())
                        return null;
                    else
                       return n;
                else ns = (Namespace)n;
            }else
                return null;
        }
        return null;
    }
}
