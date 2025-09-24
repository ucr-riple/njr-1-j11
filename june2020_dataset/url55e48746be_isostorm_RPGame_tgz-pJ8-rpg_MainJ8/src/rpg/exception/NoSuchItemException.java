package rpg.exception;

import rpg.item.Item;

/**
 * A class of exceptions, signaling the non-existence of an 
 * item in the enclosing container
 * 
 * @author Mathias, Frederic
 *
 */
public class NoSuchItemException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	
	private Item item;
	
	/**
	 * Initialize this exception with the given item
	 * 
	 * @param item
	 * 		  The item involved in this exception
	 * @post  The item of this exception equals the given item
	 * 		  | new.getItem() == item
	 */
	public NoSuchItemException(Item item){
		this.item = item;
	}
	
	/**
	 * Return the item involved in this exception
	 */
	public Item getItem(){
		return item;
	}
}
