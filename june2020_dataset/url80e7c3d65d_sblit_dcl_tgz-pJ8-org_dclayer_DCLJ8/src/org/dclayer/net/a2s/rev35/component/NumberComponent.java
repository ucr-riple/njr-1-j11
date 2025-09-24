package org.dclayer.net.a2s.rev35.component;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.IllegalCharacterException;
import org.dclayer.exception.net.parse.NumberOutOfRangeException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.NumComponentI;

public class NumberComponent extends PacketComponent implements NumComponentI {
	
	public NumberComponent() {
	}
	
	public NumberComponent(long minNumber, long maxNumber) {
		this.hasMinMax = true;
		this.minNumber = minNumber;
		this.maxNumber = maxNumber;
	}
	
	private long minNumber;
	private long maxNumber;
	private boolean hasMinMax = false;
	
	private long number;
	private int length;
	
	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		number = 0;
		length = 0;
		byte b;
		while((b = byteBuf.read()) != ' ' && b != '\n') {
			if(b < '0' || b > '9') {
				throw new IllegalCharacterException((char)b);
			}
			number *= 10;
			number += (int)(b - '0');
			length++;
		}
		
		if(hasMinMax && (number > maxNumber || number < minNumber)) {
			throw new NumberOutOfRangeException(number, minNumber, maxNumber);
		}
		
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		
		byteBuf.writeNonTerminatedString(Long.toString(number));
		
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public String toString() {
		return String.format("NumberComponent(number=%d)", number);
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
	
	@Override
	public long getNum() {
		return number;
	}
	
	@Override
	public void setNum(long num) {
		this.number = num;
		this.length = (int) Math.log10(num);
	}
	
}
