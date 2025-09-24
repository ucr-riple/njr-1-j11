package Instrucoes;

public class ICapture extends Instrucao{

	private TipoCapture tipo;
	
	public ICapture(TipoCapture tipo){
		setTipo(tipo);
	}
	
	public TipoCapture getTipo() {
		return tipo;
	}

	public void setTipo(TipoCapture tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.CAPTURE;
	}

	@Override
	@SuppressWarnings("all")
	public ICapture ICapture() {
		// TODO Auto-generated method stub
		return this;
	}

}
