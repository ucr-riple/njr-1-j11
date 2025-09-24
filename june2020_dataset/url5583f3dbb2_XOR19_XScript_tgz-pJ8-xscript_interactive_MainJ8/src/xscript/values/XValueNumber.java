package xscript.values;


public abstract class XValueNumber extends XValue {

	@Override
	public boolean noneZero() {
		return getBool();
	}

	public boolean isNumber() {
		return true;
	}
	
}
