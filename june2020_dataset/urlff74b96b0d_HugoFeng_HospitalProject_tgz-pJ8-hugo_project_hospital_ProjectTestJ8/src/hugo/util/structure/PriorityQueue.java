/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;

import hugo.util.structure.LinkedList.ListElement;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class PriorityQueue<E extends Comparable> implements Iterable<E>{
	
	private class PriorityPair implements Comparable {
		private E element;
		private Comparable priority;

		public PriorityPair(E element, Comparable priority) {
			this.element = element;
			this.priority = priority;
		}

		public E getElement(){
			return element;
		}

		@SuppressWarnings("unchecked")
		@Override
		public int compareTo(Object o) {
			PriorityPair p2 = (PriorityPair) o;
			return ((Comparable) priority).compareTo(p2.priority);
		}
		
		public String toString(){
			return element.toString();
		}
	}
	
	protected class SortedLinkedList extends LinkedList<PriorityPair> {
		public void addSorted(PriorityPair pNew){
			ListElement el = head;
			if(el == null){
				addFirst(pNew);
				return;
			}
			else if (pNew.compareTo((PriorityPair)head.first())>0) {
				addFirst(pNew);
			}
			else {
				while(el.rest()!=null && pNew.compareTo((PriorityPair)el.rest().first())<=0){
					el = el.rest();
				}
				el.setRest(new ListElement(pNew, el.rest()));
				size++;
			}
		}
	}
	
	private SortedLinkedList data;
	
	public PriorityQueue(){
		data = new SortedLinkedList();
	}
	

	public void push(E o, int priority){
		data.addSorted(new PriorityPair(o, priority));
	}
	
	public E pop(){
		if(empty()) return null;
		E o = ((PriorityPair)data.getFirst()).getElement();
		data.removeFirst();
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public void remove(E target) {
		ListElement element = data.head;
		if (data.isEmpty()) return;
		if (((PriorityPair)element.el1).element.equals(target)) {
			data.head = element.rest();
			data.size--;
			return;
		}
		for (int i = 1; i < size() ; i++) {
			if (((PriorityPair)element.rest().el1).element.equals(target)) {
				element.el2 = element.rest().el2;
				data.size--;
			} else {
				element = element.rest();
			}
		}
	}
	
	public int size(){
		return data.size();
	}
	
	public boolean empty(){
		return data.isEmpty();
	}
	
	public String toString(){
		return data.toString();
	}
	

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new PriorityQueueIterator();
	}
	
	private class PriorityQueueIterator implements Iterator<E> {

		ListElement element = data.head;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return element != null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			// TODO Auto-generated method stub
			E elementE = ((PriorityPair)element.el1).element;
			this.element = this.element.rest();
			return elementE;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
