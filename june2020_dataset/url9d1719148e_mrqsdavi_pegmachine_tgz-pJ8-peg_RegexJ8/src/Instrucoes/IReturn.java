package Instrucoes;

public class IReturn extends Instrucao{

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.RETURN;
	}
	
	@Override
	@SuppressWarnings("all")
	public IReturn IReturn() {
		// TODO Auto-generated method stub
		return this;
	}
}
