package agentes.jade.Paciente;

import gramatica.Atuador.Absyn.Acao;
import gramatica.Atuador.Absyn.EAcao;
import gramatica.Atuador.Absyn.EAcao1;
import gramatica.Atuador.Absyn.ERemedio;
import gramatica.Atuador.Absyn.ERemedio1;
import gramatica.Atuador.Absyn.Remedio;
import gramatica.Centralizador.Absyn.EApply1;
import gramatica.Centralizador.Absyn.EApply2;
import gramatica.Centralizador.Absyn.ECollect1;
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
import gramatica.Monitor.Absyn.EQuantidade;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

import util.UpdateAgentList;

import agentes.jade.Atuador.TarefaAtuador;
import agentes.jade.Centralizador.GrammarParserCentralizador;
import agentes.jade.Centralizador.Medicamento;
import agentes.jade.Centralizador.TarefaCentralizador;
import agentes.jade.Monitor.Afericao;
import agentes.jade.Monitor.TarefaMonitor;

public class AgentePaciente extends Agent {
	private static final long INTERVALO_AGENTES = 5000; // 5s
	private static final long INTERVALO_ATUALIZACAO = 6000; // 10s
//	private static final int MAX_TICKS_UNCHANGED = 5;
	private static final int MAX_TICKS_UNCHANGED = 2;
	private List<AID> monitor;
	private List<AID> atuador;
	
	protected void setup() {
		// Regista o paciente no servico de paginas amarelas
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("paciente");
		sd.setName(getName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		// atualiza lista de monitores e atuadores
		addBehaviour(new UpdateAgentsTempBehaviour(this, INTERVALO_AGENTES));
		
		// ouve requisicoes dos atuadores e monitores
		addBehaviour(new ListenBehaviour());
		
		// adiciona comportamento de mudanca de temperatura
		addBehaviour(new UpdateTemperatureBehaviour(this, INTERVALO_ATUALIZACAO));
		
		// adiciona comportamento de mudanca de hemoglobina		
		addBehaviour(new UpdateHemoglobinaBehaviour(this, INTERVALO_ATUALIZACAO));
		
		// adiciona comportamento de mudanca de bilirrubina
		addBehaviour(new UpdateBilirrubinaBehaviour(this, INTERVALO_ATUALIZACAO));

		// adiciona comportamento de mudanca de pressao		
		addBehaviour(new UpdatePressaoBehaviour(this, INTERVALO_ATUALIZACAO));
		
	}
	
	class ListenBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage ACLmsg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

			if (ACLmsg != null) {
				String mensagem = ACLmsg.getContent().toString();
				AID sender = ACLmsg.getSender();
				String resposta = "";
				
				// se o sender for monitor
				if (monitor.contains(sender)) {
					try {
						TarefaCentralizador tc = GrammarParserCentralizador.getCentralizadorMessageObject(mensagem);
						if (tc.getAcao() instanceof ECollect1) { // ECollect1. Coletar ::= "Iniciar Medicao";
							List<Object> dados = tc.getDados();
							ArrayList<Afericao> afericoes = new ArrayList<Afericao>();
							for (Object o : dados) {
								if (o instanceof EData1) { // "Temperatura"
									Afericao af = new Afericao();
									EQuantidade q = new EQuantidade(Paciente.getTemperatura());
									af.setDado(new EDados(q));
									af.setQuantidade1(Paciente.getTemperatura());
									af.setCurrentHora();
									afericoes.add(af);
								} else if (o instanceof EData2) { // "Hemoglobina"
									Afericao af = new Afericao();
									EQuantidade q = new EQuantidade(Paciente.getHemoglobina());
									af.setDado(new EDados2(q));
									af.setQuantidade1(Paciente.getHemoglobina());
									af.setCurrentHora();
									afericoes.add(af);
								}
								else if (o instanceof EData3) { // "bilirrubina"
									Afericao af = new Afericao();
									EQuantidade q = new EQuantidade(Paciente.getBilirrubina());
									af.setDado(new EDados1(q));
									af.setQuantidade1(Paciente.getBilirrubina());
									af.setCurrentHora();
									afericoes.add(af);
								}
								else if (o instanceof EData4) { // "Pressao Arterial"
									Afericao af = new Afericao();
									EQuantidade q1 = new EQuantidade(Paciente.getPressaoSist());
									EQuantidade q2 = new EQuantidade(Paciente.getPressaoDiast());
									af.setDado(new EDados3(q1,q2));
									af.setQuantidade1(q1.integer_);
									af.setQuantidade2(q2.integer_);
									af.setCurrentHora();
									afericoes.add(af);
								}
							}
							TarefaMonitor tm = new TarefaMonitor();
							tm.setAfericoes(afericoes);
							resposta = tm.prettyPrinter();
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				} else if (atuador.contains(sender)) {
					TarefaAtuador ta = new TarefaAtuador();
					Remedio remedio  = null;
					Acao acao        = null;
					
					try {
						TarefaCentralizador tc = GrammarParserCentralizador.getCentralizadorMessageObject(mensagem);
						if (tc.getAcao() instanceof EApply1) { // Aplicar
							List<Medicamento> med = tc.getMedicacao();
							
							for (Medicamento m : med) {
								if (m.remedio instanceof ERemedy1) {
									Paciente.setRemedioTemp(true); // Dipirona cures Temperatura and Hemoglobina
									Paciente.setRemedioHemoglobina(true);
									//System.out.println(getLocalName() + ": Recebi medicacao Dipirona");
									remedio = new ERemedio();
									acao = new EAcao(remedio);
									ta.setAcao(acao);
									ta.setRemedio(remedio);
								} else if (m.remedio instanceof ERemedy2) {
									// Paracetamol cures Bilirrubina and Pressao Arterial
									Paciente.setRemedioBilirrubuna(true);
									Paciente.setRemedioPressao(true);
									//System.out.println(getLocalName() + ": Recebi medicacao Paracetamol");
									remedio = new ERemedio1();
									acao = new EAcao(remedio);
									ta.setAcao(acao);
									ta.setRemedio(remedio);
								}
							}
							resposta = ta.prettyPrinterAction();
						} else if (tc.getAcao() instanceof EApply2) {
							// Parando medicao
							List<Medicamento> med = tc.getMedicacao();
							for (Medicamento m : med) {
								if (m.remedio instanceof ERemedy1) { // Dipirona
									Paciente.setRemedioTemp(false);
									Paciente.setRemedioHemoglobina(false);
									//System.out.println(getLocalName() + ": Recebi cessar Dipirona");
									remedio = new ERemedio();
									acao = new EAcao1(remedio);
									ta.setAcao(acao);
									ta.setRemedio(remedio);
								} else if (m.remedio instanceof ERemedy2) { // Paracetamol
									// Paracetamol cures Bilirrubina and Pressao Arterial
									Paciente.setRemedioBilirrubuna(false);
									Paciente.setRemedioPressao(false);
									//System.out.println(getLocalName() + ": Recebi cessar Pacacetamol");
									remedio = new ERemedio1();
									acao = new EAcao1(remedio);
									ta.setAcao(acao);
									ta.setRemedio(remedio);
								}
							}
						} else System.out.println(getLocalName() + ": Comando invalido para atuador : " + mensagem);
						resposta = ta.prettyPrinterAction();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				
				sendMessageToAgent(resposta, ACLmsg.getSender().getLocalName());
			}
		}		
	}
	
	class UpdateTemperatureBehaviour extends TickerBehaviour {
		int ticks = 0;
		
		public UpdateTemperatureBehaviour(Agent a, long period) {
			super(a, period);
		}
		
		private Integer randomTemperature(){
			int Min = 36, Max = 41;
			return Min + (int)(Math.random() * ((Max - Min) + 1));
		}

		@Override
		protected void onTick() {
			if (Paciente.getRemedioTemp()) { // tem remedio para temperatura
				// decrementa temperatura a cada ticks
				Paciente.setTemperatura(Paciente.getTemperatura()-1);
				ticks = 0;				
			} else {
				ticks++;
				if (ticks >= MAX_TICKS_UNCHANGED) {
					Paciente.setTemperatura(randomTemperature());
					ticks = 0;
				}
			}			
		}
	}
	
	int ticks = 0;
	class UpdateHemoglobinaBehaviour extends TickerBehaviour {
		
		public UpdateHemoglobinaBehaviour(Agent a, long period) {
			super(a, period);
		}
		
		private Integer randomHemoglobina(){
			// 13,5 - 18 bom
			// abaixo 12.5 anemia, acima de 18 doente
			int Min = 9, Max = 23;
			return Min + (int)(Math.random() * ((Max - Min) + 1));
		}

		@Override
		protected void onTick() {
			if (Paciente.getRemedioHemoglobina()) { // tem remedio para temperatura
				// decrementa temperatura a cada ticks
				Paciente.setHemoglobina(Paciente.getHemoglobinaBoa());
				ticks = 0;				
			} else {
				ticks++;
				if (ticks >= MAX_TICKS_UNCHANGED) {
					Paciente.setHemoglobina(randomHemoglobina());
					ticks = 0;
				}
			}
			
		}
	}
	
	class UpdateBilirrubinaBehaviour extends TickerBehaviour {
		int ticks = 0;
		
		public UpdateBilirrubinaBehaviour(Agent a, long period) {
			super(a, period);
		}
		
		private Integer randomBilirrubina(){
			// 0 - 10 bom
			// acima de 10 doente
			int Min = 0, Max = 14;
			return Min + (int)(Math.random() * ((Max - Min) + 1));
		}

		@Override
		protected void onTick() {
			if (Paciente.getRemedioBilirrubuna()) { // tem remedio para temperatura
				// decrementa temperatura a cada ticks
				Paciente.setBilirrubina(Paciente.getBilirrubina()-1);
				ticks = 0;				
			} else {
				ticks++;
				if (ticks >= MAX_TICKS_UNCHANGED) {
					Paciente.setHemoglobina(randomBilirrubina());
					ticks = 0;
				}
			}
			
		}
	}
	
	class UpdatePressaoBehaviour extends TickerBehaviour {
		int ticks = 0;
		
		public UpdatePressaoBehaviour(Agent a, long period) {
			super(a, period);
		}

		@Override
		protected void onTick() {
			if (Paciente.getRemedioPressao()) { // tem remedio para temperatura
				// decrementa temperatura a cada ticks
				PressaoArterial.setPressaoBoa();
				ticks = 0;				
			} else {
				ticks++;
				if (ticks >= MAX_TICKS_UNCHANGED) {
					PressaoArterial.setPressaoRandom();
					ticks = 0;
				}
			}
			
		}
	}
	
	// classe que atualiza os registos dos monitores e atuadores
	class UpdateAgentsTempBehaviour extends TickerBehaviour {

		public UpdateAgentsTempBehaviour(Agent a, long period) {
			super(a, period);
		}

		@Override
		protected void onTick() {
			monitor = UpdateAgentList.getAgentUpdatedList("monitor", myAgent);
//			System.out.print(getLocalName() + ": Achei os seguintes monitores:");
//			for (AID m : monitor) {
//				System.out.print(" | " + m.getLocalName());
//			}
//			System.out.println();
			
			atuador = UpdateAgentList.getAgentUpdatedList("atuador", myAgent);
//			System.out.print(getLocalName() + ": Achei os seguintes atuadores:");
//			for (AID a : atuador) {
//				System.out.print(" | " + a.getLocalName());
//			}
//			System.out.println();
		}
	}
	
	private void sendMessageToAgent(String mensagem, String agentName) {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(new AID(agentName, AID.ISLOCALNAME));
		message.setContent(mensagem);
		//System.out.println(getLocalName() + ": Enviei mensagem para " + agentName + " : " + mensagem);
		send(message);
	}
}

