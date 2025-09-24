package peg;
import java.util.ArrayList;

import Estruturas.Capture;


public class EstadoMaquina {

	private int p;
	private int i;
	private int tamanhoPilha;
	private ArrayList<EstadoMaquina> pilhaEstados;
	int numeroCapturas;
	private ArrayList<Capture> capturas;
	
	public EstadoMaquina(){
		p = -1;
		i = -1;
		tamanhoPilha=0;
		pilhaEstados = null;
		capturas = null;
	}
	
	public void inicializar(){
		pilhaEstados = new ArrayList<>();
		capturas = new ArrayList<>();
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public ArrayList<Capture> getCapturas() {
		return capturas;
	}
	public void setCapturas(ArrayList<Capture> capturas) {
		this.capturas = capturas;
	}
	public ArrayList<Capture> copiaCapturas() {
		return (ArrayList<Capture>) capturas.clone();
	}
	public void addCaptura(Capture c){
		this.capturas.add(numeroCapturas, c);
		numeroCapturas++;
	}
	public void insertCapture(Capture c){
		this.capturas.add(c);
	}
	public Capture popLastCapture(){
		Capture c = this.capturas.get(capturas.size()-1);
		capturas.remove(c);
		return c;
	}
	public void incI(){
		i++;
	}
	public void incP(){
		p++;
	}
	public void addEstado(EstadoMaquina estado){
		pilhaEstados.add(estado);
	}
	public EstadoMaquina popEstado(){
		
		if(pilhaEstados.size()>0){
			EstadoMaquina estado = pilhaEstados.get(pilhaEstados.size()-1);
			pilhaEstados.remove(pilhaEstados.size()-1);
			return estado;
		}
		return null;
	}
	
}
