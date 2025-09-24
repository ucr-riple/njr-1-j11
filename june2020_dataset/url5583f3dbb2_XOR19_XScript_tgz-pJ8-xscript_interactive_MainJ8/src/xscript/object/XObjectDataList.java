package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import xscript.values.XValue;

public class XObjectDataList extends AbstractList<XValue> implements
		XObjectData {

	private List<XValue> list;

	public XObjectDataList(List<XValue> list) {
		this.list = list;
	}

	@Override
	public void delete(XRuntime runtime, boolean cleanup) {
		if (cleanup) {
			for (XValue value : list) {
				value.decRef(runtime);
			}
		}
	}

	@Override
	public void setVisible(XRuntime runtime) {
		for (XValue value : list) {
			value.setVisible(runtime);
		}
	}

	@Override
	public void save(ObjectOutput out) throws IOException {
		out.writeInt(list.size());
		for (XValue value : list) {
			XValue.write(out, value);
		}
	}

	@Override
	public boolean add(XValue e) {
		if (e == null)
			throw new NullPointerException();
		return list.add(e);
	}

	@Override
	public void add(int index, XValue element) {
		if (element == null)
			throw new NullPointerException();
		list.add(index, element);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public XValue get(int index) {
		return list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<XValue> iterator() {
		return list.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public XValue remove(int index) {
		return list.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public XValue set(int index, XValue element) {
		if (element == null)
			throw new NullPointerException();
		return list.set(index, element);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return this;
	}

}
