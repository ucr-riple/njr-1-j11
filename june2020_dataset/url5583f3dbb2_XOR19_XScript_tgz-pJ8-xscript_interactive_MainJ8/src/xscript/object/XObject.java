package xscript.object;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import xscript.XUtils;
import xscript.values.XValue;
import xscript.values.XValueAccess;
import xscript.values.XValueAttr;
import xscript.values.XValueNull;
import xscript.values.XValueObj;

public final class XObject {

	private static final int DEFAULT_CAPACITY = 16;

	private final transient int pointer;
	private transient boolean visible;
	private int refs;
	private int extRefs;
	private XValue type;
	private final HashMap<String, XValue> fields;
	private XObjectData objectData;
	private XValue weakRef = XValueNull.NULL;

	public XObject(XRuntime runtime, int pointer, XValue type, Object[] args) {
		this.pointer = pointer;
		this.type = type;
		fields = new HashMap<String, XValue>();
		if (pointer == XUtils.OBJECT) {
			objectData = new XTypeDataObject(runtime, this);
		} else if (pointer == XUtils.TYPE) {
			objectData = new XTypeDataType(runtime, this);
		} else if (pointer == XUtils.NATIVE_FUNC) {
			objectData = new XTypeDataNativeFunc(runtime, this);
		} else {
			XTypeData typeData = (XTypeData) runtime.getObject(type).getData();
			objectData = typeData.createData(runtime, this, args);
		}
	}

	public XObject(int pointer, ObjectInput in) throws IOException {
		this.pointer = pointer;
		refs = in.readInt();
		extRefs = in.readInt();
		type = new XValueObj(in.readInt());
		int count = in.readInt();
		fields = new HashMap<String, XValue>(
				count < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : count);
		while (count > 0) {
			String key = in.readUTF();
			XValue value = XValue.read(in);
			fields.put(key, value);
			count--;
		}
		weakRef = XValue.read(in);
	}

	public XValue getType() {
		return type;
	}

	public XValue setType(XRuntime runtime, XValue type) {
		if (objectData != null)
			throw new UnsupportedOperationException();
		XTypeData typeData = (XTypeData) runtime.getObject(type).getData();
		Class<? extends XTypeData> c = typeData.getClass();
		if (!(c == XTypeData.class || c == XTypeDataObject.class)) {
			throw new UnsupportedOperationException();
		}
		XValue old = this.type;
		this.type = type;
		return old;
	}

	public void incRef(XRuntime runtime) {
		refs++;
	}

	public boolean decRef(XRuntime runtime) {
		if (refs <= 0) {
			// throw new IllegalStateException();
		}
		if (--refs == 0) {
			return !tryDelete(runtime);
		}
		return true;
	}

	public void incExtRef(XRuntime runtime) {
		extRefs++;
	}

	public boolean decExtRef(XRuntime runtime) {
		if (extRefs <= 0) {
			throw new IllegalStateException();
		}
		if (--extRefs == 0) {
			return !tryDelete(runtime);
		}
		return true;
	}

	public boolean tryDelete(XRuntime runtime) {
		// if(refs<=0&&extRefs<=0){
		// return delete(runtime);
		// }
		return false;
	}

	public boolean delete(XRuntime runtime, boolean cleanup) {
		runtime.delete(this);
		if (cleanup) {
			type.decRef(runtime);
			for (XValue value : fields.values()) {
				value.decRef(runtime);
			}
		}
		if (objectData != null) {
			objectData.delete(runtime, cleanup);
		}
		if (weakRef != XValueNull.NULL) {
			XObjectDataWeakRef ref = XUtils.getDataAs(runtime, weakRef,
					XObjectDataWeakRef.class);
			ref.release();
		}
		return true;
	}

	public void setVisible(XRuntime runtime) {
		if (visible) {
			return;
		}
		visible = true;
		type.setVisible(runtime);
		for (XValue value : fields.values()) {
			value.setVisible(runtime);
		}
		if (objectData != null) {
			objectData.setVisible(runtime);
		}
	}

	public void resetVisible() {
		visible = false;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isExternalUsed() {
		return extRefs > 0;
	}

	public XValue getRaw(String attr) {
		return fields.get(attr);
	}

	public XValue setRaw(String attr, XValue value) {
		if (value == null)
			throw new NullPointerException();
		return fields.put(attr, value);
	}

	public XValue replaceRaw(String attr, XValue value) {
		if (value == null)
			throw new NullPointerException();
		if (fields.containsKey(attr))
			return fields.put(attr, value);
		return null;
	}

	public XValue delRaw(String attr) {
		return fields.remove(attr);
	}

	public int getPointer() {
		return pointer;
	}

	public XObjectData getData() {
		return objectData;
	}

	public void setTypeData(XRuntime runtime, XTypeData data) {
		if (objectData != null)
			throw new IllegalStateException();
		if (runtime.getObject(type) == this
				|| type.equals(runtime.getBaseType(XUtils.TYPE))) {
			objectData = data;
		} else {
			throw new IllegalStateException();
		}
	}

	public Object toJava(XRuntime runtime) {
		if (objectData != null) {
			Object obj = objectData.toJava(runtime, this);
			if (obj != null)
				return obj;
		}
		return this;
	}

	public void save(ObjectOutput out) throws IOException {
		out.writeInt(refs);
		out.writeInt(extRefs);
		out.writeInt(((XValueObj) type).getPointer());
		out.writeInt(fields.size());
		for (Entry<String, XValue> e : fields.entrySet()) {
			XValue value = e.getValue();
			if (value instanceof XValueAttr || value instanceof XValueAccess)
				continue;
			out.writeUTF(e.getKey());
			XValue.write(out, value);
		}
		XValue.write(out, weakRef);
	}

	public void loadData(XRuntime runtime, ObjectInput in) throws IOException {
		XTypeData typeData = (XTypeData) runtime.getObject(type).getData();
		objectData = typeData.loadData(runtime, this, in);
	}

	public void saveData(ObjectOutput out) throws IOException {
		if (objectData != null) {
			objectData.save(out);
		}
	}

	public boolean isType(XRuntime runtime) {
		return type.equals(runtime.getBaseType(XUtils.TYPE));
	}

	public XValue getWeakRef(XRuntime runtime) {
		if (weakRef == XValueNull.NULL) {
			weakRef = runtime.alloc(runtime.getBaseType(XUtils.WEAK_REF),
					new XValueObj(pointer));
		}
		return weakRef;
	}

	public void releaseWeakRef() {
		weakRef = XValueNull.NULL;
	}

	public Set<String> getKeys() {
		return fields.keySet();
	}

}
