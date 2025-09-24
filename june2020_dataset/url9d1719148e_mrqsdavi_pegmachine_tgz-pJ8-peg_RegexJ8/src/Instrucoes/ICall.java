package Instrucoes;

public class ICall extends Instrucao{
	
	private String label;
	
	public ICall(String label){
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
		return TipoInstrucao.CALL;
	}

	@Override
	@SuppressWarnings("all")
	public Instrucoes.ICall ICall() {
		// TODO Auto-generated method stub
		return this;
	}

}
