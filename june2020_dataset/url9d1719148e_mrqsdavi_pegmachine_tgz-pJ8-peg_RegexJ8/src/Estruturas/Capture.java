package Estruturas;

public class Capture {
	private int posicaoInicial;
	private int posicaoFinal;
	private String identificador;
	private String texto;
	
	public Capture(){
		identificador = "Teste";
	}
	
	public int getPosicaoInicial() {
		return posicaoInicial;
	}
	public void setPosicaoInicial(int posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
	}
	public int getPosicaoFinal() {
		return posicaoFinal;
	}
	public void setPosicaoFinal(int posicaoFinal) {
		this.posicaoFinal = posicaoFinal;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String toString(){
		return identificador+": "+posicaoInicial+"-"+posicaoFinal;
	}
}
