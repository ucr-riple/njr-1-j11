package Estruturas;

public class Chamada extends Padrao{

	private String label;
	
	public Chamada(String label){
		setLabel(label);
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.CHAMADA;
	}
	
	@Override
	public Chamada chamada(){
		return this;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return label;
	}
	
}
