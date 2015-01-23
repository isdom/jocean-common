/**
 * 
 */
package org.jocean.util;

/**
 * @author isdom
 *
 */
public interface JMXConnectionContext {

	public String getProtocol();
	
	public String getConnectionId();
	
	public String getPeerIp();
	
	public int getPeerPort();
}
