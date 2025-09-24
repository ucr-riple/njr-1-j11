package util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.ArrayList;
import java.util.List;

public class UpdateAgentList {

	// private static List<AID> monitor;
	// private static List<AID> atuador;

	public static List<AID> getAgentUpdatedList(String agentType, Agent myAgent) {
		
		List<AID> agentes;

		// Atualiza lista de agentes
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(agentType);
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			agentes = new ArrayList<AID>();
			for (int i = 0; i < result.length; ++i) {
				agentes.add(result[i].getName());
			}
			return agentes;
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		return null;
	}
}
