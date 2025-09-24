package clarion.samples;

import java.io.OutputStream;

import clarion.system.*;
import clarion.tools.BaseSimulationTemplate;

public class Tutorial implements BaseSimulationTemplate {
	
	public OutputStream Reporter = System.out;
	
	public Tutorial ()
	{
		// TODO Print Overview Notes (from class javadoc documentation above)
	}
	
	public Tutorial (OutputStream reporter)
	{
		Reporter = reporter;
		// TODO Print Overview Notes (from class javadoc documentation above)
	}
	
	public void initializeAgentInternalSpace() {
		// TODO Print Internal Space Intialization Notes (from method javadoc documentation above)
		
	}

	public void initializeCLARIONAgent(CLARION Agent) {
		// TODO Print Agent Initialization Notes (from method javadoc documentation above)

	}

	public void initializeSensoryInformationSpace() {
		// TODO Print Sensory Information Space Initialization Notes (from method javadoc documentation above)

	}

	public void reportResults() {
		// TODO Print Report Results Notes (from method javadoc documentation above)

	}

	public void run(CLARION Agent) {
		// TODO Run Notes (from method javadoc documentation above)

	}

}
