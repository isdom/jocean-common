/**
 * 
 */
package org.jocean.util;

/**
 * @author isdom
 *
 */
public interface Visitor2<T, E> {
	public void visit(T t, E e) throws Exception;
}
