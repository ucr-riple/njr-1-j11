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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * One key, multiple values - One key can have one or more values.<br>
 * <br>
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class MultiValueLinkedIndexHashMap<K, V> extends MultiValueLinkedHashMap<K, V> {
	
	/**
	 * 
	 * @see HashMap#HashMap()
	 */
	public MultiValueLinkedIndexHashMap() {
		super();
	}
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @see HashMap#HashMap(int)
	 */
	public MultiValueLinkedIndexHashMap(int initialCapacity) {
		super(initialCapacity);
	}
	
	/**
	 * 
	 * 
	 * @param m
	 * @see HashMap#HashMap(Map)
	 */
	public MultiValueLinkedIndexHashMap(Map<? extends K, ? extends V> m) {
		super();
		putAll(m);
	}
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 * @see HashMap#HashMap(int, float)
	 */
	public MultiValueLinkedIndexHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	
	
	@Override
	public void sortByValue(Comparator<V> valueComparator) {
		super.sortByValue(valueComparator);
		
		//Also sort the values in the map because they can be accessed with get(K, int)
		for (LinkedList<V> mapValues : map.values()) {
			Collections.sort(mapValues, valueComparator);
		}
		
	}
	
	/**
	 * Puts the key-value pair at the given index.<br>
	 * If the index is out of bounds, the bounds are adjusted automatically. This 
	 * means that adding a key-value pair with a too high index will be added 
	 * at the end (using {@link #put(Object, Object)}), and a key-value pair 
	 * with an index < -1 will be added at the beginning (index=0).
	 * 
	 * @param key
	 * @param value
	 * @param index
	 * @return The previous value associated with key, or null if there was no 
	 * mapping for key (A null return can also indicate that the map previously 
	 * associated null with key, if the implementation supports null values). This 
	 * means for this {@link MultiValueLinkedIndexHashMap}, the put-methods 
	 * return the last value which has been added to the list for the given key.
	 */
	public V put(K key, V value, int index) {
		if (index >= keys.size()) {
			//Just add it at the end
			return put(key, value);
		} else if (index < 0) {
			index = 0;
		}
		
		if (!map.containsKey(key)) {
			map.put(key, new LinkedList<V>());
		}
		
		V previous = null;
		if (map.get(key).size() > 0) {
			previous = map.get(key).getLast();
		}
		
		checkIndexBounds(index, keys);
		
		//Get the position of the previous item with the same key in the whole list
		int previousIndex = getPreviousKeyIndex(key, index);
		
		int previousPosition = 0;
		if (previousIndex == -1) {
			//There is no previous item -> add it at the very beginning
			previousPosition = 0;
		} else {
			//Translate the position of the previous item to its position in the map
			previousPosition = indexToPosition(previousIndex) + 1;
		}
		
		map.get(key).add(previousPosition, value);
		size++;
		keys.add(index, key);
		values.add(index, value);
		verifyIntegrity();
		return previous;
	}
	
//Where should it be put in the whole index list???
//	/**
//	 * Adds the given value at the position of the values which have been added 
//	 * for the key.
//	 * 
//	 * @param value
//	 * @param key
//	 * @param position
//	 * @return
//	 */
//	public V putPosition(V value, K key, int position) {
//		
//	}
	
	/**
	 * Returns the value at the given index
	 * 
	 * @param index
	 * @return
	 */
	public V get(int index) {
		checkIndexBounds(index, values);
		return values.get(index);
	}
	
	/**
	 * Returns the value which is added for the given key at the given position
	 * 
	 * @param key
	 * @param position
	 * @return
	 */
	public V get(K key, int position) {
		if (!map.containsKey(key)) return null;
		List<V> l = map.get(key);
		checkIndexBounds(position, l);
		return l.get(position);
	}
	
	/**
	 * Returns the key for the value at the given index
	 * 
	 * @param index
	 * @return
	 */
	public K getKey(int index) {
		checkIndexBounds(index, keys);
		return keys.get(index);
	}
	
	/**
	 * Removes the value at the given index.
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public V remove(int index) {
		checkIndexBounds(index, keys);
		List<V> l = map.get(keys.get(index));
		l.remove(indexToPosition(index));
		size--;
		keys.remove(index);
		V v = values.remove(index);
		
		if (l.size() == 0) {
			map.remove(keys.get(index));
		}
		verifyIntegrity();
		return v;
	}
	
	/**
	 * Removes the value which is at the given position for the given key.
	 * 
	 * @param key
	 * @param position
	 * @return
	 */
	public V remove(K key, int position) {
		if (!map.containsKey(key)) return null;
		List<V> l = map.get(key);
		checkIndexBounds(position, l);
		int index = positionToIndex(key, position);
		keys.remove(index);
		values.remove(index);
		V ret = l.remove(position);
		size--;
		
		if (l.size() == 0) {
			map.remove(key);
		}
		verifyIntegrity();
		return ret;
	}
	
	/**
	 * Returns the position of the given value in the given key list
	 * 
	 * @param key
	 * @param value
	 * @return The position in the key list, or -1 if such a key does not exist.
	 */
	public int positionOf(K key, V value) {
		if (!map.containsKey(key)) return -1;
		return map.get(key).indexOf(value);
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element in this 
	 * list, or -1 if this list does not contain the element. More formally, returns 
	 * the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), 
	 * or -1 if there is no such index.
	 * 
	 * @param value
	 * @return
	 */
	public int indexOf(V value) {
		return values.indexOf(value);
	}
	
	/**
	 * Returns the index of the last occurrence of the specified element in this list, 
	 * or -1 if this list does not contain the element. More formally, returns 
	 * the highest index i such that (o==null ? get(i)==null : o.equals(get(i))), 
	 * or -1 if there is no such index.
	 * 
	 * @param value
	 * @return
	 */
	public int lastIndexOf(V value) {
		return values.lastIndexOf(value);
	}
	
	/**
	 * Returns the index which contains the same key-value pair as at the given 
	 * position for the given key.
	 * 
	 * @param key
	 * @param position
	 * @return
	 */
	protected int positionToIndex(K key, int position) {
		if (!map.containsKey(key)) return -1;
		checkIndexBounds(position, map.get(key));
		
		int positionCount = 0;
		for (int index = 0; index < keys.size(); index++) {
			if (keys.get(index).equals(key)) {				
				if (positionCount == position) {
					return index;
				}
				positionCount++;
			}
		}
		
		return -1;
	}
	
	/**
	 * Returns the position of the key-value pair in its map list of the given index.
	 *  
	 * @param index
	 * @return Translates the index into the position within the map.
	 */
	protected int indexToPosition(int index) {
		checkIndexBounds(index, keys);
		return positionOf(keys.get(index), values.get(index));
	}
	
	/**
	 * Returns the index of the previous item with the same key which appears before the given index.<br>
	 * <br>
	 * <pre>
	 * 0: key1 -> value1
	 * 1: key2 -> value2
	 * 2: key1 -> value3
	 * 3: key1 -> value4
	 * 4: key2 -> value5
	 * </pre>
	 * getPreviousKeyIndex("key2", 4) = index 1
	 * <br>
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	protected int getPreviousKeyIndex(K key, int index) {
		for (int i = index - 1; i >= 0; i--) {
			if (keys.get(i).equals(key)) {
				return i;
			}
		}
		return -1;
	}

}
