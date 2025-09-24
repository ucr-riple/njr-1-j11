package ch.zhaw.regularLanguages.language;

import ch.zhaw.regularLanguages.helpers.PublicCloneable;

public class BooleanWrapper implements PublicCloneable{
	private boolean booleanValue;
	public BooleanWrapper(boolean booleanValue){
		this.booleanValue = booleanValue;
	}
	
	public boolean getBooleanValue(){
		return booleanValue;
	}
	
	@Override
	public Object clone(){
		return new BooleanWrapper(this.booleanValue);
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o  instanceof BooleanWrapper)) {
			return false;
		}
		BooleanWrapper obj = (BooleanWrapper)o;
		return obj.getBooleanValue() == this.getBooleanValue();
	}
	
}
