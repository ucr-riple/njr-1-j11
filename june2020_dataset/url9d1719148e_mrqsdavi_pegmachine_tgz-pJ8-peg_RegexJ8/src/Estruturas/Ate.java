package Estruturas;

public class Ate extends Padrao{

	private Padrao padrao;
	
	public Ate(Padrao padrao){
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
		return TipoPadrao.ATE;
	}

	@Override
	public Ate ate() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		
		return "~"+padrao.toString();
	}
	
}
