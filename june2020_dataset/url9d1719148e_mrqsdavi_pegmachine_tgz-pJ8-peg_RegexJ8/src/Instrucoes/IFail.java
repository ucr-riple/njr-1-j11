package Instrucoes;

public class IFail extends Instrucao{

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.FAIL;
	}
	
	@Override
	@SuppressWarnings("all")
	public IFail IFail() {
		// TODO Auto-generated method stub
		return this;
	}
}
