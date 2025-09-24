package test.generics;

import java.util.ArrayList;
import java.util.Collection;

public class SinkTest {

	public static <T> T writeAll(Collection<? extends T> coll, Sink<T> sink) {
		T lastItemFlushed=null;
		for(T item:coll){
			sink.flush(item);
			lastItemFlushed=item;
		}
		return lastItemFlushed;
	}
	
	public static void main(String[] args) {
		Sink<Object> sink=new KitchenSink<Object>();
		Collection<String> cs=new ArrayList<String>();
		Object s=SinkTest.writeAll(cs, sink);
	}
}
