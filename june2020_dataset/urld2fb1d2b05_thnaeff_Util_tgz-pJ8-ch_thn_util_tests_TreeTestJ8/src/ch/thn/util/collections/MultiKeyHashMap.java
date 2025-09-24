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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * One value, multiple keys - One or more key(s) point to the same value.<br>
 * <br>
 * A {@link LinkedHashMap} with the possibility to add multiple mappings for one key. 
 * The value in the hash map can then be accessed with multiple keys and the 
 * original key will be preserved.<br>
 * <br>
 * Example: All four keys point to the same value:
 * <pre>
 * Key1 --\
 * Key2 ----> value123
 * Key3 --/
 * Key4 -/
 * </pre>
 * 
 * Example: Two keys point to the same value and one key points to another key, 
 * which means that value567 can be accessed with Key5, Key6 and Key7.
 * <pre>
 * Key5 --> Key7 --> value567
 * Key6 ---------/
 * </pre>
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <K>
 * @param <V>
 */
public class MultiKeyHashMap<K, V> extends LinkedHashMap<K, V> {
	//TODO could implement the Map interface and be backed up by any map
	private static final long serialVersionUID = -2010837799101195781L;

	/** added mapping -> original key **/
	private HashMap<K, K> mapping = null;
	/** original key -> all its mappings **/
	private HashMap<K, HashSet<K>> mappingReverse = null;
	
	/**
	 * 
	 * @see HashMap#HashMap()
	 */
	public MultiKeyHashMap() {
		super();
		initMappingMap();
	}
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @see HashMap#HashMap(int)
	 */
	public MultiKeyHashMap(int initialCapacity) {
		super(initialCapacity);
		initMappingMap();
	}
	
	/**
	 * 
	 * 
	 * @param m
	 * @see HashMap#HashMap(Map)
	 */
	public MultiKeyHashMap(Map<? extends K, ? extends V> m) {
		super(m);
		initMappingMap();
	}
	
	/**
	 * 
	 * 
	 * @param initialCapacity
	 * @param loadFactor
	 * @see HashMap#HashMap(int, float)
	 */
	public MultiKeyHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		initMappingMap();
	}
	
	/**
	 * Prepares the mapping map
	 */
	private void initMappingMap() {
		mapping = new HashMap<K, K>();
		mappingReverse = new HashMap<K, HashSet<K>>();
	}
	
	/**
	 * Maps the <code>newKey</code> to <code>mapTo</code>, which means that 
	 * <code>newKey</code> points to <code>mapTo</code>. It is also possible to 
	 * map a key to a mapping: newKey->mapTo, newKey2->newKey, newKey3->newKey2 etc.
	 * 
	 * @param newKey
	 * @param mapTo
	 */
	public void addKeyMapping(K newKey, K mapTo) {
		if (super.containsKey(newKey)) {
			//TODO this could be left out so that even original keys could be re-mapped
			throw new MultiKeyHashMapMappingError("The key " + newKey + " can not be used as a new key, because it is an original key in the actual hash map.");
		}
		
		if (mapping.containsKey(newKey)) {
			//A mapping for the key has already been set and will be overwritten. 
			//Clear the old entry in the reverse mapping
			K old = mapping.get(newKey);
			mappingReverse.get(old).remove(newKey);
			//Remove empty lists
			if (mappingReverse.get(old).size() == 0) {
				mappingReverse.remove(old);
			}
		}
		
		mapping.put(newKey, mapTo);
		
		if (!mappingReverse.containsKey(mapTo)) {
			mappingReverse.put(mapTo, new HashSet<K>(1));
		}
		
		mappingReverse.get(mapTo).add(newKey);
	}
	
	/**
	 * Returns the key to which <code>newKey</code> points to.<br>
	 * The returned key is the one which has been used with <code>mapTo</code> 
	 * in {@link #addKeyMapping(Object, Object)}
	 * 
	 * @param newKey
	 * @return
	 */
	public K getMappedTo(K newKey) {
		return mapping.get(newKey);
	}
	
	/**
	 * Returns the farthest key to which the given key points to.<br>
	 * The returned key is the "original" key which is the key that is actually 
	 * used in the hash map to reference to the value.<br>
	 * Since mappings can be mapped to other mappings, this method travels 
	 * through all the mappings until a mapped key matches one in the hash map
	 * 
	 * @param key
	 * @return
	 */
	public K getMapping(K key) {
		return getKeyK(key);
	}
	
	/**
	 * Returns all the keys which point to the key <code>mapTo</code>
	 * 
	 * @param mapTo
	 * @return
	 */
	public HashSet<K> getReverseMapping(K mapTo) {
		return mappingReverse.get(mapTo);
	}
	
	/**
	 * Returns the "original" key which is used in the hash map
	 * 
	 * @param key
	 * @return
	 */
	private Object getKeyObject(Object key) {
		Object returnKey = key;
		
		while (!super.containsKey(returnKey)) {
			if (mapping.containsKey(returnKey)) {
				returnKey = mapping.get(returnKey);
			} else {
				break;
			}
		}
		
		return returnKey;
	}
	
	/**
	 * Returns the "original" key which is used in the hash map
	 * 
	 * @param key
	 * @return
	 */
	private K getKeyK(K key) {
		K returnKey = key;
		
		while (!super.containsKey(returnKey)) {
			if (mapping.containsKey(returnKey)) {
				returnKey = mapping.get(returnKey);
			} else {
				break;
			}
		}
		
		return returnKey;
	}
	
	/**
	 * Copies the mappings of the given {@link MultiKeyHashMap} to this {@link MultiKeyHashMap}
	 * 
	 * @param m
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })	//Only the mapping fields are accessed, thus the type of the map does not matter
	public void copyMapping(MultiKeyHashMap m) {
		mapping.putAll(m.mapping);
		mappingReverse.putAll(m.mappingReverse);
	}
	
	@Override
	public V get(Object key) {
		return super.get(getKeyObject(key));
	}
	
	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(getKeyObject(key));
	}
	
	@Override
	public V put(K key, V value) {
		return super.put(getKeyK(key), value);
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Map<K, V> m2 = new HashMap<K, V>(m.size());
		
		//Create a new map and replace all keys with their mapping if there is 
		//a mapping available
		for (K key : m.keySet()) {
			if (mapping.containsKey(key)) {
				m2.put(mapping.get(key), m.get(key));
			} else {
				m2.put(key, m.get(key));
			}
		}
		
		super.putAll(m2);
	}
	
	
	@Override
	public String toString() {
		//Creates a string which shows a list of all the 
		//mapped keys and the original key, plus it shows the mapping relationships
		
		HashMap<K, StringBuilder> replacement = new HashMap<K, StringBuilder>();
		
		for (K key : mappingReverse.keySet()) {
			if (!replacement.containsKey(getKeyK(key))) {
				replacement.put(getKeyK(key), new StringBuilder());
			}
			
			if (replacement.get(getKeyK(key)).length() > 0) {
				replacement.get(getKeyK(key)).append(", ");
			}
			
			replacement.get(getKeyK(key)).append(mappingReverse.get(key) + "->" + key);
		}
				
		StringBuilder sb = new StringBuilder("{");
		
		for (K key : keySet()) {
			if (sb.length() > 1) {
				sb.append(", ");
			}
			
			if (replacement.containsKey(key)) {
				sb.append("[" + replacement.get(key) + ", " + key + "]");
			} else {
				sb.append(key);
			}
			
			sb.append("=" + super.get(key));
		}
		
		sb.append("}");
		return sb.toString();
	}
	

}
