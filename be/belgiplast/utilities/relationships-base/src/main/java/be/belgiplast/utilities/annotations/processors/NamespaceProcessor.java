/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.annotations.Name;
import be.belgiplast.utilities.annotations.Namespace;
import be.belgiplast.utilities.annotations.Namespaces;
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
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

/**
 *
 * @author T0194671
 */
@SupportedAnnotationTypes(
        {"be.belgiplast.utilities.annotations.Name", "be.belgiplast.utilities.annotations.Namespace", "be.belgiplast.utilities.annotations.Namespaces"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NamespaceProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

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
        Map<String,NamespaceDef> nss = new HashMap<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Namespaces.class)) {
            
            String packagePrefix = "";
            if (annotatedElement instanceof PackageElement){
                packagePrefix = ((PackageElement)annotatedElement).getQualifiedName().toString();
                for (Namespace ns2 : annotatedElement.getAnnotationsByType(Namespace.class)){
                    processAnnotation(nss, ns2, packagePrefix);
                }
            }else if (annotatedElement instanceof TypeElement){
                packagePrefix = ((TypeElement)annotatedElement).getQualifiedName().toString();
                Namespace ns = annotatedElement.getAnnotation(Namespace.class);
                processAnnotation(nss, ns, packagePrefix);
            }
            
        }
        //remove non root Namespace definitions
        Iterator<NamespaceDef> it = nss.values().iterator();
        while (it.hasNext()){
            NamespaceDef def = it.next();
            if (def.getParent() != null)
                it.remove();
        }
        
        generateCode(nss);
        
        return true;
    }

    private void processAnnotation(Map<String, NamespaceDef> nss, Namespace ns, String packagePrefix) {
        NamespaceDef def = nss.get(ns.name());
        if (def == null){
            def = new NamespaceDef();
            def.setName(ns.name());
            nss.put(ns.name(),def);
        }
        def.setPackage(packagePrefix);
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
    
    private String createClassname(NamespaceDef def){
        
        StringBuilder builder = new StringBuilder();
        if (def.getPackage() != null && !def.getPackage().isEmpty()){
            builder.append(def.getPackage());
            builder.append(".");
        }
        String name = getShortName(def);
        builder.append(name);
        return builder.toString();
    }

    private String getShortName(NamespaceDef def) {
        String name = def.getName();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        return name;
    }
    
    private String createFilename(NamespaceDef def){
        String name = createClassname(def);
        name.replaceAll(".", "/");
        return name;
    }
    
    private String createClassname(NameDef def){
        
        StringBuilder builder = new StringBuilder();
        if (def.getParent().getPackage() != null && !def.getParent().getPackage().isEmpty()){
            builder.append(def.getParent().getPackage());
            builder.append(".");
        }
        String name = getShortName(def);
        builder.append(name);
        return builder.toString();
    }

    private String getShortName(NameDef def) {
        String name = def.getName();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        return name;
    }
    
    private String createFilename(NameDef def){
        String name = createClassname(def);
        name.replaceAll(".", "/");
        return name;
    }
    
    private void generateNamespaceCode(NamespaceDef def) {
        if (def.getName().length() > 0){
            try {
                String classname = getShortName(def);
                JavaFileObject fo = filer.createSourceFile(createFilename(def));
                Writer wr = fo.openWriter();
                wr.write("package ");
                wr.write(def.getPackage());
                //wr.write(classname.substring(0,classname.lastIndexOf("/")).replaceAll("/", "."));
                wr.write(";\n");
                wr.write("import be.belgiplast.utilities.namespaces.Namespace;\n");
                wr.write("import be.belgiplast.utilities.namespaces.support.AbstractNamespaceSupport;\n");
                wr.write(String.format("public class %s extends AbstractNamespaceSupport{\n",classname));
                wr.write(String.format("    public %s (Namespace parent){\n",classname));
                wr.write(String.format("        super(\"%s\",parent);\n",def.getName()));
                for (NamespaceDef d : def.getSubNamespaces().values()){
                    wr.write(String.format("        addNamespace(new "+ d.getPackage() +"."+getShortName(d) +"(this));\n",def.getName()));
                }
                for (NameDef d : def.getNames().values()){
                    wr.write(String.format("        addName(new "+ def.getPackage() +"."+getShortName(d) +"(this));\n",d.getName()));
                }
                
                wr.write("    }\n");
                wr.write("}\n");
                wr.close();
            } catch (IOException ex) {
                Logger.getLogger(NamespaceProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!def.getSubNamespaces().isEmpty()){
            generateCode(def.getSubNamespaces());
        }
        
        if (!def.getNames().isEmpty()){
            for (NameDef nd: def.getNames().values())
                generateNameCode(nd);
        }
    }
    
    private void generateNameCode(NameDef def) {
        if (def.getName().length() > 0){
            try {
                String classname = getShortName(def);
                JavaFileObject fo = filer.createSourceFile(createFilename(def));
                Writer wr = fo.openWriter();
                wr.write("package ");
                wr.write(def.getParent().getPackage());
                //wr.write(classname.substring(0,classname.lastIndexOf("/")).replaceAll("/", "."));
                wr.write(";\n");
                wr.write("import be.belgiplast.utilities.namespaces.Name;\n");
                wr.write("import be.belgiplast.utilities.namespaces.Namespace;\n");
                wr.write("import be.belgiplast.utilities.namespaces.support.AbstractNameSupport;\n");
                wr.write(String.format("public class %s extends AbstractNameSupport{\n",classname));
                wr.write(String.format("    public %s (Namespace parent){\n",classname));
                wr.write(String.format("        super(\"%s\",parent);\n",def.getName()));
                
                wr.write("    }\n");
                wr.write("}\n");
                wr.close();
            } catch (IOException ex) {
                Logger.getLogger(NamespaceProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void generateCode(Map<String, NamespaceDef> nss) {
        for (NamespaceDef def : nss.values()){
            generateNamespaceCode(def);
        }
    }
}
