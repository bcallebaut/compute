/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships;

import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.support.RelationshipSupport;

/**
 *
 * @author benoit
 */
public class DynamicRelationship extends RelationshipSupport implements Relationship{

    public DynamicRelationship(Namespace parent, String name, RelationshipType id) {
        super(parent, name, id);
    }

    @Override
    public Relationship createInverse() {
        return new InverseRelationship();
    }
    
    protected String getInverseName(){
        return DynamicRelationship.this.getName()+".inverse";
    }
    
    private class InverseRelationship implements Relationship{

        @Override
        public RelationshipType getId() {
            return null;
        }

        @Override
        public Entity from() {
            return DynamicRelationship.this.to();
        }

        @Override
        public Entity to() {
            return DynamicRelationship.this.from();
        }

        @Override
        public Relationship getInverse() {
            return DynamicRelationship.this;
        }

        @Override
        public String getName() {
            return getInverseName();
        }

        @Override
        public Namespace getParent() {
            return DynamicRelationship.this.getParent();
        }
    }
}
