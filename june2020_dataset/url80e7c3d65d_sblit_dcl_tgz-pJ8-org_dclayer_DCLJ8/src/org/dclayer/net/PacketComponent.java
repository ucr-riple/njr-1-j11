package org.dclayer.net;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.buf.ByteBuf;

/**
 * abstract class that parts of packets (e.g. {@link Rev0Message} and {@link ServiceAddressComponent}) extend.
 */
public abstract class PacketComponent implements PacketComponentI {
	
	public static String represent(PacketComponentI packetComponentI, boolean tree, int level) {
		PacketComponentI[] children = packetComponentI.getChildren();
		StringBuilder b = new StringBuilder();
		String indent = null;
		if(tree) {
			StringBuilder ib = new StringBuilder();
			for(int i = 0; i < level; i++) ib.append("    ");
			indent = ib.toString();
			b.append(indent);
		}
		b.append(packetComponentI.toString());
		b.append(" (");
		b.append(packetComponentI.length());
		b.append(')');
		if(children != null && children.length > 0) {
			int ii = 0;
			for(int i = 0; i < children.length; i++) {
				PacketComponentI child = children[i];
				if(child == null) continue;
				if(ii++ > 0) b.append(", ");
				else b.append(" {");
				if(tree) b.append("\n");
				b.append(child.represent(tree, level+1));
			}
			if(ii > 0) {
				if(tree) b.append("\n").append(indent);
				b.append("}");
			}
		}
		return b.toString();
	}
	
	public static String represent(PacketComponentI packetComponentI, boolean tree) {
		return represent(packetComponentI, tree, 0);
	}
	
	//
	
	/**
	 * constructor called when this {@link PacketComponent} is reconstructed from data in a {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} this {@link PacketComponent} is read from
	 * @throws ParseException thrown if the {@link ByteBuf} can not be parsed
	 * @throws BufException thrown if an operation on the {@link ByteBuf} fails
	 */
	public PacketComponent(ByteBuf byteBuf) throws ParseException, BufException {
		this.read(byteBuf);
	}
	
	/**
	 * constructor called when this {@link PacketComponent} is newly created (and not reconstructed from data)
	 */
	public PacketComponent() {
		
	}
	
	/**
	 * returns a String representing this {@link PacketComponent} and its children
	 * @param tree true if the representation should be multi-lined, false otherwise
	 * @param level the current level of indentation
	 * @return a String representing this {@link PacketComponent} and its children
	 */
	@Override
	public final String represent(boolean tree, int level) {
		return PacketComponent.represent(this, tree, level);
	}
	
	/**
	 * returns a String representing this {@link PacketComponent} and its children
	 * @param tree true if the representation should be multi-lined, false otherwise
	 * @return a String representing this {@link PacketComponent} and its children
	 */
	@Override
	public final String represent(boolean tree) {
		return PacketComponent.represent(this, tree);
	}
	
	/**
	 * returns a single-line String representing this {@link PacketComponent} and its children
	 * @return a single-line String representing this {@link PacketComponent} and its children
	 */
	@Override
	public final String represent() {
		return represent(false);
	}
	
	/**
	 * reconstructs the {@link PacketComponent} from data in the given {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} containing the data that should be used to reconstruct this {@link PacketComponent}
	 * @throws ParseException thrown if the {@link ByteBuf} can not be parsed
	 * @throws BufException thrown if an operation on the {@link ByteBuf} fails
	 */
	@Override
	public abstract void read(ByteBuf byteBuf) throws ParseException, BufException;
	/**
	 * writes this {@link PacketComponent} to a {@link ByteBuf}
	 * @param byteBuf the {@link ByteBuf} to write this {@link PacketComponent} to
	 * @throws BufException thrown if an operation on the {@link ByteBuf} fails
	 */
	@Override
	public abstract void write(ByteBuf byteBuf) throws BufException;
	/**
	 * returns the amount of bytes this {@link PacketComponent} will occupy if written to a {@link ByteBuf}
	 * @return the amount of bytes this {@link PacketComponent} will occupy if written to a {@link ByteBuf}
	 */
	@Override
	public abstract int length();
	
	/**
	 * returns a {@link PacketComponent} array containing the children of this {@link PacketComponent}
	 * @return a {@link PacketComponent} array containing the children of this {@link PacketComponent}
	 */
	@Override
	public abstract PacketComponentI[] getChildren();

	@Override
	public abstract String toString();
	
}
