/**
 * 
 */
package org.jocean.management;

import java.beans.ConstructorProperties;

/**
 * @author isdom
 *
 */
public class BlobUnit {
	
	// standard JavaBean conventions with getters
	@ConstructorProperties({"bytes", "md5"})
	public BlobUnit(final byte[] bytes, final String md5) {
		this.bytes = bytes;
		this.md5 = md5;
	}
	
	public byte[] getBytes() {
		return	this.bytes;
	}
	
	public String getMd5() {
		return	this.md5;
	}
	
	private	final byte[] bytes;
	private final String md5;
}
