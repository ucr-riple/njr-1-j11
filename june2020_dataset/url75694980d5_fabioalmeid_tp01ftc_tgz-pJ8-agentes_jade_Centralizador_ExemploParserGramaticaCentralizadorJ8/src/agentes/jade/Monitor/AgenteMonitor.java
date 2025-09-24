package agentes.jade.Monitor;

import gramatica.Centralizador.Absyn.EAction3;
import gramatica.Centralizador.Absyn.ECollect1;
import gramatica.Centralizador.Absyn.ECollect2;
import gramatica.Centralizador.Absyn.EData1;
import gramatica.Centralizador.Absyn.EData2;
import gramatica.Centralizador.Absyn.EData3;
import gramatica.Centralizador.Absyn.EData4;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

import agentes.jade.Centralizador.GrammarParserCentralizador;
import agentes.jade.Centralizador.TarefaCentralizador;

public class AgenteMonitor extends Agent {

	private static final long serialVersionUID = 1L;

	private ACLMessage ACLmsg;
	private Boolean isTempRunning = false, isHemoglobinaRunning = false ,isBilirrubinaRunning = false ,isBloodPressureRunning = false;
	private Behaviour checkTemperature = null, checkHemoglobina = null, checkBilirrubina = null, checkBloodPressure = null;
	private static final int TIME_INTERVAL_MEASURE = 2000;
	
	/*
	 * TODO LIST
	 * TODO Todas as requisiÃ§Ãµes para informar temperatura, hemoblobina, pressao e etc devem ser repassadas ao paciente
	 * TODO Todas as mensagens que o monitor receber do paciente , devem ser repassadas Ipsis litteris para o centralizador
	 * 
	 * */
	protected void setup() {

		// Regista o monitor no servico de paginas amarelas
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("monitor");
		sd.setName(getName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				// blockingReceive();
				ACLmsg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

				if (ACLmsg != null) {
					String mensagem = ACLmsg.getContent().toString();
					AID sender = ACLmsg.getSender();
					if (sender.getLocalName().equals("centralizador")) {
						try {
							TarefaCentralizador tc = GrammarParserCentralizador.getCentralizadorMessageObject(mensagem);
							if (tc.getAcao() instanceof ECollect1) { // veja na gramatica o que significa ECollect1
								//System.out.print(getLocalName() + ": Iniciando a medicao");
								List<Object> dados = tc.getDados();
								for (Object o : dados) {
									if (o instanceof EData1) { // TEMPERATURE
										//System.out.print(" Temperatura");
										if (!isTempRunning) {
											checkTemperature = new InformTempBehaviour(myAgent, ACLmsg);
											addBehaviour(checkTemperature);
											isTempRunning = true;
										} else
											System.out.print(getLocalName() + " ja esta monitorando temperatura.\n");
									} else if (o instanceof EData2) { // HEMOGLOBINA
										//System.out.print(" Hemoglobina");
										//if (!isHemoglobinaRunning) {
											checkHemoglobina = new InformHemoglobinaBehaviour(myAgent, ACLmsg);
											addBehaviour(checkHemoglobina);
											isHemoglobinaRunning = true;
										//} else
											//System.out.print(getLocalName() + " ja esta monitorando Hemoglobina.\n");
									} else if (o instanceof EData3) { // BILIRRUBINA
										//System.out.print(" Bilirrubina");
										if (!isBilirrubinaRunning) {
											checkBilirrubina = new InformBilirrubinaBehaviour(myAgent, ACLmsg);
											addBehaviour(checkBilirrubina);
											isBilirrubinaRunning = true;
										} else
											System.out.println(getLocalName() + " ja esta monitorando Bilirrubina.\n");
									} else if (o instanceof EData4) { // PRESSAO ARTERIAL
										//System.out.print(" Pressao Arterial");
										if (!isBloodPressureRunning) {
											checkBloodPressure = new InformBilirrubinaBehaviour(myAgent, ACLmsg);
											addBehaviour(checkBloodPressure);
											isBloodPressureRunning = true;
										} else
											System.out .println(getLocalName() + " ja esta monitorando Pressao Arterial.\n");
									}
								} //System.out.println("");
							} else if (tc.getAcao() instanceof ECollect2) {
								//System.out.print(getLocalName() + ": Parando medicao");
								List<Object> dados = tc.getDados();
								for (Object o : dados) {
									if (o instanceof EData1) {
										//System.out.print(" Temperatura\n");
										if (isTempRunning) {
											removeBehaviour(checkTemperature);
											isTempRunning = false;
										} else
											System.out.println(getLocalName() + " nao esta monitorando Temperatura.");
									} else if (o instanceof EData2) {
										//System.out.print(" Hemoglobina\n");
										if (isHemoglobinaRunning) {
											removeBehaviour(checkHemoglobina);
											isHemoglobinaRunning = false;
										} else
											System.out.println(getLocalName() + " nao esta monitorando Hemoglobina.");
									} else if (o instanceof EData3) {
										//System.out.print(" bilirrubina\n");
										if (isBilirrubinaRunning) {
											removeBehaviour(checkBilirrubina);
											isBilirrubinaRunning = false;
										} else
											System.out.println(getLocalName() + " nao esta monitorando Bilirrubina.");
									} else if (o instanceof EData4) {
										//System.out.print(" Pressao Arterial\n");
										if (isBloodPressureRunning) {
											removeBehaviour(checkBloodPressure);
											isBloodPressureRunning = false;
										} else
											System.out.println(getLocalName() + " nao esta monitorando Pressao Arterial.");
									}
								}

							} else if (tc.getAcao() instanceof EAction3) {
								System.out.println(getLocalName() + " : Mensagem de autodestruicao recebida.");
								this.myAgent.doDelete();
							} else System.out.println(getLocalName() + ": Comando invalido para monitor : " + mensagem);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else { 
						//System.out.println("Entao veio do paciente: " + mensagem);
						try {
							TarefaMonitor mensagemRecebida = GrammarParserMonitor.getMonitorMessageObject(mensagem);
							TarefaMonitor mensagemAEnviar = new TarefaMonitor();
							for (Afericao af : mensagemRecebida.getAfericoes()) {
								mensagemAEnviar.setAfericoes(af);
							}
							sendInformMessageToAgent(mensagemAEnviar.prettyPrinter(), "centralizador");							
							System.out.println(getLocalName() + ": " + mensagemAEnviar.prettyPrinter());
						} catch (Exception e) {
							System.out.println(getName()
											+ ": Mensagem nao pertence a gramatica do atuador: "
											+ mensagem + ". Erro: " + e.getMessage());
						}
					}
				}
			}
		});
	}

	
	// MONITOR informs to Centralizador a given temperature at each 2s
	class InformTempBehaviour extends TickerBehaviour {
		
		private static final long serialVersionUID = 1L;
		private ACLMessage aclMessage;

		public InformTempBehaviour(Agent a, ACLMessage aclMessage) {
			super(a, TIME_INTERVAL_MEASURE);
			this.aclMessage = aclMessage;
			//System.out.println("Monitor: aclMessage********************************" + aclMessage);
		}

		@Override
		protected void onTick() {
			//System.out.println(getLocalName() + ": Enviando ao paciente : " + aclMessage.getContent());
			sendRequestMessageToAgent(aclMessage.getContent(), "paciente");
			//sendMsgtoCentralizador(aclMessage, resposta);
		}
	}

	
	// MONITOR informs to Centralizador a given Hemoglobina at each 2s
	class InformHemoglobinaBehaviour extends TickerBehaviour {
		private static final long serialVersionUID = 1L;
		private ACLMessage aclMessage;

		public InformHemoglobinaBehaviour(Agent a, ACLMessage aclMessage) {
			super(a, TIME_INTERVAL_MEASURE);
			this.aclMessage = aclMessage;
			//System.out.println("Monitor: aclMessage********************************" + aclMessage);
		}

		@Override
		protected void onTick() {
			sendRequestMessageToAgent(aclMessage.getContent(), "paciente");
		}
	}

	
	// MONITOR informs to Centralizador a given Bilirrubina at each 2s
	class InformBilirrubinaBehaviour extends TickerBehaviour {
		private static final long serialVersionUID = 1L;
		private ACLMessage aclMessage;

		public InformBilirrubinaBehaviour(Agent a, ACLMessage aclMessage) {
			super(a, TIME_INTERVAL_MEASURE);
			this.aclMessage = aclMessage;
			//System.out.println("Monitor: aclMessage********************************" + aclMessage);
		}

		@Override
		protected void onTick() {
			sendRequestMessageToAgent(aclMessage.getContent(), "paciente");
		}
	}

	
	// MONITOR informs to Centralizador a given PRESSAO ARTERIAL at each 2s
	class InformBloodPressureBehaviour extends TickerBehaviour {
		private static final long serialVersionUID = 1L;
		private ACLMessage aclMessage;

		public InformBloodPressureBehaviour(Agent a, ACLMessage aclMessage) {
			super(a, TIME_INTERVAL_MEASURE);
			this.aclMessage = aclMessage;
			//System.out.println("Monitor: aclMessage********************************" + aclMessage);
		}

		@Override
		protected void onTick() {
			sendRequestMessageToAgent(aclMessage.getContent(), "paciente");
		}
	}
	
	private void sendInformMessageToAgent(String mensagem, String agentName) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		AID agentid = new AID(agentName, AID.ISLOCALNAME);
		message.addReceiver(agentid);
		message.setContent(mensagem);
		//System.out.println(getLocalName() + ": Enviei mensagem para " + agentName + " : " + mensagem);
		send(message);
	}
	
	private void sendRequestMessageToAgent(String mensagem, String agentName) {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(new AID(agentName, AID.ISLOCALNAME));
		message.setContent(mensagem);
		//System.out.println(getLocalName() + ": Enviei mensagem para " + agentName + " : " + mensagem);
		send(message);
	}

//	private void sendMsgtoCentralizador(ACLMessage msg, String msgToSend) {
//		ACLMessage replica = msg.createReply();
//		replica.setPerformative(ACLMessage.INFORM);
//		replica.setContent(msgToSend);
//		send(replica);
//
//	}
}
