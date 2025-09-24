package mLibrary;

public class mPieceVar extends mVar {
	private final String delimiter;
	private final Integer position;

	public mPieceVar(mVar var, Object delimiter, Object position) {
		super(var.getSubs(), var.getmData());
		this.delimiter = String.valueOf(delimiter);
		if (position instanceof Integer) {
			this.position = (Integer) position;
		} else if (position instanceof Double) {
			this.position = ((Double) position).intValue();
		}   else {
			if (position instanceof String) {
				this.position = Double.valueOf(position.toString()).intValue();				
				try {
				} catch (NumberFormatException e) {	
				}
			}else{
				this.position = null;
				
			}
		}
		if (this.position == null || this.position < 0) {
			throw new IllegalArgumentException(
					"The position paramenter must greater than zero");
		}
	}

	public void set(Object value) {		
		if(value instanceof mVar){
			value = ((mVar)value).get();
		}
		super.set(mFnc.$setpiece(this.get(), delimiter, position, value));
	}
}
