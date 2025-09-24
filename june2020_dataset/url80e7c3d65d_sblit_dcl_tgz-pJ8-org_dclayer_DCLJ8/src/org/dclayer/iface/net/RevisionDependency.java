package org.dclayer.iface.net;

import org.dclayer.net.PacketComponent;

/**
 * specifies the lowest revision an object can be used in (i.e. the revision a {@link PacketComponent} was introduced with)
 */
public interface RevisionDependency {
	public byte getMinRevision();
}
