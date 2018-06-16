/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author benoit
 */
public class NameSerializer {
    private NamespaceManager mgr;

    /**
     *  Finds the root Namespace to with belongs this nameed item
     * @param name The item we are investigating
     * @return
     */
    private static final List<NamedItem> findRootPath(NamedItem name){
        LinkedList<NamedItem> path = new LinkedList<>();
        NamedItem n = name;
        path.addFirst(n);
        while (n.getParent() != null){
            n = n.getParent();
            path.addFirst(n);
        }        
        return path;
    }
    
    public NameSerializer(NamespaceManager mgr) {
        this.mgr = mgr;
    }
    
    public String serialize(NamedItem n){
        List<NamedItem> path = findRootPath(n);
        StringBuilder builder = new StringBuilder();
        Iterator<NamedItem> it = path.iterator();
        builder.append(it.next().getName());
        while (it.hasNext()){
            builder.append(".");
            builder.append(it.next());
        }
        return builder.toString();
    }
}
