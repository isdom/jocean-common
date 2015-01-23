/**
 * 
 */
package org.jocean.util;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * @author isdom
 *
 */
public class MDUtils {
	// 用来将字节转换成 16 进制表示的字符
	private final static char HEXDIGITS[] = {       
		     '0', '1', '2', '3', '4', '5', '6', '7', 
		     '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'}; 
	
	public static String digestInBytesToString(final byte[] digest) {
		StringBuilder sb = new StringBuilder();
		
	    for (byte b : digest) {          // 从第一个字节开始，对 MD5 的每一个字节
	    	// 转换成 16 进制字符的转换
	    	
	    	// 取字节中高 4 位的数字转换,
	    	// >>> 为逻辑右移，将符号位一起右移
	    	sb.append(HEXDIGITS[b >>> 4 & 0xf]);
	    	
	    	// 取字节中低 4 位的数字转换
	    	sb.append(HEXDIGITS[b & 0xf]);
	    }
	    
	    return	sb.toString();
	}
	
	public static boolean isDigestEquals(String digest1, String digest2) {
		return	digest1.toLowerCase().equals(digest2.toLowerCase());
	}
	
	public static String getMD5Of(final byte[] bytes) {
		try {
			java.security.MessageDigest md = 
					java.security.MessageDigest.getInstance( "MD5" );
			return	MDUtils.digestInBytesToString( md.digest(bytes) );
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("MDUtils.getMD5Of", e);
		}
	}
	
	public static String getMD5Of(final Collection<byte[]> bytesCollection) {
		try {
			java.security.MessageDigest md = 
					java.security.MessageDigest.getInstance( "MD5" );
			for ( byte[] bytes : bytesCollection ) {
				md.update(bytes);
			}
			
			return	MDUtils.digestInBytesToString( md.digest() );
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("MDUtils.getMD5Of", e);
		}
	}
}
