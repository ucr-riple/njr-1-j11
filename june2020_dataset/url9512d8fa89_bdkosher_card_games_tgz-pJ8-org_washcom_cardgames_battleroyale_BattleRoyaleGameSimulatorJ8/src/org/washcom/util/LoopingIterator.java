package org.washcom.util;

import java.util.Iterator;

/**
 * An iterator that loops forever, assuming the backing iterable produces an iterator that has items to loop over.
 * 
 * @author Joe
 */
public class LoopingIterator<T> implements Iterator<T> {
    private final Iterable<T> iterable;
    
    private Iterator<T> currIterator;

    /**
     * 
     * @param iterable - cannot be null
     */
    public LoopingIterator(Iterable<T> iterable) {
        if (iterable == null) {
            throw new NullPointerException("Iterable arg cannot be null.");
        } 
        this.iterable = iterable;
        this.currIterator = iterable.iterator();
    }
    
    /**
     * Presumes the backing iterator always has at least one element and always returns true.
     * 
     * @return 
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Returns the next item of the iterable's iterator; if the iterator is at the very end, the iterable is
     * called for a new iterator and the first item of the new iterator is returned.
     * @return 
     */
    @Override
    public T next() {
        if (!currIterator.hasNext()) {
            currIterator = iterable.iterator();
        } 
        return currIterator.next();
    }

    /**
     * Throws UnsupportedOperationException
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
