package shyview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PictureList extends LinkedList<IPicture> implements IPicList, ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int PRELOAD_IMAGES = 5;
	
	String name = "Default";
	PositionIterator<IPicture> it;
	ShyviewMenu menuItem = null;
	

	public PictureList(String parname) {
		name = parname;
		it = new PositionIterator<IPicture>(this);
	}

	public String getName() {
		return name;
	}
	
	public IPicture next() throws NoSuchElementException {
		it.current().interrupt();
		it.next();
		try {
			it.preview(1).preload();
		} catch (NoSuchElementException e) {}
		return it.current();
	}
	
	public IPicture current() throws NoSuchElementException {
		return it.current();
	}

	@Override
	public IPicture previous() throws NoSuchElementException {
		it.current().interrupt();
		it.previous();
		try {
			it.preview(-1).preload();
		} catch (NoSuchElementException e) {}
		return it.current();
	}

	@Override
	public int getIndex() {
		return it.getIndex();
	}

	@Override
	public void setIndex(int i) {
		try {
			it.setIndex(i);
		} catch (ArrayIndexOutOfBoundsException e) {}
	}

	/**
	 * Removes double entrys.
	 */
	@Override
	public void cleanup() {
		ArrayList<IPicture> newlist = new ArrayList<IPicture>();
		for (IPicture p1: this) {
			boolean test = true;
			for (IPicture p2: newlist) {
				if (p1.equals(p2)) test = false;
			}
			if (test) newlist.add(p1);
		}
		this.clear();
		this.addAll(newlist);
	}

	@Override
	public void sort() {
		ShyviewComparator.PictureComparator comp = (new ShyviewComparator()).new PictureComparator();
		Collections.sort(this, comp);
	}

	@Override
	public ShyviewMenu getMenuItem() {
		if (menuItem == null)
			menuItem = new ShyviewMenu(this);
		
		return menuItem;
	}
	
	public void free() {
		for (PositionIterator<IPicture> i = new PositionIterator<IPicture>(this); i.hasNext();) {
			i.next().flush();
		}
	}

	public boolean add(IPicture p) {
		p.setActionListener(this);
		return super.add(p);
	}
	public boolean addAll(Collection<? extends IPicture> c) {
		for (Iterator<? extends IPicture> i = c.iterator(); i.hasNext();) i.next().setActionListener(this);
		return super.addAll(c);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int i = it.getOffset((IPicture) e.getSource());
		try {
			if (i >= 0 && i < PRELOAD_IMAGES)it.preview(i+1).preload();
		} catch (NoSuchElementException ex) {}
	}
}
