package org.dclayer.net.component;

import java.util.Collection;
import java.util.Iterator;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public abstract class ArrayPacketComponent<T extends PacketComponentI> extends PacketComponent implements Iterable<T> {
	
	private class Element {
		
		T packetComponent;
		Element next;
		
		public Element() {
			this(newElementPacketComponent());
		}
		
		public Element(T packetComponent) {
			this.packetComponent = packetComponent;
		}
		
	}
	
	private class ChainIterator implements Iterator<T> {
		
		private Element current;
		private int position;
		
		@Override
		public boolean hasNext() {
			return position < ArrayPacketComponent.this.chainActiveLength;
		}
		
		@Override
		public T next() {
			Element element = current;
			current = current.next;
			position++;
			return element.packetComponent;
		}

		@Override
		public void remove() {}
		
		public void reset() {
			current = chain;
			position = 0;
		}
		
	}
	
	//
	
	private Element chain;
	private Element chainEnd;
	private ChainIterator chainIterator = new ChainIterator();
	
	private int chainTotalLength = 0;
	private int chainActiveLength = 0;
	
	private FlexNum elementsFlexNum = new FlexNum(0, Integer.MAX_VALUE);
	private Collection<T> elementIterable = null;
	
	protected abstract T newElementPacketComponent();

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		elementsFlexNum.read(byteBuf);
		
		final int num = (int) elementsFlexNum.getNum();
		
		setElements(num);
		
		for(PacketComponentI packetComponent : this) {
			packetComponent.read(byteBuf);
		}
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		elementsFlexNum.write(byteBuf);
		
		for(PacketComponentI packetComponent : this) {
			packetComponent.write(byteBuf);
		}
		
	}

	@Override
	public int length() {
		
		int elementsLength = 0;
		for(PacketComponentI packetComponent : this) {
			elementsLength += packetComponent.length();
		}
		
		return elementsFlexNum.length() + elementsLength;
		
	}

	@Override
	public PacketComponentI[] getChildren() {
		if(elementIterable != null) {
			return elementIterable.toArray(new PacketComponentI[elementIterable.size()]);
		} else {
			PacketComponentI[] children = new PacketComponentI[chainActiveLength];
			Element element = chain;
			for(int i = 0; element != null && i < children.length; i++) {
				children[i] = element.packetComponent;
				element = element.next;
			}
			return children;
		}
	}

	@Override
	public String toString() {
		return String.format("ArrayPacketComponent(numElements=%d)", elementsFlexNum.getNum());
	}

	@Override
	public Iterator<T> iterator() {
		chainIterator.reset();
		return elementIterable == null ? chainIterator : elementIterable.iterator();
	}
	
	public int getNumElements() {
		return elementIterable == null ? chainActiveLength : elementIterable.size();
	}
	
	public void setElements(Collection<T> elements) {
		this.elementsFlexNum.setNum(elements.size());
		this.elementIterable = elements;
	}
	
	public void setElements(int numElements) {
		
		Element element = chainEnd;
		
		while(chainTotalLength < numElements) {
			
			chainEnd = new Element();
			
			if(element == null) {
				chain = chainEnd;
			} else {
				element.next = chainEnd;
			}
			
			element = chainEnd;
			
			chainTotalLength++;
			
		}
		
		chainActiveLength = numElements;
		
		elementsFlexNum.setNum(chainActiveLength);
		elementIterable = null;
		
	}
	
	public void setMaxElements() {
		chainActiveLength = chainTotalLength;
		elementsFlexNum.setNum(chainActiveLength);
		elementIterable = null;
	}
	
	public T addElement() {
		
		Element element = chainEnd;
		
		if(element == null) {
			element = chain = new Element();
		} else {
			element = element.next = new Element();
		}
		
		chainEnd = element;
		
		chainTotalLength++;
		chainActiveLength = chainTotalLength;
		
		return element.packetComponent;
		
	}
	
}
