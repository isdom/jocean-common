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
public interface BlobRepositoryMXBean extends VersionableAsString {
	
	public static final ObjectName BLOB_REPO_OBJECTNAME =
			MBeanUtils.safeGetObjectName("org.jocean:type=blobRepository");
	
	public BlobId[] getBlobIds();
	
	public boolean allocBlobEntry(String blobMD5);
	
	/**
	 * 特定blob是否已经有效，也就是 特定blob 已经 调用了completeAndValidBlob，并返回为true
	 * @param blobMD5
	 * @return
	 */
	public boolean isBlobValid(String blobMD5);
	
	//	alias name 
	public boolean setNameOfBlob(String blobName, String blobMD5);

	public String getBlobMD5ByName(String blobName);
	
	public void removeBlob(String blobMD5);

	//	upload blob
	public	boolean appendBlobPart(
			String blobMD5, 
			int partIdx, 
			byte[] blobPart);
	
	//	重复调用，上传所有blob 内容
	public boolean appendBlobPartWithMD5(
			String blobMD5, 
			int blobPartIdx, 
			byte[] blobPart, 
			String partMD5);
	
	public boolean completeAndValidBlob(String blobMD5);

	public boolean addBlob(String blobMD5, byte[] blob);

	//	download blob
	public int		getBlobPartCount(String blobMD5);
	
	public BlobUnit fetchBlobPart(String blobMD5, int blobPartIdx);
}
