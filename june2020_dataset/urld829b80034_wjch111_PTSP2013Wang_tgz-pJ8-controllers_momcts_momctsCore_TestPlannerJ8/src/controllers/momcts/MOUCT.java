package controllers.momcts;

import java.util.HashMap;
import java.util.Vector;

import controllers.utils.Presentation;


/**
 * One node in the MOUCT tree which stores the reward information
 * @author wj
 *
 */
public class MOUCT {
	/**
	 * An action is necessary for the creation of node in MOUCT tree 
	 */
	private int action;
	/**
	 * The state identification of a node is not necessarily used in many occassions
	 */
	private int state;
	
	/**
	 * The information about the following state of the current state is stored in sons 
	 */
	private Vector<MOUCT> sons;
	
	private Vector<Integer> context;
	
	/**
	 * The rwds map stores the total reward of each type
	 */
	private HashMap<String, Double> rwds;
	
	/**
	 * The nbs map stores the total number of visits of each type
	 */
	private HashMap<String, Double> nbs;
	
	/**
	 * Define several default reward types
	 */
	public static Vector<String> defRwdTypes;

	//TODO
	public MOUCT(){
		
	}
	
	
	
	public boolean isLeaf(){
		return !(sons.size()>0);
	}
	
	/**
	 * Return the average reward of type type
	 * @param type	indicate the type of the reward required
	 * @return the average reward
	 */
	public double avgR(String type){
		return rwds.get(type)/nbs.get(type);
	}
	
	//TODO
	public void addSon(int a){
		
	}
	
	
	
	/**
	 * Show the subtree starting from the current node
	 * @param limit		the number of levels to be shown
	 * @param rwdTypes	indicate the reward types to be shown
	 * @param nbType	inciate the number of visits recored in one nbs key
	 * @param depth		pass the depth of the tree (used in presentation)
	 */
	public void showSelf(int limit, Vector<String> rwdTypes, String nbType, int depth){
		if(rwdTypes.size()==0) rwdTypes = defRwdTypes;
		if(nbType == "") nbType = rwdTypes.get(0);
		
		Presentation.multiOut("\t",depth);
		if(context.size()>0 && depth == 0){
			System.out.println("<");
			Presentation.showSeq(context,".");
			System.out.println(">");
		}
		System.out.print(action);
		if(isLeaf()) System.out.print("->"+nbs.get(nbType));
		else System.out.print("("+nbs.get(nbType)+")");

		for(int i=0; i<rwdTypes.size();i++){
			String tp = rwdTypes.get(i);
			System.out.print("\t"+tp+":"+avgR(tp));
		}

		if(limit != 0){
			System.out.println();
			for(int i=0; i<sons.size();i++){
				sons.get(i).showSelf(limit-1, rwdTypes, nbType, depth+1);
			}
		} else {
			if( sons.size()>0){
				System.out.print("\t||Cut||");
			}
			System.out.println();
		}
	}
	
	public void showSelf(int limit, Vector<String> rwdTypes, String nbType){
		showSelf(limit, rwdTypes, nbType, 0);
	}

	public void showSelf(int limit, Vector<String> rwdTypes){
		showSelf(limit, rwdTypes, "");
	}
	
	public void showSelf(int limit){
		showSelf(limit,new Vector<String>());
	}
	
	public void showSelf(){
		showSelf(-1);
	}
	
	
	
	
	public static void main(String[] args){
		
	}
}
