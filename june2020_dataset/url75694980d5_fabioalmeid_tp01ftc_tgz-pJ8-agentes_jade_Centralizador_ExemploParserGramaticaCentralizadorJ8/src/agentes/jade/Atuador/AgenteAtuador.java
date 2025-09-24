package agentes.jade.Atuador;

import gramatica.Centralizador.Absyn.EAction3;
import gramatica.Centralizador.Absyn.EApply1;
import gramatica.Centralizador.Absyn.EApply2;
import gramatica.Centralizador.Absyn.ERemedy1;
import gramatica.Centralizador.Absyn.ERemedy2;
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
import agentes.jade.Centralizador.Medicamento;
import agentes.jade.Centralizador.TarefaCentralizador;

public class AgenteAtuador extends Agent {

	private static final int INTERVAL_FREQUENCY = 2000;

	private ACLMessage ACLmsg;
	private Boolean isDipironaRunning = false, isParacetamolRunning = false;
	private Behaviour checkDipirona = null, checkParacetamol = null;

	protected void setup() {

		// Regista o monitor no servico de paginas amarelas
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("atuador");
		sd.setName(getName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

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
							if (tc.getAcao() instanceof EApply1) {
								StringBuilder temp = new StringBuilder();
								temp.append(getLocalName() + ": Liberar Remedio");
								List<Medicamento> med = tc.getMedicacao();
								for (Medicamento m : med) {
									if (m.remedio instanceof ERemedy1) {
										temp.append(" Dipirona");
										System.out.println(temp.toString());
										//if (!isDipironaRunning) {
											checkDipirona = new ApplyDipironaBehaviour(myAgent, ACLmsg);
											addBehaviour(checkDipirona);
											isDipironaRunning = true;
										//} else
											//System.out.println(getLocalName() + " ja esta liberando Dipirona.");
									} else if (m.remedio instanceof ERemedy2) {
										temp.append(" Paracetamol");
										System.out.println(temp.toString());
										//if (!isParacetamolRunning) {
											checkParacetamol = new ApplyParacetamolBehaviour(myAgent, ACLmsg);
											addBehaviour(checkParacetamol);
											isParacetamolRunning = true;
										//} else
											//System.out.println(getLocalName() + " ja esta liberando Paracetamol.");
									} else System.out.println(getLocalName() + ": Comando invalido para atuador : " + mensagem);
								}
							} else if (tc.getAcao() instanceof EApply2) {
								StringBuilder temp = new StringBuilder();
								temp.append(getLocalName() + ": Cessar Liberacao");
								List<Medicamento> med = tc.getMedicacao();
								for (Medicamento m : med) {
									if (m.remedio instanceof ERemedy1) {
										temp.append(" Dipirona");
										System.out.println(temp.toString());
										//if (isDipironaRunning) {
											removeBehaviour(checkDipirona);
										//} else
											//System.out.println(getLocalName() + " nao esta liberando Dipirona.");
									} else if (m.remedio instanceof ERemedy2) {
										temp.append(" Paracetamol");
										System.out.println(temp.toString());
										//if (isParacetamolRunning) {
											removeBehaviour(checkParacetamol);
										//} else
											//System.out.println(getLocalName() + " nao esta liberando Paracetamol.");
									}
								}

							} else if (tc.getAcao() instanceof EAction3) {
								System.out.println(getLocalName() + " : Mensagem de autodestruicao recebida.");
								this.myAgent.doDelete();
							} else 
								System.out.println(getLocalName() + ": Comando invalido para atuador : " + mensagem);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}else {
						//System.out.println(getLocalName() + ": Recebi mensagem do PACIENTE!!!!");
						// mensagem so pode ser do paciente
						try {
							TarefaAtuador mensagemRecebida = GrammarParserAtuador.getAtuadorMessageObject(mensagem);
							TarefaAtuador mensagemAEnviar = new TarefaAtuador();
							mensagemAEnviar.setAcao(mensagemRecebida.getAcao());
							mensagemAEnviar.setRemedio(mensagemRecebida.getRemedio());
							sendInformMessageToAgent(mensagemAEnviar.prettyPrinterAction(), "centralizador");							
							//System.out.println(getLocalName() + ": Respondendo centralizador com " + mensagemAEnviar.prettyPrinterAction());
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

	class ApplyDipironaBehaviour extends TickerBehaviour {
		
		private static final long serialVersionUID = 1L;
		private ACLMessage aclMessage;

		public ApplyDipironaBehaviour(Agent a, ACLMessage aclMessage) {
			super(a, INTERVAL_FREQUENCY);
			
			//System.out.println(getLocalName() + ": ApplyDipironaBehaviour acionado");
			this.aclMessage = aclMessage;
		}

		@Override
		protected void onTick() {
			//System.out.println(getLocalName() + ": " + aclMessage.getContent());
			sendRequestMessageToAgent(aclMessage.getContent(), "paciente");			
		}

	}

	class ApplyParacetamolBehaviour extends TickerBehaviour {

		private ACLMessage aclMessage;

		public ApplyParacetamolBehaviour(Agent a, ACLMessage aclMessage) {
			super(a, INTERVAL_FREQUENCY);
			//System.out.println(getLocalName() + ": ApplyParacetamolBehaviour acionado");
			this.aclMessage = aclMessage;
		}

		@Override
		protected void onTick() {
			//System.out.println(getLocalName() + ": " + aclMessage.getContent());
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
}
