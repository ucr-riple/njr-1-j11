package xscript.compiler.inst;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import xscript.XOpcode;
import xscript.compiler.XCodeGen;
import xscript.compiler.XDataOutput;
import xscript.compiler.XJumpTarget;

public class XInstSwitch extends XInst {

	public XJumpTarget _default;
	private HashMap<Object, XJumpTarget> switches = new HashMap<Object, XJumpTarget>();
	private int resolvedDefault;
	private long min=Long.MAX_VALUE;
	private long max=Long.MIN_VALUE;
	private HashMap<Object, Integer> resolvedSwitches = new HashMap<Object, Integer>();
	private int resolvedDefault2;
	private HashMap<Object, Integer> resolvedSwitches2 = new HashMap<Object, Integer>();
	
	public XInstSwitch(int line) {
		super(line, XOpcode.SWITCH);
	}

	@Override
	public boolean pointingTo(XInst inst) {
		if(_default.is(inst))
			return true;
		for(XJumpTarget o:switches.values()){
			if(o.is(inst))
				return true;
		}
		return false;
	}

	@Override
	public void resolve(XCodeGen codeGen, List<XInst> instructions) {
		resolvedDefault = _default.resolve(codeGen);
		resolvedDefault2 = _default.resolve(instructions);
		for(Entry<Object, XJumpTarget> e:switches.entrySet()){
			Object k = e.getKey();
			resolvedSwitches.put(k, e.getValue().resolve(codeGen));
			resolvedSwitches2.put(k, e.getValue().resolve(instructions));
			if(k instanceof Number){
				long l = ((Number)k).longValue();
				if(min>l)
					min=l;
				if(max<l)
					max=l;
			}
		}
	}

	@Override
	public void replace(XCodeGen codeGen, XInst instruction, XInst with, List<XInst> instructions) {
		_default.replace(instruction, with);
		for(Entry<Object, XJumpTarget> e:switches.entrySet()){
			e.getValue().replace(instruction, with);
		}
	}

	@Override
	public boolean isNormalJump() {
		return false;
	}

	@Override
	public boolean isAlwaysJump() {
		return true;
	}

	@Override
	public int tryWay(XCodeGen codeGen, int programPointer, int stackSize, int[] sizes) {
		for(Integer i:resolvedSwitches2.values()){
			codeGen.tryWay(i, stackSize, sizes);
		}
		return resolvedDefault2;
	}
	
	@Override
	public void toCode(XDataOutput dataOutput) {
		super.toCode(dataOutput);
		dataOutput.writeShort(resolvedDefault);
		Integer i = resolvedSwitches.get(null);
		if(i==null){
			dataOutput.writeShort(resolvedDefault);
		}else{
			dataOutput.writeShort(i);
		}
		i = resolvedSwitches.get(true);
		if(i==null){
			dataOutput.writeShort(resolvedDefault);
		}else{
			dataOutput.writeShort(i);
		}
		i = resolvedSwitches.get(false);
		if(i==null){
			dataOutput.writeShort(resolvedDefault);
		}else{
			dataOutput.writeShort(i);
		}
		List<Long> longSwitches = new LinkedList<Long>();
		List<Number> doubleSwitches = new LinkedList<Number>();
		List<String> stringSwitches = new LinkedList<String>();
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		for(Entry<Object, Integer> e:resolvedSwitches.entrySet()){
			Object key = e.getKey();
			if(key instanceof Long){
				long l = ((Long)key).longValue();
				if(l>max)
					max = l;
				if(l<min)
					min = l;
				longSwitches.add((Long)key);
			}else if(key instanceof Float || key instanceof Double){
				doubleSwitches.add((Number)key);
			}else if(key instanceof String){
				stringSwitches.add((String)key);
			}else{
				throw new AssertionError();
			}
		}
		
		Number[] doubleKeys = doubleSwitches.toArray(new Number[doubleSwitches.size()]);
		Arrays.sort(doubleKeys, DOUBLE_COMPARATOR);
		dataOutput.writeShort(doubleKeys.length);
		for(Number key:doubleKeys){
			if(key instanceof Float){
				dataOutput.writeByte(0);
				dataOutput.writeFloat((Float)key);
			}else if(key instanceof Double){
				dataOutput.writeByte(1);
				dataOutput.writeDouble((Float)key);
			}
			dataOutput.writeShort(resolvedSwitches.get(key));
		}
		
		String[] stringKeys = stringSwitches.toArray(new String[stringSwitches.size()]);
		Arrays.sort(stringKeys);
		dataOutput.writeShort(stringKeys.length);
		for(String key:stringKeys){
			dataOutput.writeUTF(key);
			dataOutput.writeShort(resolvedSwitches.get(key));
		}

		if(longSwitches.size()==0){
			dataOutput.writeByte(5);
		}else if(useTabel(min, max, longSwitches.size())){
			if(min>=Byte.MIN_VALUE && min<=Byte.MAX_VALUE){
				dataOutput.writeByte(0);
				dataOutput.writeByte((int)min);
			}else if(min>=Short.MIN_VALUE && min<=Short.MAX_VALUE){
				dataOutput.writeByte(1);
				dataOutput.writeShort((int)min);
			}else if(min>=Integer.MIN_VALUE && min<=Integer.MAX_VALUE){
				dataOutput.writeByte(2);
				dataOutput.writeInt((int)min);
			}else{
				dataOutput.writeByte(3);
				dataOutput.writeLong(min);
			}
			int size = (int) (max-min);
			dataOutput.writeShort(size);
			for(int j=0; j<=size; j++){
				i = resolvedSwitches.get(Long.valueOf(min+j));
				if(i==null){
					dataOutput.writeShort(resolvedDefault);
				}else{
					dataOutput.writeShort(i);
				}
			}
		}else{
			dataOutput.writeByte(4);
			Long[] longKeys = longSwitches.toArray(new Long[longSwitches.size()]);
			Arrays.sort(longKeys, LONG_COMPARATOR);
			dataOutput.writeShort(longKeys.length);
			for(Long key:longKeys){
				long k = key;
				if(k>=Short.MIN_VALUE && k<=Short.MAX_VALUE){
					dataOutput.writeByte(0);
					dataOutput.writeShort((int)k);
				}else if(k>=Integer.MIN_VALUE && k<=Integer.MAX_VALUE){
					dataOutput.writeByte(1);
					dataOutput.writeInt((int)k);
				}else{
					dataOutput.writeByte(2);
					dataOutput.writeLong(k);
				}
				dataOutput.writeShort(resolvedSwitches.get(key));
			}
		}
		
	}
	
	@Override
	public int getSize() {
		int size = 14;
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		int cnt=0;
		int nonTableSize = 0;
		for(Object key:switches.keySet()){
			if(key instanceof Long){
				long l = ((Long)key).longValue();
				cnt++;
				if(l>max)
					max = l;
				if(l<min)
					min = l;
				nonTableSize += 5;
			}else if(key instanceof Float || key instanceof Double){
				size += 5;
			}else if(key instanceof String){
				size += 4;
			}
		}
		if(cnt!=0){
			if(useTabel(min, max, cnt)){
				if(min>=Byte.MIN_VALUE && min<=Byte.MAX_VALUE){
					size+=3;
				}else{
					size+=4;
				}
				size += cnt*2;
			}else{
				size += 2+nonTableSize;
			}
		}
		return size;
	}

	private static boolean useTabel(long min, long max, int count){
		return false;
	}
	
	private static final LongComparator LONG_COMPARATOR = new LongComparator();
	
	private static final DoubleComparator DOUBLE_COMPARATOR = new DoubleComparator();
	
	private static class LongComparator implements Comparator<Number>{

		@Override
		public int compare(Number o1, Number o2) {
			return Long.compare(o1.longValue(), o2.longValue());
		}
		
	}
	
	private static class DoubleComparator implements Comparator<Number>{

		@Override
		public int compare(Number o1, Number o2) {
			return Double.compare(o1.doubleValue(), o2.doubleValue());
		}
		
	}
	
	public boolean putIfNonExist(Object _const, XJumpTarget target){
		if(_const instanceof Character){
			_const = Long.valueOf(((Character)_const).charValue());
		}else if(_const instanceof Byte || _const instanceof Short || _const instanceof Integer){
			_const = Long.valueOf(((Number)_const).longValue());
		}
		if(switches.containsKey(_const))
			return false;
		switches.put(_const, target);
		return true;
	}
	
}
