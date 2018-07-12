/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.annotations.Entities;
import be.belgiplast.utilities.annotations.Entity;
import be.belgiplast.utilities.annotations.Name;
import be.belgiplast.utilities.annotations.Namespace;
import be.belgiplast.utilities.annotations.Namespaces;
import be.belgiplast.utilities.annotations.Relationship;
import be.belgiplast.utilities.annotations.Relationships;
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
        {"be.belgiplast.utilities.annotations.Entity", "be.belgiplast.utilities.annotations.Relationship",
        "be.belgiplast.utilities.annotations.Entities", "be.belgiplast.utilities.annotations.Relationships"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntitiesProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Context context;
    
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
        context = new Context();
        
        retrieveNamespaces(roundEnv);
        retrieveEntities(roundEnv);
        retrieveRelationships(roundEnv);
        cleanNamespaces();
        
        generateCode();
        
        return true;
    }
    
    private void retrieveEntities(RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Entities.class)) {

            String packagePrefix = "";
            if (annotatedElement instanceof PackageElement){
                packagePrefix = ((PackageElement)annotatedElement).getQualifiedName().toString();
                for (Entity ns2 : annotatedElement.getAnnotationsByType(Entity.class)){
                    processAnnotation(ns2, packagePrefix);
                }
            }else if (annotatedElement instanceof TypeElement){
                packagePrefix = ((TypeElement)annotatedElement).getQualifiedName().toString();
                Entity ns = annotatedElement.getAnnotation(Entity.class);
                processAnnotation( ns, packagePrefix);
            }

        }
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Entity.class)) {

            String packagePrefix = "";
            if (annotatedElement instanceof PackageElement){
                packagePrefix = ((PackageElement)annotatedElement).getQualifiedName().toString();
                for (Entity ns2 : annotatedElement.getAnnotationsByType(Entity.class)){
                    processAnnotation(ns2, packagePrefix);
                }
            }else if (annotatedElement instanceof TypeElement){
                packagePrefix = ((TypeElement)annotatedElement).getQualifiedName().toString();
                Entity ns = annotatedElement.getAnnotation(Entity.class);
                processAnnotation( ns, packagePrefix);
            }

        }
    }
    
    private void retrieveRelationships(RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Relationships.class)) {

            String packagePrefix = "";
            if (annotatedElement instanceof PackageElement){
                packagePrefix = ((PackageElement)annotatedElement).getQualifiedName().toString();
                for (Relationship ns2 : annotatedElement.getAnnotationsByType(Relationship.class)){
                    processAnnotation(ns2, packagePrefix);
                }
            }else if (annotatedElement instanceof TypeElement){
                packagePrefix = ((TypeElement)annotatedElement).getQualifiedName().toString();
                Relationship ns = annotatedElement.getAnnotation(Relationship.class);
                processAnnotation( ns, packagePrefix);
            }
        }
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Relationship.class)) {

            String packagePrefix = "";
            if (annotatedElement instanceof PackageElement){
                packagePrefix = ((PackageElement)annotatedElement).getQualifiedName().toString();
                for (Relationship ns2 : annotatedElement.getAnnotationsByType(Relationship.class)){
                    processAnnotation(ns2, packagePrefix);
                }
            }else if (annotatedElement instanceof TypeElement){
                packagePrefix = ((TypeElement)annotatedElement).getQualifiedName().toString();
                Relationship ns = annotatedElement.getAnnotation(Relationship.class);
                processAnnotation( ns, packagePrefix);
            }
        }
    }

    private void retrieveNamespaces(RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Namespaces.class)) {
            
            String packagePrefix = "";
            if (annotatedElement instanceof PackageElement){
                packagePrefix = ((PackageElement)annotatedElement).getQualifiedName().toString();
                for (Namespace ns2 : annotatedElement.getAnnotationsByType(Namespace.class)){
                    processAnnotation(ns2, packagePrefix);
                }
            }else if (annotatedElement instanceof TypeElement){
                packagePrefix = ((TypeElement)annotatedElement).getQualifiedName().toString();
                Namespace ns = annotatedElement.getAnnotation(Namespace.class);
                processAnnotation( ns, packagePrefix);
            }
            
        }
    }
    
    private void processAnnotation( Entity ns, String packagePrefix) {
        EntityDef def = context.getEntities().get(ns.type());
        if (def == null){
            def = new EntityDef(ns);
            def.setName(ns.type());
            context.getEntities().put(ns.type(),def);
        }
        def.setPackage(packagePrefix);
    }
    
    private void processAnnotation( Relationship ns, String packagePrefix) {
        RelationshipDef def = context.getRelationships().get(ns.type());
        if (def == null){
            def = new RelationshipDef(ns);
            def.setName(ns.type());
            context.getRelationships().put(ns.type(),def);
        }
        def.setPackage(packagePrefix);
    }

    private void processAnnotation( Namespace ns, String packagePrefix) {
        NamespaceDef def = context.getNamespaces().get(ns.name());
        if (def == null){
            def = new NamespaceDef();
            def.setName(ns.name());
            context.getNamespaces().put(ns.name(),def);
        }
        def.setPackage(packagePrefix);
        if (ns.parent() != ""){
            NamespaceDef parent = context.getNamespaces().get(ns.parent());
            if (parent == null){
                parent = new NamespaceDef();
                parent.setName(ns.parent());
                context.getNamespaces().put(ns.parent(),parent);
            }
            parent.addNamespace(def);
        }
        for (Name n : ns.names()){
            NameDef ndef = new NameDef(n);
            def.addName(ndef);
        }
    }
    
    private void cleanNamespaces() {
        //remove non root Namespace definitions
        Iterator<NamespaceDef> it = context.getNamespaces().values().iterator();
        while (it.hasNext()){
            NamespaceDef def = it.next();
            if (def.getParent() != null)
                it.remove();
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
    
    private String createClassname(EntityDef def){
        StringBuilder builder = new StringBuilder();
        if (def.getPackage() != null && !def.getPackage().isEmpty()){
            builder.append(def.getPackage());
            builder.append(".");
        }
        String name = getShortName(def);
        builder.append(name);
        return builder.toString();
    }
    
    private String createClassname(RelationshipDef def){
        StringBuilder builder = new StringBuilder();
        if (def.getPackage() != null && !def.getPackage().isEmpty()){
            builder.append(def.getPackage());
            builder.append(".");
        }
        String name = getShortName(def);
        builder.append(name);
        return builder.toString();
    }

    private String getShortName(AbstractDefinition def) {
        String name = def.getName();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        return name;
    }
    
    private String createFilename(NameDef def){
        String name = createClassname(def);
        name.replaceAll(".", "/");
        return name;
    }
    
    private String createFilename(EntityDef def){
        String name = createClassname(def);
        name.replaceAll(".", "/");
        return name;
    }
    
    private String createFilename(RelationshipDef def){
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
                Logger.getLogger(EntitiesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!def.getSubNamespaces().isEmpty()){
            for (NamespaceDef n : def.getSubNamespaces().values())
            generateNamespaceCode(n);
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
                Logger.getLogger(EntitiesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void generateEntityCode(EntityDef def) {
        if (def.getName().length() > 0){
            try {
                String classname = getShortName(def);
                JavaFileObject fo = filer.createSourceFile(createFilename(def)+"Entity");
                Writer wr = fo.openWriter();
                wr.write("package ");
                wr.write(def.getPackage());
                //wr.write(classname.substring(0,classname.lastIndexOf("/")).replaceAll("/", "."));
                wr.write(";\n");
                wr.write("import be.belgiplast.utilities.namespaces.Namespace;\n");
                wr.write("import be.belgiplast.utilities.namespaces.SystemNamespace;\n");
                wr.write("import be.belgiplast.utilities.relationships.support.EntitySupport;\n");
                wr.write(String.format("public class %sEntity extends EntitySupport{\n",classname));
                wr.write(String.format("    public %sEntity(String name,Namespace parent){\n",classname));
                wr.write(String.format("        super(SystemNamespace.getInstance().getName(\"%s\"),name,parent);\n",def.getName()));
                
                wr.write("    }\n");
                wr.write("}\n");
                wr.close();
            } catch (IOException ex) {
                Logger.getLogger(EntitiesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void generateRelationshipCode(RelationshipDef def) {
        if (def.getName().length() > 0){
            try {
                String classname = getShortName(def);
                JavaFileObject fo = filer.createSourceFile(createFilename(def)+"Relationship");
                Writer wr = fo.openWriter();
                wr.write("package ");
                wr.write(def.getPackage());
                //wr.write(classname.substring(0,classname.lastIndexOf("/")).replaceAll("/", "."));
                wr.write(";\n");
                wr.write("import be.belgiplast.utilities.namespaces.Name;\n");
                wr.write("import be.belgiplast.utilities.namespaces.Namespace;\n");
                wr.write("import be.belgiplast.utilities.namespaces.SystemNamespace;\n");
                wr.write("import be.belgiplast.utilities.relationships.support.RelationshipSupport;\n");
                wr.write(String.format("public class %sRelationship extends RelationshipSupport{\n",classname));
                wr.write(String.format("    public %sRelationship (String name,Namespace parent){\n",classname));
                wr.write(String.format("        super(parent,name,SystemNamespace.getInstance().getName(\"%s\"));\n",def.getName()));
                
                wr.write("    }\n");
                wr.write("}\n");
                wr.close();
            } catch (IOException ex) {
                Logger.getLogger(EntitiesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void generateCode() {
        for (NamespaceDef def : context.getNamespaces().values()){
            generateNamespaceCode(def);
        }
        for (EntityDef def : context.getEntities().values()){
            generateEntityCode(def);
        }
        for (RelationshipDef def : context.getRelationships().values()){
            generateRelationshipCode(def);
        }
    }
    
    
}
