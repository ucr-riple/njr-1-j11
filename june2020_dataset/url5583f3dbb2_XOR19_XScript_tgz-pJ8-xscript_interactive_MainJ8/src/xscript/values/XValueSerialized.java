package xscript.values;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

final class XValueSerialized implements Externalizable{

	private static final long serialVersionUID = -7098878281086059572L;
	
	private XValue value;
	
	XValueSerialized(){
		
	}
	
	XValueSerialized(XValue value) {
		this.value = value;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException {
		int type = in.readByte();
		switch(type){
		case 0:
			value = XValueNull.NULL;
			break;
		case 1:
			value = XValueBool.TRUE;
			break;
		case 2:
			value = XValueBool.FALSE;
			break;
		case 3:
			value = XValueInt.valueOf(in.readByte());
			break;
		case 4:
			value = new XValueInt(in.readShort());
			break;
		case 5:
			value = new XValueInt(in.readInt());
			break;
		case 6:
			value = new XValueInt(in.readLong());
			break;
		case 7:
			value = new XValueFloat(in.readDouble());
			break;
		case 8:
			value = new XValueObj(in.readInt());
			break;
		case 9:
			value = new XValueAccess(new XValueObj(in.readInt()));
			break;
		case 10:
			value = new XValueObjSuper(XValue.read(in), in.readInt());
			break;
		default:
			throw new IOException("Unknown value type");
		}
		
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		if(value instanceof XValueNull){
			out.writeByte(0);
		}else if(value instanceof XValueBool){
			if(((XValueBool)value).getBool()){
				out.writeByte(1);
			}else{
				out.writeByte(2);
			}
		}else if(value instanceof XValueInt){
			long l = ((XValueInt)value).getInt();
			if(l<=Byte.MAX_VALUE && l>=Byte.MIN_VALUE){
				out.writeByte(3);
				out.writeByte((byte)l);
			}else if(l<=Short.MAX_VALUE && l>=Short.MIN_VALUE){
				out.writeByte(4);
				out.writeShort((short)l);
			}else if(l<=Integer.MAX_VALUE && l>=Integer.MIN_VALUE){
				out.writeByte(5);
				out.writeInt((int)l);
			}else{
				out.writeByte(6);
				out.writeLong(l);
			}
		}else if(value instanceof XValueFloat){
			out.writeByte(7);
			out.writeDouble(((XValueFloat)value).getFloat());
		}else if(value instanceof XValueObj){
			out.writeByte(8);
			out.writeInt(((XValueObj)value).getPointer());
		}else if(value instanceof XValueAccess){
			out.writeByte(9);
			out.writeInt(((XValueAccess)value).getHolding().getPointer());
		}else if(value instanceof XValueObjSuper){
			out.writeByte(10);
			XValue.write(out, ((XValueObjSuper)value).getValue());
			out.writeInt(((XValueObjSuper)value).getCastToType());
		}else if(value instanceof XValueClosure){
			value = ((XValueClosure)value).value;
			writeExternal(out);
		}else{
			throw new IOException("Unknown value type");
		}
	}
	
	private Object readResolve() throws ObjectStreamException {
		return value;
	}

	XValue getValue(){
		return value;
	}
	
}
