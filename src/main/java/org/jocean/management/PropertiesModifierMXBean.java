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
public interface PropertiesModifierMXBean {

	public static final ObjectName PROPS_MODIFIER_OBJECTNAME =
			MBeanUtils.safeGetObjectName("org.jocean:type=propsModifier");
	
	public	KeyValueAsString[] getAllProperty();

	public void setProperty(String key, String value);
}
