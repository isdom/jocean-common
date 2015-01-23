/**
 * 
 */
package org.jocean.util;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author isdom
 *
 */
public class StringUtils {
	
	public static final Charset CHARSET_UTF_8;
	
	static {
		CHARSET_UTF_8 = Charset.forName("UTF-8");
	}
	
	/**
	* 内部方法resolveFirstPlaceholder
	* 功能为：对传入的带有占位符的text，进行从左侧开始第一个占位符的替换，
	*        并且仅替换一个占位符
	* @param text 带有占位符的字符串
	* @param properties 预定义的占位符到实际字符串值的字典
	* @param prefix 占位符的前缀，例如"${"
	* @param suffix 占位符的后缀，例如"}"
	* @return 如果进行了一次替换，返回值为替换后的文本(String)
	*         如替换未发生，则返回null
	*/	
	private static String resolveFirstPlaceholder(
			final String text, 
			final Map<Object, Object> properties, 
			final String prefix, 
			final String suffix) {

		int start = text.indexOf(prefix);
		
		while ( -1 != start ) {
			int end = text.indexOf(suffix, start + prefix.length());
			if ( -1 == end ) {
				break;
			}
			
			String placeholder = text.substring(start + prefix.length(), end);
			String value = properties.get(placeholder).toString();
			if ( null != value ) {
				//	do replaced ment
				String replacedText = text.substring(0, start) + value + text.substring(end + suffix.length());
				return	replacedText;
			}
			start = text.indexOf(suffix, end + suffix.length());
		}
		
		return	null;
	}

	/**
	* 公开方法resolvePlaceholder
	* 功能为：对传入的带有占位符的text，进行全替换，调用内部resolveFirstPlaceholder实现
	* @param text 带有占位符的字符串
	* @param properties 预定义的占位符到实际字符串值的字典
	* @param prefix 占位符的前缀，例如"${"
	* @param suffix 占位符的后缀，例如"}"
	* @return 返回值为替换后的文本(String)，若未发生替换，则返回原字符串
	*/	
	public static String resolvePlaceholder(String text, 
			final Map<Object, Object> properties, 
			final String prefix, 
			final String suffix) {
		
		String replacedText = text;
		
		do {
			text = replacedText;
			replacedText = resolveFirstPlaceholder(text, properties, prefix, suffix);
		} while ( null != replacedText);
		
		return	text;
	}
}
