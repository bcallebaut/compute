/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.Name;
import be.belgiplast.utilities.namespaces.Namespace;

/**
 *
 * @author benoit
 */
public class DynamicRelationship implements Relationship{

    private Entity from;
    private Entity to;
    private Name name;
    private Relationship inverse;
    private Namespace namespace;
    
    @Override
    public Name getId() {
        return null;
    }

    @Override
    public Entity from() {
        return from;
    }

    @Override
    public Entity to() {
        return to;
    }

    @Override
    public Relationship getInverse() {
        if (inverse == null)
            inverse = new InverseRelationship();
        return inverse;
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Namespace getParent() {
        return namespace;
    }
    
    private class InverseRelationship implements Relationship{

        @Override
        public Name getId() {
            return null;
        }

        @Override
        public Entity from() {
            return to;
        }

        @Override
        public Entity to() {
            return from;
        }

        @Override
        public Relationship getInverse() {
            return DynamicRelationship.this;
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Namespace getParent() {
            return namespace;
        }
    }
}
