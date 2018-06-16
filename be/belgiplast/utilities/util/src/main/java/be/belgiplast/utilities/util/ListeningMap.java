/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

/**
 * Listen on a List sending property change events upon element insertion/deletion 
 * and maps them to maps insertion/deletion events
 * 
 * @author benoit
 */
public class ListeningMap<K,V> extends HashMap<K,V>{
    private PropertyChangeListener pcl;
    private Converter<K,V> converter;

    public ListeningMap(Converter<K,V> cvt) {
        this.converter = cvt;
        pcl = new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getOldValue() == null && evt.getNewValue() != null){
                    try{
                        put(converter.getKey(evt.getNewValue()),converter.getValue(evt.getNewValue()));
                    }catch (Exception e){}
                }else if (evt.getOldValue() != null && evt.getNewValue() == null){
                    try{
                        remove(converter.getKey(evt.getOldValue()));
                    }catch (Exception e){}
                }
            }
            
        };
    }

    public PropertyChangeListener getPropertyChangeListener() {
        return pcl;
    }
    
    public interface Converter<K,V>{
        K getKey(Object obj);
        V getValue(Object obj);
    }
}
