package org.dclayer.net;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.buf.ByteBuf;

/**
 * abstract class that Message classes override for each revision,
 * this covers all data after the revision field
 */
public abstract class RevisionMessage extends PacketComponent {
	/**
	 * constructor called when this {@link RevisionMessage} is reconstructed from data in a {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} this {@link RevisionMessage} is read from
	 * @throws ParseException thrown if the {@link ByteBuf} can not be parsed
	 * @throws BufException thrown if an operation on the {@link ByteBuf} fails
	 */
	public RevisionMessage(ByteBuf byteBuf) throws ParseException, BufException {
		super(byteBuf);
	}

	/**
	 * constructor called when this {@link RevisionMessage} is newly created (and not reconstructed from data)
	 */
	public RevisionMessage() {
	}
	
	/**
	 * returns the revision of this message
	 * @return the revision of this message
	 */
	public abstract byte getRevision();
	/**
	 * returns the message type id of this message
	 * @return the message type id of this message
	 */
	public abstract int getMessageTypeId();
}
