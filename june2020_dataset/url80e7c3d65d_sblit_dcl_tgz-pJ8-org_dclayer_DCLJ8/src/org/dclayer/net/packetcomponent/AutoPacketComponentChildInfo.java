package org.dclayer.net.packetcomponent;

import java.lang.reflect.Field;

import org.dclayer.net.PacketComponentI;

public class AutoPacketComponentChildInfo<T extends PacketComponentI> {
	
	private Field field;
	private Object object;
	
	private int index;
	
	private T packetComponent = null;
	
	public void setField(Field field, Object object) {
		this.field = field;
		this.object = object;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public T getPacketComponent() {
		
		if(packetComponent == null) {
			
			try {
				packetComponent = (T) field.get(object);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(String.format("AutoPacketComponent %s: Field '%s': Could not access", field.getName(), object), e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(String.format("AutoPacketComponent %s: Field '%s': Could not access", field.getName(), object), e);
			}
			
			if(packetComponent instanceof ChildPacketComponent) {
				((ChildPacketComponent) packetComponent).setIndexInParent(index);
			}
			
		}
		
		return packetComponent;
		
	}
	
}