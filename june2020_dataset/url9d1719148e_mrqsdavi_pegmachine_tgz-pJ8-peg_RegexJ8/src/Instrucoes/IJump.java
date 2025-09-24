package Instrucoes;

public class IJump extends Instrucao{
	
	private String label;
	
	public IJump(String label){
		setLabel(label);
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.JUMP;
	}

	@Override
	@SuppressWarnings("all")
	public IJump IJump() {
		// TODO Auto-generated method stub
		return this;
	}
}
