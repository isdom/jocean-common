package org.jocean.management;

import javax.management.ObjectName;

import org.jocean.util.MBeanUtils;
import org.jocean.util.VersionableAsString;

public interface ProcessControllerMXBean extends VersionableAsString {

	public static final ObjectName PROCESS_CTRL_OBJECTNAME = 
			MBeanUtils.safeGetObjectName("org.jocean:type=processCtrl");
	
	/**
	 * 根据主机操作系统，打开一个shell，可在其中进一步执行命令，一般用于交互式操作
	 * 输入参数出没有cmd之外，和startProcess定义一致
	 * @param env
	 * 	代表命令执行时，预设的环境参数，数组方式传递，按照
	 * 	env[0] = env[1],
	 * 	...
	 * 	env[2i] = env[2i+1] 
	 * 	在执行命令前，预设环境参数
	 * 	如果为null，则没有附加的预设环境参数
	 * @param workdir
	 * 	命令启动的工作路径，若为null，则在 当前JVM的工作路径启动
	 * @param charset
	 * 	对输出、输入的编码字符集，若为空，则强制使用UTF-8
	 * @param timeToLive
	 * 	命令成功启动后，在经过 timeToLive 的时间后，将强制销毁命令
	 * 	若为 -1，表示该命令不会被自动销毁
	 * 	时间单位为 毫秒
	 * @param timeToIdle
	 * 	命令成功启动后，若没有任何相关操作，在经过 timeToIdle 的时间后，将强制销毁命令进程
	 * 	若为 -1，表示该命令不会被自动销毁
	 * 	时间单位为 毫秒
	 * @param maxOutputLines
	 * 	命令成功启动后，在两次fetchOutputs 之间，最大保留的输出信息行数
	 * 	若为 -1，则使用缺省最大输出行数（1000）
	 * 	若该参数超过1000，会强制按照 1000进行约束
	 * @return
	 * 	返回有效字符串，则表示启动命令成功，后续可用该字符串获取输出，增加输入或强制结束命令执行
	 * 	返回为 null，表明命令未能成功启动
	 * @throws Exception
	 */
	public String openShell(
			final String[] 	env, 
			final String 	workdir,
			final String 	charset,
			final long		timeToLive,
			final long		timeToIdle,
			final int		maxOutputLines)
			throws Exception;
	
	/**
	 * 启动外部进程，非阻塞方式执行，方法返回时，外部进程可能仍在继续执行中
	 * @param cmd 
	 * 	代表启动进程的字符串数组，eg: "cmd","/c","ping www.sina.com.cn" ...
	 * @param env
	 * 	代表进程执行时，预设的环境参数，数组方式传递，按照
	 * 	env[0] = env[1],
	 * 	...
	 * 	env[2i] = env[2i+1] 
	 * 	在执行前，预设环境参数
	 * 	如果为null，则没有附加的预设环境参数
	 * @param workdir
	 * 	进程启动的工作路径，若为null，则在 当前JVM的工作路径启动
	 * @param charset
	 * 	对输出、输入的编码字符集，若为空，则强制使用UTF-8
	 * @param timeToLive
	 * 	进程成功启动后，在经过 timeToLive 的时间后，将强制销毁进程
	 * 	若为 -1，表示该进程不会被自动销毁
	 * 	时间单位为 毫秒
	 * @param timeToIdle
	 * 	进程成功启动后，若没有任何相关操作，在经过 timeToIdle 的时间后，将强制销毁命令进程
	 * 	若为 -1，表示该进程不会因为idle而被自动销毁
	 * 	时间单位为 毫秒
	 * @param maxOutputLines
	 * 	进程成功启动后，在两次fetchOutputs 之间，最大保留的输出信息行数
	 * 	若为 -1，则使用缺省最大输出行数（1000）
	 * 	若该参数超过1000，会强制按照 1000进行约束
	 * @return
	 * 	返回有效字符串，则表示进程启动成功，后续可用该字符串获取输出，增加输入或强制结束进程执行
	 * 	返回为 null，表明命令未能成功启动
	 * @throws Exception
	 */
	public String startProcess (
			final String[] 	cmd, 
			final String[] 	env, 
			final String 	workdir,
			final String 	charset,
			final long		timeToLive,
			final long		timeToIdle,
			final int		maxOutputLines)
			throws Exception;

	/**
	 * 获取成功启动的进程的输出
	 * @param handle
	 * 由startProcess 成功返回的handle，用于标识正在运行的进程实体
	 * @return
	 * 存在有效地输出文本，以String[]形式返回给调用者
	 * 如进程未结束，而暂时没有文本输出，返回String[0](!=null)
	 * 如进程已经结束，且没有任何剩余的输出信息，返回null
	 * @throws Exception
	 */
	public String[] fetchProcessOutputs(final String handle);

	/**
	 * 对成功启动的进程，追加输入文本，可多行
	 * @param handle
	 * 由startProcess 成功返回的handle，用于标识正在运行的进程实体
	 * @param inputs
	 * 追加的输入多行文本
	 * @throws Exception
	 */
	public void	appendProcessInputs(final String handle, final String[] inputs);

	/**
	 * 检测指定的进程是否执行结束
	 * @param handle
	 * 由startProcess成功返回的handle，用于标识运行的进程实体
	 * @return
	 * @throws Exception
	 */
	public boolean isProcessEnded(final String handle);
	
	/**
	 * 获取已经结束的进程退出值
	 * @param handle
	 * 由startProcess成功返回的handle，用于标识运行的进程实体
	 * @return
	 * @throws Exception
	 */
	public int	getProcessExitValue(final String handle)  throws IllegalThreadStateException;
	
	/**
	 * 强制结束成功启动的进程
	 * @param handle
	 * 由startProcess成功返回的handle，用于标识运行的进程实体
	 * @throws Exception
	 */
	public void	destroyProcess(final String handle);
	
	/**
	 * 检测特定进程 是否由ProcessController主动销毁（可能为超时时间到或是通过destroyProcess接口销毁）
	 * @param handle
	 * @return
	 * 如果返回为true，表明特定进程是被外部主动销毁，若返回为false，则是其自身正常结束，若进程尚未结束，则抛出RuntimeException
	 */
	public boolean isProcessEndedByDestroy(final String handle);
	
	/**
	 * 获取当前存在的 由ProcessController 启动的所有进程个数，包括正在执行和已经执行结束，但返回值、输出还存在的进程实体
	 * @return
	 * 符合要求的进程个数
	 * @throws Exception
	 */
	public int getTotalProcessCount();
	
	/**
	 * 获取当前所有还在执行的进程个数
	 * @return
	 * 符合要求的进程个数
	 * @throws Exception
	 */
	public int getRunningProcessCount();
	
	/**
	 * 获取当前所有已经执行结束，但返回值、输出还存在的进程实体
	 * @return
	 * 符合要求的进程个数
	 * @throws Exception
	 */
	public int getEndedProcessCount();
	
	/**
	 * 获取历史上已经结束，并且所有信息都不存在的进程总数
	 * @return
	 * 符合要求的进程个数
	 * @throws Exception
	 */
	public int getVanishedProcessCount();
	
	/**
	 * 获取当前还存在于 ProcessController 中的所有进程的状态，状态信息均以字符串表示
	 * @return
	 * 为偶数字符串数组，按照
	 * ret[0]'s status is ret[1],
	 * ...
	 * ret[2i]'s status is ret[2i+1],
	 * @throws Exception
	 */
	public String[] getAllProcessStatus();
	
}