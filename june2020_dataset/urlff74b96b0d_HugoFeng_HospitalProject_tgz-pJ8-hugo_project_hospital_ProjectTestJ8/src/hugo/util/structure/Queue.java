/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;


@SuppressWarnings("rawtypes")
public class Queue<E extends Comparable> {
	private LinkedList<E> data;
	
	public Queue(){
		data = new LinkedList<E>();
	}
	
	public void push(E o){
		data.addFirst(o);
	}
	
	public E pop(){
		E o = data.getLast();
		data.removeLast();
		return o;
	}
	
	public E top(){
		return data.getLast();
	}
	
	public int size(){
		return data.size();
	}
	
	public boolean empty(){
		return data.isEmpty();
	}
	
}
