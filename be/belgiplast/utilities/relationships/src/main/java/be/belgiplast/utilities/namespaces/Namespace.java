/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import java.util.Collection;

/**
 *
 * @author Benoit Callebaut
 */
public interface Namespace extends NamedItem{
    Collection<? extends NamedItem> getChildren();
    Collection<Namespace> getNamespaces();
    Collection<Name> getNames();
    
    <E extends NamedItem> E findByName(String name);
}
