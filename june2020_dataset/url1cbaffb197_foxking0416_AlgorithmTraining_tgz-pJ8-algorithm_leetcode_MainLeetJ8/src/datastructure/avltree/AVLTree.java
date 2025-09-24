package datastructure.avltree;
import java.util.Stack;

import datastructure.binarytree.*;

public class AVLTree extends BinaryTree {
	

	
	@Override
 	protected void insertNode(TreeNode currentNode, TreeNode newNode){
		if(newNode.val < currentNode.val){
			if(currentNode.getLeftLeafNode() == null){
				currentNode.setLeftLeafNode(newNode);
			}
			else {
				insertNode(currentNode.getLeftLeafNode(), newNode);
			}
		}
		else{
			if(currentNode.getRightLeafNode() == null){
				currentNode.setRightLeafNode(newNode);
			}
			else {
				insertNode(currentNode.getRightLeafNode(), newNode);
			}
		}
		
		checkBalance(currentNode);
	}
	
	
	@Override
	public void remove(TreeNode removeNode){
		TreeNode nodeToRemoveNode =  this.rootNode;
		TreeNode parentNode = null;
		Stack<TreeNode> pathStack = new Stack<TreeNode>();
		pathStack.add(this.rootNode);
		
		while(nodeToRemoveNode != null && nodeToRemoveNode != removeNode){
			parentNode = nodeToRemoveNode;
			if(removeNode.val < nodeToRemoveNode.val){
				nodeToRemoveNode = parentNode.getLeftLeafNode();
			}
			else{
				nodeToRemoveNode = parentNode.getRightLeafNode();
			}
			pathStack.push(nodeToRemoveNode);
		}
		
		
		if(nodeToRemoveNode == null)
			return;
		
		//parentNode = findParentNode(removeNode, this.rootNode);
		
		
		if(count == 1)
			this.rootNode = null;//remove the only one element of this tree
		else{
			if(nodeToRemoveNode.getLeftLeafNode() == null && nodeToRemoveNode.getRightLeafNode() == null){//remove the leaf node
				if(nodeToRemoveNode.val < parentNode.val){
					parentNode.setLeftLeafNode(null);
				}
				else {
					parentNode.setRightLeafNode(null);
				}
			}
			else if (nodeToRemoveNode.getLeftLeafNode() != null && nodeToRemoveNode.getRightLeafNode() == null){//remove the node with only left leaf node
				if(nodeToRemoveNode.val < parentNode.val){
					parentNode.setLeftLeafNode(nodeToRemoveNode.getLeftLeafNode());
				}
				else{
					parentNode.setRightLeafNode(nodeToRemoveNode.getLeftLeafNode());
				}
			}
			else if (nodeToRemoveNode.getLeftLeafNode() == null && nodeToRemoveNode.getRightLeafNode() != null){//remove the node with only right leaf node
				if(nodeToRemoveNode.val < parentNode.val){
					parentNode.setLeftLeafNode(nodeToRemoveNode.getRightLeafNode());
				}
				else{
					parentNode.setRightLeafNode(nodeToRemoveNode.getRightLeafNode());
				}	
			}
			else{//remove the node with two leaf nodes
				TreeNode largestNodeFromLeft = nodeToRemoveNode.getLeftLeafNode();
				while(largestNodeFromLeft.getRightLeafNode() != null){
					largestNodeFromLeft = largestNodeFromLeft.getRightLeafNode();
				}
				
				
				TreeNode tempParent = findParentNode(largestNodeFromLeft, this.rootNode);

				
				largestNodeFromLeft.setRightLeafNode(nodeToRemoveNode.getRightLeafNode());
				if(largestNodeFromLeft.val < tempParent.val){
					largestNodeFromLeft.setLeftLeafNode(nodeToRemoveNode.getLeftLeafNode().getLeftLeafNode());	
				}
				else{
					largestNodeFromLeft.setLeftLeafNode(nodeToRemoveNode.getLeftLeafNode());	
					tempParent.setRightLeafNode(null);
				}
				
				if(parentNode != null){
					if(nodeToRemoveNode.val < parentNode.val){
						parentNode.setLeftLeafNode(largestNodeFromLeft);
					}
					else{
						parentNode.setRightLeafNode(largestNodeFromLeft);
					}
				}
				else{
					this.rootNode = largestNodeFromLeft;
				}
			}
		}
		
		while(pathStack.size() > 0){
			checkBalance(pathStack.pop());
		}
		--count;
	}
	
	
	public void checkBalance(TreeNode node){
			
		if(height(node.getLeftLeafNode()) - height(node.getRightLeafNode()) > 1){
			TreeNode parentNode = findParentNode(node, this.rootNode);
			if(height(node.getLeftLeafNode().getLeftLeafNode()) > height(node.getLeftLeafNode().getRightLeafNode())){
				rightRotation(node, parentNode);
			}
			else{
				leftAndRightRotation(node, parentNode);
			}
		}
		else if(height(node.getLeftLeafNode()) - height(node.getRightLeafNode()) < -1){
			TreeNode parentNode = findParentNode(node, this.rootNode);
			if(height(node.getRightLeafNode().getRightLeafNode()) > height(node.getRightLeafNode().getLeftLeafNode())){
				leftRotation(node, parentNode);
			}
			else{
				rightAndLeftRotation(node, parentNode);
			}
		}
	}
	
	//     Q                           P
	//    / \                         / \
	//   P   C   ==>   P   Q    ==>  A   Q
	//  / \           / \ / \           / \
	// A   B         A   B   C         B   C
	public void rightRotation(TreeNode nodeQ, TreeNode parentNode){
		if(nodeQ.getLeftLeafNode() == null)
			return;
		
		TreeNode nodeP = nodeQ.getLeftLeafNode();
		
		nodeQ.setLeftLeafNode(nodeP.getRightLeafNode());
		nodeP.setRightLeafNode(nodeQ);
		
		if(parentNode == null){
			this.rootNode = nodeP;
		}
		else{
			if(nodeQ.val < parentNode.val){
				parentNode.setLeftLeafNode(nodeP);
			}
			else{
				parentNode.setRightLeafNode(nodeP);
			}
		}
	}
	
	//     P                           Q
	//    / \                         / \
	//   A   Q   ==>   P   Q    ==>  P   C
	//      / \       / \ / \       / \
	//     B   C     A   B   C     A   B 
	public void leftRotation(TreeNode nodeP, TreeNode parentNode){
		if(nodeP.getRightLeafNode() == null)
			return;
		TreeNode nodeQ = nodeP.getRightLeafNode();
		
		nodeP.setRightLeafNode(nodeQ.getLeftLeafNode());
		nodeQ.setLeftLeafNode(nodeP);
		
		if(parentNode == null){
			this.rootNode = nodeQ;
		}
		else{
			if(nodeP.val < parentNode.val){
				parentNode.setLeftLeafNode(nodeQ);
			}
			else{
				parentNode.setRightLeafNode(nodeQ);
			}
		}
	}
	
	//       Q             Q                 Q                               B
	//      / \           / \               / \                            /   \
	//     P   C   ==>   /   C    ==>      B   C   ==>     B   Q    ==>   P     Q      
	//    / \           /                 / \             / \ / \        / \   / \
	//   A   B         P   B             P   Y           P   Y   C      A   X Y   C
	//      / \       / \ / \           / \             / \
	//     X   Y     A   X   Y         A   X           A   X
	public void leftAndRightRotation(TreeNode node, TreeNode parentNode){
		leftRotation(node.getLeftLeafNode(), parentNode);
		rightRotation(node, parentNode);
	}
	
	//       P             P                 P                               B
	//      / \           / \               / \                            /   \
	//     A   Q   ==>   A   \  ==>        A   B   ==>     P   B    ==>   P     Q      
	//        / \             \               / \         / \ / \        / \   / \
	//       B   C         B   Q             X   Q       A   X   Q      A   X Y   C
	//      / \           / \ / \               / \             / \
	//     X   Y         X   Y   C             Y   C           Y   C
	public void rightAndLeftRotation(TreeNode node, TreeNode parentNode){
		rightRotation(node.getRightLeafNode(), parentNode);
		leftRotation(node, parentNode);
	}
}
