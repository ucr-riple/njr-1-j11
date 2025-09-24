package datastructure.binarytree;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;




public class BinaryTree {
	
	protected TreeNode rootNode;
	protected int count;
	
	public BinaryTree(){
		count = 0;
	}
	
	
	public void insert(TreeNode newNode){
		if(rootNode == null){
			this.rootNode = newNode;
		}
		else{
			insertNode(this.rootNode, newNode);
		}
		count++;
	}
	
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
	}
	
	public TreeNode getRootNode(){
		return this.rootNode;
	}
	
	public boolean contains(TreeNode currentNode, TreeNode newNode){
		
		if(currentNode == null)
			return false;
		
		if(currentNode.val == newNode.val){
			return true;
		}
		else{
			if(newNode.val < currentNode.val){
				return contains(currentNode.getLeftLeafNode(), newNode);
			}
			else {
				return contains(currentNode.getRightLeafNode(), newNode);
			}
		}
	}
	
	public TreeNode findNode(TreeNode currentNode, TreeNode newNode){
		if(currentNode == null)
			return null;
		
		if(currentNode.val == newNode.val){
			return currentNode;
		}
		else{
			if(newNode.val < currentNode.val){
				return findNode(currentNode.getLeftLeafNode(), newNode);
			}
			else {
				return findNode(currentNode.getRightLeafNode(), newNode);
			}
		}
	}
	

	public int findMin(TreeNode currentNode){
		if(currentNode == null)
			return -1;
		else{
			if(currentNode.getLeftLeafNode() == null)
				return currentNode.val;
			else {
				return findMin(currentNode.getLeftLeafNode());
			}
		}
	}
	
	public int findMax(TreeNode currentNode){
		if(currentNode == null)
			return -1;
		else {
			if(currentNode.getRightLeafNode() == null)
				return currentNode.val;
			else {
				return findMax(currentNode.getRightLeafNode());
			}
		}
	}
	
	public int height(TreeNode node){
		
		if(node == null)
			return -1;
		
		int leftHeight = height(node.getLeftLeafNode());
		int rightHeight = height(node.getRightLeafNode());
		
		if(leftHeight > rightHeight)
			return leftHeight + 1;
		else 
			return rightHeight + 1;
		
		
	}
	
	public int minDepth(TreeNode rNode) {
        if(rNode == null)
        	return 0;
        
        if(rNode.getLeftLeafNode() == null && rNode.getRightLeafNode() == null)
        	return 1;
        else if(rNode.getLeftLeafNode() == null)
        	return minDepth(rNode.getRightLeafNode()) + 1;
        else if(rNode.getRightLeafNode() == null)
        	return minDepth(rNode.getLeftLeafNode()) + 1;
        
        int leftHeight = minDepth(rNode.getLeftLeafNode());
        int rightHeight = minDepth(rNode.getRightLeafNode());
        
        if(leftHeight < rightHeight)
        	return leftHeight + 1;
        else 
			return rightHeight + 1;
		
    }
	
	public void remove(TreeNode removeNode){
		TreeNode nodeToRemoveNode =  findNode(this.rootNode, removeNode);
		if(nodeToRemoveNode == null)
			return;
		
		TreeNode parentNode = findParentNode(removeNode, this.rootNode);
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
//				if(largestNodeFromLeft.value < tempParent.value){
//					largestNodeFromLeft.setLeftLeafNode(nodeToRemoveNode.getLeftLeafNode().getLeftLeafNode());	
//				}
//				else{
					largestNodeFromLeft.setLeftLeafNode(nodeToRemoveNode.getLeftLeafNode());	
					tempParent.setRightLeafNode(null);
//				}
				
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
		
		--count;
	}

	public TreeNode findParentNode(TreeNode node, TreeNode rNode){
		if(rNode == null)
			return null;
		if(node.val == rNode.val)
			return null;
		
		if(node.val < rNode.val){
			if(rNode.getLeftLeafNode() == null)
				return null;//Tree doesn't contain node
			else{
				if(rNode.getLeftLeafNode().val == node.val)
					return rNode;
				else {
					return findParentNode(node, rNode.getLeftLeafNode());
				}
			}
		}
		else {
			if(rNode.getRightLeafNode() == null)
				return null;//Tree doesn't contain node
			else {
				if(rNode.getRightLeafNode().val == node.val)
					return rNode;
				else {
					return findParentNode(node, rNode.getRightLeafNode());
				}
			}
		}
	}
	
	//Pre-Order Traverse method
	public void preorderTraverseRecursion(TreeNode rNode){
		if(rNode != null){
			System.out.print(rNode.val + ",");
			preorderTraverseRecursion(rNode.getLeftLeafNode());
			preorderTraverseRecursion(rNode.getRightLeafNode());
		}
	}
	
	public void preorderTraverseLoop(TreeNode rNode){
		ArrayList<Integer> array = new ArrayList<>();
		
		Stack<TreeNode> myStack = new Stack<>();
		myStack.push(rNode);
		
		while(myStack.isEmpty() != true){
			TreeNode node = myStack.pop();
			
			array.add(node.val);
			if(node.getRightLeafNode() != null)
				myStack.push(node.getRightLeafNode());
			if(node.getLeftLeafNode() != null)
				myStack.push(node.getLeftLeafNode());
			
		}
		
		for (int i =0; i < array.size(); ++i){
			System.out.print(array.get(i) + ",");
		}
	}
	
	//Post-Order Traverse method
	public void postorderTraverseRecursion(TreeNode rNode){
		if(rNode != null){

			postorderTraverseRecursion(rNode.getLeftLeafNode());
			postorderTraverseRecursion(rNode.getRightLeafNode());
			System.out.print(rNode.val + ",");
		}
	}
	
	public void postorderTraverseLoop(TreeNode rNode){
		ArrayList<Integer> array = new ArrayList<>();
		
		Stack<TreeNode> treeStack = new Stack<>();
		treeStack.push(rNode);
		while(treeStack.empty() != true){
			TreeNode node = treeStack.pop();
			array.add(node.val);
			
			if(node.getLeftLeafNode() != null)
				treeStack.push(node.getLeftLeafNode());
			if(node.getRightLeafNode() != null)
				treeStack.push(node.getRightLeafNode());
			
		}
		
		ArrayList<Integer> returnList = new ArrayList<>();
	
		//Print element from end to start
		for (int i = array.size()-1 ; i >= 0; --i) {
			returnList.add(array.get(i));
			//			System.out.print(array.get(i) + ",");
		}
	}
	
	//In-Order Traverse method
	public void inorderTraverse(TreeNode rNode){
		/*if(rNode != null){
			inorderTraverse(rNode.getLeftLeafNode());
			System.out.print(rNode.val + ",");
			inorderTraverse(rNode.getRightLeafNode());
		}*/
		List<Integer> array = new ArrayList<Integer>();
		inorderTraverseRecur(rNode, array);
		
		inorderTraverseLoop(rNode);
	}
	
	public void inorderTraverseRecur(TreeNode rNode, List<Integer> array){
		if(rNode != null){
			inorderTraverseRecur(rNode.getLeftLeafNode(), array);
			//System.out.print(rNode.val + ",");
			array.add(rNode.val);
			inorderTraverseRecur(rNode.getRightLeafNode(), array);
		}
	}
	
	public void inorderTraverseLoop(TreeNode rNode){
		
		HashMap<TreeNode, Boolean> myHashtable = new HashMap<>();
		ArrayList<Integer> array = new ArrayList<>();
		
		Stack<TreeNode> treeStack = new Stack<>();
		treeStack.push(rNode);
		while(treeStack.empty() != true){
			
			TreeNode node = treeStack.pop();
			if(myHashtable.containsKey(node) || (node.getLeftLeafNode() == null && node.getRightLeafNode() == null)){
				array.add(node.val);	
			}else{
				myHashtable.put(node, true);
				
				if(node.getRightLeafNode() != null)
					treeStack.push(node.getRightLeafNode());
				
				treeStack.push(node);
				

				if(node.getLeftLeafNode() != null)
					treeStack.push(node.getLeftLeafNode());
			}
		}
		
	
		//Print element from end to start
		for (int i = 0; i < array.size(); ++i) {
			System.out.print(array.get(i) + ",");

		}
	}
	
    public boolean isValidBST(TreeNode root) {
        if(root == null)
            return true;
		return isValidBSTHelper(root, new ArrayList<Integer>());

    }
    
    private boolean isValidBSTHelper(TreeNode rNode, List<Integer> array){//Modified inorder traversal
		if(rNode != null){
			if(isValidBSTHelper(rNode.getLeftLeafNode(), array) == false)
			    return false;
			    
			if(array.size() > 0 && rNode.val <= array.get(array.size()-1))
			    return false;
			    
			array.add(rNode.val);
			
			if(isValidBSTHelper(rNode.getRightLeafNode(), array) == false)
			    return false;
		    
		}
		return true;
	}
	
	//Breadth First Traverse method
	public void breadthFirstTraverse(TreeNode rNode){
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		
		
		while(rNode != null){
			System.out.print(rNode.val + ",");
			
			if(rNode.getLeftLeafNode() != null){
				queue.offer(rNode.getLeftLeafNode());
			}
			if(rNode.getRightLeafNode() != null){
				queue.offer(rNode.getRightLeafNode());
			}
			
			if(queue.isEmpty() == false){
				rNode = queue.poll();
			}
			else{
				rNode = null;
			}
		}
	}
		
	//Level-Order Traverse method
	public void levelOrderTraverse(TreeNode rNode){

		ArrayList<ArrayList<Integer>> array = new ArrayList<>();
		if(rNode == null)
		    return;
		Queue<TreeNode> currentLevel = new LinkedList<TreeNode>();
		Queue<TreeNode> nextLevel = new LinkedList<TreeNode>();
		ArrayList<Integer> tempArray = new ArrayList<>();
		
		while(rNode != null){

			tempArray.add(rNode.val);
			
			if(rNode.getLeftLeafNode() != null){
				nextLevel.add(rNode.getLeftLeafNode());
			}
			if(rNode.getRightLeafNode() != null){
				nextLevel.add(rNode.getRightLeafNode());
			}

			
			if(currentLevel.isEmpty() == false){
				rNode = currentLevel.poll();
			}
			else{
				
				ArrayList<Integer> storeArray = new ArrayList<>(tempArray);
				/*System.out.print("[");
				for(Integer i : tempArray){
					storeArray.add(i);
					System.out.print(i + ",");
				}
				System.out.print("]");*/				
				array.add(storeArray);
				tempArray.clear();
				
				if(nextLevel.isEmpty() == false){

					for(TreeNode n : nextLevel) {
						currentLevel.add(n);
				    }
					
					nextLevel.clear();
					rNode = currentLevel.poll();
				}
				else{
					break;
				}
			}

		}
	}
	
	//Zigzag-Order Traverse method
	public void zigzagLevelOrderTraverse(TreeNode rNode){

		Stack<TreeNode> currentLevel = new Stack<TreeNode>();
		Stack<TreeNode> nextLevel = new Stack<TreeNode>();
		boolean leftToRight = true;
		
		while(rNode != null){
			System.out.print(rNode.val + ",");
			
			if(leftToRight){
				if(rNode.getLeftLeafNode() != null){
					nextLevel.push(rNode.getLeftLeafNode());
				}
				if(rNode.getRightLeafNode() != null){
					nextLevel.push(rNode.getRightLeafNode());
				}
			}else {
				if(rNode.getRightLeafNode() != null){
					nextLevel.push(rNode.getRightLeafNode());
				}
				if(rNode.getLeftLeafNode() != null){
					nextLevel.push(rNode.getLeftLeafNode());
				}
			}


			
			if(currentLevel.isEmpty() == false){
				rNode = currentLevel.pop();
			}
			else{
				if(nextLevel.isEmpty() == false){
					currentLevel = (Stack<TreeNode>)nextLevel.clone();// = nextLevel;
					
					nextLevel.clear();
					leftToRight = !leftToRight;
					rNode = currentLevel.pop();
				}
				else{
					break;
				}
			}

		}
	}

	
	public TreeNode inorderSucc(TreeNode n){
		if(n == null)
			return null;
		
		if(n.getRightLeafNode() != null){
			return findTheMostLeftNode(n.getRightLeafNode());
		}
		else{
			TreeNode parentNode = findParentNode(n, this.rootNode);
			
			while (parentNode != null) {
				if( n.val < parentNode.val){
					break;
				}
				n = parentNode;
				parentNode = findParentNode(n, this.rootNode);
			}
			
			
			return parentNode;
		}
		
		
		
	}
	
	public TreeNode findTheMostLeftNode(TreeNode n){
		
		if(n.getLeftLeafNode() == null)
			return n;
		else 
			return findTheMostLeftNode(n.getLeftLeafNode());
	}
	
	public boolean cover(TreeNode root, TreeNode n){
		if(root.equals(n))
			return true;	
		else{
			boolean leftCover = false;
			boolean rightCover = false;
			if(root.getLeftLeafNode() != null)
				leftCover = cover(root.getLeftLeafNode(), n);
			if(root.getRightLeafNode() != null)
				rightCover = cover(root.getRightLeafNode(), n);
			
			return (leftCover || rightCover);
		}
		
//		return false;
	}

	public boolean isSameTree(TreeNode p, TreeNode q) {
        
		boolean result = true;
		
		if(p == null && q == null)
			return true;
		else if(p == null && q != null)
			return false;
		else if(p != null && q == null)
			return false;
		
		result &= (p.val == q.val);
		if(result == false)
			return false;
		
		
		 if((p.getLeftLeafNode() == null && q.getLeftLeafNode() != null) || (p.getLeftLeafNode() != null && q.getLeftLeafNode() == null))
			return false;
		 else if((p.getLeftLeafNode() != null && q.getLeftLeafNode() != null) && (!isSameTree(p.getLeftLeafNode(), q.getLeftLeafNode())))
			 return false;
		
		 if((p.getRightLeafNode() == null && q.getRightLeafNode() != null) || (p.getRightLeafNode() != null && q.getRightLeafNode() == null))
			return false;
		 else if((p.getRightLeafNode() != null && q.getRightLeafNode() != null) && (!isSameTree(p.getRightLeafNode(), q.getRightLeafNode())))
			 return false;
		

		
		return true;
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        
    	if(root.getLeftLeafNode() == null && root.getRightLeafNode() == null){
    		if(sum == root.val)
    			return true;
    		else 
				return false;
    	}
    	
    	boolean resultLeft = false;
    	boolean resultRight = false;
    	
    	if(root.getLeftLeafNode() != null){
    		resultLeft = hasPathSum(root.getLeftLeafNode(), sum - root.val);
    	}
    	if(root.getRightLeafNode() != null){
    		resultRight = hasPathSum(root.getRightLeafNode(), sum - root.val);
    	}
    	
    	return (resultLeft | resultRight);
    }
    
    public List<List<Integer>> pathSumII(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> path = new ArrayList<Integer>();
        
        if(root == null)
        	return result;

        pathSumHelper(root, sum, path, result);
        
        return result;
    }
    
    private void pathSumHelper(TreeNode root, int sum, List<Integer> path, List<List<Integer>> totalPathResult) {
    
    	//if(root != null)
    		path.add(root.val);
		
		if(root.getLeftLeafNode() == null && root.getRightLeafNode() == null){
			if(sum == root.val){
				List<Integer> pathToStore = new ArrayList<Integer>(path);
				totalPathResult.add(pathToStore);
			}
	    	path.remove(path.size()-1);
			return;
		}

    
    	
    	if(root.getLeftLeafNode() != null){
    		pathSumHelper(root.getLeftLeafNode(), sum - root.val, path, totalPathResult);
    	}
    	if(root.getRightLeafNode() != null){
    		pathSumHelper(root.getRightLeafNode(), sum - root.val,  path, totalPathResult);
    	}
    	
    	path.remove(path.size()-1);
	}
    
    public static boolean isSymmetric(TreeNode root) {
        
    	if(root == null)
    		return true;
    	
    	if(root.getLeftLeafNode() != null && root.getRightLeafNode() == null){
    		return false;
    	}
    	if(root.getLeftLeafNode() == null && root.getRightLeafNode() != null){
    		return false;
    	}
    	
    	return symmetric(root.getLeftLeafNode(), root.getRightLeafNode());
    }
    
    private static boolean symmetric(TreeNode p, TreeNode q){
    	
		boolean result = true;
    	
    	if(p == null && q == null)
    		return true;
    	
		result &= (p.val == q.val);
		if(result == false)
			return false;
		
		if(p.getLeftLeafNode() != null && q.getRightLeafNode() != null)
			result &= symmetric(p.getLeftLeafNode(), q.getRightLeafNode());
		else if(p.getLeftLeafNode() == null && q.getRightLeafNode() != null)
			return false;
		else if(p.getLeftLeafNode() != null && q.getRightLeafNode() == null)
			return false;	
    	
		if(result == false)
			return false;
		
		if(p.getRightLeafNode() != null && q.getLeftLeafNode() != null)
			result &= symmetric(p.getRightLeafNode(), q.getLeftLeafNode());
		else if(p.getRightLeafNode() == null && q.getLeftLeafNode() != null)
			return false;
		else if(p.getRightLeafNode() != null && q.getLeftLeafNode() == null)
			return false;
    	
    	return result;
    }

    public static TreeNode flatten(TreeNode root){
    	   	
    	flattenHelper(root, null);
    	
    	
    		
    	return root;
    }
    
    private static TreeNode flattenHelper(TreeNode root, TreeNode right){
    	
    	TreeNode currentRight = null;
    	if(root.getRightLeafNode() != null){
    		currentRight = flattenHelper(root.getRightLeafNode(), right);
    	
    	}
    	
    	TreeNode currentLeft = null;
    	if(root.getLeftLeafNode() != null){
    		if(currentRight != null)
    			currentLeft = flattenHelper(root.getLeftLeafNode(), currentRight);
    		else
    			currentLeft = flattenHelper(root.getLeftLeafNode(), right);
    		
    	}
    	
    	if(currentLeft != null){
    		root.setRightLeafNode(currentLeft);
    		root.setLeftLeafNode(null);
    	
    	}
    	
    	if(currentLeft == null && currentRight == null)
    		root.setRightLeafNode(right);
    	return root;
    	
    }
    
    public static int numTrees(int n) {
    	/*
    	 * Solution:
    	 * DP
    	 * a BST can be destruct to root, left subtree and right subtree.
    	 * if the root is fixed, every combination of unique left/right subtrees forms
    	 * a unique BST.
    	 * Let a[n] = number of unique BST's given values 1..n, then
    	 * a[n] = a[0] * a[n-1]     // put 1 at root, 2...n right
    	 *      + a[1] * a[n-2]     // put 2 at root, 1 left, 3...n right
    	 *      + ...
    	 *      + a[n-1] * a[0]     // put n at root, 1...n-1 left
    	 */
    	 /*
    	 f(0) = 1
    	 f(1) = 1
    	 f(2) = 2
    	 f(n) = f(n-1) * f(1 -1) + f(n-2) * f(2 -1) + f(n-3) * f(3-1) + ... + f(n-n) * f(n-1)*/
    	
        int[] nums = new int[n+1];
    	nums[0] = 1;
    	nums[1] = 1;
    	
    	for(int i = 2 ; i <= n; ++i){
    		int num = 0;
    		for(int j = 0; j < i; ++j){
    			num += nums[j] * nums[i-j-1];
    		}
    		nums[i] = num;
    	}
    	
    	return nums[n];
    }
 

    public static List<TreeNode> generateTrees(int n) {
        
    	List<TreeNode> result = new ArrayList<TreeNode>();
    	if(n == 0){
    		result.add(null);
    		return result;
    	}
    		
    	result = generateTreesHelper(1, n);	
    	return result;
    }
    
    private static List<TreeNode> generateTreesHelper(int startValue, int endValue){
    	
    	List<TreeNode> result = new ArrayList<TreeNode>();
    	
    	for(int i = startValue; i <= endValue; ++i){
    		
    		List<TreeNode> leftRootCollection = generateTreesHelper(startValue, i-1);
    		List<TreeNode> rightRootCollection = generateTreesHelper(i+1, endValue);
    	
    		if(leftRootCollection.size() == 0 && rightRootCollection.size() == 0){
    			TreeNode node = new TreeNode(i);
    			result.add(node);
    		}
    		else if(leftRootCollection.size() == 0){
    			for(int k = 0; k < rightRootCollection.size(); k++){
    				TreeNode node = new TreeNode(i);
    				node.setRightLeafNode(rightRootCollection.get(k));
    				result.add(node);
    			}
    		}
    		else if(rightRootCollection.size() == 0){
    			for(int k = 0; k < leftRootCollection.size(); k++){
    				TreeNode node = new TreeNode(i);
    				node.setLeftLeafNode(leftRootCollection.get(k));
    				result.add(node);
    			}
    		}
    		else{ 		
	    		for(int j = 0; j < leftRootCollection.size(); j++){
	    			for(int k = 0; k < rightRootCollection.size(); k++){
	    				TreeNode node = new TreeNode(i);
	    				node.setLeftLeafNode(leftRootCollection.get(j));
	    				node.setRightLeafNode(rightRootCollection.get(k));
	    				result.add(node);
	    			}			
	    		}
    		}	
    	}
    	return result;
    }
    
    
    public int sumNumbers(TreeNode root) {
        if(root == null)
            return 0;
        
        ArrayList<Integer> array = new ArrayList<>();
        array.add(root.val);
    	int result = sumNumbersHelper(root, array);
    	return result;
    }
    
    private int sumNumbersHelper(TreeNode root, ArrayList<Integer> array){
        int rightNum = 0;
        int leftNum = 0;
        
        if(root.getRightLeafNode() != null){
        	array.add(root.getRightLeafNode().val);
            rightNum = sumNumbersHelper(root.getRightLeafNode(), array);
        }

        

        if(root.getLeftLeafNode() != null){
        	array.add(root.getLeftLeafNode().val);
            leftNum = sumNumbersHelper(root.getLeftLeafNode(), array);
        }
        

        if(root.getRightLeafNode() == null && root.getLeftLeafNode() == null){
        	int value = 0;
        	
        	for(int i = 0, power = 1; i < array.size(); ++i, power*=10){
        		value += array.get(array.size() - 1 - i) * power;
        	}
        	array.remove(array.size() -1);
        	
            return value;
        }
        else{
        	array.remove(array.size() -1);
        	return rightNum + leftNum;
        }
    	
    	
    }

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
    	
    	if(inorder.length ==0 || postorder.length == 0)
    		return null;
    	
    	if(inorder.length != postorder.length)
    		return null;
    	
    	Map<Integer, Integer> myHashtable = new HashMap<Integer, Integer>();
    	
    	for(int i = 0; i < inorder.length; i++){
    		myHashtable.put(inorder[i], i);
    	}
    	
    	TreeNode rootNode =new TreeNode(postorder[postorder.length-1]);
    	Stack<TreeNode> stack = new Stack<>(); 
    	stack.push(rootNode);
    	//利用stack存left node還沒被佔有的node
    	
    	for(int i = postorder.length -2; i >= 0 ; i--){
    		TreeNode newNode = new TreeNode(postorder[i]);
    		
    		int indexOfNew = myHashtable.get(postorder[i]);
    		
    		TreeNode topStackNode = stack.pop();
    		int indexOfTop = myHashtable.get(topStackNode.val);
    		if(indexOfNew > indexOfTop){
    			
    			topStackNode.setRightLeafNode(newNode);
    			stack.push(topStackNode);
    			stack.push(newNode);
    		}
    		else{
    		
	    		while(topStackNode != null){
	    			
	    			
	    			if(stack.size() != 0){
	    				TreeNode nextTopNode = stack.peek();
		    			
		    			int indexOfNextTop = myHashtable.get(nextTopNode.val);
		    			
		    			if(indexOfNew > indexOfNextTop){
		    				
		    				topStackNode.setLeftLeafNode(newNode);
		    				stack.push(newNode);
		    				break;
		    			}
		    			else{
		    				topStackNode = stack.pop();
		    			}
	    			}
	    			else{
	    				topStackNode.setLeftLeafNode(newNode);
	    				stack.push(newNode);
	    				break;
	    			}
	    		}
    		}
    		
    		
    		/*while(currentNode != null){
    			int indexOfOld = myHashtable.get(currentNode.value);
    			
	    		if(indexOfNew < indexOfOld){
	    			if(currentNode.getLeftLeafNode() == null){
	    				currentNode.setLeftLeafNode(new Node(postorder[i]));
	    				break;
	    			}
	    			else{
	    				currentNode = currentNode.getLeftLeafNode();
	    			}
	    			
	    		}
	    		else{
	    			if(currentNode.getRightLeafNode() == null){
	    				currentNode.setRightLeafNode(new Node(postorder[i]));
	    				break;
	    			}
	    			else{
	    				currentNode = currentNode.getRightLeafNode();
	    			}
	    		}
    		}	*/
    	}
    	
    	return rootNode;
    }
    
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
    	if(inorder.length ==0 || preorder.length == 0)
    		return null;
    	
    	if(inorder.length != preorder.length)
    		return null;
    	
    	Map<Integer, Integer> myHashtable = new HashMap<Integer, Integer>();
    	
    	for(int i = 0; i < inorder.length; i++){
    		myHashtable.put(inorder[i], i);
    	}
    	
    	TreeNode rootNode =new TreeNode(preorder[0]);
    	Stack<TreeNode> stack = new Stack<>(); 
    	stack.push(rootNode);
    	
    	
    	for(int i = 1; i < preorder.length ; i++){
    		TreeNode newNode = new TreeNode(preorder[i]);
    		
    		int indexOfNew = myHashtable.get(preorder[i]);
    		
    		TreeNode topStackNode = stack.pop();
    		int indexOfTop = myHashtable.get(topStackNode.val);
    		if(indexOfNew < indexOfTop){
    			
    			topStackNode.setLeftLeafNode(newNode);
    			stack.push(topStackNode);
    			stack.push(newNode);
    		}
    		else{
    		
	    		while(topStackNode != null){
	    			
	    			
	    			if(stack.size() != 0){
	    				TreeNode nextTopNode = stack.peek();
		    			
		    			int indexOfNextTop = myHashtable.get(nextTopNode.val);
		    			
		    			if(indexOfNew < indexOfNextTop){
		    				
		    				topStackNode.setRightLeafNode(newNode);
		    				stack.push(newNode);
		    				break;
		    			}
		    			else{
		    				topStackNode = stack.pop();
		    			}
	    			}
	    			else{
	    				topStackNode.setRightLeafNode(newNode);
	    				stack.push(newNode);
	    				break;
	    			}
	    		}
    		}
    		
    	}
    	
    	return rootNode;
    }

    public static int maxPathSum(TreeNode root) {
    	
    	int result = maxPathSumHelper(root)[1];
        return result;
    }
    
    public static int[] maxPathSumHelper(TreeNode root) {
        int[] result = new int[2];//[0] = single path sum; [1] = double path sum
    	
        
        if(root == null){
        	result[0] = 0;
        	result[1] = Integer.MIN_VALUE;
        	return result;
        }
        
        int[] leftStatus = maxPathSumHelper(root.getLeftLeafNode());
        int[] rightStatus = maxPathSumHelper(root.getRightLeafNode());
        int left = Math.max(0, leftStatus[0]);
        int leftMax = leftStatus[1];
        //int leftMax = Math.max(0, leftStatus[1]);
        int right = Math.max(0, rightStatus[0]);
        int rightMax = rightStatus[1];
        //int rightMax = Math.max(0, rightStatus[1]);
        result[0] = Math.max(left, right) + root.val;

        result[1] = Math.max(Math.max(leftMax, rightMax), root.val + left + right);
        return result;
        /*
        if(root.getLeftLeafNode() == null && root.getRightLeafNode() == null){
        	result[0] = root.value;
        	result[1] = root.value;
        	//result[2] = root.value;
        }
        else if(root.getLeftLeafNode() == null && root.getRightLeafNode() != null){
        	int rightSinglePathSum = maxPathSumHelper(root.getRightLeafNode())[0];
        	int rightDoublePathSum = maxPathSumHelper(root.getRightLeafNode())[1];
        	
        	result[0] = root.value + rightSinglePathSum;
        	result[1] = Math.max(root.value+ rightSinglePathSum, rightDoublePathSum);
        	//result[2] = Math.max(a, b)
        }
        else if(root.getLeftLeafNode() != null && root.getRightLeafNode() == null){
        	int leftSinglePathSum = maxPathSumHelper(root.getLeftLeafNode())[0];
        	int leftDoublePathSum = maxPathSumHelper(root.getLeftLeafNode())[1];
        	
        	result[0] = root.value + leftSinglePathSum;
        	result[1] = Math.max(root.value+ leftSinglePathSum, leftDoublePathSum);
        }
        else if(root.getLeftLeafNode() != null && root.getRightLeafNode() != null){
        	int rightSinglePathSum = maxPathSumHelper(root.getRightLeafNode())[0];
        	int rightDoublePathSum = maxPathSumHelper(root.getRightLeafNode())[1];
        	int leftSinglePathSum = maxPathSumHelper(root.getLeftLeafNode())[0];
        	int leftDoublePathSum = maxPathSumHelper(root.getLeftLeafNode())[1];
        	
        	result[0] = root.value + rightSinglePathSum + leftSinglePathSum;
        	result[1] = Math.max(Math.max(root.value + rightSinglePathSum + leftSinglePathSum, rightDoublePathSum), leftDoublePathSum);
        }*/
        	
        
        
    	//return result;
    }
    

}
