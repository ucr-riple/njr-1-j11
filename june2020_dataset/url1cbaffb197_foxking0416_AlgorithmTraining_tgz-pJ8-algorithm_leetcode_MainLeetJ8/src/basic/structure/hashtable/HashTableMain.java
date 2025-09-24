package basic.structure.hashtable;

import java.util.Hashtable;

public class HashTableMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		hashTableTest();
	}

	
	private static void hashTableTest(){
		
		Hashtable<Integer, String> myHashtable = new Hashtable();
		
		myHashtable.put(0, "I am 0");
		myHashtable.put(1, "I am 1");
		myHashtable.put(2, "I am 1");
		
		String s = myHashtable.get(3);
		for(Integer i : myHashtable.keySet())	{
			
			System.out.print(myHashtable.get(i) +"\n");
			
		}
		//System.out.print(s);
	}
}
