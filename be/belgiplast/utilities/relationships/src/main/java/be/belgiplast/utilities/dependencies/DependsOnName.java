/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.dependencies;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;

/**
 *
 * @author benoit
 */
class DependsOnName implements Name{

    private Namespace parent;

    public DependsOnName(Namespace parent) {
        this.parent = parent;
    }
    
    @Override
    public String getName() {
        return "dependsOn";
    }

    @Override
    public Namespace getParent() {
        return parent;
    }
}
