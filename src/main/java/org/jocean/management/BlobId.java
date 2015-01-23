/**
 * 
 */
package org.jocean.management;

import java.beans.ConstructorProperties;

/**
 * @author isdom
 *
 */
public class BlobId {

	// standard JavaBean conventions with getters
	@ConstructorProperties({"md5", "name"})
	public BlobId(final String md5, final String name) {
		this.md5 = md5;
		this.name = name;
	}
	
	public String getMd5() {
		return md5;
	}
	
	public String getName() {
		return name;
	}

	private	final String md5;

	private	final String name;
}
