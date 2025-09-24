package sg.edu.nus.cs5344.spring14.twitter;


public interface KeyValueFormatter<K, V> {
	String format(K key, V value);
}
