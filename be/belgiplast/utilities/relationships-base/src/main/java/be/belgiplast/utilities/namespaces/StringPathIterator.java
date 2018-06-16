/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.namespaces;

/**
 *
 * @author benoit
 */
public class StringPathIterator extends PathIterator<String>{
    private String separator;
    private String[] parts;
    private int index = 0;
    
    public StringPathIterator(String separator){
        super();
        this.separator = separator;
    }

    @Override
    public void initialize(String element) {
        super.initialize(element); 
        parts = element.split(separator);
        index = 0;
    }

    @Override
    public String getCategory() {
        return "word";
    }

    @Override
    public boolean accept(Object e) {
        return e instanceof String;
    }

    @Override
    public boolean hasNext() {
        return index < parts.length;
    }

    @Override
    public String next() {
        String result = parts[index];
        index++;
        return result;
    }
}
