/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.belgiplast.utilities.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author T0194671
 */
@Target({ElementType.PACKAGE,ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Name {
    String name();
    String namespace() default "";
}
