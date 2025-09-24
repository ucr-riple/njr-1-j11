package matcher;

import java.util.HashMap;
import java.util.LinkedList;

import regexpToNFA.K;

public class MatchGroups {
	HashMap<Integer,MatchGroup> group = new HashMap<Integer,MatchGroup>();
	LinkedList<MatchGroup> mgs = new LinkedList<MatchGroup>();
	LinkedList<K> en;
	String matchString;
	public MatchGroups(LinkedList<K> en){
		this.en=en;
		System.out.println(en.size()+"----en.size()");
		firstHandle();
	}
	public void firstHandle(){
		for(K k:en){
			MatchGroup mg = new MatchGroup(k.getK());
			mg.setNode(k.getStartNewNode(), k.getEndNewNode(),k.getCanReachNode());
			group.put(k.getK(), mg);
			mgs.add(mg);
		}
	}
	public void handle(int lastNode,int newNode,int index){
		int index_=index-1;
		//System.out.println("group:start:lastNode:"+lastNode+":newNode:"+newNode+":index:"+index);
		for(MatchGroup mg:mgs){
			//System.out.println("group:end"+mg.k+" "+index);
			if(mg.isCanSetStartIndex(lastNode)){
				mg.setStartIndex(index_);
			} 
			if(mg.isCanSetEndIndex(newNode)){
				mg.setEndIndex(index);
			}
			
			if(!mg.isCanReach(newNode)){
				mg.init();
			} else {
				mg.setStartIndex(index_);
				//mg.setEndIndex(index);
			}
		}
	}
	public void init(){
		for(MatchGroup mg:mgs){
			mg.init();
		}
	}
	public void setMatchString(String str){
		this.matchString=str;
	}
	public String group(int i){
		if(i>mgs.size()){
			System.out.println("error for i:"+i);
		}
		return group.get(i).getString(matchString);
	}
	class MatchGroup{
		int k;
		int startIndex=-1;
		int endIndex=-1;
		private LinkedList<Integer> startNewNode = new LinkedList<Integer>();
		private LinkedList<Integer> endNewNode = new LinkedList<Integer>();
		private  LinkedList<Integer> canReachNode = new LinkedList<Integer>();
		public MatchGroup(int k){
			this.k=k;
		}
		public boolean isCanReach(int newNode){
			if(canReachNode.contains(newNode)){
				return true;
			}
			return false;
		}
		public boolean isStartInit(int newNode){
			if(canReachNode.contains(newNode)){
				return false;
			}
			return true;
		}
		public boolean isCanSetStartIndex(int newNode){
			if(startNewNode.contains(newNode)){
				return true;
			}
			return false;
		}
		public boolean isCanSetEndIndex(int newNode){
			if(endNewNode.contains(newNode)){
				return true;
			}
			return false;
		}
		public void setStartIndex(int s){
			if(startIndex==-1){
				this.startIndex=s;
			}
		}
		public void setEndIndex(int e){
			this.endIndex=e;
		}
		public void setNode(LinkedList<Integer> s,LinkedList<Integer> e,LinkedList<Integer> c){
			this.startNewNode=s;
			this.endNewNode=e;
			this.canReachNode=c;
			//System.out.println("start"+s+":end"+e);
		}
		public String getString(String str){
			//System.out.println(startIndex+" "+endIndex);
			if(startIndex!=-1 && endIndex !=-1 && endIndex-startIndex>0){
				return str.substring(startIndex, endIndex);
			}
			return null;
		}
		public void init(){
			this.startIndex=-1;
			this.endIndex=-1;
		}
	}
}
