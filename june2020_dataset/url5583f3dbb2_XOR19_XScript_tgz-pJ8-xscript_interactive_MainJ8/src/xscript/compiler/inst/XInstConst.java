package xscript.compiler.inst;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;

public class XInstConst extends XInst {

	private Object _const;
	
	public XInstConst(int line, Object _const) {
		super(line, getOP(_const));
		this._const = _const;
	}

	private static XOpcode getOP(Object _const){
		if(_const==null){
			return XOpcode.LOADN;
		}else if(_const instanceof Boolean){
			if(((Boolean)_const).booleanValue()){
				return XOpcode.LOAD_TRUE;
			}
			return XOpcode.LOAD_FALSE;
		}else if(_const instanceof Byte){
			int num = ((Byte)_const).byteValue();
			switch(num){
			case -1:
				return XOpcode.LOADI_M1;
			case 0:
				return XOpcode.LOADI_0;
			case 1:
				return XOpcode.LOADI_1;
			case 2:
				return XOpcode.LOADI_2;
			}
			return XOpcode.LOADB;
		}else if(_const instanceof Byte||_const instanceof Short||_const instanceof Character){
			int num = 0;
			if(_const instanceof Character){
				num = ((Character)_const).charValue();
			}else{
				num = ((Number)_const).intValue();
			}
			switch(num){
			case -1:
				return XOpcode.LOADI_M1;
			case 0:
				return XOpcode.LOADI_0;
			case 1:
				return XOpcode.LOADI_1;
			case 2:
				return XOpcode.LOADI_2;
			}
			if(num>=Byte.MIN_VALUE && num<=Byte.MAX_VALUE){
				return XOpcode.LOADB;
			}
			return XOpcode.LOADS;
		}else if(_const instanceof Integer){
			int i = (Integer)_const;
			switch(i){
			case -1:
				return XOpcode.LOADI_M1;
			case 0:
				return XOpcode.LOADI_0;
			case 1:
				return XOpcode.LOADI_1;
			case 2:
				return XOpcode.LOADI_2;
			}
			if(i>=Byte.MIN_VALUE && i<=Byte.MAX_VALUE){
				return XOpcode.LOADB;
			}else if(i>=Short.MIN_VALUE && i<=Short.MAX_VALUE){
				return XOpcode.LOADS;
			}
			return XOpcode.LOADI;
		}else if(_const instanceof Long){
			long l = ( Long)_const;
			if(l==-1){
				return XOpcode.LOADI_M1;
			}else if(l==0){
				return XOpcode.LOADI_0;
			}else if(l==1){
				return XOpcode.LOADI_1;
			}else if(l==2){
				return XOpcode.LOADI_2;
			}else if(l>=Byte.MIN_VALUE && l<=Byte.MAX_VALUE){
				return XOpcode.LOADB;
			}else if(l>=Short.MIN_VALUE && l<=Short.MAX_VALUE){
				return XOpcode.LOADS;
			}else if(l>=Integer.MIN_VALUE && l<=Integer.MAX_VALUE){
				return XOpcode.LOADI;
			}
			return XOpcode.LOADL;
		}else if(_const instanceof Float){
			float f = ((Float)_const).floatValue();
			if(f==(int)f){
				switch((int)f){
				case -1:
					return XOpcode.LOADD_M1;
				case 0:
					return XOpcode.LOADD_0;
				case 1:
					return XOpcode.LOADD_1;
				case 2:
					return XOpcode.LOADD_2;
				}
			}
			return XOpcode.LOADF;
		}else if(_const instanceof Double){
			double d = ((Double)_const).doubleValue();
			if(d==(int)d){
				switch((int)d){
				case -1:
					return XOpcode.LOADD_M1;
				case 0:
					return XOpcode.LOADD_0;
				case 1:
					return XOpcode.LOADD_1;
				case 2:
					return XOpcode.LOADD_2;
				}
			}
			return XOpcode.LOADD;
		}
		if(((String)_const).isEmpty()){
			return XOpcode.LOADT_E;
		}
		return XOpcode.LOADT;
	}
	
	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		switch(opcode){
		case LOADB:
			dataOutput.writeByte(((Number)_const).byteValue());
			break;
		case LOADS:
			if(_const instanceof Character){
				dataOutput.writeShort((Character)_const);
			}else{
				dataOutput.writeShort(((Number)_const).shortValue());
			}
			break;
		case LOADI:
			dataOutput.writeInt(((Number)_const).intValue());
			break;
		case LOADL:
			dataOutput.writeLong(((Number)_const).longValue());
			break;
		case LOADF:
			dataOutput.writeFloat(((Number)_const).floatValue());
			break;
		case LOADD:
			dataOutput.writeDouble(((Number)_const).doubleValue());
			break;
		case LOADT:
			dataOutput.writeUTF(_const.toString());
			break;
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return super.toString()+" "+_const;
	}
	
	@Override
	public int getSize() {
		return opcode==XOpcode.LOADB?2:opcode==XOpcode.LOADS||opcode==XOpcode.LOADI||opcode==XOpcode.LOADF||opcode==XOpcode.LOADD||opcode==XOpcode.LOADT?3:1;
	}
	
}
