package agentes.jade.Atuador;

import gramatica.Atuador.Absyn.EAcao;
import gramatica.Atuador.Absyn.EAcao1;
import gramatica.Atuador.Absyn.ERemedio;


public class TarefaAtuador {
	private Object acao; 
	/*possÃ­veis valores
	 * EAcao. Acao ::= "Liberando" Remedio;
	 * EAcao1. Acao ::= "Cessando Liberacao de" Remedio;
	 * */
	
	private Object remedio;
	/*
	 * ERemedio. Remedio::= "Dipirona";
	 * ERemedio1. Remedio::= "Paracetamol";
	 * */

	public Object getAcao() {
		return acao;
	}

	public void setAcao(Object acao) {
		this.acao = acao;
	}

	public Object getRemedio() {
		return remedio;
	}

	public void setRemedio(Object remedio) {
		this.remedio = remedio;
	}
	
	public String prettyPrinterAction() {
		StringBuilder pretty = new StringBuilder();
		
		if (getAcao() instanceof EAcao) {
			pretty.append("Liberando ");
			pretty.append( (getRemedio() instanceof ERemedio) ? "Dipirona" : "Paracetamol" );
		} else if (getAcao() instanceof EAcao1) {
			pretty.append("Cessando Liberacao de ");
			pretty.append( (getRemedio() instanceof ERemedio) ? "Dipirona" : "Paracetamol" );
		}
		
		return pretty.toString();
	}
}
