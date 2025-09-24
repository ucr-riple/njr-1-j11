/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;
import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class Vector<E extends Comparable> implements Iterable<E>
{
	protected Object data[];
	protected int count;
	
	public Vector(int capacity)
	{
		data = new Object[capacity];
		count = 0;
	}

	public int size()
	{
		return count;
	}
 
	public boolean isEmpty()
	{
		return size() == 0;
	}

	public Object get(int index)
	{
		return data[index];
	}

	public void set(int index, Object obj)
	{
		data[index] = obj;
	}

	public boolean contains(Object obj)
	{
		for(int i=0;i<count;i++)
		{
			if(data[i] == obj) return true;
		}
		return false;
	}
	
	public void addFirst(Object item)
	{
		if (data.length==size()) {
			this.extendCapacity();
		}
		for (int i = count; i>0; i--) {
			data[count] = data[count-1];
		}
		data[0] = item;
		count++;
	}

	public void addLast(Object o)
	{
		if (data.length==size()) {
			this.extendCapacity();
		}
		for (int i = count; i>0; i--) {
			data[count] = data[count-1];
		}
		data[count] = o;
		count++;
	}

	public Object getFirst()
	{
		return data[0];
	}

	public Object getLast()
	{
		return data[count-1];
	}

	public void remove(E target) {
		for (int i = 0; i < count; i++) {
			if (data[i].equals(target)) {
				int repeat = 0;
				int next = i+1;
				while (next<count && data[next].equals(target)) {
					repeat++;
					next++;
				}
				if (i!=count-1) {
					for (int j = i; j < count-1-repeat; j++) {
						data[j] = data[j+1+repeat];
					}
				}
				count = count-1-repeat;
			}
		}
	}

	public void removeLast()
	{
		data[count-1] =  null;
		count--;
		if (count<data.length/4) {
			shrinkCapacity();
		}
	} 

	public void removeFirst()
	{
		for (int i = 0; i < count-1; i++) {
			data[i] = data[i+1];
		}
		count--;
		if (count<data.length/4) {
			shrinkCapacity();
		}
	}
	
	public void reverse() {
		for (int i = 0; i < count/2; i++) {
			Object tmp = data[i];
			data[i] = data[count-1-i];
			data[count-1-i] = tmp;
		}
	}
	
	public Vector doubleV() {
		Vector newVector = new Vector(2*size());
		for (int i = 0; i < size(); i++) {
			newVector.addLast(get(i));
			newVector.addLast(get(i));	
		}
		return newVector;
	}
	
	
	private void extendCapacity() {
		Object data2[] = new Object[size()*2];
		for (int i = 0; i < size(); i++) {
			data2[i] = data[i];
		}
		data = data2;
	}
	
	private void shrinkCapacity() {
		Object data2[] = new Object[size()/2];
		for (int i = 0; i < size(); i++) {
			data2[i] = data[i];
		}
		data = data2;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new VectorIterator<E>();
	}
	
	@SuppressWarnings("hiding")
	private class VectorIterator<E> implements Iterator<E> {

		int index = 0;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return index!=size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			// TODO Auto-generated method stub
			return (E) get(index++);
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}