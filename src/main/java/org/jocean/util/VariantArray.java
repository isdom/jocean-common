/**
 * 
 */
package org.jocean.util;

/**
 * @author isdom
 *
 */
public interface VariantArray {
	public int getArrayCount();
	
	public Class<?> getTypeAt(final int idx);
	
	public Object 	getVariantAt(final int idx);
	
	public void 	setVariantAt(final int idx, final Object variant);
	
	public String	getNameAt(final int idx);
}
