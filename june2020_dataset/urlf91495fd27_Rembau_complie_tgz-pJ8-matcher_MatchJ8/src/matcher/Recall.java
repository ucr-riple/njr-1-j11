package matcher;

import NFAToDNA.Dtran;
import NFAToDNA.NewNode;

public class Recall {
	int index;
	NewNode nowNode;
	NewNode lastNode;
	Dtran d;
	char ch;
	public Recall(int index,NewNode nowNode,NewNode lastNode,Dtran d,char ch){
		this.index = index;
		this.nowNode = nowNode;
		this.lastNode = lastNode;
		this.d=d;
		this.ch=ch;
	}
	public char getCh(){
		return this.ch;
	}
	public Dtran getDtran(){
		return this.d;
	}
	public NewNode getNowNode(){
		return this.nowNode;
	}
	public NewNode getLastNode(){
		return this.lastNode;
	}
	public int getIndex(){
		return this.index;
	}
}
