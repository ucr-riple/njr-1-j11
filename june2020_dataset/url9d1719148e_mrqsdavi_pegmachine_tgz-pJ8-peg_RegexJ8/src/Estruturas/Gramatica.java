package Estruturas;

import java.util.ArrayList;

public class Gramatica extends Padrao{

	private String nome;
	private Padrao padrao;
	
	private ArrayList<Gramatica> subgramaticas;
	
	public Gramatica(String nome){
		setNome(nome);
		subgramaticas = new ArrayList<>();
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		
		if(nome != null && padrao != null){
			Gramatica newSubgramatica = new Gramatica(nome);
			newSubgramatica.setPadrao(padrao);
			subgramaticas.add(0, newSubgramatica);
		}
		
		this.nome = nome;
	}
	
	public Padrao getPadrao(){
		return padrao;
	}
	
	public void setPadrao(Padrao padrao){
		this.padrao = padrao;
	}
	
	@Override
	public TipoPadrao getTipo() {
		// TODO Auto-generated method stub
		return TipoPadrao.GRAMATICA;
	}

	@Override
	public Gramatica gramatica() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return "("+nome+" <- "+padrao.toString()+")";
	}

	public ArrayList<Gramatica> getSubgramaticas() {
		return subgramaticas;
	}

	public void setSubgramaticas(ArrayList<Gramatica> subgramaticas) {
		this.subgramaticas = subgramaticas;
	}
	
	public void addSubgramatica(Gramatica subgramatica){
		subgramaticas.add(subgramatica);
	}

}
