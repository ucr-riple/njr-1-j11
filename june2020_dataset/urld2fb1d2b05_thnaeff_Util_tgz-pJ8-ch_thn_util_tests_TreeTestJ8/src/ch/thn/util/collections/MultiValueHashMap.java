/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util.collections;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * One key, multiple values - One key can have one or more values.<br>
 * <br>
 * The order of the key-value pairs is not defined, since it uses a regular 
 * {@link HashMap} for the storage. The values however are stored in a {@link LinkedList}, 
 * thus the order in which the values are added is also the order in which they 
 * are stored.
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class MultiValueHashMap<K, V> extends AbstractMap<K, V> {
	
	protected Map<K, LinkedList<V>> map = null;
	
	protected int size = 0;
	
	/**
	 * 
	 * @see HashMap#HashMap()
	 */
	public MultiValueHashMap() {
		map = new HashMap<K, LinkedList<V>>();
	}
	
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @see HashMap#HashMap(int)
	 */
	public MultiValueHashMap(int initialCapacity) {
		map = new HashMap<K, LinkedList<V>>(initialCapacity);
	}
	
	/**
	 * 
	 * 
	 * @param m
	 * @see HashMap#HashMap(Map)
	 */
	public MultiValueHashMap(Map<? extends K, ? extends V> m) {
		map = new HashMap<K, LinkedList<V>>();
		putAll(m);
	}
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 * @see HashMap#HashMap(int, float)
	 */
	public MultiValueHashMap(int initialCapacity, float loadFactor) {
		map = new HashMap<K, LinkedList<V>>(initialCapacity, loadFactor);
	}
	
	/**
	 * Associates the specified value with the specified key in this map (optional operation). 
	 * If the map previously contained a mapping for the key, the new value 
	 * is added to the value list for that key.
	 * 
	 * @param key
	 * @param value
	 * @return the previous value associated with key, or null if there was no 
	 * mapping for key (A null return can also indicate that the map previously 
	 * associated null with key, if the implementation supports null values). This 
	 * means for this {@link MultiValueHashMap}, the put-methods 
	 * return the last value which has been added to the list for the given key.
	 */
	@Override
	public V put(K key, V value) {
		if (!map.containsKey(key)) {
			map.put(key, new LinkedList<V>());
		}
		
		V previous = null;
		if (map.get(key).size() > 0) {
			previous = map.get(key).getLast();
		}
		
		map.get(key).add(value);
		size++;
		
		/* JavaDoc for Map.put(K, V):
		 * Returns: the previous value associated with key, or null if there was 
		 * no mapping for key. (A null return can also indicate that the map 
		 * previously associated null with key, if the implementation supports 
		 * null values.)
		 */
		return previous;
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		try {
			if (m instanceof MultiValueHashMap) {
				@SuppressWarnings("unchecked")
				MultiValueHashMap<K, V> mvhm = (MultiValueHashMap<K, V>)m;
				for (K key : mvhm.keySet()) {
					//Add the content list by list
					if (!map.containsKey(key)) {
						map.put(key, new LinkedList<V>(mvhm.getAll(key)));
					} else {
						map.get(key).addAll(mvhm.getAll(key));
					}
					size += map.get(key).size();
				}
			}
		} catch (ClassCastException e) {
			//If there is a class cast exception, just add the content individually
		}
		
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}
	
	/**
	 * Returns the last value which has been added for the given key, or <code>null</code> 
	 * if there is no mapping for that key.
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public V get(Object key) {
		if (!map.containsKey(key)) return null;
		return map.get(key).getLast();
	}
	
	/**
	 * Returns a list of all the values which have been added for the given key, or 
	 * <code>null</code> if there is no mapping for that key. The list is backed 
	 * by the map, so changes to the map are reflected in the list, and vice-versa.
	 * 
	 * @param key
	 * @return
	 */
	public LinkedList<V> getAll(Object key) {
		if (!map.containsKey(key)) return null;
		return map.get(key);
	}
	
	/**
	 * Removes the last of the the values which have been added for the given key
	 * 
	 * @param key
	 * @return The removed value, or null if there was no mapping for the given key
	 */
	@Override
	public V remove(Object key) {
		if (!map.containsKey(key)) return null;
		V value = map.get(key).removeLast();
		if (map.get(key).size() == 0) {
			map.remove(key);
		}
		size--;
		return value;
	}
	
	/**
	 * Removes all the values which are stored for the given key.
	 * 
	 * @param key
	 * @return <code>true</code> if the values have been removed (or if there 
	 * is no such key), false if removing the values did not work.
	 */
	public boolean removeAll(Object key) {
		if (!map.containsKey(key)) return true;
		int number = map.get(key).size();
		if (map.remove(key) != null) {
			size -= number;
			return true;
		}
		return false;
	}
	
	@Override
	public void clear() {
		size = 0;
		map.clear();
	}

	/**
	 * Returns a set which contains all the entries.<br>
	 * The returned set is a new set, thus changes in the returned set are not 
	 * reflected in the {@link MultiValueHashMap}.
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
		for (K k : map.keySet()) {
			for (V v : map.get(k)) {
				set.add(new SimpleEntry<K, V>(k, v));
			}
		}
		return set;
	}
	
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	
	@Override
	public boolean containsValue(Object value) {
		for (K k : map.keySet()) {
			for (V v : map.get(k)) {
				if (v.equals(value)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns the number of different keys stored in this map. 
	 * 
	 * @return
	 */
	public int keyCount() {
		return map.size();
	}
	
	/**
	 * Returns a list which contains all the values.<br>
	 * The returned list is a new list, thus changes in the returned list are not 
	 * reflected in the {@link MultiValueHashMap}.
	 */
	@Override
	public Collection<V> values() {
		LinkedList<V> values = new LinkedList<V>();
		for (K k : map.keySet()) {
			values.addAll(map.get(k));
		}
		return values;
	}
	
	/**
	 * Returns the number of different keys in the map
	 * 
	 * @return
	 */
	public int getKeyCount() {
		return map.size();
	}
	
	/**
	 * Returns the number of values which are stored for the given key
	 * 
	 * @param key
	 * @return
	 */
	public int getValueCount(K key) {
		if (!map.containsKey(key)) return 0;
		return map.get(key).size();
	}
	
	/**
	 * Checks if the given index is within the bounds of the given list, and throws 
	 * an {@link IndexOutOfBoundsException} if it is out of the range.
	 * 
	 * @param index
	 * @param l
	 * @throws IndexOutOfBoundsException
	 */
	protected void checkIndexBounds(int index, List<?> l) {
		if (l.size() <= index || index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + 
					" is out of bounds: max=" + (l.size() - 1) + ", min=0.");
		}
	}
	
	@Override
	public String toString() {
		return map.toString();
	}

}
