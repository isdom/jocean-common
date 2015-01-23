/**
 * 
 */
package org.jocean.common;

import static org.junit.Assert.*;

import org.jocean.common.LocalVirtualMachineVO;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author isdom
 *
 */
public class LocalVirtualMachineVOTestCase {

	/**
	 * Test method for {@link org.jocean.common.LocalVirtualMachineVO#getDisplayName()}.
	 */
	@Test
	public final void testFastJson() {
		final String source = "{\"attachable\":true,\"connectorAddress\":" +
				"\"service:jmx:rmi://127.0.0.1/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2Vydm" +
				"VySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZ" +
				"hLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc2AAtVbmljYXN0UmVmMgAACzE3Mi4xNi4zLjI3AABV" +
				"zk3uxrVJqzF5M2ZN/AAAATrkhCb1gAEAeA==\"," +
				"\"displayName\":\"jocean-logkeeper.jar\",\"manageable\":true," +
				"\"nameByPathAndCommand\":\"/database/zookeeper/logkeeper/jocean-logkeeper-0.0.1-5-2012-11-09_17-30-58-scm-1541/bin/jocean-logkeeper.jar\"," +
				"\"userName\":\"zookeeper\"}";
		LocalVirtualMachineVO lvm = JSON.parseObject(source, LocalVirtualMachineVO.class);
		
		assertEquals(true, lvm.isAttachable());
		assertEquals("zookeeper", lvm.getUserName());
	}

}
