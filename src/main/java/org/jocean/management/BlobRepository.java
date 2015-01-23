/**
 * 
 */
package org.jocean.management;

import java.io.InputStream;

/**
 * @author isdom
 *
 */
public interface BlobRepository extends BlobRepositoryMXBean {
	
	public InputStream getStreamDataOfBlob(String blobMD5);
	
	public InputStream getStreamDataByName(String blobName);

}
