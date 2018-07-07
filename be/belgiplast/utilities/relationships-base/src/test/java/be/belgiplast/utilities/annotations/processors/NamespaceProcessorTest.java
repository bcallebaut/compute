/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations.processors;

import com.google.common.io.Resources;
import com.google.testing.compile.Compilation;
import static com.google.testing.compile.Compiler.javac;
import com.google.testing.compile.JavaFileObjects;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author benoit
 */
public class NamespaceProcessorTest {
    
    public NamespaceProcessorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class NamespaceProcessor.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        //URL url = Resources.getResource("package-info.java");
        Compilation compilation;
        compilation = javac()
                .withProcessors(new NamespaceProcessor())
                .compile(JavaFileObjects.forSourceLines("be.belgiplast.utilities.annotations.processors.package-info", 
                        "@be.belgiplast.utilities.annotations.Namespace(name=\"test\",names={\n" +
                    "    @Name(name=\"foo\"),@Name(name=\"bar\")\n" +
                        "})\n" +
                        "@be.belgiplast.utilities.annotations.Namespace(name=\"test2\",names={\n" +
                    "    @Name(name=\"john\"),@Name(name=\"smith\")\n" +
                        "},parent=\"test\")\n" +
                        "package be.belgiplast.utilities.annotations.processors;\n" +
                        "\n" +
                        "import be.belgiplast.utilities.annotations.Name;"));
        for (JavaFileObject jfo : compilation.sourceFiles()){
            System.out.println(jfo.getName());
        }
        for (JavaFileObject jfo : compilation.generatedSourceFiles()){
            System.out.println(jfo.getName());
            try {
                Reader rd = jfo.openReader(true);
                BufferedReader br = new BufferedReader(rd);
                br.lines().forEach((String l)->{System.out.println(l);});
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(NamespaceProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        for (JavaFileObject jfo : compilation.generatedFiles()){
            System.out.println(jfo.getName());
            
            
        }
    }
}
