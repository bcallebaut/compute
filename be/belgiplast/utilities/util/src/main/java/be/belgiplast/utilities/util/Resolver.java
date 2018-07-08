/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 *
 * @author benoit
 */
public final class Resolver<C> implements Resolvable<C>{
    private C context;
    private List<Resolvable> resolvable = new LinkedList<>();
    private Executor executor = null;

    public Resolver(C context, Executor executor) {
        this.context = context;
        this.executor = executor;
    }
    
    public Resolver(C context) {
        this(context, null);
    }

    public final boolean add(Resolvable e) {
        return resolvable.add(e);
    }

    @Override
    public final boolean resolve(C context) {
        this.context = context;
        if (executor != null){
            executor.execute(runnable);
            return resolvable.isEmpty();
        }else{
            runnable.run();
            return resolvable.isEmpty();
        }
    }
    
    private Runnable runnable = new Runnable(){
        public void run(){
            Iterator<Resolvable> it = resolvable.iterator();
        int changes;
            do{
            changes = 0;
            while (it.hasNext()){
                if (it.next().resolve(context)){
                    changes++;
                    it.remove();
                }
            }
            }while (changes > 0);
        }
    };
}
