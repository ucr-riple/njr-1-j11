package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

import xscript.values.XValue;

public class XObjectDataTuple extends AbstractList<XValue> implements
		XObjectData {

	private List<XValue> list;

	public XObjectDataTuple(List<XValue> list) {
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
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
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
