/**
 * Copyright Justin Short. All rights reserved.
 */
package AMath;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import Game.AGPmain;

public class ArrayUtils {

	/** by braylan */
	public static <T> T randomIndexOf(T[] array) {
		return (array == null || array.length == 0) ? null : array[AGPmain.rand.nextInt(array.length)];
	}
	

	/** by braylan */
	public static <T> T[] orderByComparator(Class<T> clasz, T[] array, Comparator<T> comparator) {
		SortedSet<T> set = new TreeSet<T>(comparator);
		for(T t : array) {set.add(t);}
		T[] result = createArray(clasz, set.size());
		int i = 0;   for (T t : set) {
			result[i++] = t;
		}
		return result;
	}
	
	/** by braylan */
	public static <T> T[] shuffle(Class<T> clasz, T[] array) {
		ArrayList<T> arrayList = new ArrayList<T>();
		for (T t : array) {arrayList.add(t);}
		T[] result = createArray(clasz, arrayList.size());
		int i = 0;   int x;   while (arrayList.size() != 0) {
			x = AGPmain.rand.nextInt(arrayList.size());
			result[i++] = arrayList.get(x);
			arrayList.remove(x);
		}
		return result;
	}
	
	
	/** Find and return the first item in the array such that (o == null ? item == null : o.equals(item))
	 */
	public static final <T> int indexOf(T[] ts, Object o) {
		if (ts != null) for (int i = 0; i < ts.length; ++i) {
			if (Calc.equal(ts[i], o)) return i;
		}
		return -1;
	}

	
	public static final <T> int lastIndexOf(T[] ts, Object o) {
		for (int i = ts.length - 1; i >= 0; --i) {
			if (Calc.equal(ts[i], o)) return i;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public static final <T> T[] createArray(Class<T> clasz, int len) {
		return (T[])Array.newInstance(clasz, len);
	}
	
	public static final <T> T[] cat(T... items) {
		return items;
	}

	@SuppressWarnings("unchecked")
	public static final <T> T[] append(T[] array, T item) {
		return append((Class<T>)array.getClass().getComponentType(), array, item);
	}

	public static final <T> T[] appendItem(Class<T> clasz, T[] array, T item) {
		if (array == null || array.length == 0) {
			final T[] result = createArray(clasz, 1);
			result[0] = item;
			return result;
		} 
		
		final T[] result = createArray(clasz, array.length + 1);
		System.arraycopy(array, 0, result, 0, array.length);
		result[array.length] = item;
		return result;
	}
	
	public static final <T> T[] append(Class<T> clasz, T[] array, T... items) {
		if(items != null && items.length == 1){
			return appendItem(clasz, array, items[0]);
		}
		
		if (array == null || array.length == 0) {
			return items;
		} 
		
		final T[] result = createArray(clasz, array.length + items.length);
		System.arraycopy(array, 0, result, 0, array.length);
		System.arraycopy(items, 0, result, array.length, items.length);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T[] prepend(T[] array, T item) {
		return prepend((Class<T>)array.getClass().getComponentType(), array, item);
	}

	public static final <T> T[] prepend(Class<T> clasz, T[] array, T item) {
		if (array == null || array.length == 0) {
			final T[] result = createArray(clasz, 1);
			result[0] = item;
			return result;
		} 

		T[] result = createArray(clasz, array.length + 1);
		System.arraycopy(array, 0, result, 1, array.length);
		result[0] = item;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T[] remove(T[] array, int index) {
		return remove((Class<T>)array.getClass().getComponentType(), array, index);	
	}

	public static final <T> T[] remove(Class<T> clasz, T[] array, int index) {
		T[] result = createArray(clasz, array.length - 1);
		if (index > 0) System.arraycopy(array, 0, result, 0, index);
		if (index < array.length - 1) System.arraycopy(array, index + 1, result, index, result.length - index);
		return result;
	}
	
	/**
	 * @param <T>
	 * @param array
	 * @param item
	 * @param preferNull if true and we're removing the only element in the array, returns null instead of 
	 * an empty array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] removeFirst(T[] array, T item, boolean preferNull) {
		if (array == null || array.length == 0) return array;
		final int index = indexOf(array, item);
		if (index < 0) return array;
		if (preferNull && array.length == 1) return null;
		return remove((Class<T>)array.getClass().getComponentType(), array, index);
	}

	public static final <T> int findAll(T[] copy, Collection<?> c, boolean[] markers) {
		int result = 0;
		for (int i = copy.length - 1; i >= 0; --i) {
			if (c.contains(copy[i])) {
				if (markers != null) markers[i] = true;
				++result;
			}
		}
		return result;
	}
	

	
	/** Returns true if the two arrays contain the same elements in the same (or reversed) order
	 * (assumes each value is only in each array once..)
	 */
	public static final boolean cyclicEqual(int[] arr, int[] brr) {
		final int len = arr.length;
		if (len != brr.length) return false;
		if (len == 0) return true;
		if (len == 1) return arr[0] == brr[0];
		
		int bstart;
		int bincr;
		for (bstart = 0; bstart < len; ++bstart) if (brr[bstart] == arr[0]) break;
		if (bstart >= len) return false;
		bincr = (brr[(bstart + 1) % len] == arr[1] ? 1 : -1);
		
		for (int i = 0; i < len; bstart = (bstart + bincr + len) % len, ++i) {
			if (arr[i] != brr[bstart]) return false;
		}
		return true;
	}

	public static final int[] reverseInPlace(int[] array) {
		int to = array.length - 1;
		for (int from = 0; from < to; ++from, --to) {
			int tmp = array[from]; array[from] = array[to]; array[to] = tmp;
		}
		return array;
	}

	public static final boolean equal(int[] a, int[] b) {
		final int len = a.length;
		if (len != b.length) return false;
		for (int i = len - 1; i >= 0; --i) if (a[i] != b[i]) return false;
		return true;
	}
	
	public static final <T> boolean shallowEqual(T[] a, T[] b) {
		final int len = a.length;
		if (len != b.length) return false;
		for (int i = len - 1; i >= 0; --i) if (a[i] != b[i]) return false;
		return true;
	}
	
	public static final <T> boolean deepEqual(T[] a, T[] b) {
		final int len = a.length;
		if (len != b.length) return false;
		for (int i = len - 1; i >= 0; --i) if (!Calc.equal(a[i], b[i])) return false;
		return true;
	}
	
	/** Returns the standard sequence <code>[0, 1, 2, ..]</code> of the given length
	 */
	public static final int[] ordinals(int length) {
		int[] result = new int[length];
		for (int i = 0; i < length; ++i) result[i] = i;
		return result;
	}
	
	/** Returns the median value of the values in the array. Might reorder the array on the way 
	 */
	public static final int median(int[] array) {
		return findNth(array, array.length >> 1);
	}

	/** Returns the Nth smallest value of the values in the array (0 is 1st). 
	 * Might reorder the array on the way 
	 */
	public static int findNth(int[] array, int n) {
		int start = 0;
		int end = array.length - 1;
		while (true) {
			if (array[start] > array[end]) {
				int tmp = array[start]; array[start] = array[end]; array[end] = tmp;
			}
			final int pivot = (start + end) >>> 1;
			if (array[start] > array[pivot]) {
				int tmp = array[start]; array[start] = array[pivot]; array[pivot] = tmp;
			} else if (array[pivot] > array[end]) { 
				int tmp = array[pivot]; array[pivot] = array[end]; array[end] = tmp;
			}
	
			if (end - start < 3) {
				return array[n];
			}
			
			final int pivotVal = array[pivot];
			int lt = start;
			int ge = end;
			while (true) {
				while (array[++lt] < pivotVal);
				while (array[--ge] >= pivotVal);
				if (lt < ge) {
					int tmp = array[lt]; array[lt] = array[ge]; array[ge] = tmp;
				} else {
					break;
				}
			}

			if (++ge == n) {
				return pivotVal;
			}
			if (n < ge) end = ge - 1;
			else start = ge;
		}
	}

	public static <T> T first(T[] array) {
		return (array == null || array.length == 0) ? null : array[0];
	}

	public static <T> T last(T[] array) {
		return (array == null || array.length == 0) ? null : array[array.length - 1];
	}

	public static long first(long[] array, long emptyValue) {
		return (array == null || array.length == 0) ? emptyValue : array[0];
	}

	public static long last(long[] array, long emptyValue) {
		return (array == null || array.length == 0) ? emptyValue : array[array.length - 1];
	}

	public static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}

	public static <T> T[] insertAt(T[] array, int index, T item) {
		@SuppressWarnings("unchecked")
		final Class<T> klass = (Class<T>)array.getClass().getComponentType();
		final T[] result;
		if (array == null || array.length == 0) {
			result = createArray(klass, index + 1);
		} else {
			result = createArray(klass, Math.max(array.length, index) + 1);
			if (index > 0) System.arraycopy(array, 0, result, 0, Math.min(index, array.length));
			if (index < array.length) System.arraycopy(array, index, result, index + 1, array.length - index);
		}
		result[index] = item;
		return result;
	}

	/** Calls == on each pair
	 */
	public static <T> boolean equalShallow(T[] a1, T[] a2) {
		if (a1 == null) return a2 == null;
		if (a2 == null) return false;
		if (a1.length != a2.length) return false;
		for (int i = 0; i < a1.length; ++i) if (a1[i] != a2[i]) return false; 
		return true;
	}

	/** Calls .equals() on each pair
	 */
	public static <T> boolean equalDeep(T[] a1, T[] a2) {
		if (a1 == null) return a2 == null;
		if (a2 == null) return false;
		if (a1.length != a2.length) return false;
		for (int i = 0; i < a1.length; ++i) if (!Calc.equal(a1[i], a2[i])) return false; 
		return true;
	}
	
}
