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
public interface NamespaceProvider {
    Namespace getNamespace(Namespace parent);
}
