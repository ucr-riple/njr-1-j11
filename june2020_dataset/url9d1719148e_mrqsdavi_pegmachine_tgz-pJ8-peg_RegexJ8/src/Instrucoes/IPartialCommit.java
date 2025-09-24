package Instrucoes;

public class IPartialCommit extends Instrucao{

private String label;
	
	public IPartialCommit(String label){
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
		return TipoInstrucao.PARTIALCOMMIT;
	}

	@Override
	@SuppressWarnings("all")
	public IPartialCommit IPartialCommit() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
