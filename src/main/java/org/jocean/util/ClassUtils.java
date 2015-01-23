/**
 * 
 */
package org.jocean.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author isdom
 *
 */
public class ClassUtils {
	
    private static final Logger LOG = 
        	LoggerFactory.getLogger(ClassUtils.class);
    
	public static Class<?> type2Class(final String type) {
		if ( type.equals("int") || type.equals("java.lang.Integer") ) {
			return	Integer.TYPE;
		}
		else if ( type.equals("byte") || type.equals("java.lang.Byte")) {
			return	 Byte.TYPE;
		}
		else if ( type.equals("short") || type.equals("java.lang.Short")) {
			return	Short.TYPE;
		}
		else if ( type.equals("long") || type.equals("java.lang.Long")) {
			return	Long.TYPE;
		}
		else if ( type.equals("boolean") || type.equals("java.lang.Boolean")) {
			return	Boolean.TYPE;
		}
		else if ( type.equals("float") || type.equals("java.lang.Float")) {
			return	Float.TYPE;
		}
		else if ( type.equals("double") || type.equals("java.lang.Double")) {
			return	Double.TYPE;
		}
		else {
			try {
				return Class.forName(type);
			} catch (ClassNotFoundException e) {
				LOG.error("failed to Class.forName {}, detail: {}", type, ExceptionUtils.exception2detail(e));
				return	null;
			}
		}
	}


}
