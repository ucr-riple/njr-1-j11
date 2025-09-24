package edu.concordia.dpis.fifo;

import java.util.List;

import edu.concordia.dpis.commons.Message;

// A resolver will be able to get the operation name and arguments associated in the request
public interface RequestResolver {

	// get the operation name from the request message
	String getOperationName(Message request);

	// get the arguments from the request message
	List<Object> getArguments(Message request);
}
