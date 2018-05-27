/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

/**
 *
 * @author benoit
 */
public abstract class NamespaceManager {

    /**
     *  Finds the root Namespace to with belongs this nameed item
     * @param name The item we are investigating
     * @return
     */
    public static final Namespace findRoot(NamedItem name){
        NamedItem n = name;
        while (n.getParent() != null){
            n = n.getParent();
        }
        try{
            return (Namespace)n;
        }catch (ClassCastException cce){
            return null;
        }
    }
    
    /**
     *  Finds the local  root Namespace to with belongs this nameed item.
     * 
     *  Namespace can represents non-homogenous things like:
     * <ul>
     *    <li>a method/field of a class</li>
     *    <li>a class in a source file</li>
     *    <li> a source file in a software project</li>
     * </ul>
     * The user may want to limit the name to a part of it. A good example is a fully qualified class in Java or an anonymous class in a class file.
     * Note that this class does not provide any specific implementation( for Java,C++...) 
     * 
     * @param name The item we are investigating
     * @param group The group is the type (class) extending Namespace in which the item name belongs 
     * @return
     */
    public static final Namespace findLocalRoot(NamedItem name,Class<Namespace> group){
        NamedItem n = name;
        while (n.getParent() != null && group.isInstance(n.getParent())){
            n = n.getParent();
        }
        try{
            return (Namespace)n;
        }catch (ClassCastException cce){
            return null;
        }
    }
    
    public static final String getQualifiedName(NamedItem name,String separator){
        if (separator == null)
            separator = ".";
        StringBuilder builder = new StringBuilder();
        
        NamedItem n = name;
        builder.insert(0, n.getName());
        while (n.getParent() != null){                        
            n = n.getParent();
            builder.insert(0, separator);
            builder.insert(0, n.getName());
        }
        
        return builder.toString();
    }
    
    public abstract String getQualifiedName(NamedItem name);
}
