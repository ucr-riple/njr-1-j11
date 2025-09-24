package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import xscript.XExec;
import xscript.XUtils;
import xscript.values.XValue;
import xscript.values.XValueAccess;
import xscript.values.XValueAttr;
import xscript.values.XValueNull;
import xscript.values.XValueObj;

public class XTypeData implements XObjectData {
	
	private String name;
	private List<XValue> bases;
	private List<XValue> cro;
	private XValue thiz;
	
	public XTypeData(XRuntime runtime, XObject obj, String name, List<XValue> bases){
		bases = new ArrayList<XValue>(bases);
		this.name = name;
		this.thiz = new XValueObj(obj.getPointer());
		cro = calcCRO(runtime, bases, thiz);
		this.bases = Collections.unmodifiableList(bases);
		init(runtime);
		init(runtime, obj);
	}
	
	public XTypeData(XRuntime runtime, XObject obj, String name, XValue base){
		this.name = name;
		this.thiz = new XValueObj(obj.getPointer());
		this.bases = new ArrayList<XValue>(1);
		this.bases.add(base);
		cro = calcCRO(runtime, bases, thiz);
		this.bases = Collections.unmodifiableList(bases);
		init(runtime);
		init(runtime, obj);
	}
	
	public XTypeData(XRuntime runtime, XObject obj, String name){
		this.name = name;
		this.thiz = new XValueObj(obj.getPointer());
		this.bases = new ArrayList<XValue>(1);
		this.bases.add(runtime.getBaseType(XUtils.OBJECT));
		cro = calcCRO(runtime, bases, thiz);
		this.bases = Collections.unmodifiableList(bases);
		init(runtime);
		init(runtime, obj);
	}
	
	XTypeData(XRuntime runtime, XObject obj){
		this.name = "Object";
		this.thiz = new XValueObj(obj.getPointer());
		bases = new ArrayList<XValue>(0);
		cro = new ArrayList<XValue>();
		cro.add(thiz);
		init(runtime);
		init(runtime, obj);
	}
	
	XTypeData(XRuntime runtime, XObject obj, String name, ObjectInput in) throws IOException{
		this.name = name;
		init(runtime);
		load(obj);
	}

	private void init(XRuntime runtime){
		String[] methods = getMethods();
		if(methods!=null){
			for(int i=0; i<methods.length; i++){
				String method = methods[i];
				int index = method.indexOf('(');
				int close = method.indexOf(')');
				String[] paramNames;
				int defStart = -1;
				boolean useList = false;
				boolean useMap = false;
				if(index==-1){
					if(close!=-1)
						throw new IllegalArgumentException();
					paramNames = new String[0];
				}else{
					int c = close;
					while(c<method.length()-1){
						char cc = method.charAt(c+1);
						if(!useList && cc=='l'){
							useList = true;
						}else if(!useMap && cc=='m'){
							useMap = true;
						}else{
							break;
						}
						c++;
					}
					if(c!=method.length()-1)
						throw new IllegalArgumentException();
					paramNames = method.substring(index+1, close).split(",");
					if(paramNames.length==1&&paramNames[0].isEmpty()){
						paramNames = new String[0];
					}
					for(int j=0; j<paramNames.length; j++){
						String s = paramNames[j];
						if(!s.isEmpty() && s.charAt(0)=='#'){
							paramNames[j] = s.substring(1);
							if(defStart==-1)
								defStart = j;
						}
					}
					method = method.substring(0, index);
				}
				runtime.addNativeMethod("intern."+name+"."+method, new XFunctionData(new TypeFunc(i), defStart, useList, useMap, paramNames));
			}
		}
	}
	
	public void init(XRuntime runtime, XObject obj){
		if(!runtime.doInit())
			return;
		String[] attributes = getAttributes();
		if(attributes!=null){
			for(int i=0; i<attributes.length; i++){
				obj.setRaw(attributes[i], new XValueAttr(i));
			}
		}
		String[] methods = getMethods();
		if(methods!=null){
			for(int i=0; i<methods.length; i++){
				String method = methods[i];
				int index = method.indexOf('(');
				if(index!=-1){
					method = method.substring(0, index);
				}
				obj.setRaw(method, new XValueAccess((XValueObj) runtime.createFunction("intern."+name+"."+method)));
			}
		}
	}
	
	private void load(XObject obj){
		String[] attributes = getAttributes();
		if(attributes!=null){
			for(int i=0; i<attributes.length; i++){
				obj.setRaw(attributes[i], new XValueAttr(i));
			}
		}
	}
	
	@Override
	public void delete(XRuntime runtime, boolean cleanup) {
		if(cleanup){
			for(XValue value:bases){
				value.decRef(runtime);
			}
		}
	}

	@Override
	public void setVisible(XRuntime runtime) {
		for(XValue value:bases){
			value.setVisible(runtime);
		}
	}

	@Override
	public void save(ObjectOutput out) throws IOException {
		out.writeUTF(name);
		//TODO
	}

	public List<XValue> getCRO(){
		return cro;
	}
	
	public List<XValue> setBases(XRuntime runtime, List<XValue> bases){
		List<XValue> old = this.bases;
		cro = calcCRO(runtime, bases, this.thiz);
		this.bases = bases;
		return old;
	}
	
	public List<XValue> getBases(){
		return bases;
	}
	
	public static List<XValue> calcCRO(XRuntime runtime, List<XValue> bases, XValue thiz){
		if(bases.isEmpty())
			throw new IllegalArgumentException();
		List<XValue> cro = new ArrayList<XValue>();
		List<List<XValue>> list = new ArrayList<List<XValue>>();
		List<XValue> thizList = new ArrayList<XValue>();
		thizList.add(thiz);
		list.add(thizList);
		list.add(new ArrayList<XValue>(bases));
		for(XValue value:bases){
			list.add(new ArrayList<XValue>(((XTypeData)runtime.getObject(value).getData()).getCRO()));
		}
		boolean again;
		do{
			again = false;
			XValue v = null;
			for(List<XValue> l:list){
				if(!l.isEmpty()){
					again = true;
					v = l.get(0);
					for(List<XValue> l2:list){
						if(l2.indexOf(v)>0){
							v = null;
							break;
						}
					}
					if(v!=null)
						break;
				}
			}
			if(v==null){
				if(again){
					throw new IllegalArgumentException();
				}else{
					break;
				}
			}
			cro.add(v);
			for(List<XValue> l2:list){
				if(l2.contains(v)){
					l2.remove(0);
				}
			}
		}while(again);
		return Collections.unmodifiableList(cro);
	}

	public String getName(){
		return name;
	}
	
	public XObjectData loadData(XRuntime runtime, XObject obj, ObjectInput in) throws IOException{
		return null;
	}
	
	public XObjectData createData(XRuntime runtime, XObject obj, Object[] args){
		return null;
	}
	
	public XValue getAttr(XRuntime runtime, XValue value, XObject obj, int attrID){
		return null;
	}
	
	public XValue setAttr(XRuntime runtime, XValue value, XObject obj, int attrID, XValue v){
		return null;
	}
	
	public XValue delAttr(XRuntime runtime, XValue value, XObject obj, int attrID){
		return null;
	}
	
	public XValue invoke(XRuntime runtime, XExec exec, int id, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map){
		return XValueNull.NULL;
	}
	
	public String[] getAttributes(){
		return null;
	}
	
	public String[] getMethods(){
		return null;
	}
	
	private class TypeFunc implements XFunction {

		private int id;
		
		TypeFunc(int id){
			this.id = id;
		}
		
		@Override
		public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) {
			return XTypeData.this.invoke(runtime, exec, id, thiz, params, list, map);
		}

	}

	public XValue alloc(XRuntime runtime, XValue type, List<XValue> list, Map<String, XValue> map){
		return runtime.alloc(type);
	}

	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return null;
	}
	
	
}
