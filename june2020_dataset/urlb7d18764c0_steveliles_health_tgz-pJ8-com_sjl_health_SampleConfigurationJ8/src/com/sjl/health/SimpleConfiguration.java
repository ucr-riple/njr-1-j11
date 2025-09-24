package com.sjl.health;

import java.util.*;

import com.sjl.health.Transitions.TransitionBuilder;
import com.sjl.health.internal.*;

public class SimpleConfiguration implements Configuration {

	private interface StateFactory {		
		public State newState(Issue aCause, IssueTracker anIssues, Clock aClock);	
	}
	
	private final TransitionBuilder[] builders;
	private StateFactory initial;
	private Map<String, StateFactory> states;
	private Clock clock;
	private IssueTrackerFactory issuesFactory;
	
	public SimpleConfiguration(
		TransitionBuilder... aBuilders) {
		builders = aBuilders;
	}
	
	public void init(IssueTrackerFactory anIssueTrackerFactory, Clock aClock) {
		states = new HashMap<String, StateFactory>();
		clock = aClock;
		issuesFactory = anIssueTrackerFactory;
		
		TransitionPairs _pairs = initialiseTransitionPairs();
		
		final TransitionPair _top = 
			_pairs.getInitialStateTransitionPair();
		
		initial = new StateFactory() {
			public State newState(Issue anIssue, IssueTracker anIssues, Clock aClock) {
				return new MutableState(
					_top.getName(), anIssue, anIssues, _top.getPromotion(), _top.getDemotion(), aClock);
			}			
		};
		
		initialiseHealthStates(_pairs);
	}
	
	private TransitionPairs initialiseTransitionPairs() {
		TransitionPairs _pairs = new TransitionPairs();
		for (TransitionBuilder _b : builders) {
			if (_b.isDemotion()) {
				_pairs.addDemoter(_b.getFrom(), _b.build(this));
			} else {
				_pairs.addPromoter(_b.getFrom(), _b.build(this));
			}
		}
		
		_pairs.validate();
		return _pairs;
	}

	private void initialiseHealthStates(TransitionPairs aTransitionPairs) {
		for (final TransitionPair _tp : aTransitionPairs) {
			states.put(_tp.getName(), new StateFactory() {
				public State newState(Issue aCause, IssueTracker anIssues, Clock aClock) {
					return new MutableState(
						_tp.getName(), aCause, anIssues, _tp.getPromotion(), _tp.getDemotion(), aClock);
				}
				public String toString() {
					return "+" + _tp.getPromotion() + "/-" + _tp.getDemotion(); 
				}
			});
		}
	}
	
	@Override
	public String toString() {
		StringBuilder _sb = new StringBuilder();
		for (Map.Entry<String, StateFactory> _e : states.entrySet()) {
			_sb.append(_e.getKey());
			_sb.append("\r\n  ");
			_sb.append(_e.getValue());
			_sb.append("\r\n");
		}
		return _sb.toString();
	}
	
	@Override
	public State newInitialState(Issue aCause) {
		return initial.newState(aCause, issuesFactory.newTracker(), clock);
	}

	@Override
	public State newStateInstance(String aStateName, Issue aCause) {
		StateFactory _factory = states.get(aStateName); 
		if (_factory == null)
			throw new RuntimeException("unknown state: " + aStateName);
		
		return _factory.newState(aCause, issuesFactory.newTracker(), clock);
	}
	
	private class TransitionPair {
		private String name;
		private Transition promotion;
		private Transition demotion;
		
		public TransitionPair(String aName) {
			name = aName;
		}
		
		public String getName() {
			return name;
		}
		
		public void setPromotion(Transition aPromo) {
			promotion = aPromo;
		}
		
		public Transition getPromotion() {
			return promotion;
		}
		
		public void setDemotion(Transition aDemo) {
			demotion = aDemo;
		}
		
		public Transition getDemotion() {
			return demotion;
		}
		
		public boolean isDemotionOnly() {
			return promotion == null && demotion != null;
		}
	}
	
	private class TransitionPairs implements Iterable<TransitionPair> {
		private Map<String, TransitionPair> pairs = new HashMap<String, TransitionPair>();
		
		public void addPromoter(String aState, Transition aTransition) {
			TransitionPair _pair = get(aState);
			_pair.setPromotion(aTransition);
		}
		
		public void addDemoter(String aState, Transition aTransition) {
			TransitionPair _pair = get(aState);
			_pair.setDemotion(aTransition);
		}
		
		public void validate() {
			// make sure there's only one unmatched top-pair and one unmatched bottom-pair
		}
		
		public TransitionPair getInitialStateTransitionPair() {
			for (Map.Entry<String, TransitionPair> _e : pairs.entrySet()) {
				if (_e.getValue().isDemotionOnly())
					return _e.getValue();
			}
			throw new RuntimeException("No demotion-only pair found!");
		}
		
		public TransitionPair get(String aState) {
			TransitionPair _p = pairs.get(aState);
			if (_p == null) {
				_p = new TransitionPair(aState);
				pairs.put(aState, _p);
			}
			return _p;
		}
		
		public Iterator<TransitionPair> iterator() {
			return pairs.values().iterator();
		}
	}
}
