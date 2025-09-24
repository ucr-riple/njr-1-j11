package edu.concordia.dpis;

import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.fifo.RequestResolver;

// A Handler must be able to process a request and return a reply 

public interface RequestHandler extends RequestResolver {

	// do the operation if any for this request
	Object doOperation(Message request) throws UnsupportedOperationException;

}
