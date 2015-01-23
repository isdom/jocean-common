/**
 * 
 */
package org.jocean.management;

import javax.management.ObjectName;

import org.jocean.util.MBeanUtils;
import org.jocean.util.VersionableAsString;


/**
 * @author isdom
 *
 */
public interface MBeansIssuerMXBean extends VersionableAsString {

	public static final ObjectName MBEANS_ISSUER_OBJECTNAME = 
			MBeanUtils.safeGetObjectName("org.jocean:type=agent,name0=mbeansIssuer");
	
	public String getConnectString();
	
	public void setConnectString(String connectString);
	
	public int getSessionTimeout();
	
	public void setSessionTimeout(int sessionTimeout);
	
	public String getPublishCategory();
	
	public void setPublishCategory(final String category);
	
	public boolean isPublishLocalNode();
	
	public void setPublishLocalNode(boolean publishLocalNode);
	
	public void start();
	
	public void stop();
	
	public boolean isRunning();
	
	public boolean isZKConnected();
	
	public void republish(
			String nodeName, 
			String objectName, 
			String attrOrOpName);
}
