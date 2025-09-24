package Instrucoes;

public class ITestChar extends Instrucao {

	private char caracter;
	private String label;
	
	public ITestChar(char caracter, String label){
		setCaracter(caracter);
		setLabel(label);
	}

	public char getCaracter() {
		return caracter;
	}

	public void setCaracter(char caracter) {
		this.caracter = caracter;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.TESTCHAR;
	}
	
	@Override
	@SuppressWarnings("all")
	public ITestChar ITestChar() {
		// TODO Auto-generated method stub
		return this;
	}
}
