/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author A595564
 */
public class MutableMap<K, V> implements Map<K, V> {

    private Map<K, V> backend;
    private PropertyChangeSupport pcs;

    private PropertyChangeListener pcl;

    public MutableMap(Map<K, V> backend, PropertyChangeSupport pcs) {
        this.backend = backend;
        this.pcs = pcs;

        pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //forward event up
                MutableMap.this.pcs.firePropertyChange("subelement", null, evt);
            }
        };
    }

    public MutableMap(Map<K, V> backend) {
        this.backend = backend;
        pcs = new PropertyChangeSupport(this);

        pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //forward event up
                MutableMap.this.pcs.firePropertyChange("subelement", null, evt);
            }
        };
    }

    public MutableMap() {
        this(new HashMap<K, V>());
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return pcs;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport pcs) {
        this.pcs = pcs;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public int size() {
        return backend.size();
    }

    @Override
    public boolean isEmpty() {
        return backend.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backend.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backend.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return backend.get(key);
    }

    @Override
    public V put(K key, V value) {
        V result = backend.put(key, value);
        pcs.firePropertyChange("map", result, value);
        if (value instanceof Trackable) {
            ((Trackable) value).addPropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = backend.remove(key);
        pcs.firePropertyChange("map", result, null);
        if (result instanceof Trackable) {
            ((Trackable) result).addPropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        backend.putAll(m);
        pcs.firePropertyChange("map", null, m);
        for (V value : m.values()) {
            if (value instanceof Trackable) {
                ((Trackable) value).addPropertyChangeListener(pcl);
            }
        }
    }

    @Override
    public void clear() {
        backend.clear();
        pcs.firePropertyChange("map", this, null);
        for (V value : values()) {
            if (value instanceof Trackable) {
                ((Trackable) value).removePropertyChangeListener(pcl);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        return backend.keySet();
    }

    @Override
    public Collection<V> values() {
        return backend.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return backend.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return backend.equals(o);
    }

    @Override
    public int hashCode() {
        return backend.hashCode();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return backend.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        backend.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        backend.replaceAll(function);
        pcs.firePropertyChange("map", null, this);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V result = backend.putIfAbsent(key, value);
        pcs.firePropertyChange("map", result, value);
        if (value instanceof Trackable) {
            ((Trackable) value).addPropertyChangeListener(pcl);
        }

        return result;
    }

    @Override
    public boolean remove(Object key, Object value) {
        boolean result = backend.remove(key, value);
        pcs.firePropertyChange("map", value, null);
        if (value instanceof Trackable) {
            ((Trackable) value).removePropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        boolean result = backend.replace(key, oldValue, newValue);
        pcs.firePropertyChange("map", oldValue, newValue);
        return result;
    }

    @Override
    public V replace(K key, V value) {
        V result = backend.replace(key, value);
        pcs.firePropertyChange("map", result, value);
        return result;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return backend.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return backend.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return backend.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return backend.merge(key, value, remappingFunction);
    }

}
