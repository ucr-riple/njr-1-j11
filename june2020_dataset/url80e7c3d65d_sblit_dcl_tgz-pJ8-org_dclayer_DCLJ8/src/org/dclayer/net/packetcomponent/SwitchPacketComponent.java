package org.dclayer.net.packetcomponent;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.exception.net.parse.UnsupportedMessageTypeException;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.ByteBuf;

public abstract class SwitchPacketComponent<T extends PacketComponentI> extends AutoPacketComponent<T, SwitchPacketComponent.ChildInfo<T>> {
	
	private static interface TypeComponent {
		
		public int readType(ByteBuf byteBuf) throws BufException, ParseException;
		public void writeType(ByteBuf byteBuf, int type) throws BufException;
		public int typeLength();
		
	}
	
	protected static class ChildInfo<U extends PacketComponentI> extends AutoPacketComponentChildInfo<U> {
		Method onReceiveMethod;
	}
	
	//
	
	private ChildInfo<T> activeChild;
	private int type;
	
	private final TypeComponent typeComponent;
	
	private Object onReceiveObject;
	
	public SwitchPacketComponent() {
		super(PacketComponentI.class, ChildInfo.class);
		this.typeComponent = makeTypeComponent(children.length);
	}
	
	public SwitchPacketComponent(Object onReceiveObject) {
		this();
		collectOnReceiveMethods(onReceiveObject);
	}
	
	//
	
	/**
	 * scans the given object for methods with {@link OnReceive} annotations and
	 * stores them for later use with {@link #callOnReceive()}
	 * @param onReceiveObject the object to scan
	 * @deprecated use constructor {@link #SwitchPacketComponent(Object)} instead
	 */
	@Deprecated
	public void loadOnReceiveObject(Object onReceiveObject) {
		
		collectOnReceiveMethods(onReceiveObject);
	
	}
	
	private void collectOnReceiveMethods(Object onReceiveObject) {
		
		for(Method method : onReceiveObject.getClass().getMethods()) {
			
			OnReceive onReceiveAnnotation = method.getAnnotation(OnReceive.class);
			if(onReceiveAnnotation != null) {
				
				Class<?>[] parameterTypes = method.getParameterTypes();
				if(parameterTypes.length != 1) {
					throw new InstantiationError(String.format("Invalid on receive callback method '%s': must accept exactly one parameter", method.getName()));
				}
				
				if(onReceiveAnnotation.index() < 0) {
					
					ChildInfo<T> matchingChild = null;
					
					for(ChildInfo<T> child : children) {
						
						if(child.onReceiveMethod == method) {
							
							throw new InstantiationError(String.format("Invalid on receive callback method '%s': parameter type '%s' matches multiple child packet components", method.getName(), parameterTypes[0].getSimpleName()));
							
						} else if(parameterTypes[0].isAssignableFrom(child.getField().getType())) {
							
							if(matchingChild != null) {
								throw new InstantiationError(String.format("Invalid on receive callback method '%s': parameter type '%s' matches multiple child packet components", method.getName(), parameterTypes[0].getSimpleName()));
							}
							
							if(child.onReceiveMethod != null) {
								throw new InstantiationError(String.format("Invalid on receive callback method '%s': overlaps with method '%s'", method.getName(), child.onReceiveMethod.getName()));
							}
							
							child.onReceiveMethod = method;
							matchingChild = child;
							
							// don't break, finish checking for overlapping methods and children
							
						}
						
					}
					
					if(matchingChild == null) {
						throw new InstantiationError(String.format("Invalid on receive callback method '%s': no child packet component found that can be cast to '%s'", method.getName(), parameterTypes[0].getSimpleName()));
					}
					
				} else {
					
					ChildInfo<T> child = children[onReceiveAnnotation.index()];
					
					if(!parameterTypes[0].isAssignableFrom(child.getField().getType())) {
						throw new InstantiationError(String.format("Invalid on receive callback method '%s': must accept exactly one parameter of type or supertype of '%s'", method.getName(), child.getField().getType().getSimpleName()));
					}
					
					child.onReceiveMethod = method;
					
				}
				
			}
			
		}
		
		for(int i = 0; i < children.length; i++) {
			ChildInfo<T> child = children[i];
			if(child.onReceiveMethod == null) {
				throw new InstantiationError(String.format("Invalid on receive object: missing on receive callback method for child type '%s' at index %d", child.getField().getType().getSimpleName(), i));
			}
		}

		this.onReceiveObject = onReceiveObject;
		
	}
	
	private TypeComponent makeTypeComponent(int numChildren) {
		
		if(numChildren < 0x100) {
			
			return new TypeComponent() {
				@Override
				public void writeType(ByteBuf byteBuf, int type) throws BufException {
					byteBuf.write((byte) type);
				}
				
				@Override
				public int typeLength() {
					return 1;
				}
				
				@Override
				public int readType(ByteBuf byteBuf) throws BufException, ParseException {
					return byteBuf.read() & 0xFF;
				}
			};
			
		} else if(numChildren < 0x10000) {
			
			return new TypeComponent() {
				@Override
				public void writeType(ByteBuf byteBuf, int type) throws BufException {
					byteBuf.write16(type);
				}
				
				@Override
				public int typeLength() {
					return 2;
				}
				
				@Override
				public int readType(ByteBuf byteBuf) throws BufException, ParseException {
					return byteBuf.read16();
				}
			};
			
		} else {
			
			return new TypeComponent() {
				@Override
				public void writeType(ByteBuf byteBuf, int type) throws BufException {
					byteBuf.write32(type);
				}
				
				@Override
				public int typeLength() {
					return 4;
				}
				
				@Override
				public int readType(ByteBuf byteBuf) throws BufException, ParseException {
					return (int) byteBuf.read32();
				}
			};
			
		}
		
	}
	
	//

	@Override
	public final void read(ByteBuf byteBuf) throws ParseException, BufException {
		
		int type = typeComponent.readType(byteBuf);
		if(type > children.length) {
			throw new UnsupportedMessageTypeException(type);
		}
		
		this.type = type;
		
		activeChild = children[type];
		activeChild.getPacketComponent().read(byteBuf);
		
	}

	@Override
	public final void write(ByteBuf byteBuf) throws BufException {
		
		typeComponent.writeType(byteBuf, type);
		activeChild.getPacketComponent().write(byteBuf);
		
	}

	@Override
	public final int length() {
		
		return typeComponent.typeLength() + activeChild.getPacketComponent().length();
		
	}

	@Override
	public final PacketComponentI[] getChildren() {
		return new PacketComponentI[] { activeChild.getPacketComponent() };
	}

	@Override
	public String toString() {
		return String.format("%s(type=%d)", this.getClass().getSimpleName(), type);
	}
	
	public T get() {
		return activeChild.getPacketComponent();
	}
	
	public T set(int index) {
		type = index;
		return (activeChild = children[index]).getPacketComponent();
	}
	
	public <W extends ChildPacketComponent> W set(W child) {
		if(child.getIndexInParent() < 0) {
			accessChildrenPacketComponents();
		}
		set(child.getIndexInParent());
		return child;
	}
	
	public void callOnReceive() {
		try {
			activeChild.onReceiveMethod.invoke(onReceiveObject, activeChild.getPacketComponent());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
}
