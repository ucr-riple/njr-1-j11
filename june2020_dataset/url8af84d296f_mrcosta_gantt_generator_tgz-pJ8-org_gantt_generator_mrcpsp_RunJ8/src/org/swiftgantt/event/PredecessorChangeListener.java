package org.swiftgantt.event;

import java.util.EventListener;

public interface PredecessorChangeListener extends EventListener {

	void predecessorChanged(PredecessorChangeEvent e);
}
