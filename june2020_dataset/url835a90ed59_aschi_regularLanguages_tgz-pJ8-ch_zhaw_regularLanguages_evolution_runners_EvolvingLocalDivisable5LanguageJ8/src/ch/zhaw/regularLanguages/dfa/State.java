package ch.zhaw.regularLanguages.dfa;


public class State implements Comparable<State> {
	private String id;
	private TransitionTable tt;
	
	public State(String id, TransitionTable tt){
		this.id = id;
		this.tt = tt;
	}
	
	public State(String id){
		this.id = id;
	}
	
	public TransitionTable getTransitionTable(){
		return tt;
	}
	
	public void setTransitionTable(TransitionTable tt){
		this.tt = tt;
	}
	
	public State process(Character character){
		return tt.process(character);
	}
	
	public String getId(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof State){
			State s = (State)o;
			if(s.getId().equals(this.getId())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return getId().hashCode();
	}
	public String toString(){
		return id;
	}

	@Override
	public int compareTo(State o) {
		return id.compareTo(o.getId());
	}
}