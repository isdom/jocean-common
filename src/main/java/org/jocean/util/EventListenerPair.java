/**
 * 
 */
package org.jocean.util;

import java.util.Comparator;
import java.util.EventListener;

/**
 * @author isdom
 *
 */
public class EventListenerPair<T extends EventListener> {
	public final T 		listener;
	public final Object handback;
	
	public EventListenerPair(T l, Object o) {
		this.listener = l;
		this.handback = o;
	}

	public static <T extends EventListener> Comparator<EventListenerPair<T>> newDefaultComparator() {

		return new Comparator<EventListenerPair<T>>() {
			@Override
			public int compare(EventListenerPair<T> o1, 
					EventListenerPair<T> o2) {
				return o1.listener.equals(o2.listener) ? 0 : -1;
			}
		};
	}
}
