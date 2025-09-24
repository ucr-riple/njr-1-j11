package agentes.jade.Monitor;

import java.util.ArrayList;
import java.util.List;

public class TarefaMonitor {
	//private List<Afericao> afericoes = new ArrayList<Afericao>();
	private List<Afericao> afericoes = new ArrayList<Afericao>();
	/*possÃ­veis valores
	 * EAcao. Acao ::= Dados "as" Hora;
	 * EAcao2. Acao ::= Acao  Operador Acao;
	 * EDados. Dados ::= "Temperatura de" Quantidade "C" ;
	 * EDados1. Dados ::= "Bilirrubina" Quantidade "g/dL" ;
	 * EDados2. Dados ::= "Hemoglobina" Quantidade "mg/dL";
	 * EDados3. Dados ::= "Pressao Arterial" Quantidade ":" Quantidade "mmHg";
	 * E qualquer concatenaÃ§Ã£o de duas ou mais aÃ§Ãµes
	 * */

	public List<Afericao> getAfericoes() {
		return afericoes;
	}

	public void setAfericoes(List<Afericao> afericoes) {
		this.afericoes = afericoes;
	}
	
	public void setAfericoes(Afericao afericao) {
		this.afericoes.add(afericao);
	}

	public String prettyPrinter() {
		StringBuilder pretty = new StringBuilder();
		
		int cont = 0;
		for (Afericao af : getAfericoes()) {
			if (cont > 0) pretty.append(" e ");
			pretty.append(af.prettyPrinter());
			cont++;
		}
		
		return pretty.toString();
	}
}
