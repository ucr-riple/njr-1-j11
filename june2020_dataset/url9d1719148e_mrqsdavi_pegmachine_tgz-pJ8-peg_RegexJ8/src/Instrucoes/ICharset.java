package Instrucoes;

import java.util.Set;

public class ICharset extends Instrucao{

	private String texto;
	private Set<Character> set;
	
	public ICharset(String texto, Set<Character> set){
		setTexto(texto);
		setSet(set);
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Set<Character> getSet() {
		return set;
	}
	public void setSet(Set<Character> set) {
		this.set = set;
	}

	public boolean isCharecterIn(char caracter){
		return set.contains(caracter);
	}
	
	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.CHARSET;
	}

	@Override
	@SuppressWarnings("all")
	public ICharset ICharset() {
		// TODO Auto-generated method stub
		return this;
	}	
}
