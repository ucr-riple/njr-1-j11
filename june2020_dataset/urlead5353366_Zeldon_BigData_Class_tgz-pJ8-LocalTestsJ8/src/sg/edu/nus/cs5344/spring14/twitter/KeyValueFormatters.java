package sg.edu.nus.cs5344.spring14.twitter;

public class KeyValueFormatters {

	public static <K, V> KeyValueFormatter<K, V> getTabDelimimted() {
		return new KeyValueFormatter<K, V>() {
			@Override
			public String format(K key, V value) {
				return key.toString() + "\t" + value.toString();
			}
		};
	}

}
