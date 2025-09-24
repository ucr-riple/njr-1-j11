package Instrucoes;

public class IAny extends Instrucao{

	private int n;
	
	public IAny(int n){
		this.n = n;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.ANY;
	}

	@Override
	@SuppressWarnings("all")
	public IAny IAny() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
