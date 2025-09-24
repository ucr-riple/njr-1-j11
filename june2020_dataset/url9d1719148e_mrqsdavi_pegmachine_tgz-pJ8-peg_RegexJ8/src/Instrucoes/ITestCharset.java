package Instrucoes;

import java.util.Set;

public class ITestCharset extends Instrucao {
	
	private Set<Character> set;
	private String label;

	public ITestCharset(Set<Character> set, String label){
		setSet(set);
		setLabel(label);
	}
	
	public Set<Character> getSet() {
		return set;
	}

	public void setSet(Set<Character> set) {
		this.set = set;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public TipoInstrucao getTipoInstrucao() {
		// TODO Auto-generated method stub
		return TipoInstrucao.TESTCHARSET;
	}

	@Override
	@SuppressWarnings("all")
	public ITestCharset ITestCharset() {
		// TODO Auto-generated method stub
		return this;
	}
}
