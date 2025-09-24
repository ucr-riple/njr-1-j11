package xscript.object;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import xscript.values.XValue;

public class XObjectDataMap implements XObjectData, Map<String, XValue> {

	private Map<String, XValue> map;

	public XObjectDataMap(Map<String, XValue> map) {
		this.map = map;
	}

	@Override
	public void delete(XRuntime runtime, boolean cleanup) {
		if (cleanup) {
			for (XValue value : map.values()) {
				value.decRef(runtime);
			}
		}
	}

	@Override
	public void setVisible(XRuntime runtime) {
		for (XValue value : map.values()) {
			value.setVisible(runtime);
		}
	}

	@Override
	public void save(ObjectOutput out) throws IOException {
		out.writeInt(map.size());
		for (Entry<String, XValue> e : map.entrySet()) {
			out.writeUTF(e.getKey());
			XValue.write(out, e.getValue());
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public XValue get(Object key) {
		return map.get(key);
	}

	@Override
	public XValue put(String key, XValue value) {
		return map.put(key, value);
	}

	@Override
	public XValue remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends XValue> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<XValue> values() {
		return map.values();
	}

	@Override
	public Set<Entry<String, XValue>> entrySet() {
		return map.entrySet();
	}
	
	@Override
	public Object toJava(XRuntime runtime, XObject object) {
		return this;
	}

}
