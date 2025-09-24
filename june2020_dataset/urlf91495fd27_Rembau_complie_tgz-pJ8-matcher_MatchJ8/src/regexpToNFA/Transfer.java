package regexpToNFA;

public class Transfer {
	private int start;
	private int end;
	private String ch;
	public Transfer(int s,int e,String ch){
		this.start=s;
		this.end=e;
		this.ch=ch;
	}
	public int getStart(){
		return this.start;
	}
	public int getEnd(){
		return this.end;
	}
	public String getCh(){
		return this.ch;
	}
}
