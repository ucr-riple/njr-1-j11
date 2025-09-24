package ccproxy;

public class AsteriskQueue {
	String nazwa;
	String ilosc;
	// AgentConnectionList agenci = new AgentConnectionList();
	QueueMemberList agenci = new QueueMemberList();
	
	/**
	 * Określa czy to kolejka specjalna, np.: IVR
	 */
	String customType = null;

	public AsteriskQueue(String qname, String ilosc) {
		this.nazwa = new String(qname);
		this.ilosc = new String(ilosc);
	}

	public AsteriskQueue(String qname, String ilosc, String customType) {
		this(qname, ilosc);
		this.customType = customType;
	}

	
}
