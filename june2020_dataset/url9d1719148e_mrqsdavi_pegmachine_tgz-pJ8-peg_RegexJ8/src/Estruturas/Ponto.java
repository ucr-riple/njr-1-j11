package Estruturas;

public class Ponto extends Padrao{

	private int numero;
	
	public Ponto(int numero){
		this.numero = numero;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.PONTO;
	}

	@Override
	public Ponto ponto() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return ".";
	}
	
}
