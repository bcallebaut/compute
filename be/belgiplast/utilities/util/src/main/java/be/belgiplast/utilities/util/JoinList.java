/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 * @author benoit
 */
public class JoinList<E> implements List<E>{
    private final List<List<E>> backend = new ArrayList<>();
    private final Map<String,List<E>> creatorMap = new HashMap<>();
    
    public void addList(List<? extends E> l ){
        backend.add((List<E>)l);
        l.getClass().getTypeParameters()[0].getBounds()[0].getTypeName();
    }

    @Override
    public int size() {
        int size = 0;
        for (List item : backend){
            size +=item.size();
        }
        return size();
    }

    @Override
    public boolean isEmpty() {
        boolean result = true;
        for (List item : backend){
            result &= item.isEmpty();
            if (! result)
                return false;
        }
        return result;
    }

    @Override
    public boolean contains(Object o) {
        boolean result = false;
        for (List<E> l : backend){
            if (l.contains(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            private Iterator<List<E>> metaIterator = backend.iterator();
            private Iterator<E> currentIterator = metaIterator.next().iterator();
            
            @Override
            public boolean hasNext() {
                if (currentIterator.hasNext() == false){
                    if (metaIterator.hasNext()){
                        currentIterator = metaIterator.next().iterator();
                        return hasNext();
                    } else return false;
                }else return true;
            }

            @Override
            public E next() {
                if (currentIterator.hasNext())
                    return currentIterator.next();
                if (metaIterator.hasNext()){
                    currentIterator = metaIterator.next().iterator();
                    return next();
                } else return null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(E e) {
        try{
            return backend.get(backend.size() -1).add(e);
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        List.super.replaceAll(operator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sort(Comparator<? super E> c) {
        List.super.sort(c); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        for (List<E> e : backend)
            e.clear();
    }

    @Override
    public E get(int index) {
        int ndx = 0;
        while (index > backend.get(ndx).size()){
            index = index - backend.get(ndx).size();
            ndx++;
        }
        try{
            return backend.get(ndx).get(index);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public E set(int index, E element) {
        int ndx = 0;
        while (index > backend.get(ndx).size()){
            index = index - backend.get(ndx).size();
            ndx++;
        }
        try{
            return backend.get(ndx).set(index, element);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public void add(int index, E element) {
        int ndx = 0;
        while (index > backend.get(ndx).size()){
            index = index - backend.get(ndx).size();
            ndx++;
        }
        try{
            backend.get(ndx).add(index, element);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            return;
        }
    }

    @Override
    public E remove(int index) {
        int ndx = 0;
        while (index > backend.get(ndx).size()){
            index = index - backend.get(ndx).size();
            ndx++;
        }
        try{
            return backend.get(ndx).remove(index);
        }catch (NullPointerException | IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return List.super.removeIf(filter); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<E> stream() {
        return List.super.stream(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stream<E> parallelStream() {
        return List.super.parallelStream(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        List.super.forEach(action); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
