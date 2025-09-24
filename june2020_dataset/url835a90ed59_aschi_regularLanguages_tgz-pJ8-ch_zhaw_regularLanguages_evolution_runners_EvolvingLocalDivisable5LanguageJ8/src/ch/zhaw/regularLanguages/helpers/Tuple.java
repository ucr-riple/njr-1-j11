package ch.zhaw.regularLanguages.helpers;

public class Tuple<A, B> {
    private final A first;
    private final B second;

    public Tuple(A first, B second) {
    	super();
    	this.first = first;
    	this.second = second;
    }

    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
    	if (other instanceof Tuple) {
    		Tuple otherTuple = (Tuple) other;
    		return 
    		((  this.first == otherTuple.first ||
    			( this.first != null && otherTuple.first != null &&
    			  this.first.equals(otherTuple.first))) &&
    		 (	this.second == otherTuple.second ||
    			( this.second != null && otherTuple.second != null &&
    			  this.second.equals(otherTuple.second))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return first + ": " + second; 
    }

    public A getFirst() {
    	return first;
    }

    public B getSecond() {
    	return second;
    }
}
