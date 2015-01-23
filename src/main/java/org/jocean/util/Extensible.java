package org.jocean.util;

public interface Extensible {
	
	public <EXT> EXT getExtend(final Class<EXT> type);

}
