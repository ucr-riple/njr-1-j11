package Estruturas;

public class E extends Padrao{

	Padrao padrao;
	
	public E(Padrao padrao){
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
		return TipoPadrao.E;
	}

	@Override
	public E e() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return "&("+padrao.toString()+")";
	}
	
}
