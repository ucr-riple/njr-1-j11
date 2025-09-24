package Questing;

import java.util.Stack;

import Descriptions.GobLog;
import Questing.Quest.QuestRetrievalQuest;
import Questing.Quest.Unquenchable;
import Sentiens.Clan;

@SuppressWarnings("serial")
public class QStack extends Stack<Quest> {
	private int maxCapacity;
	public QStack(int c) {maxCapacity = c;}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Quest getOfType(Class clasz) {
		for (Quest member : this) {
			if (clasz.isAssignableFrom(member.getClass())) {
				return member;
			}
		}
		return null;
	}
	public boolean prioritizeExistingMemberOfType(Class<? extends Quest> clasz) {
		Quest q = getOfType(clasz);
		if (q == null) return false;
		this.remove(q);
		this.push(q);
		return true;
	}
	@Override
	public Quest peek() {
		if (isEmpty()) {return null;}
		else {return super.peek();}
	}
	@Override
	public Quest push(Quest item) {
		super.push(item);
		if (this.elementCount >= maxCapacity) {return explode();}
		else return item;
	}
	/** look at item right before latest item */
	public Quest peekUp() {return elementAt(size() - 2);}
	private Quest explode() {
		Clan clan = null;
		@SuppressWarnings("unchecked")
		Class<? extends Quest>[] oldQuests = new Class[maxCapacity];
		int i = 0; for (Quest q : this) {
			clan = q.getDoer();
			oldQuests[i++] = q.getClass();
		}
		clan.addReport(GobLog.questExplosion());
		System.out.println(clan.getNomen() + " the " + clan.getJob() + " QUEST EXPLOSION!!! " + this);
		this.clear();
		return super.push(new QuestRetrievalQuest(clan, oldQuests));
	}
	public void quenchQuests() {
		for (int i = size() - 1; i >= 0; i--) {
			final Quest q = elementAt(i);
//			Unquenchable.class.isAssignableFrom(q)
			if (q instanceof Unquenchable && ((Unquenchable) q).isUnquenchable()) continue;
			this.removeElementAt(i);
		}
	}
}