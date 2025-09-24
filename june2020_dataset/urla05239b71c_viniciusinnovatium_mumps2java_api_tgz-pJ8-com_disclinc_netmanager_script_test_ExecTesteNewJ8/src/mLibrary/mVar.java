package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;

import java.util.Arrays;

import br.com.innovatium.mumps2java.todo.REMOVE;
import br.com.innovatium.mumps2java.todo.TODO;
public class mVar {
	/*
	 * The subscripts should be converted to String soon.
	 */
	@TODO
	private Object[] subs;
	private mData mData;

	public mVar(Object[] subs, mLibrary.mData mData) {
		this.subs = subs;
		this.mData = mData;
	}

	@REMOVE
	public int getType() {
		char type = subs[0].toString().charAt(0);
		if ('%' == type) {
			return 1;
		} else if ('^' == type) {
			return 2;
		} else {
			return 3;
		}
	}

	@REMOVE
	public boolean isSameType(mVar var) {
		return this.getType() == var.getType();
	}

	public Object order(int direction) {
		return mData.order(subs, direction);
	}

	public Object order() {
		return order(1);
	}

	public void set(Object value) {
		if (generateKey(subs).contains("^WWWSOR")) {
			System.out.print("");
			if(String.valueOf(value).contains("null")){
				System.out.print("");
			}		
		}
		if (generateKey(subs).contains("YSATZ")) {
			System.out.print("");
			if(String.valueOf(value).contains("null")){
				System.out.print("");
			}		
		}		
		if (generateKey(subs).contains("objWWW122")) {
			System.out.print("");
			if(String.valueOf(value).contains("null")){
				System.out.print("");
			}		
		}			
		mData.subs(subs).set(value);
	}

	public Object get() {
		Object value = mData.get(subs);
		if(String.valueOf(value).contains("Sexo")){
			System.out.print("");
		}
		if(generateKey(subs).contains("YSATZ")){
			System.out.print("");
		}		
		return value;
	}

	public void kill() {
		mData.kill(subs);
	}

	public int data() {
		return data(1);
	}

	public int data(int direction) {
		return mData.data(subs);
	}

	public mVar var(Object... subs) {
		return new mVar(mFncUtil.concat(this.subs, subs), mData);
	}
	
	public mVar lastVar(Object... subs) {
		return new mVar(mFncUtil.concatSinceLastSubscript(this.subs, subs), mData);  
	} 

	public String getName() {
		return subs[0].toString();
	}

	public Object[] getParameters() {
		return Arrays.asList(subs).subList(1, subs.length).toArray();
	}

	public Object[] getSubs() {
		return subs;
	}

	public mData getmData() {
		return mData;
	}

	public mClass getORef() {
		Object objRef = get();
		if (objRef instanceof mClass) {
			return (mClass) objRef;
		} else {
			return null;
		}
	}

}
