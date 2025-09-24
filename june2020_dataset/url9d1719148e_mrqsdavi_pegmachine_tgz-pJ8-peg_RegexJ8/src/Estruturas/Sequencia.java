package Estruturas;

public class Sequencia extends Padrao{

	private String texto;
	
	public Sequencia(String texto){
		setTexto(texto);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.SEQUENCIA;
	}

	@Override
	public Sequencia sequencia() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return "'"+texto+"'";
	}
	
}
