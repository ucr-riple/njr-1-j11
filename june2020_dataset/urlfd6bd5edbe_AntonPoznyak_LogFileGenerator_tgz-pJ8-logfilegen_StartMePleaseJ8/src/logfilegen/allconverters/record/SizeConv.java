package logfilegen.allconverters.record;

import logfilegen.allmodels.record.Size;

public class SizeConv {
	private Size size;
	
	public SizeConv(Size size){
		this.size = size;
	}
	public String convertToString(){
		return Long.toString(size.getSize());
	}  
}
