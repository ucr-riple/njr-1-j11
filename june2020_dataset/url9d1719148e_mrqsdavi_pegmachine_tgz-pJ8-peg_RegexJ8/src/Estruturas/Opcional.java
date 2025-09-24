package Estruturas;

public class Opcional extends Padrao{

	Padrao padrao;
	
	public Opcional(Padrao padrao){
		setPadrao(padrao);
	}
	
	public void setPadrao(Padrao padrao){
		this.padrao = padrao;
	}
	
	public Padrao getPadrao(){
		return padrao;
	}

	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.OPCIONAL;
	}

	@Override
	public Opcional opcional() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return "("+padrao.toString()+")?";
	}
	
}
