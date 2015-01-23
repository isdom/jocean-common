/**
 * 
 */
package org.jocean.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author isdom
 *
 */
public class NetworkUtils {
    private static final Logger LOG = LoggerFactory.getLogger(NetworkUtils.class);

	/**
	 * @return
	 */
	private static Collection<NetworkInterface> getAllNetworkInterfaces() {
		//	NullObject 模式
		Collection<NetworkInterface> interfaces = Collections.emptyList();

		try {
			interfaces = Collections.
					list( NetworkInterface.getNetworkInterfaces() );
		} catch (SocketException e) {
			LOG.error("failed when NetworkInterface.getNetworkInterfaces()", e);
		}
		return interfaces;
	}
	
	public static InetAddress[] getAllInetAddressReachableFor(
			final String destAddress, final int destPort, final boolean onlyFirst) {
		
		final List<InetAddress> ret = new ArrayList<InetAddress>();

		final Collection<NetworkInterface> interfaces = getAllNetworkInterfaces();

		//	如果 NetworkInterface.getNetworkInterfaces() 抛出异常
		//	则 interfaces 即为初始赋值 emptyList
		//	不会进入执行 循环体内操作，引用  NullObject 模式
		//	参见：http://en.wikipedia.org/wiki/Null_Object_pattern
		
		OUTER: for (NetworkInterface interface_ : interfaces ) {

			try {
				// we shouldn't care about loopback addresses
				if (interface_.isLoopback())
					continue;

				// if you don't expect the interface to be up you can skip this
				// though it would question the usability of the rest of the
				// code
				if (!interface_.isUp())
					continue;
			}
			catch(SocketException e) {
				LOG.warn("failed check isLoopback and isUp", e);
				continue;
			}
			
			// iterate over the addresses associated with the interface
			final Enumeration<InetAddress> addresses = interface_
					.getInetAddresses();
			for (InetAddress address : Collections.list(addresses)) {
				// look only for ipv4 addresses
				if (address instanceof Inet6Address)
					continue;

				if ( LOG.isInfoEnabled() ) {
					LOG.info("begin test address {}", address.getHostAddress());
				}
				SocketChannel socket = null;
				try {
					socket = SocketChannel.open();
					// again, use a big enough timeout
					socket.socket().setSoTimeout(3000);

					// bind the socket to your local interface
					//	A valid port value is between 0 and 65535. 
					//		A port number of zero will let the system pick up an ephemeral port in a bind operation. 
					socket.socket().bind(new InetSocketAddress(address, 0));

					// try to connect to *somewhere*
					socket.connect(new InetSocketAddress(destAddress,
							destPort));
					
					if ( LOG.isInfoEnabled() ) {
						LOG.info("test address {} succeed, connect to {}:{} ok", 
								new Object[]{address.getHostAddress(), destAddress, destPort});
					}
				} catch (IOException ex) {
					if ( LOG.isInfoEnabled() ) {
						LOG.info("test address {} failed, can't connect to {}:{}", 
								new Object[]{address.getHostAddress(), destAddress, destPort});
					}
					continue;
				}
				finally {
					if ( null != socket ) {
						try {
							socket.close();
						} catch (IOException e) {
							// e.printStackTrace();
						}
					}
				}
				
				ret.add(address);
				if ( onlyFirst ) {
					break OUTER;
				}
			}
		}

		return ret.toArray(new InetAddress[0]);
	}

	private static NetworkInterface getNetworkInterfaceContains(
			final Collection<NetworkInterface> interfaces, 
			final InetAddress ipv6) {
		for (NetworkInterface interface_ : interfaces ) {

			// iterate over the addresses associated with the interface
			final Enumeration<InetAddress> addresses = interface_.getInetAddresses();
			for (InetAddress address : Collections.list(addresses)) {
				// look only for ipv4 addresses
				if (ipv6.equals(address)) {
					return	interface_;
				}
			}
		}
		
		return	null;
	}
	
	private static InetAddress getIPv4AddrOf(final NetworkInterface intf) {
		final Enumeration<InetAddress> addresses = intf.getInetAddresses();
		for (InetAddress address : Collections.list(addresses)) {
			if (address instanceof Inet4Address)
				return address;
		}
		
		return	null;
	}

	public static InetAddress getIPv4AddrOfIPv6(final InetAddress ipv6) {
		if (ipv6 instanceof Inet6Address) {
			final NetworkInterface intf = getNetworkInterfaceContains(getAllNetworkInterfaces(), ipv6);

			if ( null != intf ) {
				return	getIPv4AddrOf(intf);
			}
			else {
				return	null;
			}
		}
		else {
			return	ipv6;
		}
	}
	
	public static InetAddress getFirstInetAddressReachableFor(
			final String destAddress, final int destPort) {
		final InetAddress[] addrs = getAllInetAddressReachableFor(destAddress, destPort, true);
		return	(addrs.length == 0) ? null : addrs[0];
	}
	
	public static String getFirstInetAddressReachableForAsString(
			final String destAddress, final int destPort) {
		final InetAddress[] addrs = getAllInetAddressReachableFor(destAddress, destPort, true);
		return	(addrs.length == 0) ? "" : addrs[0].getHostAddress();
	}
	
	public static String genJMXServiceURLOf(
			final String protocol, final String testAddress, final int testPort) {
		return "service:jmx:" + protocol + "://" +
			NetworkUtils.getFirstInetAddressReachableForAsString(testAddress, testPort);
	}
	
	public static String genJMXServiceURLWithSuffixOf(
			final String protocol, final String testAddress, final int testPort, final String suffix ) {
		return "service:jmx:" + protocol + "://" +
			NetworkUtils.getFirstInetAddressReachableForAsString(testAddress, testPort)
			+ ( suffix != null ? suffix : "");
	}
	
	public static String genJMXServiceURLByInetAddress(
			final String protocol, final InetAddress address, final String suffix ) {
		final InetAddress ipv4 = getIPv4AddrOfIPv6(address);
		return "service:jmx:" + protocol + "://" 
			+ ( ipv4 != null ? ipv4.getHostAddress() : "localhost" )
			+ ( suffix != null ? suffix : "");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(
				getFirstInetAddressReachableFor("172.16.3.27", 8080).getHostAddress());
	}

}
