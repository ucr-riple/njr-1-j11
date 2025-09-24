package agentes.jade.Centralizador;

import java.util.ArrayList;
import java.util.List;

public class ExemploParserGramaticaCentralizador {
	public static void main(String args[]) {
		List<String> mensagens = new ArrayList<String>();
		
		// todas mensagens corretas - Simulacao de envio do centralizador
		mensagens.add("Iniciar Medicao de Temperatura");
		mensagens.add("Iniciar Medicao de Hemoglobina");
		mensagens.add("Iniciar Medicao de Bilirrubina");
		mensagens.add("Iniciar Medicao de Temperatura e Hemoglobina");
		mensagens.add("Iniciar Medicao de Hemoglobina e Bilirrubina e Temperatura");
		mensagens.add("Parar Medicao de Temperatura");
		mensagens.add("Parar Medicao de Bilirrubina");
		mensagens.add("Parar Medicao de Temperatura e Bilirrubina");
		mensagens.add("Liberar Dipirona");
		mensagens.add("Liberar Paracetamol");
		mensagens.add("Liberar 8 ml de Dipirona");
		mensagens.add("Liberar 6 ml de Paracetamol");
		mensagens.add("Cessar Liberacao de Dipirona");
		mensagens.add("Cessar Liberacao de Paracetamol");		

		for (String s : mensagens) {
			System.out.println("mensagem recebida do centralizador:\n" + s);
			try { // simula o monitor recebendo e validando a mensagem
				TarefaCentralizador tc = GrammarParserCentralizador.getCentralizadorMessageObject(s);
				System.out.println(tc.prettyPrinterTarefa());
				System.out.println("\n-------------------");	
			} catch (Exception e){
				System.out.println(e.getMessage());
			}						
		}

		System.out.println("acabou");

	}
}
