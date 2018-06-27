/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import be.belgiplast.utilities.annotations.Name;
import be.belgiplast.utilities.annotations.Namespace;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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

/**
 *
 * @author T0194671
 */
@SupportedAnnotationTypes(
        {"be.belgiplast.utilities.annotations.Name", "be.belgiplast.utilities.annotations.Namespace"})
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

    private void generateCode(Map<String, NamespaceDef> nss) {
        
    }

}
