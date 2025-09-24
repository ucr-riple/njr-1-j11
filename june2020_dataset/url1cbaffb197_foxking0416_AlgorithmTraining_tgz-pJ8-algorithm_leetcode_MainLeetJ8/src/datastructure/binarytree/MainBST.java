package datastructure.binarytree;



import java.util.ArrayList;
import java.util.List;

public class MainBST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testBinaryTree();
		testGenerateTrees();
		testBuildTree();
		testMaxPathSum();
	}

	private static void testBinaryTree(){
		
		BinaryTree binaryTree = new BinaryTree();
		ArrayList<TreeNode> array = new ArrayList<TreeNode>();
		
//		Node rootNode = new Node((int)(Math.random() * 100));
//		binaryTree.insert(rootNode);
//		array.add(rootNode);
//		System.out.print(rootNode.value + ",");
//		
//		for(int i = 1; i < 10 ; i++){
//			int value = (int)(Math.random() * 100);
//			Node newNode = new Node(value);
//			
//			
//			binaryTree.insert(newNode);
//			array.add(newNode);
//			System.out.print(value + ",");
//		}
		
		//                         50
		//                  /-------------\
		//                 /               \
		//                /                 \
		//               /                   \
		//              25                   75
		//             /  \                 /  \
		//            /    \               /    \
		//           /      \             /      \
		//          15      40           69      83
		//         /  \    /  \         /        / \
		//        /    \  /    \       /        /   \  
		//        1   17 39    48     53       77    99
		//                    /  \   /        /
		//                   45  49 50       76
		TreeNode rootNode = new TreeNode(50);
		TreeNode node1 = new TreeNode(25);
		TreeNode node2 = new TreeNode(75);
		TreeNode node3 = new TreeNode(15);
		TreeNode node4 = new TreeNode(40);
		TreeNode node5 = new TreeNode(69);
		TreeNode node6 = new TreeNode(83);
		TreeNode node7 = new TreeNode(53);
		TreeNode node8 = new TreeNode(99);
		TreeNode node9 = new TreeNode(77);
		TreeNode node10 = new TreeNode(48);
		TreeNode node11 = new TreeNode(49);
		TreeNode node12 = new TreeNode(39);
		TreeNode node13 = new TreeNode(45);
		TreeNode node14 = new TreeNode(1);
		TreeNode node15 = new TreeNode(17);
		TreeNode node16 = new TreeNode(51);
		TreeNode node17 = new TreeNode(76);
		binaryTree.insert(rootNode);
		binaryTree.insert(node1);
		binaryTree.insert(node2);
		binaryTree.insert(node3);
		binaryTree.insert(node4);
		binaryTree.insert(node5);
		binaryTree.insert(node6);
		binaryTree.insert(node7);
		binaryTree.insert(node8);
		binaryTree.insert(node9);
		binaryTree.insert(node10);
		binaryTree.insert(node11);
		binaryTree.insert(node12);
		binaryTree.insert(node13);
		binaryTree.insert(node14);
		binaryTree.insert(node15);
		binaryTree.insert(node16);
		binaryTree.insert(node17);
		
		System.out.print("\n");
		System.out.print("Height: ");	
		System.out.print(binaryTree.height(node17));
		System.out.print("\n");
		System.out.print("Minimum Depth: ");	
		System.out.print(binaryTree.minDepth(node2));		
		System.out.print("\n");
		System.out.print("Preorder Traverse using recursive: ");
		binaryTree.preorderTraverseRecursion(rootNode);
		System.out.print("\n");
		System.out.print("Preorder Traverse using loop:      ");
		binaryTree.preorderTraverseLoop(rootNode);
		System.out.print("\n");
		System.out.print("Postorder Traverse using recursive: ");
		binaryTree.postorderTraverseRecursion(rootNode);
		System.out.print("\n");
		System.out.print("Postorder Traverse using loop:      ");
		binaryTree.postorderTraverseRecursion(rootNode);
		System.out.print("\n");
		System.out.print("Inorder Traverse using recursive: ");//Sequence will be from small to big
		binaryTree.inorderTraverse(rootNode);
		System.out.print("\n");
		System.out.print("Inorder Traverse using loop     : ");//Sequence will be from small to big
		binaryTree.inorderTraverseLoop(rootNode);
		System.out.print("\n");
		System.out.print("Breadth First Traverse: ");//Sequence will be from small to big
		binaryTree.breadthFirstTraverse(rootNode);
		System.out.print("\n");
		System.out.print("Level Order Traverse: ");//Sequence will be from small to big
		binaryTree.levelOrderTraverse(rootNode);		
		System.out.print("\n");
		System.out.print("Zigzag Level Order Traverse: ");//Sequence will be from small to big
		binaryTree.zigzagLevelOrderTraverse(rootNode);

		System.out.print("\n");
		System.out.print("Test Path Sum: " + binaryTree.hasPathSum(rootNode, 307));//Sequence will be from small to big
		System.out.print("\n");
		
		System.out.print("\n");
		System.out.print("Test Flatten: ");//Sequence will be from small to big
		//binaryTree.flatten(rootNode);
		System.out.print("\n");		
		
		
		int maxValue = binaryTree.findMax(rootNode);
		int minValue = binaryTree.findMin(rootNode);
		TreeNode pNode = binaryTree.findParentNode(node9, rootNode);
		
		TreeNode succNode = binaryTree.inorderSucc(node4);
		System.out.print("\n");
		System.out.print("Current Node = " +node4.val+ " Succint Node = " + succNode.val);
		System.out.print("\n");
		
		System.out.print("\n");
		System.out.print("Cover Test = " + binaryTree.cover(node5, node17));
		System.out.print("\n");	
		
		
		System.out.print("\n");
		System.out.print("Min value = " + minValue);
		System.out.print("\n");
		System.out.print("Max value = " + maxValue);
		System.out.print("\n");
		System.out.print("Node " + node9.val + "'s parent node value = " + pNode.val);
		System.out.print("\n");
		
		binaryTree.remove(rootNode);
		rootNode = binaryTree.getRootNode();
		System.out.print(rootNode.val);
		System.out.print("\n");
		binaryTree.preorderTraverseLoop(rootNode);
		
		System.out.print("\n");
		System.out.print("Test two tree identical: " + binaryTree.isSameTree(rootNode, rootNode));//Sequence will be from small to big
		System.out.print("\n");
		
		TreeNode nodeForSumTest1 = new TreeNode(8);
		TreeNode nodeForSumTest2 = new TreeNode(3);
		TreeNode nodeForSumTest3 = new TreeNode(5);
		TreeNode nodeForSumTest4 = new TreeNode(9);
		TreeNode nodeForSumTest5 = new TreeNode(9);
		TreeNode nodeForSumTest6 = new TreeNode(5);
		nodeForSumTest1.setLeftLeafNode(nodeForSumTest2);
		nodeForSumTest1.setRightLeafNode(nodeForSumTest3);
		nodeForSumTest2.setRightLeafNode(nodeForSumTest4);
		nodeForSumTest4.setLeftLeafNode(nodeForSumTest5);
		nodeForSumTest4.setRightLeafNode(nodeForSumTest6);
		int sumNumbersResult = binaryTree.sumNumbers(nodeForSumTest1);
		System.out.print("Test Sum Numbers result: " + sumNumbersResult);
		System.out.print("\n");
		

	}
	
	private static void testGenerateTrees(){
		System.out.print("Test Generate Trees result: ");
		BinaryTree binaryTree = new BinaryTree();
		List<TreeNode> result = binaryTree.generateTrees(0);
		System.out.print("\n");
		
	}
	
	private static void testBuildTree(){
		System.out.print("Test Build Tree: ");
		BinaryTree binaryTree = new BinaryTree();
		int[] inorder = new int[]{1,15,17,25,39,40,45,48,49,50,51,53,69,75,76,77,83,99};
		int[] postorder = new int[]{1,17,15,39,45,49,48,40,25,51,53,69,76,77,99,83,75,50};
		int[] preorder = new int[]{50,25,15,1,17,40,39,48,45,49,75,69,53,51,83,77,76,99};
		
		TreeNode result = binaryTree.buildTree(inorder, postorder);
		TreeNode result2 = binaryTree.buildTree2(preorder, inorder);
		System.out.print("\n");
	}
	
	private static void testMaxPathSum(){
		System.out.print("Test Max Path Sum: ");
		
		BinaryTree binaryTree = new BinaryTree();
		//ArrayList<Node> array = new ArrayList<Node>();
		
		TreeNode rootNode = new TreeNode(-3);
		TreeNode node1 = new TreeNode(50);
		TreeNode node2 = new TreeNode(40);
		TreeNode node3 = new TreeNode(51);
		TreeNode node4 = new TreeNode(48);
		TreeNode node5 = new TreeNode(49);
		TreeNode node6 = new TreeNode(39);
		
		binaryTree.insert(rootNode);
		binaryTree.insert(node1);
		binaryTree.insert(node2);
		binaryTree.insert(node3);
		binaryTree.insert(node4);
		binaryTree.insert(node5);
		binaryTree.insert(node6);
		
		int result = binaryTree.maxPathSum(rootNode);
		
		System.out.print("\n");
	}
}
