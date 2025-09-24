package DATA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Cette classe est une copie honteuse de la classe native java.util.HashMap<K, V>.
 * J'ai cependant été obligé de la recréer afin de pallier au problème de l'ordre de renvoi des HashMap classiques :
 * il est spécifié dans la javadoc de java.util.HashMap que les clefs ne sont pas nécessairement renvoyées dans l'ordre auquel elles sont ajoutées.
 * Hors, l'ordre dans lequel ces valeurs sont renvoyées est presque aléatoire (une simple ligne de code commentée dans une autre classe
 * peut le modifier), ce qui impactait gravement la réussite de l'algorythme.
 * Veuillez donc m'excuser pour ce bricolage absolument honteux et malpropre, mais je n'en suis venu à cette extrémité qu'à l'approche de la 
 * date limite de rendu, quand la recherce d'une solution plus viable aurait pris trop de temps.
 * @author arsaell
 */


public class HashMap<K, V> implements Map<K, V>, Iterable, Serializable {

	private ArrayList<K> keys = new ArrayList<K>();
	private ArrayList<V> values = new ArrayList<V>();

	@Override
	public int size() {
		return keys.size();
	}
	
	@Override
	public boolean isEmpty() {
		return keys.size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	public K getKey(int index)	{
		return this.keys.get(index);
	}
	
	public V getValue(int index)	{
		return this.values.get(index);
	}
	
	@Override
	public V get(Object key) {
		if (keys.indexOf(key) != -1)
			return values.get(keys.indexOf(key));
		return null;
	}

	@Override
	public V put(K key, V value) {
		int index = keys.indexOf(key);
		
		if (index == -1)	{
			keys.add(key);
			values.add(value);
		}
		
		else	{
			values.remove(index);
			values.add(index, value);
		}
		return value;
	}

	public void put(int index, K key, V value)	{
		int i = keys.indexOf(key);
		if (i != -1)	{
			keys.remove(i);
			values.remove(i);
		}
		keys.add(index, key);
		values.add(index, value);
	}
	
	@Override
	public V remove(Object key) {
		int index = keys.indexOf(key);
		if (index != - 1)	{
			V temp = values.get(index);
			keys.remove(index);
			values.remove(index);
			return temp;
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {

		for (K k : m.keySet())	{
			keys.add(k);
			values.add(m.get(k));
		}
	}

	@Override
	public void clear() {
		keys.clear();
		values.clear();
	}

	@Override
	public Set<K> keySet() {
		CopyOnWriteArraySet<K> temp = new CopyOnWriteArraySet<K>(keys);
		return temp;
	}

	@Override
	public Collection<V> values() {
		return values;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}

	@Override
	public Iterator<K> iterator() {
		return this.keys.iterator();
	}
	
	public K[] toArray()	{
		return (K[]) this.keys.toArray();
	}

	public String toString()	{
		String res = "HashMap :";
		for (int i = 0 ; i < this.size() ; i++)
			res += "\n\t" + this.keys.get(i) + "\t==>\t" + this.values.get(i);
		return res;
	}
}
