package Estruturas;

public class Nao extends Padrao{
	
	Padrao padrao;
	
	public Nao(Padrao padrao){
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
		return TipoPadrao.NAO;
	}

	@Override
	public Nao nao() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return "!("+padrao.toString()+")";
	}

}
