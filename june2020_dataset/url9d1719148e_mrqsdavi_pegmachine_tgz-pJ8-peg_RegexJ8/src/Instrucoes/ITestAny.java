package Instrucoes;

public class ITestAny extends Instrucao {

	private String label;
	
	public ITestAny(String label){
		setLabel(label);
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
		return TipoInstrucao.TESTANY;
	}
	
	@Override
	@SuppressWarnings("all")
	public ITestAny ITestAny() {
		// TODO Auto-generated method stub
		return this;
	}
}
