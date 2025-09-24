package Instrucoes;

public class IFailTwice extends Instrucao{

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.FAILTWICE;
	}
	
	@Override
	@SuppressWarnings("all")
	public IFailTwice IFailTwice() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
