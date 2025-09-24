package edu.concordia.dpis;

import java.util.List;

// A command, which can execute an operation with the given parameters
public interface Command {
	Object execute(List<Object> params);
}
