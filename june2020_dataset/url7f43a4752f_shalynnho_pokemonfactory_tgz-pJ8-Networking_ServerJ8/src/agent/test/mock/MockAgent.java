/**
 * 
 */
package agent.test.mock;

/**
 * This is the base class for a mock agent. It only defines that an agent should
 * contain a name.
 * @author Sean Turner
 */
public class MockAgent {
	private final String name;
	private final EventLog log;

	public MockAgent(String name, EventLog log) {
		this.name = name;
		this.log = log;
	}

	public String getName() {
		return name;
	}

	public EventLog getLog() {
		return log;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + ": " + name;
	}

}
