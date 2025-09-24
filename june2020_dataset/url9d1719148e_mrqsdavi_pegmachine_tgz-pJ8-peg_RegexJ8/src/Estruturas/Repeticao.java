package Estruturas;

public class Repeticao extends Padrao{

	private TipoRepeticao tipoRepeticao;
	private Padrao padrao;
	
	public Repeticao(Padrao padrao, TipoRepeticao tipoRepeticao){
		this.padrao = padrao;
		this.tipoRepeticao = tipoRepeticao;
	}
	
	public TipoRepeticao getTipoRepeticao() {
		return tipoRepeticao;
	}
	public void settipoRepeticao(TipoRepeticao tipoRepeticao) {
		this.tipoRepeticao = tipoRepeticao;
	}
	public Padrao getPadrao() {
		return padrao;
	}
	public void setPadrao(Padrao padrao) {
		this.padrao = padrao;
	}

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.REPETICAO;
	}

	@Override
	public Repeticao repeticao() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		String retorno = padrao.toString();
		
		if (tipoRepeticao == TipoRepeticao.ZERO_OU_MAIS) {
			retorno +="*";
		}else{
			retorno +="+";
		}
		
		return retorno;
	}
	
}
