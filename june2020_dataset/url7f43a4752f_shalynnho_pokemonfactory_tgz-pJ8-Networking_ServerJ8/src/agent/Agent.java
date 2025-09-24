package agent;

import java.util.concurrent.Semaphore;

import DeviceGraphics.DeviceGraphics;
import Utils.ConsoleWriter;
import Utils.StringUtil;

/** Base class for simple agents */
public abstract class Agent {
	Semaphore stateChange = new Semaphore(1, true);// binary semaphore, fair
	private AgentThread agentThread;
	ConsoleWriter console;

	protected Agent() {
	}

	/**
	 * This should be called whenever state has changed that might cause the agent to do something.
	 */
	protected void stateChanged() {
		stateChange.release();
	}

	/**
	 * Agents must implement this scheduler to perform any actions appropriate for the current state. Will be called
	 * whenever a state change has occurred, and will be called repeated as long as it returns true.
	 * 
	 * @return true iff some action was executed that might have changed the state.
	 */
	public abstract boolean pickAndExecuteAnAction();

	/**
	 * Return agent name for messages. Default is to return java instance name.
	 */
	protected String getName() {
		return StringUtil.shortName(this);
	}

	/** The simulated action code */
	protected void Do(String msg) {
		print(msg, null);
	}

	/** Print message */
	protected void print(String msg) {
		print(msg, null);
	}

	/** Print message with exception stack trace */
	protected void print(String msg, Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append("[Agent]");
		sb.append(getName());
		sb.append(": ");
		sb.append(msg);
		sb.append("\n");
		if (e != null) {
			sb.append(StringUtil.stackTraceString(e));
		}

		// if (this.getClass() != agent.FCSAgent.class // && this.getClass() != agent.StandAgent.class
		// && this.getClass() != agent.KitRobotAgent.class && this.getClass() != agent.ConveyorAgent.class
		// && this.getClass() != agent.LaneAgent.class && this.getClass() != agent.GantryAgent.class
		// && this.getClass() != agent.CameraAgent.class */&& this.getClass() != agent.NestAgent.class
		// && this.getClass() != agent.FeederAgent.class // && this.getClass() != agent.PartsRobotAgent.class
		// && this.getClass() != agent.test.mock.MockGraphics.class) {
		System.out.print(sb.toString());
		// }

		if (console != null) {
			console.sendMessage(getName(), msg);
		}
	}

	/**
	 * Sets the graphical representation of this agent.
	 * 
	 * @param dg
	 */
	public abstract void setGraphicalRepresentation(DeviceGraphics dg);

	public void setConsoleWriter(ConsoleWriter cw) {
		console = cw;
	}

	/** Start agent scheduler thread. Should be called once at init time. */
	public synchronized void startThread() {
		print("Thread started.");
		if (agentThread == null) {
			agentThread = new AgentThread(getName());
			agentThread.start(); // causes the run method to execute in the
								 // AgentThread below
		} else {
			agentThread.interrupt();// don't worry about this for now
		}
	}

	/** Stop agent scheduler thread. */
	// In this implementation, nothing calls stopThread().
	// When we have a user interface to agents, this can be called.
	public void stopThread() {
		print("Thread stopped");
		if (agentThread != null) {
			agentThread.stopAgent();
			agentThread = null;
		}
	}

	/**
	 * Agent scheduler thread, calls respondToStateChange() whenever a state change has been signalled.
	 */
	private class AgentThread extends Thread {
		private volatile boolean goOn = false;

		private AgentThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			goOn = true;

			while (goOn) {
				try {
					// The agent sleeps here until someone calls,
					// stateChanged(),
					// which causes a call to stateChange.give(), which wakes up
					// agent.
					stateChange.acquire();
					// The next while clause is the key to the control flow.
					// When the agent wakes up it will call
					// respondToStateChange()
					// repeatedly until it returns FALSE.
					// You will see that pickAndExecuteAnAction() is the agent
					// scheduler.
					// print("In scheduler.");
					while (pickAndExecuteAnAction()) {
						;
					}
				} catch (InterruptedException e) {
					// no action - expected when stopping or when deadline
					// changed
				} catch (Exception e) {
					print("Unexpected exception caught in Agent thread: ", e);
				}
			}
		}

		private void stopAgent() {
			goOn = false;
			this.interrupt();
		}
	}
}
