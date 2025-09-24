package Instrucoes;


public class IChar extends Instrucao{

	private char caracter;
	private boolean isVazio;
	
	public IChar(char caracter){
		this.caracter = caracter;
		setVazio(false);
	}
	
	public IChar(boolean isVazio){
		setVazio(isVazio);
	}

	public char getCaracter() {
		return caracter;
	}

	public void setCaracter(char caracter) {
		this.caracter = caracter;
	}
	
	public boolean isVazio() {
		return isVazio;
	}

	public void setVazio(boolean isVazio) {
		this.isVazio = isVazio;
	}

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.CHAR;
	}

	@Override
	@SuppressWarnings("all")
	public IChar IChar() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
