/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.util;

/**
 *
 * @author benoit
 */
public interface Decorated {
    <D extends Decorator> D getDecorator(Class<D> clazz);
}
