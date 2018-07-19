/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.io.xml;

import be.belgiplast.utilities.namespaces.Namespace;
import be.belgiplast.utilities.relationships.Entities;
import be.belgiplast.utilities.relationships.io.DefaultEntities;
import be.belgiplast.utilities.relationships.io.FactoryManager;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author benoit
 */
public class Loader {
    private StorageInterface storage = new StorageInterface();
    private LoaderFactoryManager mgr = new LoaderFactoryManager();
    
    public Namespace getNamespace() {
        return mgr.getNamespace();
    }

    public Namespace getHomeNamespace() {
        return mgr.getHomeNamespace();
    }

    public void setNamespace(Namespace namespace) {
        mgr.setNamespace(namespace);
    }

    public void setHomeNamespace(Namespace homeNamespace) {
        mgr.setHomeNamespace(homeNamespace);
    }
    
    public void initialize(){
        
    }
    
    public Entities load(InputStream is){
        Entities result = mgr.createEntities();
        
        try {
            Graph gr = storage.unmarshal(is);
            
            for (Entity xmlEntity : gr.getEntities()){
                be.belgiplast.utilities.relationships.Entity entity = mgr.createEntity(xmlEntity.getType(), xmlEntity.getName());
            }
        } catch (JAXBException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    private class LoaderFactoryManager extends FactoryManager{

        public Namespace getNamespace() {
            return namespace;
        }

        public Namespace getHomeNamespace() {
            return homeNamespace;
        }

        public void setNamespace(Namespace namespace) {
            this.namespace = namespace;
        }

        public void setHomeNamespace(Namespace homeNamespace) {
            this.homeNamespace = homeNamespace;
        }

        @Override
        public Entities createEntities() {
            return new DefaultEntities();
        }
    }
}
