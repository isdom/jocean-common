/**
 * 
 */
package org.jocean.management;

import javax.management.ObjectName;

import org.jocean.util.MBeanUtils;


/**
 * @author isdom
 *
 */
public interface AgentLocatorMXBean {
	
	public static final ObjectName AGENT_LOCATOR_OBJECTNAME = 
			MBeanUtils.safeGetObjectName("org.jocean:type=agentLocator");
	
    public void publishSelfAsCurrentAgent();
    
    public void retireSelfAsCurrentAgent();
}
