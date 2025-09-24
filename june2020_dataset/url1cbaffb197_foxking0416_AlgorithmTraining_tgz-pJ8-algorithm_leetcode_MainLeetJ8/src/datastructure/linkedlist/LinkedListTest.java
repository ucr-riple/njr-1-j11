package datastructure.linkedlist;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.event.ListDataEvent;
import javax.swing.tree.TreeNode;

import datastructure.binarytree.BinaryTree;


public class LinkedListTest {

	 
	
	public void addList(){
		
		ListNode list1 = new ListNode(3);
		list1.push(1);
		list1.push(5);
		
		ListNode list2 = new ListNode(8);
		list2.push(0);
		list2.push(8);
		
		int value = 0;

		
		
		int pop1 = getValue(list1);
		//int pop2 = list1.pop();
		System.out.print(pop1 + "\n");
		//System.out.print(pop2);
		
	}
	
	private int getValue(ListNode list){
		
//		if(list.next == null)
//			return list.pop();
//		
//		int preValue = getValue(list);
//		int currentValue = list.pop();
//		
//		return preValue + currentValue;
		return 0;
	}
	
	//if the linkedlist could make a circle 
	//     0--> 1 --> 2 --> 3 --> 4 --> 5 --> 6
	//                ^                       |            
	//                |_______________________|           
	//This function is to find the start node of the circle which is node 2
	public void findBeginningTest(){
		ListNode node0 = new ListNode(0);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		ListNode node6 = new ListNode(6);
		
		node0.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = node2;
		
		ListNode startNode = findBeginning(node0);
		System.out.print("Start node value = " + startNode.val);
	}
	
    public boolean hasCycle(ListNode head) {
        if(head == null)
            return false;
        ListNode n1 = head;
		ListNode n2 = head;
		
		while(n2 != null && n2.next != null){
			n1 = n1.next;
			n2 = n2.next.next;
			if(n1 == n2)
				return true;
		}
		
        return false;
    }
	
	private ListNode findBeginning(ListNode head){
		
		ListNode n1 = head;
		ListNode n2 = head;
		
		while(n2.next != null){
			n1 = n1.next;
			n2 = n2.next.next;
			if(n1 == n2)
				break;
		}
		
		n1 = head;
		
		while (n1 != n2) {
			n1 = n1.next;
			n2 = n2.next;
			
		}
		return n2;
	}
	
	public static void swapPairsTest(){
		ListNode node0 = new ListNode(0);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		ListNode node6 = new ListNode(6);
		
		node0.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		swapPairs(node0);
	}
	
	private static void swapPairs(ListNode head){
		if(head == null)
			return;
		
		printLinkedList(head);
		ListNode startNode = new ListNode(-1);
		startNode.next = head;
		ListNode currentNode = startNode;
//		head = nextNode;
		
		while(currentNode.next != null && currentNode.next.next != null){

			currentNode =	swapNode(currentNode.next, currentNode.next.next);
			currentNode = currentNode.next; 

		}
		printLinkedList(startNode);
	}
	
	private static ListNode swapNode(ListNode n1, ListNode n2){
		if(n1 == null || n2 == null)
			return null;
		else {
			n1.next = n2.next;
			n2.next = n1;
			return n2;
		}
	}
	
	//Use to print the linklist node
	private static void printLinkedList(ListNode head){
		
		while(head != null){
			System.out.print(head.val + ", ");
			head = head.next;
		}
		System.out.print("\n");
	}

	public static void deleteDuplicatesTest() {
		ListNode node0 = new ListNode(1);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(2);
		ListNode node4 = new ListNode(3);
		ListNode node5 = new ListNode(4);
		ListNode node6 = new ListNode(5);
		
		node0.next = node1;
		node1.next = node2;
		node2.next = node3;
		//node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		
		ListNode result =	deleteDuplicates_II(node0);
		while(result != null){
			System.out.print(result.val + ", ");
			result = result.next;
		}
		
		int a = 0;
	}
	
	private static ListNode deleteDuplicates(ListNode head) {
        if(head == null)
            return null;
        HashMap<Integer, Boolean> myHashtable = new HashMap<>();
        
        ListNode node = head;
        myHashtable.put(node.val, true);
        
        while(node != null && node.next != null){
            if(myHashtable.containsKey(node.next.val) != true){
				myHashtable.put(node.next.val, true);
				node = node.next;
            }
			else {
				
				node.next = node.next.next;
			}
			

        }
        
        return head;
    }
	
	private static ListNode deleteDuplicates_Easy(ListNode head) {
        if(head == null)
            return null;
        
        
        ListNode preHead = new ListNode(0);
        preHead.next = head;
        ListNode currentNode = head;
        
        while(currentNode.next != null){
        	if(currentNode.val == currentNode.next.val){
        		currentNode.next = currentNode.next.next;
        	}
        	else{
        		currentNode = currentNode.next;
        	}
        }
        
        
        
        return preHead.next;
    }
	private static ListNode deleteDuplicates_II(ListNode head) {
		if(head == null)
			return null;
    
    
		ListNode preHead = new ListNode(0);
	    preHead.next = head;
	    ListNode currentNode = preHead;
	    
	    while(currentNode != null && currentNode.next != null){
	    	ListNode n1 = currentNode.next;
	        if(n1.next != null && n1.val == n1.next.val){
	            while(n1.next != null && n1.val == n1.next.val){
	                n1 = n1.next;
	            }
	                
	            currentNode.next = n1.next;
	        }
	        else
	        	currentNode = currentNode.next;
	        
	
	    }
	    
	    
	    
	    return preHead.next;
	}
	
	public static void testMergeTwoLists(){
		ListNode node0 = new ListNode(1);
		ListNode node1 = new ListNode(3);
		ListNode node2 = new ListNode(5);
		ListNode node3 = new ListNode(7);
		ListNode node4 = new ListNode(2);
		ListNode node5 = new ListNode(4);
		ListNode node6 = new ListNode(6);
		
		node0.next = node1;
		node1.next = node2;
		node2.next = node3;
//		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		printLinkedList(node0);
		printLinkedList(node4);		
		
		ListNode afterMerge =	mergeTwoLists(node0, node4);
		printLinkedList(afterMerge);		
	}
	
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
	    if(l1 == null)
	        return l2;
	    if(l2 == null)
	        return l1;
//	    if(l1 == null && l2 == null)
//	        return null;
	        
	    boolean small2Big = true;
	    if(l1.next != null){
	        if(l1.val > l1.next.val)
	            small2Big = false;
	    }
	     
	    if(l2.next != null){
	        if(l2.val > l2.next.val)
	            small2Big = false;
	    }   
	        
	    ListNode newHead = new ListNode(0);
	    ListNode newHeadCopy = newHead;
	    
	        while(l1 != null && l2 != null){
	        	if(small2Big){        	
		            if(l1.val < l2.val){
		            	newHead.next = l1;
		            	l1 = l1.next;
		            }
		            else{
		            	newHead.next = l2;
		            	l2 = l2.next;
		            }
	        	}else{
		            if(l1.val > l2.val){
		            	newHead.next = l1;
		            	l1 = l1.next;
		            }
		            else{
		            	newHead.next = l2;
		            	l2 = l2.next;
		            }
	        	}
	            newHead = newHead.next;
	          
	        }
	        
	        if(l1 == null){
	        	newHead.next = l2;
	        }else if(l2 == null)
	        	newHead.next = l1;
	    
	    
	    return newHeadCopy.next;
	}

	//Not Finished Yet
	public static ListNode sortListTest() {
        
		ListNode node0 = new ListNode(3);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(4);
		ListNode node3 = new ListNode(5);
		ListNode node4 = new ListNode(2);
		ListNode node5 = new ListNode(9);
		ListNode node6 = new ListNode(7);
		
		node0.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		
		List<ListNode> myArray = new ArrayList<>();
		while(node0 != null){
			myArray.add(node0);
			node0 = node0.next;
		}
		
		List<ListNode> newArray = sortListHelper(myArray, 0, myArray.size()-1);
		
		return null;
    }
	
	private static List<ListNode> sortListHelper(List array, int p, int r){
		
		if(p>=r)
			return array;	
		
		int q = (p+r) / 2;
		
		sortListHelper(array, p, q);
		sortListHelper(array, q+1, r);
		return merge(array, p, q, r);
		
	}

	private static List<ListNode> merge(List array, int p, int q, int r){
		
		List<ListNode> copyArrayFront = new ArrayList<>();
		List<ListNode> copyArrayBack = new ArrayList<>();		
		
		for(int i = 0 ; i < q - p + 1; i++){ // copy from p to q, include p and q
			copyArrayFront.add((ListNode) array.get(i + p));
		}
		for(int i = 0; i < r - q; i++){ // copy form q+1 to r (include q+1 and r)
			copyArrayBack.add((ListNode) array.get(i + q + 1));
		}
		
		copyArrayFront.add(new ListNode(Integer.MAX_VALUE));
		copyArrayBack.add(new ListNode(Integer.MAX_VALUE));
		
		
		for(int k = p, i = 0, j = 0 ; k <= r ;k++){
			if(copyArrayFront.get(i).val <= copyArrayBack.get(j).val){
				array.set(k, copyArrayFront.get(i));
				i++;
			}
			else{
				array.set(k, copyArrayFront.get(j));
				j++;
			}
		}
		
		return array;
	}
	
	public static void reorderList() {
		ListNode head = new ListNode(1);
		ListNode node1 = new ListNode(2);
		ListNode node2 = new ListNode(3);
		ListNode node3 = new ListNode(4);
		ListNode node4 = new ListNode(5);
		ListNode node5 = new ListNode(6);
		ListNode node6 = new ListNode(7);
		
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		
		ListNode pre = new ListNode(0);
		ListNode nodeNext = head;
		ListNode nodeNextNext = head;
		
		//Fine the middle of the link list
		while(nodeNextNext.next != null && nodeNextNext.next.next != null){
			nodeNextNext = nodeNextNext.next.next;
			nodeNext = nodeNext.next;
		}
		ListNode linkList2Head = nodeNext.next;
		nodeNext.next = null;
		
		printLinkedList(head);
		ListNode list2Reverse = reverseList(linkList2Head);
		printLinkedList(head);
		printLinkedList(list2Reverse);
		pre.next = head;
		while(list2Reverse != null){
			

			ListNode temp = head.next;
			head.next = list2Reverse;
			head = temp;
			
			ListNode temp2 = list2Reverse.next;
			list2Reverse.next = temp;
			list2Reverse = temp2;
		}
		printLinkedList(pre.next);
    }
	
	private static ListNode reverseList(ListNode head){
		
		if(head == null || head.next == null)
			return head;
		
		ListNode prev  = new ListNode(0);
		prev.next = head;
	    head = prev ;
		
		ListNode currentNode = prev.next;
		while(currentNode.next != null){
			ListNode temp = currentNode.next;
			
			currentNode.next = temp.next;
	        temp.next = prev.next;
	        prev.next = temp;

		}
		
		return prev.next;
	}
		
	public static void addTwoNumbersTest(){
		ListNode la1 = new ListNode(9);


		
		ListNode lb1 = new ListNode(1);
		ListNode lb2 = new ListNode(9);
		ListNode lb3 = new ListNode(9);
		ListNode lb4 = new ListNode(9);
		ListNode lb5 = new ListNode(9);
		ListNode lb6 = new ListNode(9);
		ListNode lb7 = new ListNode(9);
		lb1.next = lb2;
		lb2.next = lb3;
		lb3.next = lb4;
		lb4.next = lb5;
		lb5.next = lb6;
		lb6.next = lb7;
		
		
		ListNode result = addTwoNumbers(la1, lb1);
		int a = 0;
	}
	
	private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		
		if(l1 == null && l2 == null)
            return null;
		ListNode prehead = new ListNode(0);
        int pre = 0;

        ListNode currentNode = prehead;
        while(l1 != null || l2 != null){
            int value1 = 0;
            int value2 = 0;
            
            if(l1 != null)
                value1 = l1.val;
            if(l2 != null)
                value2 = l2.val;
            
            int value = value1 + value2 + pre;
            
            if(value >= 10){
                pre = 1;
                value -= 10;
            }
            else
                pre = 0;
            
            ListNode node = new ListNode(value);
            currentNode.next = node;
            currentNode = currentNode.next;
            
            if(l1 != null)
            	l1 = l1.next;
            if(l2 != null)
            	l2 = l2.next;
        }
        
        if(pre != 0){
        	ListNode node = new ListNode(pre);
        	currentNode.next = node;
        }
        
        return prehead.next;
	}
	
	public static ListNode partition(ListNode head, int x) {
        
		ListNode preHeadGreater = new ListNode(0);
		ListNode greaterNode = preHeadGreater;
		ListNode preHeadLess = new ListNode(0);
		ListNode lessNode = preHeadLess;

		while(head != null){
			if(head.val < x){
				lessNode.next = head;
				lessNode = lessNode.next;
			}
			else{
				greaterNode.next = head;
				greaterNode = greaterNode.next;
			}
			head = head.next;
		}
		
		greaterNode.next = null;
		lessNode.next = preHeadGreater.next;
		return preHeadLess.next;		
    }

	public static ListNode rotateRight(ListNode head, int n) {

		ListNode fastNode = head;
		ListNode slowNode = head;
		int count = 0;
		while(count < n)		{
			count++;
			if(fastNode.next == null){
				
				fastNode = head;
				n = n % count;//加速 知道一圈有幾個node之後就不用真的繞了一圈再去判斷是不是繞了一圈			
				count = 0;//剛好繞一圈
			}
			else
				fastNode = fastNode.next;
		}
		
		if(count != 0){//如果是剛好繞一圈 那就直接回傳原本的head就行了
			while(fastNode.next != null){
				fastNode = fastNode.next;
				slowNode = slowNode.next;
			}
			ListNode newHead = slowNode.next;
			slowNode.next = null;
			fastNode.next = head;
			
			return newHead;
			
		}
		else
			return head;

    }

	public static ListNode reverseBetween(ListNode head, int m, int n) {
		
		if(head == null)
			return null;
		if(m == n)
			return head;
		
		ListNode preHead = new ListNode(0);
		preHead.next = head;
		ListNode preSubHeadNode = preHead;
		ListNode subHeadNode = head;
		ListNode subTailNode = head;


		int count = 1;
		
		while(count <= n){
			
			if(count < m){
				subTailNode = subTailNode.next;
				subHeadNode = subHeadNode.next;
				preSubHeadNode = preSubHeadNode.next;
			}
			else if(count > m){
				ListNode nextNode = subTailNode.next;
				subTailNode.next = nextNode.next;
				nextNode.next = subHeadNode;
				subHeadNode = nextNode;
				
				preSubHeadNode.next = subHeadNode;
			}

			count++;
		}
		
		return preHead.next;
	}
	
	public static ListNode reverseBetween2(ListNode head, int m, int n) {
        
		if(head == null)
			return null;
		if(m == n)
			return head;
		
		ListNode preHead = new ListNode(0);
		preHead.next = head;
		ListNode preNodeM = preHead;
		ListNode preNodeN = preHead;
		
		
		int count = 0;
		
		while(count < n-1){
			
			if(count < m-1){
				preNodeM = preNodeM.next;
				preNodeN = preNodeN.next;
			}
			else{
				preNodeN = preNodeN.next;
			}
			count++;
		}
		
		if(n-m == 1){
			ListNode nodeM = preNodeM.next;
			ListNode nodeN = preNodeN.next;
			nodeM.next = nodeN.next;
			nodeN.next = nodeM;
			preNodeM.next = nodeN;
			
			
		}
		else{
		
			ListNode temp = preNodeN.next.next;
			preNodeN.next.next = preNodeM.next.next;
			preNodeM.next.next = temp;
			ListNode temp2 = preNodeN.next;
			preNodeN.next = preNodeM.next;
			preNodeM.next = temp2;
		}
		
		return preHead.next;
    }

	public static ListNode removeNthFromEnd(ListNode head, int n) {
        
		if(n == 0)
			return head;
		
		ListNode preHead = new ListNode(0);
		preHead.next = head;
		ListNode slowNode = preHead;
		ListNode fastNode = preHead;
		for(int i = 0; i < n; i++){
			fastNode = fastNode.next;
		}
		
		while(fastNode.next != null){
			fastNode = fastNode.next;
			slowNode = slowNode.next;
		}
		
		slowNode.next = slowNode.next.next;

		return preHead.next;
    }
	
	public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		
		if(headA == null || headB == null)
			return null;
		
		ListNode currentA = headA;
		ListNode currentB = headB;
		
		while(currentA.equals(currentB) == false){
			if(currentA.next == null && currentB.next == null && currentA.equals(currentB) == false)
				return null;
			
			currentA = currentA.next;
			currentB = currentB.next;
			
			//if(currentA == null && currentB == null && currentA.equals(currentB) == false)
			//	return null;
			
			if(currentA == null)
				currentA = headB;
			
			if(currentB == null)
				currentB = headA;
			
		}
		
        return currentA;
    }

	public static ListNode insertionSortList(ListNode head) {
        
		ListNode preHead = new ListNode(0);
		ListNode current = head;
		ListNode preCurrent = preHead;
		while(current != null) {
			ListNode movedNode = current;
            current = current.next;
            
            preCurrent = preHead;
            while (preCurrent != null){
                if (preCurrent.next == null){
                    movedNode.next = preCurrent.next;
                    preCurrent.next = movedNode;
                    break;
                }
                else if (movedNode.val < preCurrent.next.val){
                    movedNode.next = preCurrent.next;
                    preCurrent.next = movedNode;
                    break;
                }
                else
                    preCurrent = preCurrent.next;
            }
		}
		return preHead.next;
		
		/*LinkedListNode preHead = new LinkedListNode(0);
		LinkedListNode preCurrent = preHead;
		LinkedListNode current = head;
	    while(current != null) {
	    	preCurrent = preHead;
	        while(preCurrent.next != null && preCurrent.next.value < current.value) {
	        	preCurrent = preCurrent.next;
	        }
	        LinkedListNode next = current.next;
	        current.next = preCurrent.next;
	        preCurrent.next = current;
	        current=next;
	    }
	    return preHead.next;*/
		

    }
	
	public static ListNode mergeKLists(List<ListNode> lists) {

		
		if (lists.size()==0) 
            return null;
        if (lists.size()==1) 
            return lists.get(0);
        if (lists.size()==2) 
            return mergeKListsHelper(lists.get(0), lists.get(1));
        
        return mergeKListsHelper(mergeKLists(lists.subList(0, lists.size()/2)), 
                                 mergeKLists(lists.subList(lists.size()/2, lists.size())));
    }
	
	public static ListNode mergeKListsHelper(ListNode n1, ListNode n2){
		
		if(n1 == null)
			return n2;
		if(n2 == null)
			return n1;
		
		ListNode preHead = new ListNode(0);
		ListNode currentNode = preHead;
		while(n1 != null && n2 != null){
			if(n1.val < n2.val){
				currentNode.next = n1;			
				n1 = n1.next;
			}
			else{
				currentNode.next = n2;
				n2 = n2.next;
			}
			currentNode = currentNode.next;
		}
		
		if(n1 == null)
			currentNode.next = n2;
		else if(n2 == null)
			currentNode.next = n1;
		
		
		return preHead.next;
	}

	public static ListNode reverseKGroup(ListNode head, int k) {
		
		if(head == null)
			return null;
		if(k < 2)
			return head;
		
		ListNode preHead = new ListNode(0);
		preHead.next = head;
		ListNode preHeadCopy = preHead;
		ListNode currentNode = head;
		ListNode startNode = head;

		int count = 0;
		
		while(currentNode != null){

			count++;
			if(count == k){
				ListNode newHead = startNode;
				for(int i = 0; i < k-1; i++){

					ListNode nextNode = startNode.next;
					startNode.next = nextNode.next;
					nextNode.next = newHead;
					newHead = nextNode;
				}
				
				preHead.next = newHead;
				preHead = startNode;
				startNode = startNode.next;
				currentNode= startNode;
				count = 0;
			}
			else
				currentNode = currentNode.next;
		}
		
		
		return preHeadCopy.next;
    }
	
	private static ListNode reverseKGroupHelper(ListNode head, ListNode end, int k) {
		
		ListNode preHead = new ListNode(0);
		preHead.next = head;
		ListNode currentNode = head;
		ListNode newHead = head;
		
		for(int i = 0; i < k-1; i++){
		//while(currentNode.next != null){
			ListNode nextNode = currentNode.next;
			currentNode.next = nextNode.next;
			nextNode.next = newHead;
			newHead = nextNode;
		}
		
		return head;
    }

}
