/**
 * 
 */
package org.jocean.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author isdom
 *
 */
public class DefaultPropertyEditorSource 
	implements PropertyEditorSource {

    @SuppressWarnings("unused")
	private static final Logger LOG = 
        	LoggerFactory.getLogger(DefaultPropertyEditorSource.class);
    
	/* (non-Javadoc)
	 * @see jocean.zkoss.util.PropertyEditorSource#getPropertyEditor(java.lang.Class)
	 */
	@Override
	public PropertyEditor getPropertyEditor(final Class<?> type) {
		return PropertyEditorManager.findEditor(ClassUtils.type2Class(type.getName()));
	}

	@Override
	public PropertyEditor getPropertyEditor(String typeAsText) {
		return PropertyEditorManager.findEditor(ClassUtils.type2Class(typeAsText));
	}

	@Override
	public String getDefaultValueAsText(Class<?> type) {
		final Class<?> realType = ClassUtils.type2Class(type.getName());
		if ( realType.equals( Integer.TYPE ) 
			|| realType.equals( Byte.TYPE ) 
			|| realType.equals( Short.TYPE ) 
			|| realType.equals( Long.TYPE ) 
			|| realType.equals( Float.TYPE ) 
			|| realType.equals( Double.TYPE ) 
			) {
			return	"0";
		}
		else if ( realType.equals( Boolean.TYPE )  ) {
			return	"false";
		}
		return null;
	}
}
