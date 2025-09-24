package Instrucoes;

public class ICommit extends Instrucao{

	private String label;
	
	public ICommit(String label){
		this.label = label;
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
		return TipoInstrucao.COMMIT;
	}

	@Override
	@SuppressWarnings("all")
	public ICommit ICommit() {
		// TODO Auto-generated method stub
		return this;
	}	
}
