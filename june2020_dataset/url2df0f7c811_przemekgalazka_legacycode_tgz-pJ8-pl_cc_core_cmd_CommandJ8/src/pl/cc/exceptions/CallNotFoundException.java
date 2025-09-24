package pl.cc.exceptions;

import pl.cc.real.RealCall;

public class CallNotFoundException extends Exception {
	private static final long serialVersionUID = -8159982812757456316L;
	RealCall call;

	public CallNotFoundException(RealCall call){
		this.call = call;
	}

	@Override
	public String getMessage(){
		return new String("Nie znaleziono połączenia: "+call.toString());
	}
	
}
