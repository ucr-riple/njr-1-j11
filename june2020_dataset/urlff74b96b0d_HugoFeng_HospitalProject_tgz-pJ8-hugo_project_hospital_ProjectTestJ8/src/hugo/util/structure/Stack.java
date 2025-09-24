/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.util.structure;


@SuppressWarnings("rawtypes")
class Stack<E extends Comparable> {
	private LinkedList<E> data;
	
	public Stack(){
		data = new LinkedList<E>();
	}
	
	public void push(E o){
		data.addFirst(o);
	}
	
	public E pop(){
		E o = data.getFirst();
		data.removeFirst();
		return o;
	}
	
	public E top(){
		return data.getFirst();
	}
	
	public int size(){
		return data.size();
	}
	
	public boolean empty(){
		return data.isEmpty();
	}
}