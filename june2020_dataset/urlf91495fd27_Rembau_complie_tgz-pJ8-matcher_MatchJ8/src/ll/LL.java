package ll;

import java.util.Hashtable;
import java.util.Stack;
import java.util.TreeSet;

public class LL {
	AnalysisTable at;
	String str[];
	TreeSet<String> notEndChars = new TreeSet<String>();
	public LL(AnalysisTable at,String inStr[]){
		this.at=at;
		notEndChars=at.list;
		//System.out.println("非终结符列表："+at.list);
		this.str=inStr;
	}
	public void handle() throws Exception{
		Stack<String> stack1 = new Stack<String>();
		stack1.add("$");
		stack1.add(at.startChar);
		
		Stack<String> stack2 = new Stack<String>();
		stack2.push("$");
		for(int i=str.length-1;i>=0;i--){
			stack2.push(str[i]);
		}
		String value1;
		String value2;
		String tran_="";
		String tran[]=new String[]{};
		while(stack1.size()>1){
			int length=0;
			for(int i=0;i<stack1.size();i++){
				System.out.print(stack1.get(i));
				length+=stack1.get(i).length();
			}
			for(int i=0;i<15-length;i++){
				System.out.print(" ");
			}
			length=0;
			for(int i=stack2.size()-1;i>=0;i--){
				System.out.print(stack2.get(i));
				length+=stack2.get(i).length();
			}
			for(int i=0;i<18-length;i++){
				System.out.print(" ");
			}
			for(int i=0;i<tran.length;i++){
				if(i==0){
					System.out.print(tran_+"->");
				}
				System.out.print(tran[i]);
			}
			System.out.println();
			value1=stack1.pop();
			if(!notEndChars.contains(value1)){
				//System.out.println(value1+"不是非终结符");
				if(!stack2.isEmpty()){
					if(!stack2.pop().equals(value1)){
						throw new Exception("分析失败！");
					}
				} else {
					throw new Exception("分析失败！");
				}
			} else {
				if(!stack2.isEmpty()){
					value2 = stack2.peek();
					String t[] = at.get(value1,value2);
					if(t==null){
						System.out.println(value1+"->"+value2+"空！");
						throw new Exception("分析失败！");
					}
					tran_=value1;
					tran=t;
					for(int i=t.length-1;i>=0;i--){
						stack1.push(t[i]);
					}
				} else {
					throw new Exception("分析失败！");
				}
			}
		}
		if(stack2.pop().equals("$")){
			System.out.println("succeed");
		}
	}
	public static void main(String args[]){
		AnalysisTable at =new AnalysisTable("E");
		at.add(new AnalysisUnit("E","id",new String[]{"T","E'"}));
		at.add(new AnalysisUnit("E","(",new String[]{"T","E'"}));
		at.add(new AnalysisUnit("E'","+",new String[]{"+","T","E'"}));
		at.add(new AnalysisUnit("E'",")",new String[]{}));
		at.add(new AnalysisUnit("E'","$",new String[]{}));
		at.add(new AnalysisUnit("T","id",new String[]{"F","T'"}));
		at.add(new AnalysisUnit("T","(",new String[]{"F","T'"}));
		at.add(new AnalysisUnit("T'","+",new String[]{}));
		at.add(new AnalysisUnit("T'","*",new String[]{"*","F","T'"}));
		at.add(new AnalysisUnit("T'",")",new String[]{}));
		at.add(new AnalysisUnit("T'","$",new String[]{}));
		at.add(new AnalysisUnit("F","id",new String[]{"id"}));
		at.add(new AnalysisUnit("F","(",new String[]{"(","E",")"}));
		
		try {
			new LL(at,new String[]{"id","*","id","+","id"}).handle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
 * 运行结果：
$E             id*id+id$         
$E'T           id*id+id$         E->TE'
$E'T'F         id*id+id$         T->FT'
$E'T'id        id*id+id$         F->id
$E'T'          *id+id$           F->id
$E'T'F*        *id+id$           T'->*FT'
$E'T'F         id+id$            T'->*FT'
$E'T'id        id+id$            F->id
$E'T'          +id$              F->id
$E'            +id$              
$E'T+          +id$              E'->+TE'
$E'T           id$               E'->+TE'
$E'T'F         id$               T->FT'
$E'T'id        id$               F->id
$E'T'          $                 F->id
$E'            $                 
succeed

 */
class AnalysisTable{
	String startChar;
	TreeSet<String> list = new TreeSet<String>();
	Hashtable<String,Hashtable<String,String[]>> collection = 
		new Hashtable<String,Hashtable<String,String[]>>();
	public AnalysisTable(String sc){
		this.startChar=sc;
	}
	public void add(AnalysisUnit au){
		list.add(au.notEndChar);
		//System.out.println(au.notEndChar+" "+au.endChar+" "+au.translation);
		if(collection.containsKey(au.notEndChar)){
			collection.get(au.notEndChar).put(au.endChar, au.translation);
		} else {
			Hashtable<String,String[]> ss = new Hashtable<String,String[]>();
			ss.put(au.endChar, au.translation);
			//System.out.print(" "+au.endChar);
			collection.put(au.notEndChar, ss);
		}
	}
	public String[] get(String notEndChar,String endChar){
		return collection.get(notEndChar).get(endChar);
	}
}
class AnalysisUnit{
	String endChar;
	String notEndChar;
	String translation[];
	public AnalysisUnit(String e,String n,String t[]){
		this.endChar=n;
		this.notEndChar=e;
		this.translation=t;
	}
}