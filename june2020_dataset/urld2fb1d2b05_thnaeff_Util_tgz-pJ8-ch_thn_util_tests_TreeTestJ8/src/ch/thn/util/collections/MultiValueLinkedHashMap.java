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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * One key, multiple values - One key can have one or more values.<br>
 * <br>
 * The order of the key-value pairs is defined by their insertion order, since it 
 * uses linked collections for the storage.
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class MultiValueLinkedHashMap<K, V> extends MultiValueHashMap<K, V> {

	protected LinkedList<K> keys = null;
	protected LinkedList<V> values = null;
	
	/**
	 * 
	 * @see HashMap#HashMap()
	 */
	public MultiValueLinkedHashMap() {
		super();
		map = new LinkedHashMap<K, LinkedList<V>>();
		keys = new LinkedList<K>();
		values = new LinkedList<V>();
	}
	
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @see HashMap#HashMap(int)
	 */
	public MultiValueLinkedHashMap(int initialCapacity) {
		super(initialCapacity);
		map = new LinkedHashMap<K, LinkedList<V>>(initialCapacity);
		keys = new LinkedList<K>();
		values = new LinkedList<V>();
	}
	
	/**
	 * 
	 * 
	 * @param m
	 * @see HashMap#HashMap(Map)
	 */
	public MultiValueLinkedHashMap(Map<? extends K, ? extends V> m) {
		super();
		map = new LinkedHashMap<K, LinkedList<V>>();
		keys = new LinkedList<K>();
		values = new LinkedList<V>();
		putAll(m);
	}
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 * @see HashMap#HashMap(int, float)
	 */
	public MultiValueLinkedHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		map = new LinkedHashMap<K, LinkedList<V>>(initialCapacity, loadFactor);
		keys = new LinkedList<K>();
		values = new LinkedList<V>();
	}
	
	/**
	 * Sorts this map by key
	 * 
	 * @param keyComparator
	 */
	public void sortByKey(Comparator<K> keyComparator) {
		Collections.sort(keys, keyComparator);
		
		values.clear();
		
		K currentKey = null;
		
		//Add the values in the order of the new keys
		for (K key : keys) {
			if (key.equals(currentKey)) {
				continue;
			} else {
				currentKey = key;
			}
			
			LinkedList<V> mapValues = map.get(key);
			values.addAll(mapValues);
			
		}
	}
	
	/**
	 * Sorts this map by value
	 * 
	 * @param valueComparator
	 */
	public void sortByValue(Comparator<V> valueComparator) {
		LinkedList<K> k = new LinkedList<>(keys);
		LinkedList<V> v = new LinkedList<>(values);
		Collections.sort(values, valueComparator);
		
		keys.clear();
		
		//Add the keys in the order of the new values
		for (V value : values) {
			//Get the key from the location of the old value
			keys.add(k.get(v.indexOf(value)));
		}
	}
	
	@Override
	public V put(K key, V value) {
		V v = super.put(key, value);
		keys.add(key);
		values.add(value);
		verifyIntegrity();
		return v;
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet()) {
			keys.add(e.getKey());
			values.add(e.getValue());
		}
		super.putAll(m);
		verifyIntegrity();
	}
	
	/**
	 * Returns a list of all the values which have been added for the given key, 
	 * or null if there is no mapping for that key. The returned list is a new 
	 * list, so changes in that list are not reflected in the map!
	 * 
	 */
	@Override
	public LinkedList<V> getAll(Object key) {
		if (!map.containsKey(key)) return null;
		//Returns a new list, since adding/removing a list item is not that 
		//simple and also needs to be removed from its key and value list.
		return new LinkedList<>(map.get(key));
	}
	
	@Override
	public V remove(Object key) {
		V v = super.remove(key);
		int index = keys.lastIndexOf(key);
		keys.remove(index);
		values.remove(index);
		verifyIntegrity();
		return v;
	}
	
	@Override
	public boolean removeAll(Object key) {
		if (!super.removeAll(key)) {
			verifyIntegrity();
			return false;
		}
		
		int index = keys.indexOf(key);
		while (index != -1) {
			keys.remove(index);
			values.remove(index);
			index = keys.indexOf(key);
		}
		verifyIntegrity();
		return true;
	}
	
	@Override
	public void clear() {
		super.clear();
		keys.clear();
		values.clear();
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set = new LinkedHashSet<Entry<K, V>>();
		for (int i = 0; i < keys.size(); i++) {
			set.add(new SimpleEntry<K, V>(keys.get(i), values.get(i)));
		}
		return set;
	}
	
	@Override
	public Collection<V> values() {
		//Has to return the linked value list because the order matters.
		//Since changes in the values list are not reflected in all the other 
		//used collections, a new list is returned.
		return new LinkedList<V>(values);
	}
	
	/**
	 * Returns a Set view of the keys contained in this map. Since the 
	 * {@link MultiValueLinkedHashMap} uses linked lists to store the key-value 
	 * pairs, the returned set is a new set with all the keys.
	 */
	@Override
	public Set<K> keySet() {
		//Has to return the linked key list because the order matters.
		//Since changes in the keys list are not reflected in all the other 
		//used collections, a new set is returned.
		return new LinkedHashSet<>(keys);
	}
	
	@Override
	public String toString() {
		return entrySet().toString();
	}
	
	
	/**
	 * 
	 * 
	 */
	protected void verifyIntegrity() {
		if (super.size() != values.size() && super.size() != keys.size()) {
			throw new MultiKeyHashMapMappingError("Inconsistency in " + 
					this.getClass().getSimpleName() + 
					". Size of map (" + super.size() + "), key list (" + 
					keys.size() + ") and values list (" + values.size() + ") does not match!");
		}
	}
	
}
