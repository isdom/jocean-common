/**
 * 
 */
package org.jocean.util;

/**
 * @author isdom
 *
 */
public interface JMXConnectionContextProvider {
	
	/**
	 * 获取当前线程相关的JMXConnectionContext
	 * @return
	 * 有效上下文或null
	 */
	public JMXConnectionContext currentJMXConnectionContext();
}
