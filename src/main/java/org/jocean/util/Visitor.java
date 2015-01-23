/**
 * 
 */
package org.jocean.util;

/**
 * @author isdom
 *
 */
public interface Visitor<T> {
	public void visit(T t) throws Exception;
}
