package org.dclayer.net.packetcomponent;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.LinkedList;

import org.dclayer.net.PacketComponent;
import org.dclayer.net.PacketComponentI;

public abstract class AutoPacketComponent<T extends PacketComponentI, U extends AutoPacketComponentChildInfo<T>> extends ChildPacketComponent {
	
	private static class ChildField {
		
		Child child;
		Field field;
		
		public ChildField(Child child, Field field) {
			this.child = child;
			this.field = field;
		}
		
	}
	
	//
	
	protected final U[] children;
	
	protected AutoPacketComponent(Class<?> packetComponentType, Class<?> childInfoType) {
		
		this.children = collectChildren(packetComponentType, childInfoType);
		
	}
	
	//
	
	private U[] collectChildren(Class<?> packetComponentType, Class<?> childInfoType) {
		
		LinkedList<ChildField> fields = new LinkedList<>();
		
		for(Field field : this.getClass().getDeclaredFields()) {
			Child childAnnotation = field.getAnnotation(Child.class);
			if(childAnnotation != null) {
				fields.add(new ChildField(childAnnotation, field));
			}
		}
		
		U[] children = (U[]) Array.newInstance(childInfoType, fields.size());
		
		for(ChildField childField : fields) {
			
			if(!packetComponentType.isAssignableFrom(childField.field.getType())) {
				throw new InstantiationError(String.format("AutoPacketComponent %s: Field '%s': Field type %s is not a sub-class of %s", this.getClass().getName(), childField.field.getName(), childField.field.getType().getName(), packetComponentType.getName()));
			}
			
			if(children[childField.child.index()] != null) {
				throw new InstantiationError(String.format("AutoPacketComponent %s: Field '%s': Duplicate index %d", this.getClass().getName(), childField.field.getName(), childField.child.index()));
			}
			
			if(childField.child.create()) {
				
				T packetComponent;
				
				try {
					packetComponent = (T) childField.field.getType().newInstance();
				} catch (InstantiationException e) {
					throw new InstantiationError(String.format("AutoPacketComponent %s: Field '%s': Could not instantiate type %s", this.getClass().getName(), childField.field.getName(), childField.field.getType().getName()));
				} catch (IllegalAccessException e) {
					throw new InstantiationError(String.format("AutoPacketComponent %s: Field '%s': Could not access type %s", this.getClass().getName(), childField.field.getName(), childField.field.getType().getName()));
				}
				
				try {
					
					childField.field.setAccessible(true);
					childField.field.set(this, packetComponent);
					childField.field.setAccessible(false);
					
				} catch (IllegalArgumentException e) {
					throw new InstantiationError(String.format("AutoPacketComponent %s: Field '%s': Could not assign instance of type %s", this.getClass().getName(), childField.field.getName(), childField.field.getType().getName()));
				} catch (IllegalAccessException e) {
					throw new InstantiationError(String.format("AutoPacketComponent %s: Field '%s': Could not access", this.getClass().getName(), childField.field.getName()));
				}
				
			}
			
			U childInfo;
			try {
				childInfo = (U) childInfoType.newInstance();
			} catch (InstantiationException e) {
				throw new InstantiationError(String.format("AutoPacketComponent %s: Could not instantiate child info type %s (InstantiationException)", this.getClass().getName(), childInfoType.getName()));
			} catch (IllegalAccessException e) {
				throw new InstantiationError(String.format("AutoPacketComponent %s: Could not instantiate child info type %s (IllegalAccessException)", this.getClass().getName(), childInfoType.getName()));
			}
			
			childInfo.setField(childField.field, this);
			childInfo.setIndex(childField.child.index());
			
			children[childField.child.index()] = childInfo;
			
		}
		
		return children;
		
	}
	
	protected void accessChildrenPacketComponents() {
		for(U childInfo : children) {
			childInfo.getPacketComponent();
		}
	}
	
}
