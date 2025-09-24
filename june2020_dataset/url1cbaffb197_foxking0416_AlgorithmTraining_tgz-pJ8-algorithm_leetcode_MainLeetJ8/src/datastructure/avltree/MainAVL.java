package datastructure.avltree;

import datastructure.binarytree.TreeNode;

public class MainAVL {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testAVLTree();
	}
	
	private static void testAVLTree(){
	//  1       1           1               2            2           2               2
	//	         \           \             / \          / \         / \             / \
	//     ==>    2    ==>    2     ==>   1   3   ==>  1   3  ==>  1   3     ==>   1   4
	//                         \                            \           \             / \
	//                          3                            4           4           3   5
	//                                                                    \
	//                                                                     5
		AVLTree tree = new AVLTree();
		TreeNode rootNode = new TreeNode(1);
		TreeNode node1 = new TreeNode(2);
		TreeNode node2 = new TreeNode(3);
		TreeNode node3 = new TreeNode(4);
		TreeNode node4 = new TreeNode(5);
		tree.insert(rootNode);
		tree.insert(node1);
		tree.insert(node2);
		tree.insert(node3);
		tree.insert(node4);

		
		System.out.print("Preorder Traverse: ");
		tree.preorderTraverseRecursion(tree.getRootNode());
		System.out.print("\n");
		
		System.out.print("Postorder Traverse: ");
		tree.postorderTraverseRecursion(tree.getRootNode());
		System.out.print("\n");
		
		tree.remove(node3);
		System.out.print("Preorder Traverse after removed: ");
		tree.preorderTraverseRecursion(tree.getRootNode());
		System.out.print("\n");
		
		System.out.print("Postorder Traverse after removed: ");
		tree.postorderTraverseRecursion(tree.getRootNode());
		System.out.print("\n");
	}
}
