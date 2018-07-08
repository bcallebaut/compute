/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

/**
 *
 * @author benoit
 */
public class AbstractDefinition {
    
    protected String name;

    public AbstractDefinition(String name) {
        this.name = name;
    }

    public AbstractDefinition() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
