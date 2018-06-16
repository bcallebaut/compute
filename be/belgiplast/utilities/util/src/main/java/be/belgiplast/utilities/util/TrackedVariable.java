/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author A595564
 */
public final class TrackedVariable<T> implements Trackable{
    private PropertyChangeSupport pcs;
    private T value;
    private String name;

    public TrackedVariable(String name,PropertyChangeSupport pcs) {
        this.pcs = pcs;
        this.name = name;
    }

    public TrackedVariable(String name) {
        this(name,null);
        pcs = new PropertyChangeSupport(this);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        T old = this.value;
        this.value = value;
        pcs.firePropertyChange(name, old, value);
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
}
