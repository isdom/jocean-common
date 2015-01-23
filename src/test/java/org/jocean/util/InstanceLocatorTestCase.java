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
public class InstanceLocatorTestCase {

	/**
	 * Test method for {@link org.jocean.util.InstanceLocator#locateInstance(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLocateInstance() {
		final Object testObj = new Object();
		InstanceLocator.registerInstance("TestType", "test", testObj);
		
		assertEquals(InstanceLocator.locateInstance("TestType", "test"), testObj);
	}

}
