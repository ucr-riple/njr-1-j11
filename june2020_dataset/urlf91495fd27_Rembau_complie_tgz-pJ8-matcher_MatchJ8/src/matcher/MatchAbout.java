package matcher;

import java.util.LinkedList;

import NFAToDNA.NewNode;
import regexpToNFA.K;
import regexpToNFA.Syntax;

public class MatchAbout {
	private StringBuilder sb = new StringBuilder();
	MatchGroups mgs;
	private int length=0;
	public MatchAbout(LinkedList<K> en){
		mgs = new MatchGroups(en);
	}
	public void addCh(char ch,NewNode lastNode,NewNode newNode,String tran){
		if(Syntax.isNoPlace(tran)){
			
		}else {
			sb.append(ch);
			length++;
		}
		
		handle(lastNode,newNode,0);
	}
	public void handle(NewNode lastNode,NewNode newNode,int l){
		mgs.handle(lastNode.getId(),newNode.getId(),length-l);
	}
	public void init(){
		sb = new StringBuilder();
		length=0;
		mgs.init();
	}
	public boolean isLengthZero(){
		return length==0;
	}
	public void setString(){
		String str=new String(sb);
		mgs.setMatchString(str);
	}
	public String group(){
		return group(0);
	}
	public String group(int i){
		i++;
		return mgs.group(i);
	}
}
