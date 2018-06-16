/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/**
 *
 * @author A595564
 */
public class MutableList<E> implements List<E>,Trackable {

    private List<E> backend;
    private PropertyChangeSupport pcs;

    private PropertyChangeListener pcl;

    public MutableList(List<E> backend, PropertyChangeSupport pcs) {
        this.backend = backend;
        this.pcs = pcs;
        
        pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //forward event up
                MutableList.this.pcs.firePropertyChange("subelement", null, evt);
            }
        };
    }

    public MutableList(List<E> backend) {
        this.backend = backend;
        this.pcs = new PropertyChangeSupport(this);
        pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //forward event up
                MutableList.this.pcs.firePropertyChange("subelement", null, evt);
            }
         };
    }

    public MutableList() {
        this(new ArrayList<E>());
    }

    @Override
    public PropertyChangeSupport getPropertyChangeSupport() {
        return pcs;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport pcs) {
        this.pcs = pcs;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    @Override
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
    public boolean contains(Object o) {
        return backend.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return backend.iterator();
    }

    @Override
    public Object[] toArray() {
        return backend.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backend.toArray(a);
    }

    @Override
    public boolean add(E e) {
        boolean result = backend.add(e);
        pcs.firePropertyChange("list", null, e);
        if (e instanceof Trackable){
            ((Trackable)e).addPropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = backend.remove(o);
        pcs.firePropertyChange("list", o, null);
        if (o instanceof Trackable){
            ((Trackable)o).removePropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backend.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = backend.addAll(c);
        pcs.firePropertyChange("list", null, c);
        for (E e : c)
        if (e instanceof Trackable){
            ((Trackable)e).addPropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean result = backend.addAll(index, c);
        pcs.firePropertyChange("list", null, c);
        for (E e : c)
        if (e instanceof Trackable){
            ((Trackable)e).addPropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = backend.removeAll(c);
        pcs.firePropertyChange("list", c, null);
        for (Object e : c)
            if (e instanceof Trackable){
                ((Trackable)e).removePropertyChangeListener(pcl);
            }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = backend.retainAll(c);
        pcs.firePropertyChange("list", null, c);
        
        return result;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        backend.replaceAll(operator);
        pcs.firePropertyChange("list", null, this);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        backend.sort(c);
    }

    @Override
    public void clear() {
        backend.clear();
        pcs.firePropertyChange("list", this, null);
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
    public E get(int index) {
        return backend.get(index);
    }

    @Override
    public E set(int index, E element) {
        E result = backend.set(index, element);
        pcs.firePropertyChange("list", result, element);
        if (result instanceof Trackable){
            ((Trackable)result).removePropertyChangeListener(pcl);
        }
        if (element instanceof Trackable){
            ((Trackable)element).addPropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public void add(int index, E element) {
        E result = backend.get(index);
        backend.add(index, element);
        pcs.firePropertyChange("list", result, element);
        if (element instanceof Trackable){
            ((Trackable)element).addPropertyChangeListener(pcl);
        }
    }

    @Override
    public E remove(int index) {
        E result = backend.remove(index);
        pcs.firePropertyChange("list", result, null);
        if (result instanceof Trackable){
            ((Trackable)result).removePropertyChangeListener(pcl);
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        return backend.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backend.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return backend.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return backend.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return backend.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<E> spliterator() {
        return backend.spliterator();
    }
}
