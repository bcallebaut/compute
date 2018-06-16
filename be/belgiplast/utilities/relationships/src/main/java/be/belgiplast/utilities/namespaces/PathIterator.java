/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.belgiplast.utilities.namespaces;

import java.util.Iterator;

/**
 *
 * @author benoit
 */
public abstract class PathIterator<E> implements Iterator<String>{    
    private E uri;

    public PathIterator(){
    }
    
    public void initialize(E element){
        uri = element;
    }

    protected E getSource() {
        return uri;
    }
    
    public abstract String getCategory();

    public abstract boolean accept(Object e);

    @Override
    public abstract boolean hasNext();

    @Override
    public abstract String next();
    
    
}
