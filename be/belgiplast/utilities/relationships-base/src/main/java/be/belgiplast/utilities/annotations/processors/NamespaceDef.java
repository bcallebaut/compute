/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author T0194671
 */
public class NamespaceDef {
    private NamespaceDef parent;
    private String name;
    private String pkg;
    private final Map<String,NamespaceDef> subNamespaces = new HashMap<>();
    private final Map<String,NameDef> names = new HashMap<>();

    public NamespaceDef getParent() {
        return parent;
    }

    public void setParent(NamespaceDef parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackage() {
        return pkg;
    }

    public void setPackage(String pkg) {
        this.pkg = pkg;
    }

    public Map<String, NamespaceDef> getSubNamespaces() {
        return subNamespaces;
    }

    public Map<String, NameDef> getNames() {
        return names;
    }
    
    public void addNamespace(NamespaceDef namespace){
        if (!subNamespaces.containsKey(namespace.name)){
            subNamespaces.put(namespace.name, namespace);
            namespace.setParent(this);
        }
    }
    
    public void addName(NameDef nm){
        if (!names.containsKey(nm.getName())){
            names.put(nm.getName(), nm);
            nm.setParent(this);
        }
    }
}
