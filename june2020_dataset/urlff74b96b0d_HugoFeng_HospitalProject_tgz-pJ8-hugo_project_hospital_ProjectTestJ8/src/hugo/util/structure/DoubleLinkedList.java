/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class DoubleLinkedList<E extends Comparable> implements Iterable<E> {
	private class DoubleLinkedListElement {
		private E data;
		protected DoubleLinkedListElement preElement;
		protected DoubleLinkedListElement nextElement;
		
		private DoubleLinkedListElement(E dataE, DoubleLinkedListElement pre, DoubleLinkedListElement next) {
			this.data = dataE;
			this.preElement = pre;
			this.nextElement = next;
			if(nextElement != null) nextElement.preElement = this;
			if(preElement != null) preElement.nextElement = this;
		}
		
		@SuppressWarnings("unused")
		public DoubleLinkedListElement(E dataE) {
			this(dataE, null, null);
		}
		
		@SuppressWarnings("unused")
		public E value() {
			return data;
		}
		
		@SuppressWarnings("unused")
		public DoubleLinkedListElement previous() {
			return preElement;
		}
		
		@SuppressWarnings("unused")
		public DoubleLinkedListElement next() {
			return nextElement;
		}
		
		public void setNext(DoubleLinkedListElement next) {
			nextElement = next;
			if(next != null) next.preElement = this;
		}
		
		public void setPrevious(DoubleLinkedListElement previous) {
			preElement = previous;
			if(previous != null) previous.nextElement = this;
		}
		
		public void remove() {
			if(preElement != null) preElement.setNext(nextElement);
			if(nextElement != null) nextElement.setPrevious(preElement);
		}
	}
	
	private DoubleLinkedListElement head;
	private DoubleLinkedListElement tail;
	private int size;

	public DoubleLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	public void addFirst(E dataE) {
		DoubleLinkedListElement newElement = new DoubleLinkedListElement(dataE, null, head);
		head = newElement;
		if(isEmpty()) tail = newElement;
		this.size++;
	}
	
	public void addLast(E dataE) {
		DoubleLinkedListElement newElement = new DoubleLinkedListElement(dataE, tail, null);
		tail = newElement;
		if(isEmpty()) head = newElement;
		this.size++;
	}
	
	public void removeFirst() {
		if(isEmpty()) return;
		head = head.nextElement;
		if(head != null) head.preElement = null;
		size--;
	}
	
	public void removeLast() {
		if(isEmpty()) return;
		tail = tail.preElement;
		if(tail != null) tail.nextElement = null; 
		size--;
	}

	public E get(int index) {
		DoubleLinkedListElement tmp = head;
		for (int i = 0; i < index; i++) {
			tmp = tmp.nextElement;
		}
		return tmp.data;
	}
	
	public boolean contains(E target) {
		DoubleLinkedListElement tmp = head;
		for (int i = 0; i < size(); i++) {
			if(tmp!=null && tmp.data.equals(target)) return true;
			tmp = tmp.nextElement;
		}
		return false;
	}
	
	public void remove(E target) {
		DoubleLinkedListElement tmp = head;
		while(tmp != null) {
			if(tmp.data.equals(target)) {
				tmp.remove();
				if(tmp.preElement == null) head = tmp.nextElement;
				if(tmp.nextElement == null) tail = tmp.preElement;
				size--;
			}
			tmp = tmp.nextElement;
		}
	}

	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new DoubleLinkedListIterator();
	}
	
	
	private class DoubleLinkedListIterator implements Iterator<E> {

		DoubleLinkedListElement tmp = head;
		@Override
		public boolean hasNext() {
			return tmp != null;
		}

		@Override
		public E next() {
			DoubleLinkedListElement element = tmp;
			tmp = tmp.nextElement;
			return element.data;
		}

		@Override
		public void remove() {
			//not implemented
		}
		
	}
	
	public Iterable<E> reversedIterator() {
		return new Iterable<E>() {

			@Override
			public Iterator<E> iterator() {
				// TODO Auto-generated method stub
				return new Iterator<E>() {
					
					DoubleLinkedListElement element = tail;
					@Override
					public boolean hasNext() {
						// TODO Auto-generated method stub
						return element != null;
					}

					@Override
					public E next() {
						// TODO Auto-generated method stub
						DoubleLinkedListElement tmp = element;
						element = element.preElement;
						return tmp.data;
					}

					@Override
					public void remove() {
						// TODO Auto-generated method stub
						
					}
				};
			}
		};
	}
	

}
