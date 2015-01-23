/**
 * 
 */
package org.jocean.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author isdom
 *
 */
public class CompositeSupport<T> {
	
    private static final Logger LOG = 
        	LoggerFactory.getLogger(CompositeSupport.class);
    
	/**
	 * 创建 CompositeSupport实例，并使用 ArrayList 作为内部集合数据结构，适合在单线程模式下使用
	 * @return
	 */
	public static <T> CompositeSupport<T> newCompositeSupport() {
		return	newCompositeSupport(ArrayList.class);
	}
	
	/**
	 *  创建 CompositeSupport实例，并允许调用方配置其内部使用的集合类型，目的为适应使用环境下的线程模型
	 * @param cls 为内部使用的集合数据结构类型，若为 ArrayList，则适合在单线程模式下使用
	 * 				若应用环境为多线程，则应使用 ConcurrentLinkedQueue 等允许多线程并发操作的集合类型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> CompositeSupport<T> newCompositeSupport(
			Class<? extends Collection> cls) {
		return	new CompositeSupport<T>((Class<? extends Collection<T>>)cls);
	}
	
	private CompositeSupport(Class<? extends Collection<T>> cls) {
		try {
			this.components = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("CompositeSupport.ctor", e);
		} 
	}
	
	public void addComponent(T component) {
		components.add(component);
	}
	
	public void removeComponent(Comparator<T>  comparator, T toRemove) {
		Iterator<T> itr = components.iterator();
		
		while (itr.hasNext()) {
			try {
				if ( 0 == comparator.compare( itr.next(), toRemove ) ) {
					itr.remove();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void foreachComponent(final Visitor<T>  visitor) {
		Iterator<T> itr = components.iterator();
		
		while (itr.hasNext()) {
			try {
				visitor.visit( itr.next() );
			}
			catch (final Exception e) {
				LOG.error("exception when call CompositeSupport.foreachComponent, detail:{}",
						ExceptionUtils.exception2detail(e));
			}
		}
	}
	
	private	final Collection<T> components;
	
}
