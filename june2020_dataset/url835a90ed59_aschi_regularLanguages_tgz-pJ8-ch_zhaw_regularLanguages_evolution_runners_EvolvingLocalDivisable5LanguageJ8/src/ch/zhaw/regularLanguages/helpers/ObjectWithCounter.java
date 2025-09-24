package ch.zhaw.regularLanguages.helpers;


public class ObjectWithCounter<O> implements Comparable<ObjectWithCounter<O>>{
	private O object;
	private long counter;
	public ObjectWithCounter(O object, long counter){
		this.object = object;
		this.counter = counter;
	}
	
	public O getObject(){
		return object;
	}
	
	public long getCounter(){
		return counter;
	}
	
	@Override
	public int compareTo(ObjectWithCounter<O> o){
		if(getCounter() == o.getCounter()){
			return 0;
		}else if(getCounter() > o.getCounter()){
			return 1;
		}else{
			return -1;
		}
	}
	
	@Override
	public String toString(){
		return "("+object+")->"+counter;
	}
}