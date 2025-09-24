package Instrucoes;

public class IChoice extends Instrucao{

	private String label;
	
	public IChoice(String label){
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
		return TipoInstrucao.CHOICE;
	}

	@Override
	@SuppressWarnings("all")
	public IChoice IChoice() {
		// TODO Auto-generated method stub
		return this;
	}
}
