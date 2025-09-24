package datastructure.binarytree;

public class TreeNode {
	public int val;
	
//	Node parentNode;
	private TreeNode leftLeafNode;
	private TreeNode rightLeafNode;
	
	public TreeNode(int v){
		this.val = v;
	}
	
//	public void setParentNode(Node node){
//		this.parentNode = node;
//	}
	
	public void setLeftLeafNode(TreeNode node){
//		node.parentNode = this;
		this.leftLeafNode = node;
	}
	
	public void setRightLeafNode(TreeNode node){
//		node.parentNode = this;
		this.rightLeafNode = node;
	}
	
//	public Node getParentNode(){
//		return this.parentNode;
//	}
	
	public TreeNode getLeftLeafNode(){
		return this.leftLeafNode;
	}
	
	public TreeNode getRightLeafNode(){
		return this.rightLeafNode;
	}
	
}
