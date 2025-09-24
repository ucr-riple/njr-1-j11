package Instrucoes;

public class IBackCommit extends Instrucao{

	private String label;
	
	public IBackCommit(String label){
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
		return TipoInstrucao.BACKCOMMIT;
	}

	@Override
	@SuppressWarnings("all")
	public IBackCommit IBackCommit() {
		// TODO Auto-generated method stub
		return this;
	}	
	
}
