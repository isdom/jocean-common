/**
 * 
 */
package org.jocean.util;

/**
 * @author isdom
 *
 */
public class VersionUtils {

	public static int compareVersion(String v1, String v2) {
		do {
			int subV1 = getVersionNumber(v1);
			int subV2 = getVersionNumber(v2);
			if ( subV1 != subV2 ) {
				return	subV1 - subV2;
			}
			v1 = leftShiftVersion(v1);
			v2 = leftShiftVersion(v2);
		} while ( null != v1 || null != v2 );
		
		return 0;
	}

	private static String leftShiftVersion(final String version) {
		if ( null == version ) {
			return null;
		}
		final int idx = version.indexOf('.');
		return ( -1 == idx ) 
				? null
				: version.substring(idx+1);
	}

	private static int getVersionNumber(final String version) {
		try {
			if ( null == version ) {
				return -1;
			}
			final int idx = version.indexOf('.');
			return ( -1 == idx ) 
					? Integer.parseInt( version ) 
					: Integer.parseInt( version.substring(0, idx) );
		}
		catch ( Exception e) {
			return -1;
		}
	}
}
