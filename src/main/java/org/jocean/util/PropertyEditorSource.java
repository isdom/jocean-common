/**
 * 
 */
package org.jocean.util;

import java.beans.PropertyEditor;

/**
 * @author isdom
 *
 */
public interface PropertyEditorSource {
	public PropertyEditor getPropertyEditor(final Class<?> type);
	public PropertyEditor getPropertyEditor(final String typeAsText);
	public String		getDefaultValueAsText(final Class<?> type);
}
