/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.dependencies;

import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.namespaces.NamespaceProvider;

/**
 *
 * @author benoit
 */
public class DependencyNamespaceProvider implements NamespaceProvider{

    @Override
    public final Namespace getNamespace(Namespace parent) {
        return new DependencyNamespace(parent);
    }
    
}
