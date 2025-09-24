package agentes.jade.Centralizador;

import gramatica.Centralizador.Absyn.EApply1;
import gramatica.Centralizador.Absyn.EApply2;
import gramatica.Centralizador.Absyn.ECollect2;
import gramatica.Centralizador.Absyn.EData1;
import gramatica.Centralizador.Absyn.EData2;
import gramatica.Centralizador.Absyn.EData3;
import gramatica.Centralizador.Absyn.EData4;
import gramatica.Centralizador.Absyn.ERemedy1;
import gramatica.Centralizador.Absyn.ERemedy2;
import gramatica.Monitor.Absyn.EDados;
import gramatica.Monitor.Absyn.EDados1;
import gramatica.Monitor.Absyn.EDados2;
import gramatica.Monitor.Absyn.EDados3;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import util.GeradorAleatorioMsg;
import util.UpdateAgentList;
import agentes.jade.Monitor.Afericao;
import agentes.jade.Monitor.GrammarParserMonitor;
import agentes.jade.Monitor.TarefaMonitor;

public class AgenteCentralizador extends Agent {
	private static final int INICIO_FEBRE = 38;
	private static final int MIN_PRESSAO_DIAS_BOA = 60;
	private static final int MIN_PRESSAO_SIST_BOA = 100;
	private static final int MAX_PRESSAO_DIAS_BOA = 90;
	private static final int MAX_PRESSAO_SIST_BOA = 140;
	private final static int MIN_HEMO_BOA = 14;
	private final static int MAX_HEMO_BOA = 18;
	private final static int MIN_BILI_BOA = 0;
	private final static int MAX_BILI_BOA = 5;
	
	private static final int INTERVALO_REQUISICAO = 8000;
	private final int AGENTSNUMBER = 2;
	
	private AtuadorBusyControl atuadorIsBusy[] = new AtuadorBusyControl[AGENTSNUMBER];
	
	private List<AID> monitor;
	private List<AID> atuador;
	
	private String monitorName, atuadorName;
	
	private File file;
	
	/*
	 * TODO LIST
	 * TODO Implementar metodo que transforma objeto em mensagem de texto string
	 * TODO Implementar geraÃ§Ã£o automatica de mensagens randomicas a partir da gramatica
	 * TODO Implementar logica de decisao do centralizador
	 * TODO Implementar armazenamento e tabulacao dos dados no centralizador
	 * TODO Centralizador precisa saber de tudo que estÃ¡ fazendo, exemplos:
	 * 1 - Estou aplicando remedios agora? quais? NÃ£o vou pedir para aplciar a mesma coisa se jÃ¡ estou aplicando
	 * 2 - Estou medindo alguma cosia agora? o que? Nao vou pedir para medir a mesma coisa se jÃ¡ estou medindo
	 * 
	 * */
	
	public void setup() {
		System.out.println(getLocalName() +  ": Sou o agente " + getAID().getLocalName() + " e estou pronto.");

		// create agent t1 on the same container of the creator agent
		AgentContainer container = (AgentContainer) getContainerController();
   
		// create the file on project directory
		file =new File("outputData.txt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < AGENTSNUMBER; i++) {
			// create MONITOR
			monitorName = "monitor" + Integer.toString(i);
			CreatNewAgent(container, monitorName, "agentes.jade.Monitor.AgenteMonitor");

			// create ATUADOR
			atuadorName = "atuador" + Integer.toString(i);
			CreatNewAgent(container, atuadorName, "agentes.jade.Atuador.AgenteAtuador");
			AtuadorBusyControl _atuador = new AtuadorBusyControl();
			atuadorIsBusy[i] = _atuador;
		}
		
		CreatNewAgent(container, "paciente", "agentes.jade.Paciente.AgentePaciente");

		// minute
		addBehaviour(new TickerBehaviour(this, INTERVALO_REQUISICAO) {
			protected void onTick() {
				// manda para algum monitor uma mensagem aleatoria
				int indexMsg = (int) (Math.random() * (AGENTSNUMBER));
				sendMessageToAgent(GeradorAleatorioMsg.getRandomStartMeasure(), "monitor" + indexMsg);
							
				monitor = UpdateAgentList.getAgentUpdatedList("monitor", myAgent);
//				System.out.print(getLocalName() + ": Achei os seguintes monitores:");
//				for (AID m : monitor) {
//					System.out.print(" | " + m.getLocalName());
//				}
//				System.out.println();
				
				atuador = UpdateAgentList.getAgentUpdatedList("atuador", myAgent);
//				System.out.print(getLocalName() + ": Achei os seguintes atuadores:");
//				for (AID a : atuador) {
//					System.out.print(" | " + a.getLocalName());
//				}
//				System.out.println();
			}
		});
		
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				ACLMessage msg = receive(mt);

				if (msg != null) {
					// quem enviou a mensagem?
					AID sender = msg.getSender();

					// se o sender for monitor
					if (monitor.contains(sender)) {
						//System.out.println(getLocalName() + ": " + msg.getSender().getLocalName() + " respondeu " + msg.getContent());

						TarefaMonitor tm = new TarefaMonitor();						// valida mensagem
						try {
							tm = GrammarParserMonitor.getMonitorMessageObject(msg.getContent());
							List<Afericao> afericoes = tm.getAfericoes();
							// exemplo de tratamento para temperatura

							String indexCurrentAgent = sender.getLocalName().substring(sender.getLocalName().indexOf('r') + 1, sender.getLocalName().length());
							for (Afericao af : afericoes) {
								if (af.getDado() instanceof EDados) { 
									//  Decisao de parar medicao de temp ou continuar medicao e aplicar remedio
									if (af.getQuantidade1() > INICIO_FEBRE) {
										if (!atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyDipirona()) {
											StringBuilder temp = new StringBuilder();
											temp.append(getLocalName() + ": Temperatura ruim, " + String.valueOf(af.getQuantidade1()) + " C.");
											temp.append(getReferenceValue("temperatura"));
											temp.append(". Aplicando remedio.");
											System.out.println(temp.toString());
											updateOutputFile(temp.toString(), file);
											
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply1()); // aplica
											Medicamento medicamento = new Medicamento();
											medicamento.setQuantidade(15);
											medicamento.setRemedio(new ERemedy1()); // dipirona
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent(tarefa.prettyPrinterTarefa(),"atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyDipirona(true);
										}
									} else { // para liberacao de medicaÃ§Ã£o caso esteja aplicando
										StringBuilder temp = new StringBuilder();
										temp.append(getLocalName() + ": Temperatura boa, " + String.valueOf(af.getQuantidade1()) + " C.");
										temp.append(getReferenceValue("temperatura"));
										System.out.println(temp.toString());
										updateOutputFile(temp.toString(), file);
										
										if (atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyDipirona()) {
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply2()); // Cessar Liberacao
											Medicamento medicamento = new Medicamento();
											medicamento.setRemedio(new ERemedy1()); // dipirona
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent(tarefa.prettyPrinterTarefa(),"atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyDipirona(false);
										}

										TarefaCentralizador tarefa2 = new TarefaCentralizador();
										tarefa2.setAcao(new ECollect2()); // "Parar medicao"
										tarefa2.setDados(new EData1()); // "Temperatura"
										sendMessageToAgent(tarefa2.prettyPrinterTarefa(),"monitor" + indexCurrentAgent);
									}
								}
								if (af.getDado() instanceof EDados2) {
									// para aplicacao de dipirona caso esteja aplicando
									if (((af.getQuantidade1() >= MIN_HEMO_BOA) && (af.getQuantidade1() <= MAX_HEMO_BOA))) {
										StringBuilder temp = new StringBuilder();
										temp.append(getLocalName() + ": Hemoglobina boa, " + String.valueOf(af.getQuantidade1()) + " mg/dL");
										temp.append(getReferenceValue("hemoglobina"));
										System.out.println(temp.toString());
										updateOutputFile(temp.toString(), file);
										
										if (atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyDipirona()) {
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply2()); // Cessar Liberacao
											Medicamento medicamento = new Medicamento();
											medicamento.setRemedio(new ERemedy1()); // dipirona
											tarefa.setMedicacao(medicamento);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyDipirona(false);
											sendMessageToAgent(tarefa.prettyPrinterTarefa(),"atuador" + indexCurrentAgent);
										}
										
										TarefaCentralizador tarefa2 = new TarefaCentralizador();
										tarefa2.setAcao(new ECollect2()); // "Parar medicao"
										tarefa2.setDados(new EData2()); // "Hemoglobina"
										sendMessageToAgent( tarefa2.prettyPrinterTarefa(), "monitor" + indexCurrentAgent);
									} else { //se nao estou aplicando, entao aplica remedio
										if (!atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyDipirona()) {
											StringBuilder temp = new StringBuilder();
											temp.append(getLocalName() + ": Hemoglobina ruim , " + String.valueOf(af.getQuantidade1()) + " mg/dL");
											temp.append(getReferenceValue("hemoglobina"));
											temp.append(". Aplicando remedio.");
											System.out.println(temp.toString());
											updateOutputFile(temp.toString(), file);
											
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply1());
											Medicamento medicamento = new Medicamento();
											medicamento.setQuantidade(15);
											medicamento.setRemedio(new ERemedy1());
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent(tarefa.prettyPrinterTarefa(),"atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyDipirona(true);
										}
									}

								}
								if (af.getDado() instanceof EDados1) { 
									// Decisao de parar medicao de bilirrubina ou continuar medicao e aplicar remedio
									if (((af.getQuantidade1() >= MIN_BILI_BOA) && (af.getQuantidade1() <= MAX_BILI_BOA))) {
										StringBuilder temp = new StringBuilder();
										temp.append(getLocalName() + ": Bilirrubina boa, " + String.valueOf(af.getQuantidade1()) + " g/dL");
										temp.append(getReferenceValue("bilirrubina"));
										System.out.println(temp.toString());
										updateOutputFile(temp.toString(), file);
										
										if (atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyParacetamol()) {
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply2()); // Cessar Liberacao
											Medicamento medicamento = new Medicamento();
											medicamento.setRemedio(new ERemedy2()); // parcetamol
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent(tarefa.prettyPrinterTarefa(), "atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyParacetamol(false);
										}
										
										TarefaCentralizador tarefa2 = new TarefaCentralizador();
										tarefa2.setAcao(new ECollect2()); // "Parar medicao"
										tarefa2.setDados(new EData3());// bilirrubina
										sendMessageToAgent( tarefa2.prettyPrinterTarefa(), "monitor" + indexCurrentAgent);

										// System.out.println(getLocalName() + ": " + sender.getLocalName() + " bilirrubina boa, nao precisa remedio.NAO IMPLEMENTADO\n");
									} else {
										// Bilirrubina ruim, se nao estou aplicando remedio, entao aplica
										if (!atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyParacetamol()) {
											StringBuilder temp = new StringBuilder();
											temp.append(getLocalName() + ": Bilirrubina ruim, " + String.valueOf(af.getQuantidade1()) + " g/dL");
											temp.append(getReferenceValue("bilirrubina"));
											temp.append(". Aplicando remedio.");
											System.out.println(temp.toString());
											updateOutputFile(temp.toString(), file);
											
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply1());
											Medicamento medicamento = new Medicamento();
											medicamento.setQuantidade(20);
											medicamento.setRemedio(new ERemedy2());
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent( tarefa.prettyPrinterTarefa(), "atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyParacetamol(true);
										}
									}

								}
								if (af.getDado() instanceof EDados3) {
									// Decisao de parar medicao de Pressao arterial ou continuar medicao e aplicar remedio
									if (((af.getQuantidade1() >= MIN_PRESSAO_SIST_BOA) && (af.getQuantidade1() <= MAX_PRESSAO_SIST_BOA)) &&
										((af.getQuantidade2() >= MIN_PRESSAO_DIAS_BOA) && (af.getQuantidade2() <= MAX_PRESSAO_DIAS_BOA))){
										StringBuilder temp = new StringBuilder();
										temp.append(getLocalName() + ": Pressao arterial boa, Sist -> " + String.valueOf(af.getQuantidade1()) + 
												" Diast -> " + String.valueOf(af.getQuantidade2()));
										temp.append(getReferenceValue("pressao"));
										System.out.println(temp.toString());
										updateOutputFile(temp.toString(), file);
										
										if (atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyParacetamol()) {
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply2()); // Cessar Liberacao
											Medicamento medicamento = new Medicamento();
											medicamento.setRemedio(new ERemedy2()); // parcetamol
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent( tarefa.prettyPrinterTarefa(), "atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyParacetamol(false);
										}
										
										TarefaCentralizador tarefa2 = new TarefaCentralizador();
										tarefa2.setAcao(new ECollect2()); // "Parar medicao"
										tarefa2.setDados(new EData4());// Pressao Arterial
										sendMessageToAgent( tarefa2.prettyPrinterTarefa(), "monitor" + indexCurrentAgent);										
									} else { //se nao estou aplicando remedio, entao aplica
										if (!atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].getApplyParacetamol()) {
											StringBuilder temp = new StringBuilder();
											temp.append(getLocalName() + ": Pressao arterial ruim, Sist -> " + String.valueOf(af.getQuantidade1()) + 
													" Diast -> " + String.valueOf(af.getQuantidade2()));
											temp.append(getReferenceValue("pressao"));
											temp.append(". Aplicando remedio.");
											System.out.println(temp.toString());
											updateOutputFile(temp.toString(), file);
											
											TarefaCentralizador tarefa = new TarefaCentralizador();
											tarefa.setAcao(new EApply1());
											Medicamento medicamento = new Medicamento();
											medicamento.setQuantidade(20);
											medicamento.setRemedio(new ERemedy2());
											tarefa.setMedicacao(medicamento);
											sendMessageToAgent( tarefa.prettyPrinterTarefa(), "atuador" + indexCurrentAgent);
											atuadorIsBusy[Integer.parseInt(indexCurrentAgent)].setApplyParacetamol(true);
										}
									}

								}
							}
						} catch (Exception e) {
							System.out.println(getLocalName() + ". Erro ao ler resposta do monitor: " + e.getMessage());
						}
					} else if (atuador.contains(sender)){
						System.out.println(getLocalName() + ": Msg recebida do " + msg.getSender().getLocalName() + " -->" + msg.getContent());
						updateOutputFile(getLocalName() + ": Msg recebida do " + msg.getSender().getLocalName() + " -->" + msg.getContent(), file);
					}
						
					else {// ERRO
						System.out.println(getLocalName() + ": "
								+ sender.getLocalName() + ", "
								+ msg.getContent());
					}
					// System.out.println("**********************");
					// System.out.println();
				}

			}
		});
	}

	private AID CreatNewAgent(AgentContainer container, String agentName, String agentAdrress) {
		AID minhaID = null;
		try {
			System.out.println(getLocalName() + ": Agente " + agentName + " criado com sucesso");
			container.createNewAgent(agentName, agentAdrress, null).start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return minhaID;
	}

	private void sendMessageToAgent(String mensagem, String agentName) {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		AID receiver = new AID(agentName, AID.ISLOCALNAME);
		message.addReceiver(receiver);
		message.setContent(mensagem);
		System.out.println(getLocalName() + ": " + receiver.getLocalName() + ", " + message.getContent());
		updateOutputFile(getLocalName() + ": " + receiver.getLocalName() + ", " + message.getContent(), file);
		//System.out.println(getLocalName() + "para " + agentName + " : " + mensagem);
		send(message);
	}
	
	private String getReferenceValue(String tipo){
		StringBuilder result = new StringBuilder();
		result.append(". Valor de referencia: ");
		if (tipo == "temperatura")
			result.append("35<T<" + String.valueOf(INICIO_FEBRE));
		else if (tipo == "hemoglobina")
			result.append(String.valueOf(MIN_HEMO_BOA) + "<H<" + String.valueOf(MAX_HEMO_BOA));
		else if (tipo == "bilirrubina")
			result.append(String.valueOf(MIN_BILI_BOA) + "<B<" + String.valueOf(MAX_BILI_BOA));
		else if (tipo == "pressao") {
			result.append("Sist -> " + String.valueOf(MIN_PRESSAO_SIST_BOA) + "<SIST<" + String.valueOf(MAX_PRESSAO_SIST_BOA));
			result.append(". Diast -> " + String.valueOf(MIN_PRESSAO_DIAS_BOA) + "<DIAST<" + String.valueOf(MAX_PRESSAO_DIAS_BOA));
			}
		else System.out.println("Erro. Tipo desconhecido: " + tipo);
		
		return result.toString();
	}
	

	private void updateOutputFile(String data, File file) {
		// if file doesnt exists, then create it
		// if(!file.exists()){
		// file.createNewFile();
		// }

		// true = append file
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//			bufferWritter.write(data);
			bufferWritter.append(data);
			bufferWritter.newLine();
			bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println("Done");

	} 
	
}