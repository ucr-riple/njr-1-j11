package peg;
import java.util.ArrayList;

import Estruturas.Capture;
import Instrucoes.Instrucao;
import Instrucoes.TipoCapture;

public class Maquina {

	private char[] entrada;
	private Instrucao instrucoes[];
	EstadoMaquina estado;
	boolean rodouEnd = false;
	int tamanhoEntrada;
	
	public Maquina(String entrada, ArrayList<Instrucao> instrucoes){
		Instrucao [] insArray = new Instrucao[instrucoes.size()];
		insArray = instrucoes.toArray(insArray);
		this.setEntrada(entrada.toCharArray());
		this.setInstrucoes(insArray);
		estado = new EstadoMaquina();
		estado.inicializar();
		estado.setI(0);
		estado.setP(0);
	}

	public char[] getEntrada() {
		return entrada;
	}

	public void setEntrada(char[] entrada) {
		this.entrada = entrada;
		tamanhoEntrada = entrada.length;
	}

	public Instrucao[] getInstrucoes() {
		return instrucoes;
	}

	public void setInstrucoes(Instrucao[] instrucoes) {
		this.instrucoes = instrucoes;
	}
	
	public int getPosicaoEntrada(){
		return estado.getI();
	}
	
	public ArrayList<Capture> getCapturas(){
		return estado.getCapturas();
	}
	
	public void run(){
		
		while(estado.getP() < instrucoes.length){

			Instrucao instrucao = instrucoes[estado.getP()];
			
			boolean falhou = false;
			
			switch (instrucao.getTipoInstrucao()) {
			case CHAR:
				if(!instrucao.IChar().isVazio()){
					if(estado.getI() >= tamanhoEntrada){
						//System.out.println("Texto Acabou");
						falhou = true;
					}else if(entrada[estado.getI()] == instrucao.IChar().getCaracter()){
						estado.incI();
						//System.out.println(estado.getI()+" Casou "+instrucao.IChar().getCaracter());
					}else{
						//System.out.println(estado.getI()+" Falhou ao comparar: "+entrada.charAt(estado.getI())+"\tcom:"+instrucao.IChar().getCaracter());
						falhou = true;
					}
				}
				estado.incP();
				break;
				
			case TESTCHAR:
				if(estado.getI() >= tamanhoEntrada){
					//System.out.println("Texto Acabou");
					falhou = true;
				}else if(entrada[estado.getI()] == instrucao.ITestChar().getCaracter()){
					estado.incI();
					estado.incP();
					//System.out.println(estado.getI()+" Casou "+instrucao.IChar().getCaracter());
				}else{
					estado.setP(instrucao.getIndexDesvio());
				}
				break;
				
			case CHARSET:
				//System.out.println(estado.getI()+" ENTRADA: "+entrada);
				if(estado.getI() >= tamanhoEntrada){
					falhou = true;
				}else if(instrucao.ICharset().isCharecterIn(entrada[estado.getI()])){
					//System.out.println("Caractere no conjunto "+entrada.charAt(estado.getI()));
					estado.incI();
				}else{
					//System.out.println("Falhou ao comparar "+entrada.charAt(estado.getI())+" no conjunto: "+entrada.charAt(estado.getI())+" "+instrucao.ICharset().getSet());
					falhou = true;
				}
				estado.incP();
				break;
				
			case ANY:
				if(estado.getI() >= tamanhoEntrada){
					falhou = true;
				}else{
					estado.incI();
				}
				estado.incP();
				break;
				
			case CHOICE:{
				EstadoMaquina novoEstado = new EstadoMaquina();
				novoEstado.setP(instrucao.getIndexDesvio());
				novoEstado.setCapturas(estado.copiaCapturas());
				novoEstado.setI(estado.getI());
				estado.addEstado(novoEstado);
				estado.incP();
			}
				break;
				
			case COMMIT:
				estado.popEstado();
				estado.setP(instrucao.getIndexDesvio());
				break;
				
			case BACKCOMMIT:{
				EstadoMaquina estadoAntigo = estado.popEstado();
				estado.setCapturas(estadoAntigo.getCapturas());
				estado.setI(estadoAntigo.getI());
				estado.setP(instrucao.getIndexDesvio());
			}
				break;
				
			case PARTIALCOMMIT:{
				EstadoMaquina estadoAntigo = estado.popEstado();
				estado.setP(instrucao.getIndexDesvio());
				EstadoMaquina novoEstado = new EstadoMaquina();
				novoEstado.setI(estado.getI());
				novoEstado.setCapturas(estado.getCapturas());
				novoEstado.setP(estadoAntigo.getP());
				estado.addEstado(novoEstado);
			}
				break;
				
			case CALL:{
				EstadoMaquina novoEstado = new EstadoMaquina();
				novoEstado.setP(estado.getP()+1);
				estado.addEstado(novoEstado);
				estado.setP(instrucao.getIndexDesvio());
			}
				break;
				
			case SPAN:
				for(; estado.getI() < tamanhoEntrada; estado.incI()){
					char c = entrada[estado.getI()];
					if(!instrucao.ISpan().contem(c)) break;
				}
				estado.incP();
				break;
				
			case FIND:
				for(; estado.getI() < tamanhoEntrada; estado.incI()){
					char c = entrada[estado.getI()];
					if(instrucao.IFind().contem(c)) break;
				}
				estado.incP();
				break;
				
			case JUMP:
				estado.setP(instrucao.getIndexDesvio());
				break;
				
			case FAILTWICE:
				estado.popEstado();
				
			case FAIL:
				falhou = true;
				break;
				
			case RETURN:{
				EstadoMaquina estadoAntigo = estado.popEstado();
				estado.setP(estadoAntigo.getP());
				//System.out.println("Return "+estadoAntigo.getP());
			}
				break;
				
			case END:
				estado.incP();
				rodouEnd = true;
				break;

			case CAPTURE:
				if(instrucao.ICapture().getTipo() == TipoCapture.BEGIN){
					Capture newCapute = new Capture();
					newCapute.setPosicaoInicial(estado.getI());
					estado.insertCapture(newCapute);
				}else{
					Capture updateCapture = estado.popLastCapture();
					updateCapture.setPosicaoFinal(estado.getI());
					estado.addCaptura(updateCapture);
				}
				estado.incP();
				break;
				
			default:
				//estado.incP();
				break;
			}
			
			if(falhou){
				EstadoMaquina estadoAntigo = estado.popEstado();
				if(estadoAntigo!=null){
					estado.setP(estadoAntigo.getP());
					estado.setI(estadoAntigo.getI());
					if(estadoAntigo.getCapturas() != null){
						estado.setCapturas(estadoAntigo.getCapturas());
					}					
				}else{
					estado.setI(-1);
					break;
				}
				
			}
			
		}
				
		/*if(!rodouEnd){
			estado.setI(-1);
		}*/
	}
	
}
