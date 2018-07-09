/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.util.Resolvable;
import be.belgiplast.utilities.annotations.Entity;
import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.util.Resolver;

/**
 *
 * @author T0194671
 */
public class EntityDef extends AbstractDefinition implements Resolvable<Context>{    
    private Resolver<Context> resolver;
    private NameDef type;
    private String pkg;
    private Class implementation;

    public EntityDef(Entity entity) {
        super(entity.type());
        resolver = new Resolver<>(null);
    }
    
    public String getPackage() {
        return pkg;
    }

    public void setPackage(String pkg) {
        this.pkg = pkg;
    }

    public NameDef getType() {
        return type;
    }
    
    public Class getImplementation() {
        return implementation;
    }

    public void setImplementation(Class implementation) {
        this.implementation = implementation;
    }

    @Override
    public boolean resolve(Context context) {
        return resolver.resolve(context);
    }
    
    
}
