package controllers.momcts.core;

import java.util.HashMap;
import java.util.Vector;

import controllers.momcts.utils.Logger;
import controllers.momcts.utils.Presentation;
import controllers.momcts.utils.SetOperation;
import controllers.momcts.utils.Transformation;


/**
 * This planner is an instantiation of the abstract class MOMCTS. It is created to test the MOMCTS algorithm
 * @author wj
 * @date	created on 2013/07/17
 * @date	modified on 2013/07/17
 *
 */
public class TestPlanner extends MOMCTS{
	public TestPlanner(){
		//super(objectives, RdomType, maximize, raveLocal, defDiscount, raveLocal, defDiscount);
		super("test"+RdomType, true, 0.5, 10, 0.99);
		String[] objs = {"a","b","c"};
		Vector<String> objv = Transformation.strAry2Vec(objs);
		setObjectives(objv);
		this.setSolNbLimitPerPoint(3);
		this.setEvEConst(metaRewardType, 0.05);
	}

	@Override
	public Vector<Integer> candidateActions(MOUCT parentNode) {
		Vector<Integer> acts = new Vector<Integer>();
		if(parentNode != null && parentNode.getContext().size()>5) return acts;
		for(int i=0; i< 4 ;i++){
			acts.add(i);
		}
		return acts;
	}

	@Override
	public Vector<Double> evaluateSeq(Vector<Integer> actSeq) {
		HashMap<Integer, Double> freq = SetOperation.frequencePortion(actSeq);
		Vector<Double> rwds = new Vector<Double>();
		rwds.add(SetOperation.getDoubleValue(freq, 0));
		rwds.add(SetOperation.getDoubleValue(freq, 1));
		rwds.add(SetOperation.getDoubleValue(freq, 2));
		//rwds.add(0.5);
		//rwds.add(0.);
		//rwds.add(0.);
		return rwds;
	}

	@Override
	public Vector<Integer> generateRandomPath(MOUCT leafNode) {
		Vector<Integer> rndPth = new Vector<Integer>();
		for(int i=0; i<5; i++){
			rndPth.add(SetOperation.randomElement(candidateActions(null)));
		}
		return rndPth;
	}
	
	public static void main(String[] args){
		TestPlanner planner = new TestPlanner();
		Logger log1 = new Logger("rootLog");
		Logger log2 = new Logger("leafLog");
		int time = 1000;
		while(time >= 0){
			Vector<MOUCT>  path = planner.playOneSequence();
			log1.write(""+planner.getSmt()+"\t"+path.firstElement().getRwd(planner.getMetaRewardType()));
			log2.write(""+planner.getSmt()+"\t"+path.lastElement().getRwd(planner.getMetaRewardType()));			
			time--;
		}
		planner.getRoot().showSelf(1);
		Presentation.spr();

		planner.getArchive().showPntSols();
		Presentation.spr("~");
		
		
		Vector<Double> pref = SetOperation.ones(3, 0.);
		pref.set(1, 1.0);
		Vector<Double> optPnt = planner.getArchive().getBestPoint(pref, planner.isMaximize());
		Vector<Integer> optSol = planner.getArchive().getBestSol(pref, planner.isMaximize());
		Presentation.showSeqln(optPnt);
		Presentation.showSeqln(optSol);
		Presentation.spr("~");
		int act = optSol.firstElement();
		MOUCT s = planner.getRoot().findSon(act);
		s.getSon(0).showSelf(1);
		Presentation.spr();
		s.resetAsRoot();
		s.getSon(0).showSelf(1);
		
		//Debug.debug(planner.getArchive().getPoints().size());//
		//Debug.debug(planner.getOptimalSolutions().size());
	}
}
