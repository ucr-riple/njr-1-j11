package datastructure.linkedlist;

import datastructure.binarytree.TreeNode;

public class ListNode extends TreeNode {

	public ListNode next = null;
//	private LinkedListNode headNode;

	
	public ListNode(int v) {
		super(v);
		
//		if(headNode == null){
//			headNode = this;
//		}

	}
	
	public void push(int v){
		ListNode newNode = new ListNode(v);
		ListNode n = this;
		while(n.next != null){
			n = n.next;
		}
		n.next = newNode;
		
		
	}
	
	public ListNode deleteNode(ListNode head, int d){
		ListNode n = head;
		if(n.val == d)
			return head.next;
		
		while(n.next != null){
			if(n.next.val == d){
				n.next = n.next.next;
				return head;
			}
			
			n = n.next;
		}
		return head;
	}
	
//	public int pop(){
//		
//		LinkedListNode n = this;
//		LinkedListNode nNext = n.next;
//		while(nNext.next != null){
//			n = nNext;
//			nNext = nNext.next;
//		}
//		
//		int returnValue = nNext.value;
//		n.next = null;
//		
//		return returnValue;
//	}
//	
//	public int peek(){
//		
//		LinkedListNode n = this;
//		LinkedListNode nNext = n.next;
//		while(nNext.next != null){
//			n = nNext;
//			nNext = nNext.next;
//		}
//		
//		return nNext.value;
//	}

}
