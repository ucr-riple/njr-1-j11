package Estruturas;

import java.util.HashSet;
import java.util.Set;

public class Conjunto extends Padrao{

	private String texto;
	static Set<Character> todosOsCarcteres;
	private Set<Character> conjuntoCaracteres;
	
	public Conjunto(String texto){
		
		if(todosOsCarcteres == null){
			todosOsCarcteres = new HashSet<>();
			
			for(int i = 32; i < 127; i++){
				todosOsCarcteres.add((char) i);
			}
			
		}
		
		todosOsCarcteres.remove('[');
		todosOsCarcteres.remove(']');
		
		setTexto(texto);
		
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
		
		if(texto.charAt(0)=='^'){
			conjuntoCaracteres = new HashSet<Character>();
			conjuntoCaracteres.addAll(todosOsCarcteres);
			for(int i = 0; i < texto.length(); i++){
				char caractereAtual = texto.charAt(i);
				conjuntoCaracteres.remove(caractereAtual);
			}
			
			return;
		}
		
		conjuntoCaracteres = new HashSet<Character>();
		for(int i = 0; i < texto.length(); i++){
			char caractereAtual = texto.charAt(i);
			
			if(caractereAtual == '-' && i!=0){
				char primeiroCaractere = texto.charAt(i-1);
				char ultimoCaractere = texto.charAt(i+1);
				
				for(char c = (char) (primeiroCaractere+1); c <= ultimoCaractere; c++){
					conjuntoCaracteres.add(c);
				}
				
			}else{
				conjuntoCaracteres.add(caractereAtual);
			}
		}
	}
	
	public Set<Character> getConjuntoCaracteres() {
		return conjuntoCaracteres;
	}
	
	public void setListaCaracteres(Set<Character> conjuntoCaracteres) {
		this.conjuntoCaracteres = conjuntoCaracteres;
	}
	
	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.CONJUNTO;
	}

	@Override
	public Conjunto conjunto() {
		// TODO Auto-generated method stub
		return this;
	}

	
	@Override
	public String toString(){
		return "["+texto+"]";
	}
	
}
