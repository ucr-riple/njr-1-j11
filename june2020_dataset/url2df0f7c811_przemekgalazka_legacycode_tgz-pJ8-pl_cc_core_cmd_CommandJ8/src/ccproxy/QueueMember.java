package ccproxy;

public class QueueMember {
	AgentConnection ac;
	String penalty;
	String status; //status agenta w tej kolejce
	boolean paused; //status pauzy

	public QueueMember(AgentConnection ac, String penalty) {
		super();
		this.ac = ac;
		this.penalty = penalty;
		this.status="NOT_INUSE";
		this.paused=false;
	}

}
