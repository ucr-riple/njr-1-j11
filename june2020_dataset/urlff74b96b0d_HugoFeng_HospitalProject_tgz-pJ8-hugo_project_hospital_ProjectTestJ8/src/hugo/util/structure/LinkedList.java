/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;
import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class LinkedList<E extends Comparable> implements Iterable<E>{

	protected class ListElement {
		protected E el1;
		protected ListElement el2;

		public ListElement(E el, ListElement nextElement) {
			el1 = el;
			el2 = nextElement;
		}

		public ListElement(E el) {
			this(el, null);
		}

		public E first() {
			return  el1;
		}

		public ListElement rest() {
			return el2;
		}

		public void setFirst(E o) {
			el1 = o;
		}

		public void setRest(ListElement el) {
			el2 = el;
		}
	}

	protected ListElement head;
	protected int size;

	public LinkedList() {
		head = null;
		size = 0;
	}

	public void addFirst(E o) {
		head = new ListElement(o, head);
		size++;
	}

	public void addLast(E o) {
		ListElement d = head;
		if (isEmpty()) {
			addFirst(o);
			return;
		}
		while (d.rest() != null) {
			d = d.rest();
		}
		d.el2 = new ListElement(o, null);
		size++;
	}

	public void removeFirst() {
		if (!isEmpty()) {
			head = head.rest();
			size--;
		}
	}

	public void removeLast() {
		ListElement tmp = head;
		if (head == null) {
			return;
		}
		if (head.rest() == null) {
			head = null;
			size--;
			return;
		}
		while (tmp.rest().rest() != null) {
			tmp = tmp.rest();
		}
		tmp.el2 = null;
		size--;
	}

	public void remove(Comparable target) {
		ListElement element = head;
		if (isEmpty()) return;
		if (element.first().equals(target)) {
			head = element.rest();
			size--;
			return;
		}
		for (int i = 1; i < size() ; i++) {
			if (element.rest().el1.equals(target)) {
				element.el2 = element.rest().el2;
				size--;
			} else {
				element = element.rest();
			}
		}
	}

	public E contains(Comparable target) {
		ListElement element = head;
		while(element!=null) {
			if(element.first().equals(target)) return element.first();
			element = element.rest();
		}
		return null;
	}

	public E getFirst() {
		return head.first();
	}

	public E get(int n) {
		ListElement d = head;
		while (n > 0) {
			d = d.rest();
			n--;
		}
		return d.first();
	}

	public void set(int num, E o) {
		ListElement d = head;
		while (num > 1) {
			d = d.rest();
			num--;
		}
		d.el1 = o;
	}

	public E getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size() - 1);
	}
	
	public void attachToFrontOf(LinkedList<E> leadingLinkedList) {
		if(leadingLinkedList != null) {
			ListElement element = head;
			while (element.el2 != null) {
				element = element.el2;
			}
			element.el2 = leadingLinkedList.head;
			size += leadingLinkedList.size; //This is very dirty. Should return a new list!
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public String toString() {
		String s = "(";
		ListElement d = head;
		while (d != null) {
			s += d.first().toString();
			s += " ";
			d = d.rest();
		}
		s += ")";
		return s;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new LinkedListIterator();
	}
	
	private class LinkedListIterator implements Iterator<E>{

		ListElement el = head;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return el!=null;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			E o = (E) el.first();
			el = el.rest();
			return o;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
