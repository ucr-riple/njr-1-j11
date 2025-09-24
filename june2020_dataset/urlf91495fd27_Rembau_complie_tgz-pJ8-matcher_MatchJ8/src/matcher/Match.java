package matcher;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

import NFAToDNA.Dtran;
import NFAToDNA.NewNode;
import NFAToDNA.ToDFA;
import regexpToNFA.MainClass;
import regexpToNFA.ManagerK;
import regexpToNFA.Syntax;

public class Match {
	Hashtable<Integer,LinkedList<Dtran>> dtrans = new Hashtable<Integer,LinkedList<Dtran>>();
	NewNode startNode;
	NewNode nowNode;
	NewNode lastNode;
	MatchAbout ma;
	String rex;
	String content;
	char[] chList;
	int pointer=0;
	String tran;
	Stack<Recall> stack = new Stack<Recall>();
	public Match(String str){
		this.rex=str;
		MainClass mc= new MainClass(str);
		ToDFA td=new ToDFA(mc.getNodeList(),mc.getInputChar());
		this.dtrans=td.getDtrans();
		this.startNode=td.getStartNewNode();
		this.nowNode=startNode;
		ManagerK.handle(td.getNewNodes(),mc.getNodeList());
		ma= new MatchAbout(ManagerK.getEn());
		//System.out.println(startNode.getId());
	}
	public void match(String content){
		this.content=content;
		chList=content.toCharArray();
	}
	public boolean find(){
		ma.init();
		nowNode=this.startNode;
		int i=pointer;
		while(i<chList.length){
			//System.out.println("当前指针："+i+" "+chList[i]);
			char last=0,next=0;
			if(i!=0){
				last = chList[i-1];
			}
			if(i!=chList.length-1){
				next = chList[i+1];
			}
			Syntax.setLastNext(last, next);
			
			int result=isMatch(chList[i],i);
			if(result==1){
				ma.addCh(chList[i],lastNode,nowNode,tran);
				if(Syntax.isNoPlace(tran)){
					i--;
				}
			} else if(result==2){
				i=pointer;
				//System.out.println(ma.isLengthZero()+" "+nowNode.isEnd());
				if(nowNode.isEnd() && !ma.isLengthZero()){
					ma.setString();
					pointer=i;
					stack.removeAllElements();
					return true;
				}
			} else if(result==3){
				//System.out.println(ma.isLengthZero()+" "+nowNode.isEnd());
				if(nowNode.isEnd()&& !ma.isLengthZero()){
					ma.setString();
					pointer=i;
					stack.removeAllElements();
					return true;
				} else {
					i=pointer++;
					nowNode=this.startNode;
					//System.out.println("newNode初始化。。。。");
					ma.init();
				}
			}
			i++;
		}
		if(i==chList.length && nowNode.isEnd()){
			ma.setString();
			pointer=i;
			return true;
		}
		return false;
	} 
	public int isMatch(char ch,int index){
		int r=3;
		LinkedList<Dtran> ds=getDtran();
		boolean flag=false;
		Stack<Recall> stack_ = new Stack<Recall>();
		for(Dtran d:ds){
			if(Syntax.isMatch(d.getTran(),ch)){
				//System.out.println(d.getTran());
				stack_.push(new Recall(index,nowNode,lastNode,d,ch));
				flag = true;
			}
		}
		while(!stack_.empty()){
			stack.push(stack_.pop());
		}
		if(flag){
			Recall recall=stack.pop();
			Dtran d=recall.getDtran();
			//System.out.print("判断是否符合："+d.getTran()+" "+ch);
			lastNode=nowNode;
			nowNode=d.getD();
			tran = d.getTran();
			//System.out.println("nowNode是否是结束节点"+nowNode.isEnd()+" "+nowNode.getId()+"符合ture =========");
			r = 1;
		} else {
			//System.out.println(ch+"不符合。");
			if(!nowNode.isEnd() && !stack.isEmpty()){
				Recall recall=stack.pop();
				Dtran d=recall.getDtran();
				lastNode = recall.getNowNode();
				pointer = recall.getIndex();
				nowNode = d.getD();
				tran = d.getTran();
				//System.out.print("回溯："+d.getTran()+" "+ch);
				//System.out.println("  nowNode"+nowNode.getId()+"nowNode是否是结束节点"+nowNode.isEnd()+" " +
				//		""+nowNode.getId()+":字符为:"+chList[pointer]+" "+pointer);
				
				pointer=pointer+1;
				ma.addCh(recall.getCh(),lastNode,nowNode,tran);
				
				r = 2;
			}
		}
		return r;
	}
	public LinkedList<Dtran> getDtran(){
		LinkedList<Dtran> ds = new LinkedList<Dtran>();
		ds=dtrans.get(nowNode.getId());
		return ds;
	}
	public String group(){
		return this.group(0);
	}
	public String group(int i){
		return ma.group(i);
	}
	public static void main(String[] args) {
				//  1		     2     3     4   5   6    7        8   9   10          11         12           13
		String rex="(\\bmain\\c)|(\\()|(\\))|(,)|(;)|(==)|(\\+\\+)|({)|(})|(\\bint\\c)|(\\bif\\c)|(\\belse\\c)|(=)" +
				//    14           15
					"|(\\+|-|\\*|/)";//|(\\d+)|(\\w+)
		Match m = new Match(rex);
		m.match("main() { int  sum=12,it=1; if (sum==1)  it++; else it=it+2; }");
		int limit=0;
		while(m.find()){
			limit++;
			//System.out.println("====="+limit+":ma.group()："+m.group()+"");
			System.out.println("====="+limit);
			if(m.group(1)!=null){
				System.out.println("group(1)---main:"+m.group(1));
			}
			if(m.group(2)!=null){
				System.out.println("group(2)---左小括号:"+m.group(2));
			}
			if(m.group(3)!=null){
				System.out.println("group(3)---右小括号:"+m.group(3));
			} 
			if(m.group(4)!=null){
				System.out.println("group(4)---逗号:"+m.group(4));
			}
			if(m.group(5)!=null){
				System.out.println("group(5)---分号:"+m.group(5));
			}
			if(m.group(6)!=null){
				System.out.println("group(6)---比较:"+m.group(6));
			}
			if(m.group(7)!=null){
				System.out.println("group(7)---自加:"+m.group(7));
			}
			if(m.group(8)!=null){
				System.out.println("group(8)---左大括号:"+m.group(8));
			}
			if(m.group(9)!=null){
				System.out.println("group(9)---右大括号:"+m.group(9));
			}
			if(m.group(10)!=null){
				System.out.println("group(10)---int:"+m.group(10));
			}
			if(m.group(11)!=null){
				System.out.println("group(11)---if:"+m.group(11));
			}
			if(m.group(12)!=null){
				System.out.println("group(12)---else:"+m.group(12));
			}
			if(m.group(13)!=null){
				System.out.println("group(13)---等于号:"+m.group(13));
			}
			if(m.group(14)!=null){
				System.out.println("group(14)---运算符:"+m.group(14));
			}
			/*if(m.group(15)!=null){
				System.out.println("group(15)---数字常量:"+m.group(15));
			}	
			if(m.group(16)!=null){
				System.out.println("group(16)---标识符:"+m.group(16));
			}*/
		}
	}
}
/*
=====1
group(1)---main:main
=====2
group(2)---左小括号:(
=====3
group(3)---右小括号:)
=====4
group(8)---左大括号:{
=====5
group(10)---int:int
=====6
group(13)---等于号:=
=====7
group(4)---逗号:,
=====8
group(13)---等于号:=
=====9
group(5)---分号:;
=====10
group(11)---if:if
=====11
group(2)---左小括号:(
=====12
group(6)---比较:==
=====13
group(3)---右小括号:)
=====14
group(7)---自加:++
=====15
group(5)---分号:;
=====16
group(12)---else:else
=====17
group(13)---等于号:=
=====18
group(14)---运算符:+
=====19
group(5)---分号:;
=====20
group(9)---右大括号:}
*/
