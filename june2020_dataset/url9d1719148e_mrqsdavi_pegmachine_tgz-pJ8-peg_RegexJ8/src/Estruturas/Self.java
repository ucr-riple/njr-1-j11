package Estruturas;

public class Self extends Padrao{

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.SELF;
	}
	
	@Override
	public Self self(){
		return this;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SELF";
	}
	
}
