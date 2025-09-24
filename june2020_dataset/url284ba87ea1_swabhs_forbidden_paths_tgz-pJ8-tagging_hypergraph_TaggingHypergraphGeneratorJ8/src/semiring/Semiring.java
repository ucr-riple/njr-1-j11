package semiring;

import java.util.List;

public interface Semiring<T> {

	//T add(List<T> elements);
	T add(T element1, T element2);
	
	T multiply(List<T> elements);
}
