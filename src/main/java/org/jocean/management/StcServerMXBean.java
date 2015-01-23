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
public interface StcServerMXBean extends VersionableAsString {
	
	//	current version "0.0.1"
	
	public static final ObjectName STC_SERVER_FACADE_OBJECTNAME = 
			MBeanUtils.safeGetObjectName("stc.skymobi:name=serverFacade");
	
	public String getServerId();
}
