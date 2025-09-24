package shyview;

import java.util.NoSuchElementException;

public interface IPicList extends java.util.List<IPicture> {
	String getName();
	IPicture next() throws NoSuchElementException;
	IPicture current() throws NoSuchElementException;
	IPicture previous() throws NoSuchElementException;
	int getIndex();
	void setIndex(int i);
	void cleanup();
	void sort();
	ShyviewMenu getMenuItem();
	
	@SuppressWarnings("serial")
	public class NoRessourceAvailable extends Exception {};
}
