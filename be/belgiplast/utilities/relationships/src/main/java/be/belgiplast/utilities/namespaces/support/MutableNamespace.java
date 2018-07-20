/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces.support;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;

/**
 *
 * @author benoit
 */
public interface MutableNamespace extends Namespace {

    void addName(Name name);

    void addNamespace(Namespace name);

    void removeName(Name name);

    void removeNamespace(Namespace name);
    
}
