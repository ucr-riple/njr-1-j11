package Instrucoes;

import java.util.Set;

public class IFind extends Instrucao{

	private Set<Character> set;
	
	public IFind(Set<Character> set){
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
		return TipoInstrucao.FIND;
	}
	
	@Override
	@SuppressWarnings("all")
	public IFind IFind(){
		return this;
	}
	
}
