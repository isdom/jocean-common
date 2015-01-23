/**
 * 
 */
package org.jocean.common;

import javax.management.ObjectName;

import org.jocean.util.MBeanUtils;

/**
 * @author isdom
 *
 */
public class MBeanConstants {
	public static final String AGENT_LOCAL_NODE_PREFIX = 
			"org.jocean:type=localnode,";

	public static final ObjectName AGENT_OBJECTNAME;
	
	public static final ObjectName AGENT_JMXSVR_RELAY_OBJECTNAME;
	
	public static final ObjectName AGENT_SELFVM_OBJECTNAME;
	
	public static final ObjectName JMIMPL_MBEANSERVER_DELEGATE;
	
	public static final ObjectName INSTRUMENT_CLASSLOADINGDETAIL_OBJECTNAME;

	static {
		AGENT_OBJECTNAME = 
				MBeanUtils.safeGetObjectName("org.jocean:type=agent");
		AGENT_JMXSVR_RELAY_OBJECTNAME = 
				MBeanUtils.safeGetObjectName("org.jocean:type=agent,name0=jmxrelay");
		AGENT_SELFVM_OBJECTNAME = 
				MBeanUtils.safeGetObjectName("org.jocean:type=agent,name0=selfVM");
		JMIMPL_MBEANSERVER_DELEGATE = 
				MBeanUtils.safeGetObjectName("JMImplementation:type=MBeanServerDelegate");
		INSTRUMENT_CLASSLOADINGDETAIL_OBJECTNAME = 
				MBeanUtils.safeGetObjectName("org.jocean:type=instrument,name0=ClassLoadingDetail");
	}
}
