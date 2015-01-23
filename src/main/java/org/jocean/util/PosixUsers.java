/**
 * 
 */
package org.jocean.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author isdom
 *
 */
public class PosixUsers {
	
    private static final Logger LOG = 
        	LoggerFactory.getLogger(PosixUsers.class);
    
	public static final class UserInfo {
		private	final String	_name;
		//	for uid | gid equals 4294967294 
		private	final long		_uid;
		private	final long		_gid;
		private	final String	_gecos;
		private	final String	_homedir;
		private	final String	_shell;
		
		UserInfo(String name, long uid, long gid, String gecos, String homedir, String shell) {
			this._name = name;
			this._uid = uid;
			this._gid = gid;
			this._gecos = gecos;
			this._homedir = homedir;
			this._shell = shell;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return _name;
		}

		/**
		 * @return the uid
		 */
		public long getUid() {
			return _uid;
		}

		/**
		 * @return the gid
		 */
		public long getGid() {
			return _gid;
		}

		/**
		 * @return the gecos
		 */
		public String getGecos() {
			return _gecos;
		}

		/**
		 * @return the homedir
		 */
		public String getHomedir() {
			return _homedir;
		}

		/**
		 * @return the shell
		 */
		public String getShell() {
			return _shell;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "UserInfo [_name=" + _name + ", _uid=" + _uid + ", _gid="
					+ _gid + ", _gecos=" + _gecos + ", _homedir=" + _homedir
					+ ", _shell=" + _shell + "]";
		}
		
	}
	
	public static PosixUsers getUsers() {
		return	getUsers("/etc/passwd");
	}
	
	public static PosixUsers getUsers(final String passwdpath) {
		final File fpasswd = new File(passwdpath);
		
		if ( !fpasswd.exists() ) {
			LOG.error("can not found passwd file {}", passwdpath);
			return EMPTY_USERS;
		}
		
		BufferedReader reader = null;
		try {
			PosixUsers users = new PosixUsers();
			reader = new BufferedReader( new FileReader(fpasswd) );
			
			String line = null;
			do {
				line = reader.readLine();
				
				if ( null != line ) {
					final String parts[] = line.split(":");
					String name = null;
					long uid = 0;
					long gid = 0;
					String gecos = null;
					String homedir = null;
					String shell = null;
					
					if ( parts != null ) {
						name = safeGetByIdx(parts, 0);
						//	skip passwd field
						uid = Long.parseLong( safeGetByIdx(parts, 2) );
						gid = Long.parseLong( safeGetByIdx(parts, 3) );
						gecos = safeGetByIdx(parts, 4);
						homedir = safeGetByIdx(parts, 5);
						shell = safeGetByIdx(parts, 6);
						
						if ( null != name ) {
							users._infos.put(name, new UserInfo(name, uid, gid, gecos, homedir, shell));
						}
					}
				}
			} while ( null != line ) ;
			
			return	users;
		} 
		catch (Exception e) {
			LOG.error("failed to getUsers from passwd file [" + passwdpath + "]", e);
			return	EMPTY_USERS;
		}
		finally {
			if ( null != reader ) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * @param parts
	 */
	private static String safeGetByIdx(final String[] parts, final int idx) {
		if ( parts.length > idx ) {
			return parts[idx];
		}
		else {
			return	null;
		}
	}
	
	public UserInfo findUser(final String name ) {
		return	_infos.get(name);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 100;
		return "PosixUsers [_infos="
				+ (_infos != null ? toString(_infos.entrySet(), maxLen) : null)
				+ "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}


	private	Map<String, UserInfo>	_infos = new HashMap<String, UserInfo>();
	
	private	static final PosixUsers EMPTY_USERS = new PosixUsers();
}
