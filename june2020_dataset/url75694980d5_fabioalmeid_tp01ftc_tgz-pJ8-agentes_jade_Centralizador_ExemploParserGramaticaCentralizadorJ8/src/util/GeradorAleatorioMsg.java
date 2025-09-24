package util;



import gramatica.Centralizador.Absyn.Dados;
import gramatica.Centralizador.Absyn.EApply1;
import gramatica.Centralizador.Absyn.EApply2;
import gramatica.Centralizador.Absyn.ECollect1;
import gramatica.Centralizador.Absyn.ECollect2;
import gramatica.Centralizador.Absyn.EData1;
import gramatica.Centralizador.Absyn.EData2;
import gramatica.Centralizador.Absyn.EData3;
import gramatica.Centralizador.Absyn.EData4;

import java.util.HashMap;
import java.util.Map;

import agentes.jade.Centralizador.TarefaCentralizador;

public class GeradorAleatorioMsg {
	private static final int DATA_QUANTITY = 5;
	
	public static String getRandomStartMeasure() {
		TarefaCentralizador tc = new TarefaCentralizador();
		
		// seta iniciar medicao
		tc.setAcao(new ECollect1());

		int iRandom = (int) (Math.random() * (DATA_QUANTITY));
		switch (iRandom) {
		case 0:
			tc.setDados(new EData1());
			break;
		case 1:
			tc.setDados(new EData2());
			break;
		case 2:
			tc.setDados(new EData3());
			break;
		case 3:
			tc.setDados(new EData4());
			break;
		case 4:
			int iRandom2 = (int) (Math.random() * (DATA_QUANTITY-1));
			int iRandom3 = (int) (Math.random() * (DATA_QUANTITY-1));
			
			Map <Integer, Dados> hm = new HashMap<Integer, Dados>();
			hm.put(0, new EData1());
			hm.put(1, new EData2());
			hm.put(2, new EData3());
			hm.put(3, new EData4());
			
			tc.setDados(hm.get(iRandom2));
			if (iRandom3 != iRandom2)
				tc.setDados(hm.get(iRandom3));
							
			break;
		default:
			System.out.println("Error: Index out of range on TarefaCentralizador");
			break;
		}		
		return tc.prettyPrinterTarefa();
	}
	
	public static String getRandomStartMeasureWithErrors() {
		TarefaCentralizador tc = new TarefaCentralizador();
		
		int iRandomAcao = (int) (Math.random() * (DATA_QUANTITY-1));
		switch (iRandomAcao) {
		case 0:
			// seta iniciar medicao
			tc.setAcao(new ECollect1());
			break;
		case 1:
			tc.setAcao(new ECollect2());
			break;
		case 2:
			tc.setAcao(new EApply1());
			break;
		case 3:
			tc.setAcao(new EApply2());
			break;
		default:
			System.out.println("Error: Index out of range on TarefaCentralizador");
			break;
		}

		int iRandom = (int) (Math.random() * (DATA_QUANTITY));
		switch (iRandom) {
		case 0:
			tc.setDados(new EData1());
			break;
		case 1:
			tc.setDados(new EData2());
			break;
		case 2:
			tc.setDados(new EData3());
			break;
		case 3:
			tc.setDados(new EData4());
			break;
		case 4:
			int iRandom2 = (int) (Math.random() * (DATA_QUANTITY-1));
			int iRandom3 = (int) (Math.random() * (DATA_QUANTITY-1));
			
			Map <Integer, Dados> hm = new HashMap<Integer, Dados>();
			hm.put(0, new EData1());
			hm.put(1, new EData2());
			hm.put(2, new EData3());
			hm.put(3, new EData4());
			
			tc.setDados(hm.get(iRandom2));
			if (iRandom3 != iRandom2)
				tc.setDados(hm.get(iRandom3));
							
			break;
		default:
			System.out.println("Error: Index out of range on TarefaCentralizador");
			break;
		}		
		return tc.prettyPrinterTarefaWithError();
	}
	
//	public static String getRandomMessageCentralizador(){
//		List<String> mensagens = new ArrayList<String>();
//		mensagens.add("Iniciar Medicao Temperatura");
//		mensagens.add("Iniciar Medicao Hemoglobina");
//		mensagens.add("Iniciar Medicao bilirrubina");
//		mensagens.add("Iniciar Medicao Temperatura e Hemoglobina");
//		mensagens.add("Iniciar Medicao Hemoglobina e bilirrubina e Temperatura");
//		mensagens.add("Parar medicao Temperatura");
//		mensagens.add("Parar medicao bilirrubina");
//		mensagens.add("Parar medicao Temperatura e bilirrubina");
//		mensagens.add("Liberar Dipirona");
//		mensagens.add("Liberar Paracetamol");
//		mensagens.add("Liberar 8 Dipirona");
//		mensagens.add("Liberar 6 Paracetamol");
//		mensagens.add("Cessar Liberacao Dipirona");
//		mensagens.add("Cessar Liberacao Paracetamol");
//		// @TODO Adicionar mensagens invalidas
//		
//		int indexMsg = (int) (Math.random() * (mensagens.size()-1));
//		return mensagens.get(indexMsg);
//	}
	
//	public static String getRandomMessageCentralizadorToMonitor(){
//		List<String> mensagens = new ArrayList<String>();
//		mensagens.add("Iniciar Medicao Temperatura");
//		mensagens.add("Iniciar Medicao Hemoglobina");
//		mensagens.add("Iniciar Medicao bilirrubina");
//		mensagens.add("Iniciar Medicao Temperatura e Hemoglobina");
//		mensagens.add("Iniciar Medicao Hemoglobina e bilirrubina e Temperatura");
//		mensagens.add("Parar medicao Temperatura");
//		mensagens.add("Parar medicao bilirrubina");
//		mensagens.add("Parar medicao Temperatura e bilirrubina");
//		// @TODO Adicionar mensagens invalidas
//		
//		int indexMsg = (int) (Math.random() * (mensagens.size()-1));
//		return mensagens.get(indexMsg);
//	}
	
//	public static String getRandomMessageCentralizadorToAtuador(){
//		List<String> mensagens = new ArrayList<String>();
//		mensagens.add("Liberar Dipirona");
//		mensagens.add("Liberar Paracetamol");
//		mensagens.add("Liberar 8 Dipirona");
//		mensagens.add("Liberar 6 Paracetamol");
//		mensagens.add("Cessar Liberacao Dipirona");
//		mensagens.add("Cessar Liberacao Paracetamol");
//		// @TODO Adicionar mensagens invalidas
//		
//		int indexMsg = (int) (Math.random() * (mensagens.size()-1));
//		return mensagens.get(indexMsg);
//	}
	
	
//	public static String getRandomMessageMonitor(){
//		List<String> mensagens = new ArrayList<String>();
//		mensagens.add("Temperatura de 39 C as 19 h: 59 m");
//		mensagens.add("Bilirrubina 1000 g/dL as 16 h: 12 m");
//		mensagens.add("Hemoglobina 900 mg/dL as 22 h: 36 m");
//		mensagens.add("Pressao Arterial 15 : 9 mmHg as 00 h: 12 m");
//		mensagens.add("Bilirrubina 1000 g/dL as 14 h: 01 m e Hemoglobina 900 mg/dL as 14 h: 01 m");
//		mensagens.add("Temperatura de 39 C as 19 h: 59 m e Pressao Arterial 15 : 9 mmHg as 19 h: 18 m");
//		//@TODO Adicionar mensagens invalidas
//		
//		int indexMsg = (int) (Math.random() * (mensagens.size()-1));
//		return mensagens.get(indexMsg);
//	}
	
}
