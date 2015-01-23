/**
 * 
 */
package org.jocean.management;

import java.beans.ConstructorProperties;

/**
 * @author isdom
 *
 */
public class KeyValueAsString {

	// standard JavaBean conventions with getters
	@ConstructorProperties({"key", "value"})
	public KeyValueAsString(final String key, final String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	private	final String key;

	private	final String value;
}
