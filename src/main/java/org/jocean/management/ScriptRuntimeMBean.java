/**
 * 
 */
package org.jocean.management;

import javax.management.ObjectName;

import org.jocean.util.MBeanUtils;
import org.jocean.util.VersionableAsString;


/**
 * @author Marvin.Ma
 *
 */
public interface ScriptRuntimeMBean extends VersionableAsString {
	
	public static final ObjectName SCRIPT_RUNTIME_OBJECTNAME = 
			MBeanUtils.safeGetObjectName("org.jocean:type=scriptRuntime");
	
	public BlobRepository getBlobRepository();
	
	public long getInterpreterReleasedTimeout();

	public void setInterpreterReleasedTimeout(long releasedTimeout);

	public String openInterpreter();
	//public int acquireToken();

	public void closeInterpreter(String handle);
	//	主动释放 token
	//public void releaseToken(int token);
	
	public Object executeStringScript(final String handle, final String scriptContent) 
			throws Exception;
	
	public Object executeBlobScript(final String handle, final String blobName) 
			throws Exception;
	
	public void setVariable(final String handle, final String varName, final Object value) 
			throws Exception;

	public Object getVariable(final String handle, final String varName) 
			throws Exception;
	
	//public void resetScriptInterpreter(int token);

	public void keepAlive(final String handle);
}
