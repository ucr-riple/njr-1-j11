package Estruturas;

public class Captura extends Padrao{

	private Padrao padrao;

	public Captura(Padrao padrao){
		setPadrao(padrao);
	}
	
	public Padrao getPadrao() {
		return padrao;
	}

	public void setPadrao(Padrao padrao) {
		this.padrao = padrao;
	}

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.CAPTURA;
	}
	
	@Override
	public Captura captura() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return "{"+padrao.toString()+"}";
	}
	
}
