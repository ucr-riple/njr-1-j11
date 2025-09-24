package Instrucoes;

import java.util.Set;

public class ISpan extends Instrucao{

	private Set<Character> set;
	
	public ISpan(Set<Character> set){
		setSet(set);
	}
	
	public boolean contem(char c){
		return set.contains(c);
	}
	
	public Set<Character> getSet() {
		return set;
	}

	public void setSet(Set<Character> set) {
		this.set = set;
	}
	
	public TipoInstrucao getTipoInstrucao(){
		return TipoInstrucao.SPAN;
	}
	
	@Override
	@SuppressWarnings("all")
	public ISpan ISpan(){
		return this;
	}
	
}
