package xscript.compiler.inst;

import java.util.Arrays;

import xscript.XOpcode;
import xscript.compiler.XDataOutput;


public class XInstCall extends XInst {

	private String[] kws;
	
	private int unpackList;
	
	private int unpackMap;
	
	private int params;
	
	public XInstCall(int line, String[] kws, int unpackList, int unpackMap, int params) {
		super(line, kws==null || kws.length==0?unpackList==-1?unpackMap==-1?XOpcode.CALL:XOpcode.CALL_MAP:unpackMap==-1?XOpcode.CALL_LIST:XOpcode.CALL_LIST_MAP:unpackList==-1?unpackMap==-1?XOpcode.CALL_KW:XOpcode.CALL_MAP_KW:unpackMap==-1?XOpcode.CALL_LIST_KW:XOpcode.CALL_LIST_MAP_KW);
		this.kws = kws;
		this.unpackList = unpackList;
		this.unpackMap = unpackMap;
		this.params = params;
	}

	@Override
	public int getStackChange() {
		return -params-1;
	}

	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		dataOutput.writeByte(params);
		if(opcode!=XOpcode.CALL_LIST && opcode!=XOpcode.CALL_LIST_MAP && opcode!=XOpcode.CALL_MAP){
			if(unpackList!=-1){
				dataOutput.writeByte(unpackList);
			}
			if(unpackMap!=-1){
				dataOutput.writeByte(unpackMap);
			}
		}
		if(kws!=null && kws.length>0){
			int i=0;
			boolean b=true;
			for(String kw:kws){
				if(kw!=null && b){
					b=false;
					dataOutput.writeByte(i);
				}
				if(kw!=null)
					dataOutput.writeUTF(kw);
				i++;
			}
		}
	}

	@Override
	public int getSize() {
		return 2+(unpackList==-1?0:1)+(unpackMap==-1?0:1)+(kws==null || kws.length==0?0:1+kws.length*2);
	}

	@Override
	public String toString() {
		return super.toString()+(kws==null || kws.length==0?"":" "+Arrays.toString(kws))+(unpackList==-1?"":" "+unpackList)+(unpackMap==-1?"":" "+unpackMap)+" "+params;
	}

}
