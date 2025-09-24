package logfilegen.allgenerators.record;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logfilegen.allmodels.record.Status;

public class StatusGen {
	private Random random = new Random();
	private List<String> state = new ArrayList<String>();
	
	public StatusGen(){
		state.add("200");
		state.add("201");
		state.add("202");
		state.add("204");
		state.add("302");
		state.add("303");
		state.add("304");
		state.add("307");
		state.add("400");
		state.add("401");
		state.add("402");
		state.add("403");
		state.add("404");
		state.add("408");
		state.add("410");
		state.add("415");
		state.add("423");
		state.add("429");
		state.add("434");
		state.add("456");
		state.add("500");
		state.add("501");
		state.add("503");
		state.add("504");
		state.add("505");
	}
	public Status generate(){
		int randomIndex = random.nextInt(state.size());
		return new Status(state.get(randomIndex));
	}
}
