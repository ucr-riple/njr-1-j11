package Sentiens;


import Defs.Misc;
import Questing.QStack;
import Questing.Quest;


public class Memory implements Misc {
	private static final int QMEMORY = 10;
	public QStack QuestStack;

	public Memory() {QuestStack = new QStack(QMEMORY);}
	
	public void newQ(Quest q) {QuestStack.push(q);}
	public void finishQ() {QuestStack.pop();}
	
	@Override
	public String toString() {
		return QuestStack.peek().toString();
	}
	
}