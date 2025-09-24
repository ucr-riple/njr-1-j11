package org.dclayer.application.applicationchannel;

import org.dclayer.net.applicationchannel.ApplicationChannelTarget;

public class DCLApplicationChannel extends AbsApplicationChannel {

	public DCLApplicationChannel(ApplicationChannelTarget applicationChannelTarget, ApplicationChannelActionListener applicationChannelActionListener, boolean locallyInitiated) {
		super(applicationChannelTarget, applicationChannelActionListener, locallyInitiated);
	}

	@Override
	protected String getName() {
		return "DCLApplicationChannel";
	}

}
