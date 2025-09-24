package edu.concordia.dpis.commons;

import java.io.Serializable;
import java.util.List;

// A Message which is transferred over from one source to other, this message can carry the operation name or special instructions along with the arguments if any
public interface Message extends IReliable, Serializable {

	// any special message
	String getActualMessage();

	// parameters
	List<Object> getArguments();

	// destination address
	Address getToAddress();

	// this messages id
	int getSequenceNumber();

	// is this message need to be multicasted on the receiving side
	boolean isMulticast();

	// is this message a reply to the previous request messge
	boolean isReply();

	// is this message need to be replied with a new message
	boolean isReplyToThisMessage();
}
