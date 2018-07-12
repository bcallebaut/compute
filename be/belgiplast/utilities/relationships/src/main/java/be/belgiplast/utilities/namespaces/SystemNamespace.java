/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

import be.belgiplast.utilities.namespaces.support.AbstractNamespaceSupport;
import java.util.ServiceLoader;

/**
 *
 * @author benoit
 */
public final class SystemNamespace extends AbstractNamespaceSupport implements Namespace{
    
    private static SystemNamespace instance = null;

    public static SystemNamespace getInstance() {
        if (instance == null)
            instance = new SystemNamespace();
        return instance;
    }
    
    private SystemNamespace() {
        super("root",null);
        ServiceLoader<NamespaceProvider> loader = ServiceLoader.load(NamespaceProvider.class);
        for (NamespaceProvider provider : loader)
            addNamespace(provider.getNamespace(this));
    }
    
    public Name getName(String name){
        String[] names = name.split("\\056");
        Namespace n = this;
        for (int i = 0; i < names.length ; i++){
            if (i < names.length - 1){
                n = n.findNamespaceByName(names[i]);
                if (n == null)
                    return null;
            }else{
                return n.findNameByName(names[i]);
            }
        }
        return null;
    }
}
