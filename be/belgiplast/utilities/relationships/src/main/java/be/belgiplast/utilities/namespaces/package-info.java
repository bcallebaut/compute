/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  A Namespace is a lexical concept used to organize code an data in logical groups.
 * Example of namespace are:
 * <ul>
 *   <li>C/C++ header (include) directories</li>
 *   <li>C++ Namespace</li>
 *   <li>C++/Java Classes</li>
 *   <li>Java packages</li>  
 * </ul>
 * 
 * Namespace being a lexical concept is not tied directly to execution and thus the fact that a namespace come in scope or out of scope during execution
 * is not in the scope of this API. It is in the scope of the Scope API
 * 
 * Namespace is also not strictly tied to the source code. 
 * For example, there is no mean to access globally a local variable during execution an so, no point for a local variable to have a fully qualified name but
 * For code analysis or refactoring support, it can be useful to know exactly where a variable is defined
 */
package be.belgiplast.utilities.namespaces;
