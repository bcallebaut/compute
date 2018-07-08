/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.annotations.Name;
import be.belgiplast.utilities.annotations.Namespace;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

/**
 *
 * @author T0194671
 */
@SupportedAnnotationTypes(
        {"be.belgiplast.utilities.annotations.Entity", "be.belgiplast.utilities.annotations.Relationship",
        "be.belgiplast.utilities.annotations.Entities", "be.belgiplast.utilities.annotations.Relationships"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntitiesProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    
    Map<String,NamespaceDef> nss = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Namespace.class)) {
            Namespace ns = annotatedElement.getAnnotation(Namespace.class);
            NamespaceDef def = nss.get(ns.name());
            if (def == null){
                def = new NamespaceDef();
                def.setName(ns.name());
                nss.put(ns.name(),def);
            }
            if (ns.parent() != ""){
                NamespaceDef parent = nss.get(ns.parent());
                if (parent == null){
                    parent = new NamespaceDef();
                    parent.setName(ns.parent());
                    nss.put(ns.parent(),parent);
                }
                parent.addNamespace(def);
            }
            for (Name n : ns.names()){
                NameDef ndef = new NameDef(n);
                def.addName(ndef);
            }
        }
        //remove non root Namespace definitions
        Iterator<NamespaceDef> it = nss.values().iterator();
        while (it.hasNext()){
            NamespaceDef def = it.next();
            if (def.getParent() != null)
                nss.remove(def.getName());
        }
        
        generateCode(nss);
        
        return true;
    }
    
    private String createClassname(NamespaceDef def){
        StringBuilder builder = new StringBuilder();
        NamespaceDef current = def;
        while (current != null){
            builder = builder.insert(0, current.getName());
            current = current.getParent();
            if (current != null)
                builder.insert(0,"/");
        }
        return builder.toString();
    }

    private void generateCode(Map<String, NamespaceDef> nss) {
        for (NamespaceDef def : nss.values()){
            try {
                String classname = createClassname(def);
                JavaFileObject fo = filer.createSourceFile(classname);
                Writer wr = fo.openWriter();
                wr.write("package ");
                wr.write(classname.substring(0,classname.lastIndexOf("/")).replaceAll("/", "."));
                wr.write(";\n");
                wr.write("import be.belgipast.utilities.relationships.Entity;\n");
                wr.write("import be.belgipast.utilities.relationships.support.EntitySupport;\n");
                wr.write(String.format("public class %s extends EntitySupport{\n",def.getName()));
                wr.write(String.format("    public %s (Namespace parent){\n",def.getName()));
                wr.write(String.format("        super(\"%s\",parent);\n",def.getName()));
                wr.write("    }\n");
                wr.write("}\n");
            } catch (IOException ex) {
                Logger.getLogger(NamespaceProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
