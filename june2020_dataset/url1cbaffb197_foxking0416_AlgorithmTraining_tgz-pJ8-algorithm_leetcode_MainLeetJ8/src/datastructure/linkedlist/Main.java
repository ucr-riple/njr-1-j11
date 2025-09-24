package datastructure.linkedlist;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		linklistTest.removeDuplicatedLinkListNode();
		//linklistTest.addList();
		testFindBeginning();
		testSwapPairs();
		testDeleteDuplicates();
		testMergeTwoLists();
		testInsertionSortList();
		testSortList();
		testReorderList();
		testAddTwoNumbers();
		testPartition();
		testRotateRight();
		testReverseBetween();
		testRemoveNthFromEnd();
		testGetIntersectionNode();
		testMergeKLists();
		testReverseKGroup();
	}
	
	private static void testFindBeginning() {
		System.out.print("****Find Beginning Test****" + "\n");	
		LinkedListTest linklistTest = new LinkedListTest();
		linklistTest.findBeginningTest();
		System.out.print("\n");
	}
	
	private static void testSwapPairs(){
		System.out.print("****Swap Pairs Test****" + "\n");	
		System.out.print("Swap the 1st and 2nd element, swap the 3rd and 4th element, ..." + "\n");	
		LinkedListTest.swapPairsTest();
		System.out.print("\n");
	}
	
	private static void testDeleteDuplicates(){
		System.out.print("****Delete Duplicates Test****" + "\n");	
		LinkedListTest.deleteDuplicatesTest();
		System.out.print("\n");
	}
	
	private static void testMergeTwoLists(){
		System.out.print("****Merge Two Lists Test****" + "\n");	
		LinkedListTest.testMergeTwoLists();
		System.out.print("\n");
	}

	private static void testSortList(){
		System.out.print("****Merge Sort List Test****" + "\n");	
		LinkedListTest.sortListTest();
		System.out.print("\n");
	}
	
	private static void testReorderList(){
		System.out.print("****Reorder List Test****" + "\n");	
		LinkedListTest.reorderList();
		System.out.print("\n");
	}
	
	private static void testAddTwoNumbers(){
		System.out.print("****Add Two Numbers Test****" + "\n");	
		LinkedListTest.addTwoNumbersTest();
		System.out.print("\n");
	}

	private static void testPartition(){
		System.out.print("****Add Partition Test****" + "\n");	
		
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(4);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(2);
		ListNode n5 = new ListNode(5);
		ListNode n6 = new ListNode(2);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		
		
		LinkedListTest.partition(n1, 3);
		System.out.print("\n");
	}
	
	private static void testRotateRight(){
		System.out.print("****Rotate Right Test****" + "\n");	
		
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(4);
		ListNode n5 = new ListNode(5);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		ListNode newHead = LinkedListTest.rotateRight(n1, 2);
		
		System.out.print("\n");
	}
	
	private static void testReverseBetween(){
		System.out.print("****Reverse Between Test****" + "\n");
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(4);
		ListNode n5 = new ListNode(5);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		ListNode head = LinkedListTest.reverseBetween(n1, 2, 4);
		
		System.out.print("\n");
	}
	
	private static void testRemoveNthFromEnd(){
		
		System.out.print("***Remove Nth From End Test****" + "\n");
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(4);
		ListNode n5 = new ListNode(5);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		ListNode head = LinkedListTest.removeNthFromEnd(n1, 5);
		
		System.out.print("\n");
	}
	
	private static void testGetIntersectionNode(){
		System.out.print("***Get Intersection Node Test****" + "\n");
		
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(3);
		ListNode n3 = new ListNode(5);
		ListNode n4 = new ListNode(7);
		ListNode n5 = new ListNode(9);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		ListNode b1 = new ListNode(2);
		
		ListNode intersection = LinkedListTest.getIntersectionNode(n1, b1);
		
		System.out.print("\n");
	}
	
	private static void testInsertionSortList(){
		
		System.out.print("***Insertion Sort List Test****" + "\n");
		
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(4);
		ListNode n5 = new ListNode(5);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		ListNode newHead = LinkedListTest.insertionSortList(n1);
		
		System.out.print("\n");
	}
	
	private static void testMergeKLists(){
		System.out.print("***Insertion Sort List Test****" + "\n");
		
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(3);
		ListNode n3 = new ListNode(5);
		ListNode n4 = new ListNode(7);
		ListNode n5 = new ListNode(9);
		
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		
		ListNode n11 = new ListNode(2);
		ListNode n12 = new ListNode(4);
		ListNode n13 = new ListNode(6);
		ListNode n14 = new ListNode(8);
		ListNode n15 = new ListNode(10);
		
		n11.next = n12;
		n12.next = n13;
		n13.next = n14;
		n14.next = n15;
		
		ListNode newHead = LinkedListTest.mergeKListsHelper(n1, n11);
		
		System.out.print("\n");
	}
	
	private static void testReverseKGroup(){
		System.out.print("***Reverse KGroup Test****" + "\n");
		
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(4);
		ListNode n5 = new ListNode(5);
		ListNode n6 = new ListNode(6);
		ListNode n7 = new ListNode(7);
		ListNode n8 = new ListNode(8);
		
		//n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		ListNode newHead = LinkedListTest.reverseKGroup(n1, 2);
		
		System.out.print("\n");
	}
}
