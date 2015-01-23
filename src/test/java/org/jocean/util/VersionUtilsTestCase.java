/**
 * 
 */
package org.jocean.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author isdom
 *
 */
public class VersionUtilsTestCase {

	/**
	 * Test method for {@link org.jocean.util.VersionUtils#compareVersion(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCompareVersionEquals() {
		assertEquals( VersionUtils.compareVersion("1.0.1", "1.0.1"), 0);
	}

	@Test
	public final void testCompareVersionMoreThan() {
		assertTrue( VersionUtils.compareVersion("1.0.1", "1.0.0") > 0);
	}

	@Test
	public final void testCompareVersionLessThan() {
		assertTrue( VersionUtils.compareVersion("1.0.1", "1.9.0") < 0);
	}

	@Test
	public final void testCompareVersionMoreThan2() {
		//	native String compareTo for "12.0.1" and "9.0.1"
		assertTrue( "12.0.1".compareTo( "9.0.1") < 0);
		assertTrue( VersionUtils.compareVersion("12.0.1", "9.0.1") > 0);
	}

	@Test
	public final void testCompareVersionMoreThan3() {
		assertTrue( VersionUtils.compareVersion("12.0", "9.0.1") > 0);
	}

	@Test
	public final void testCompareVersionMoreThan4() {
		assertTrue( VersionUtils.compareVersion("9.0.0", "9.0") > 0);
	}
}
