package test.generics;

public class KitchenSink<T> implements Sink<T>{

	@Override
	public void flush(T t) {
		System.out.println(t.toString());
	}

}
