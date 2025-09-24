package rpg.exception;

import rpg.item.Item;
import rpg.item.Parent;

/**
 * A class for signaling illegal attempts to add items to a parent.
 * 
 * @author Mathias, Frederic
 */
public class IllegalAddItemException extends RuntimeException {
	 /**
	  * 
	  */
	 private static final long serialVersionUID = 1L;
	 /**
	  * Initialize this new illegal add item exception with the given
	  * parent and given item.
	  * 
	  * @param parent
	  *        The parent for the new illegal add item exception.
	  * @param item
	  *        The item for the new illegal add item exception.
	  * @post  The parent for the new illegal add item exception is set to the given parent.
	  *        | new.getParent() == parent
	  * @post  The item for the new illegal add item exception is set to the given item.
	  *        | new.getItem() == item
	  */
	 public IllegalAddItemException(Parent parent, Item item) {
		 this.item = item;
	     this.parent = parent;
	 }

	 /**
	  * Return the parent involved in this illegal add item exception.
	  */
	 public Parent getParent() {
	  return parent;
	 }

	 /**
	  * Variable referencing the parent involved in this
	  * illegal add item exception.
	  */
	 private final Parent parent;
	 
	 /**
	  * Return the item involved in this illegal add item exception.
	  */
	 public Item getItem() {
	  return item;
	 }

	 /**
	  * Variable referencing the item involved in this
	  * illegal add item exception.
	  */
	 private final Item item;

}
