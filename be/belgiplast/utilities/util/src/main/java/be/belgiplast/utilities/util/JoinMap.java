/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author benoit
 */
public class JoinMap<K,V> implements Map<K,V>{
    private final List<Map<K,V>> backend = new ArrayList<>();
        
    public void addMap(Map<K,? extends V> l ){
        backend.add((Map<K,V>)l);
        l.getClass().getTypeParameters()[0].getBounds()[0].getTypeName();
    }

    @Override
    public int size() {
        int size = 0;
        for (Map item : backend){
            size +=item.size();
        }
        return size();
    }

    @Override
    public boolean isEmpty() {
        boolean result = true;
        for (Map item : backend){
            result &= item.isEmpty();
            if (! result)
                return false;
        }
        return result;
    }

    @Override
    public boolean containsKey(Object o) {
        boolean result = false;
        for (Map<K,V> l : backend){
            if (l.containsKey(o))
                return true;
        }
        return false;
    }
    
    @Override
    public boolean containsValue(Object o) {
        boolean result = false;
        for (Map<K,V> l : backend){
            if (l.containsValue(o))
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for (Map<K,V> m : backend){
            if (m.containsKey(key))
                return m.get(key);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        for (Map<K,V> m : backend)
            m.clear();
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> result = new HashSet<>();
        for (Map<K,V> m : backend)
            result.addAll(m.keySet());
        return result;
    }

    @Override
    public Collection<V> values() {
        HashSet<V> result = new HashSet<>();
        for (Map<K,V> m : backend)
            result.addAll(m.values());
        return result;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> result = new HashSet<>();
        for (Map<K,V> m : backend)
            result.addAll(m.entrySet());
        return result;
    }
}
