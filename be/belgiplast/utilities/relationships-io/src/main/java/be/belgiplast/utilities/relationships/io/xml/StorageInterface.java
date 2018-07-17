/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.relationships.io.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author benoit
 */
public class StorageInterface {
    private static Backend backend;

    public static Backend getBackend() {
        if (backend == null)
            try{
                backend = new Backend();
            }catch (JAXBException e){
                e.printStackTrace();
            }
        return backend;
    }

    public void marshal(Graph jaxbElement, OutputStream os) throws JAXBException {
        getBackend().marshal(jaxbElement, os);
    }

    public void marshal(Graph jaxbElement, File output) throws JAXBException {
        getBackend().marshal(jaxbElement, output);
    }

    public void marshal(Graph jaxbElement, Writer writer) throws JAXBException {
        getBackend().marshal(jaxbElement, writer);
    }

    public Object unmarshal(File f) throws JAXBException {
        return getBackend().unmarshal(f);
    }

    public Graph unmarshal(InputStream is) throws JAXBException {
        return getBackend().unmarshal(is);
    }

    public Graph unmarshal(Reader reader) throws JAXBException {
        return getBackend().unmarshal(reader);
    }
            
    public static final class Backend{
        private final JAXBContext ctxt;
        private final Marshaller marshaller;
        private final Unmarshaller unmarshaller;

        public Backend() throws JAXBException{
            ctxt = JAXBContext.newInstance(Graph.class);
            marshaller = ctxt.createMarshaller();
            unmarshaller = ctxt.createUnmarshaller();
        }
        
        public JAXBContext getCtxt() {
            return ctxt;
        }

        public Marshaller getMarshaller() {
            return marshaller;
        }

        public Unmarshaller getUnmarshaller() {
            return unmarshaller;
        }

        public void marshal(Graph jaxbElement, OutputStream os) throws JAXBException {
            marshaller.marshal(jaxbElement, os);
        }

        public void marshal(Graph jaxbElement, File output) throws JAXBException {
            marshaller.marshal(jaxbElement, output);
        }

        public void marshal(Graph jaxbElement, Writer writer) throws JAXBException {
            marshaller.marshal(jaxbElement, writer);
        }

        public Object unmarshal(File f) throws JAXBException {
            return unmarshaller.unmarshal(f);
        }

        public Graph unmarshal(InputStream is) throws JAXBException {
            return (Graph)unmarshaller.unmarshal(is);
        }

        public Graph unmarshal(Reader reader) throws JAXBException {
            return (Graph)unmarshaller.unmarshal(reader);
        }
    }
            
}
