package Estruturas;

import java.util.ArrayList;

public class EscolhaOrdenada extends Padrao{
	
	
	private ArrayList<Padrao> padroes;
	
	public EscolhaOrdenada(ArrayList<Padrao> padroes){
		setPadroes(padroes);
	}
	
	public EscolhaOrdenada(Padrao padrao){
		padroes = new ArrayList<Padrao>();
		addPadrao(padrao);
	}
	
	public ArrayList<Padrao> getPadroes() {
		return padroes;
	}

	public void setPadroes(ArrayList<Padrao> padroes) {
		this.padroes = padroes;
	}
	
	public void addPadrao(Padrao padrao){
		padroes.add(0, padrao);
	}
	
	public void substituir(Padrao padrao, Padrao novoPadrao){
		int index = padroes.indexOf(padrao);
		padroes.set(index, novoPadrao);
	}

	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.ESCOLHA_ORDENADA;
	}

	@Override
	public EscolhaOrdenada escolhaOrdenada() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		String retorno = "";
		
		for(int i = 0; i < padroes.size(); i++){
			Padrao padraoIteracao = padroes.get(i);
			retorno += "("+padraoIteracao.toString()+")";
			
			if(i < padroes.size() - 1){
				retorno += " /";
			}
		}
		
		return retorno;
	}

}
