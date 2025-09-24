package mi.common;

import java.util.Iterator;

/**
 * User: goldolphin
 * Time: 2013-06-19 22:10
 */
public class CharHashMap<T> implements Iterable<CharHashMap.Entry<T>> {
    private final double loadFactor;
    private int capacity;
    private int threshold;
    private Entry<T>[] table;
    private int size;

    public CharHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        init(initialCapacity);
    }

    public CharHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    public CharHashMap() {
        this(16);
    }

    private void init(int initialCapacity) {
        capacity = 1;
        while (capacity < initialCapacity) {
            capacity *= 2;
        }
        threshold = (int) (capacity *loadFactor);
        if (threshold > capacity) {
            threshold = capacity;
        }
        table = new Entry[capacity];
        size = 0;
    }

    public boolean contains(char key) {
        return getEntry(indexOf(key), key) != null;
    }

    public T get(char key) {
        Entry<T> entry = getEntry(indexOf(key), key);
        return entry == null ? null : entry.value;
    }

    public void put(char key, T value) {
        int index = indexOf(key);
        Entry<T> entry = getEntry(indexOf(key), key);
        if (entry != null) {
            entry.value = value;
            return;
        }

        if (size >= threshold) {
            resize(capacity*2);
        }

        addEntry(index, new Entry<T>(key, value));
    }

    public void remove(char key) {
        removeEntry(indexOf(key), key);
    }

    public void clear() {
        for (int i = 0; i < capacity; i ++) {
            table[i] = null;
            size = 0;
        }
    }

    public int size() {
        return size;
    }

    private int indexOf(char key) {
        return key & (capacity -1);
    }

    private Entry<T> getEntry(int index, char key) {
        for (Entry<T> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                return entry;
            }
        }
        return null;
    }

    private void addEntry(int index, Entry<T> entry) {
        entry.next = table[index];
        table[index] = entry;
        size ++;
    }

    private void resize(int newCapacity) {
        Entry<T>[] oldTable = table;
        init(newCapacity);
        for (Entry<T> entry: oldTable) {
            for (; entry != null; entry = entry.next) {
                addEntry(indexOf(entry.key), entry);
            }
        }
    }

    private void removeEntry(int index, char key) {
        Entry<T> entry = table[index];
        if (entry == null) {
            return;
        }
        if (entry.key == key) {
            table[index] = entry.next;
            size --;
            return;
        }
        for (; entry.next != null; entry = entry.next) {
            if (entry.next.key == key) {
                entry.next = entry.next.next;
                size --;
                return;
            }
        }
    }

    @Override
    public java.util.Iterator<Entry<T>> iterator() {
        return new Iterator();
    }

    public static class Entry<T> {
        private final char key;
        private T value;
        private Entry<T> next;

        private Entry(char key, T value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public char getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }

    private class Iterator implements java.util.Iterator<Entry<T>> {
        private int index = 0;
        private Entry<T> current = null;

        private Iterator() {
            for (; index < table.length; index ++) {
                if (table[index] != null) {
                    current = table[index];
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Entry<T> next() {
            Entry<T> last = current;
            current = current.next;
            if (current == null) {
                index ++;
                for (; index < table.length; index ++) {
                    if (table[index] != null) {
                        current = table[index];
                        break;
                    }
                }
            }
            return last;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        CharHashMap<Object> map = new CharHashMap<>();
        char c = '我';
        System.out.println((int)'们');
        System.out.println(map.contains(c));
        map.put(c, null);
        map.put('们', null);
        System.out.println(map.contains(c));
        System.out.println(map.size());
        map.remove(c);
        System.out.println(map.contains(c));
        System.out.println(map.size());
        map.clear();
        System.out.println(map.size());
    }
}
