package NFAToDNA;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;

import regexpToNFA.Node;

/**
 * 作者木木
 * 转换为dfa，节点为NewNode 
 */
public class ToDFA {
	/**
	 * 节点集
	 */
	private LinkedList<NewNode> newNodes=new LinkedList<NewNode>();
	private int note=1;
	/**
	 * 所有的输入字符包括\w
	 */
	private TreeSet<String> input;
	/**
	 * 旧节点集，节点的id对应相应的节点
	 */
	private Hashtable<Integer,Node> nodeList;
	/**
	 * 新节点集，节点的id对应相应的节点
	 */
	Hashtable<Integer,LinkedList<Dtran>> dtrans = new Hashtable<Integer,LinkedList<Dtran>>();
	/**
	 * 开始节点
	 */
	NewNode startNewNode;
	/**
	 * 结束节点集
	 */
	LinkedList<NewNode> endNewNodes= new LinkedList<NewNode>();
	public ToDFA(Hashtable<Integer,Node> nodeList,TreeSet<String> input){
		this.input=input;
		this.nodeList=nodeList;
		handle();
		removeRedundancy();
	}
	public NewNode getStartNewNode(){
		//System.out.println("获取开始节点");
		return this.startNewNode;
	}
	public Hashtable<Integer,LinkedList<Dtran>> getDtrans(){
		return this.dtrans;
	}
	public LinkedList<NewNode> getNewNodes(){
		return this.newNodes;
	}
	public void handle(){
		TreeSet<Integer> nodes =new TreeSet<Integer>();
		nodes.add(nodeList.get(1).getValue());
		NewNode n=new NewNode(getNote());
		n.setNodes(closure(nodes));
		//System.out.println("========"+closure(nodes)+"===========");
		n.setStart();
		if(closure(nodes).contains(2)){
			n.setEnd();
		}
		
		newNodes.add(n);
		
		NewNode nn;
		while((nn=getNotMark())!=null){
			//System.out.println(nn.getId());
			nn.mark();
			for(String sign:input){
				TreeSet<Integer> t=closure(move(nn.getNodes(),sign));
				//System.out.println("t"+t);
				NewNode nn_=null;
				if((nn_=getNewNode(t))==null){
					nn_ = new NewNode(getNote());
					if(t.contains(1)){
						nn_.setStart();
					}
					if(t.contains(2)){
						nn_.setEnd();
						endNewNodes.add(nn_);
					}
					nn_.setNodes(t);
					newNodes.add(nn_);
				}
				addDtrans(nn.getNodes(),nn,sign,nn_);
			}
		}
		for(NewNode n_:newNodes){
			System.out.println(n_.getId()+" "+n_.getNodes());
			if(n_.isStart()){
				startNewNode = n_;
			}
		};
	}
	/**
	 * 获取一个没有被标记的新节点
	 * @return
	 */
	public NewNode getNotMark(){
		for(NewNode nn:newNodes){
			if(!nn.getMark()){
				return nn;
			}
		}
		return null;
	}
	public void addDtrans(TreeSet<Integer> nodes,NewNode mark,String tran,NewNode d){
		if(dtrans.containsKey(mark.getId())){
			dtrans.get(mark.getId()).add(new Dtran(nodes,mark,tran,d));
		} else{
			LinkedList<Dtran> dts=new LinkedList<Dtran>();
			dts.add(new Dtran(nodes,mark,tran,d));
			dtrans.put(mark.getId(), dts);
		}
	}
	/**
	 * 去除从开始节点无法到达的节点，和 无法到达结束节点的节点
	 */
	public void removeRedundancy(){
		removeNotRecFormStart();
		removeNotRecToEnd();
	}
	public void removeNotRecFormStart(){
		Stack<Integer> stack = new Stack<Integer>();
		TreeSet<Integer> r=new TreeSet<Integer>();
		r.add(this.startNewNode.getId());
		stack.push(this.startNewNode.getId());
		while(!stack.empty()){
			int key = stack.pop();
			//System.out.println("key="+key);
			for(Dtran d:dtrans.get(key)){
				if(!r.contains(d.getD().getId())){
					stack.push(d.getD().getId());
					r.add(d.getD().getId());
				}
			}
		}
		removeKey(r);
	}
	public void removeNotRecToEnd(){
		Stack<Integer> stack = new Stack<Integer>();
		TreeSet<Integer> r=new TreeSet<Integer>();
		for(NewNode nn:this.endNewNodes){
			r.add(nn.getId());
			stack.push(nn.getId());
		}
		while(!stack.empty()){
			int key = stack.pop();
			for(Dtran d:getDtransByD(key)){
				if(!r.contains(d.getMark().getId())){
					stack.push(d.getMark().getId());
					r.add(d.getMark().getId());
				}
			}
		}
		removeKey(r);
	}
	/**
	 * 当移除某节点时，应一并移除 剩余的与该节点有关系的节点
	 * @param r
	 */
	public void removeKey(TreeSet<Integer> r){
		Enumeration<Integer> e=getDtrans().keys();
		while(e.hasMoreElements()){
			Integer v = e.nextElement();
			if(!r.contains(v)){
				//System.out.println("remove2"+v);
				dtrans.remove(v);
			}
		}
		e=getDtrans().keys();
		while(e.hasMoreElements()){
			LinkedList<Dtran> ds=new LinkedList<Dtran>();
			Integer v = e.nextElement();
			for(Dtran d:dtrans.get(v)){
				if(!r.contains(d.getD().getId())){
					ds.add(d);
				}
			}
			for(Dtran d:ds){
				dtrans.get(v).remove(d);
			}
		}
	}
	/**
	 * 获取所有dtran中结束节点为end的节点
	 * @param end
	 * @return
	 */
	public LinkedList<Dtran> getDtransByD(int end){
		LinkedList<Dtran> ds= new LinkedList<Dtran>();
		Enumeration<Integer> e=getDtrans().keys();
		while(e.hasMoreElements()){
			Integer v = e.nextElement();
			for(Dtran d:dtrans.get(v)){
				if(d.getD().getId()==end){
					ds.add(d);
				}
			}
		}
		return ds;
	}
	/**
	 * 获取closure转换获得的节点集
	 * @param nodes
	 * @return
	 */
	public TreeSet<Integer> closure(TreeSet<Integer> nodes){
		TreeSet<Integer> node = new TreeSet<Integer>(); //结果
		Stack<Integer> stack = new Stack<Integer>();
		for(Integer n:nodes){
			stack.push(n);
			node.add(n);
		}
		while(!stack.empty()){
			int i=stack.pop();
			//System.out.println("从栈顶获取"+i);
			TreeSet<Integer> n_1=nodeList.get(i).getNodeMove("");
			for(int int_1:n_1){
				if(!node.contains(int_1)){
					stack.push(int_1);
					node.add(int_1);
					//System.out.println(node);
				}
			}
		}
		return node;
	}
	/**
	 * 获取当前节点集经过tran转换获得的节点集
	 * @param nodes
	 * @param tran
	 * @return
	 */
	public TreeSet<Integer> move(TreeSet<Integer> nodes,String tran){
		TreeSet<Integer> node = new TreeSet<Integer>();
		for(Integer n:nodes){
			node.addAll(nodeList.get(n).getNodeMove(tran));
		}
		return node;
	}
	public int getNote(){
		return note++;
	}
	public boolean isExit(TreeSet<Integer> n){
		for(NewNode nn:newNodes){
			if(nn.isSame(n)){
				return true;
			}
		}
		return false;
	}
	public NewNode getNewNode(TreeSet<Integer> n){
		for(NewNode nn:newNodes){
			if(nn.isSame(n)){
				return nn;
			}
		}
		return null;
	}
	public static void main(String[] args) {

	}
}
