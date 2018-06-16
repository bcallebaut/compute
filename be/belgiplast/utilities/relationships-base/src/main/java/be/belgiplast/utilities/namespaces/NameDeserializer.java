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
public class NameDeserializer {
    private NamespaceSystem system;

    public NameDeserializer(NamespaceSystem system) {
        this.system = system;
        
    }
    
    public Name getName(String text){
        try{
            return (Name)system.findName(text);
        }catch (ClassCastException e){
            return null;
        }
    }
    
    public Namespace getNamespace(String text){
        try{
            return (Namespace)system.findName(text);
        }catch (ClassCastException e){
            return null;
        }
    }
}
