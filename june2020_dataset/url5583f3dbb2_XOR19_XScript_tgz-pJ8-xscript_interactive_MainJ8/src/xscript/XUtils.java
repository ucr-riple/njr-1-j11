package xscript;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptException;

import xscript.object.XObject;
import xscript.object.XObjectData;
import xscript.object.XObjectDataString;
import xscript.object.XRuntime;
import xscript.object.XTypeData;
import xscript.values.XValue;
import xscript.values.XValueAccess;
import xscript.values.XValueAttr;
import xscript.values.XValueBool;
import xscript.values.XValueFloat;
import xscript.values.XValueInt;
import xscript.values.XValueNull;
import xscript.values.XValueObj;
import xscript.values.XValueObjSuper;

public class XUtils {

	public static final int NULL = 0;
	public static final int BOOL = 1;
	public static final int NUMBER = 2;
	public static final int INT = 3;
	public static final int FLOAT = 4;
	public static final int FUNC = 5;
	public static final int LIST = 6;
	public static final int NATIVE_FUNC = 7;
	public static final int STRING = 8;
	public static final int TUPLE = 9;
	public static final int TYPE = 10;
	public static final int OBJECT = 11;
	public static final int MAP = 12;
	public static final int WEAK_REF = 13;
	public static final int MODULE = 14;
	public static final int CONST_POOL = 15;
	public static final int NUM_BASE_TYPES = 16;

	public static XValue lookupTry(XRuntime runtime, XValue value, String attr,
			int addRef) {
		XValue v = value.getRaw(runtime, attr);
		if (v == null || v instanceof XValueAttr || v instanceof XValueAccess) {
			XValue type = value.getType(runtime);
			XObject obj = runtime.getObject(type);
			XTypeData typeData = (XTypeData) obj.getData();
			List<XValue> classes = typeData.getCRO();
			if (value instanceof XValueObjSuper) {
				int _super = ((XValueObjSuper) value).getCastToType();
				boolean found = false;
				for (XValue t : classes) {
					if (found) {
						v = t.getRaw(runtime, attr);
						if (v == null)
							continue;
						if (v instanceof XValueAttr) {
							obj = runtime.getObject(t);
							typeData = (XTypeData) obj.getData();
							v = typeData.getAttr(runtime, value, obj,
									((XValueAttr) v).getAttrID());
						} else if (v instanceof XValueAccess) {
							v = ((XValueAccess) v).getHolding();
						}
						break;
					} else {
						if (((XValueObj) t).getPointer() == _super) {
							found = true;
						}
					}
				}
			} else {
				for (XValue t : classes) {
					v = t.getRaw(runtime, attr);
					if (v == null)
						continue;
					if (v instanceof XValueAttr) {
						obj = runtime.getObject(t);
						typeData = (XTypeData) obj.getData();
						v = typeData.getAttr(runtime, value, obj,
								((XValueAttr) v).getAttrID());
					} else if (v instanceof XValueAccess) {
						v = ((XValueAccess) v).getHolding();
					}
					break;
				}
			}
		}
		return XValue.incRef(runtime, v, addRef);
	}

	public static XValue lookup(XRuntime runtime, XValue value, String attr,
			int addRef) {
		XValue v = lookupTry(runtime, value, attr, addRef);
		if (v == null) {
			throw new XRuntimeException("TypeError",
					"Attribute '%s' not found", attr);
		}
		return v;
	}

	public static XValue set(XRuntime runtime, XValue value, String attr,
			XValue v, int addRef) {
		if (v == null)
			throw new NullPointerException();
		XObject obj = runtime.getObject(value);
		XValue v2 = obj == null ? null : obj.replaceRaw(attr, v);
		boolean ok = false;
		if (v2 == null) {
			XValue type = value.getType(runtime);
			XObject typeObj = runtime.getObject(type);
			XTypeData typeData = (XTypeData) typeObj.getData();
			List<XValue> classes = typeData.getCRO();
			if (value instanceof XValueObjSuper) {
				int _super = ((XValueObjSuper) value).getCastToType();
				boolean found = false;
				for (XValue t : classes) {
					if (found) {
						v2 = t.getRaw(runtime, attr);
						if (v2 != null) {
							if (v2 instanceof XValueAttr) {
								typeObj = runtime.getObject(t);
								typeData = (XTypeData) typeObj.getData();
								v2 = typeData.setAttr(runtime, value, typeObj,
										((XValueAttr) v2).getAttrID(), v);
								ok = true;
							}
							break;
						}
					} else {
						if (((XValueObj) t).getPointer() == _super) {
							found = true;
						}
					}
				}
			} else {
				for (XValue t : classes) {
					v2 = t.getRaw(runtime, attr);
					if (v2 != null) {
						if (v2 instanceof XValueAttr) {
							typeObj = runtime.getObject(t);
							typeData = (XTypeData) typeObj.getData();
							v2 = typeData.setAttr(runtime, value, typeObj,
									((XValueAttr) v2).getAttrID(), v);
							ok = true;
						}
						break;
					}
				}
			}
		}
		if (!ok && obj != null) {
			v2 = obj.setRaw(attr, v);
		}
		v.incRef(runtime);
		return XValue.incRef(runtime, v2, addRef);
	}

	public static XValue del(XRuntime runtime, XValue value, String attr,
			int addRef) {
		XValue v = value.delRaw(runtime, attr);
		if (v == null) {
			XValue type = value.getType(runtime);
			XObject obj = runtime.getObject(type);
			XTypeData typeData = (XTypeData) obj.getData();
			List<XValue> classes = typeData.getCRO();
			if (value instanceof XValueObjSuper) {
				int _super = ((XValueObjSuper) value).getCastToType();
				boolean found = false;
				for (XValue t : classes) {
					if (found) {
						v = t.getRaw(runtime, attr);
						if (v instanceof XValueAttr) {
							obj = runtime.getObject(t);
							typeData = (XTypeData) obj.getData();
							v = typeData.delAttr(runtime, value, obj,
									((XValueAttr) v).getAttrID());
							break;
						} else if (v != null) {
							return null;
						}
					} else {
						if (((XValueObj) t).getPointer() == _super) {
							found = true;
						}
					}
				}
			} else {
				for (XValue t : classes) {
					v = t.getRaw(runtime, attr);
					if (v instanceof XValueAttr) {
						obj = runtime.getObject(t);
						typeData = (XTypeData) obj.getData();
						v = typeData.delAttr(runtime, value, obj,
								((XValueAttr) v).getAttrID());
						break;
					} else if (v != null) {
						return null;
					}
				}
			}
		}
		return XValue.incRef(runtime, v, addRef);
	}

	public static XValue getDeclaring(XRuntime runtime, XValue value,
			String attr, int addRef) {
		XValue v = value.getRaw(runtime, attr);
		if (v != null
				&& !(v instanceof XValueAccess || v instanceof XValueAttr))
			return value;
		XValue type = value.getType(runtime);
		XObject obj = runtime.getObject(type);
		XTypeData typeData = (XTypeData) obj.getData();
		List<XValue> classes = typeData.getCRO();
		for (XValue t : classes) {
			if (t.getRaw(runtime, attr) != null) {
				return t;
			}
		}
		return null;
	}

	public static boolean isOverwritten(XRuntime runtime, XValue value,
			String attr) {
		XValue declarer = getDeclaring(runtime, value, attr, XValue.REF_NONE);
		return declarer != null && declarer != runtime.getBaseType(OBJECT);
	}

	@SuppressWarnings("unchecked")
	public static List<XValue> asList(XRuntime runtime, XValue pointer) {
		XObject obj = runtime.getObject(pointer);
		XObjectData data = obj == null ? null : obj.getData();
		return data instanceof List ? (List<XValue>) data : null;
	}

	public static void putAtMapItem(XRuntime rt, XValue v1, String s, XValue v2) {
		int i = s.indexOf('.');
		int l = 0;
		while (i != -1) {
			String sub = s.substring(l, i);
			XValue v3 = lookupTry(rt, v1, sub, XValue.REF_NONE);
			if (v3 == null || v3 == XValueNull.NULL) {
				v3 = rt.createMap(new HashMap<String, XValue>());
				set(rt, v1, sub, v3, XValue.REF_NONE);
			}
			v1 = v3;
			l = i + 1;
			i = s.indexOf('.', l);
		}
		set(rt, v1, s.substring(l), v2, XValue.REF_NONE);
	}

	public static String getString(XRuntime rt, XValue value) {
		XObjectData data = getData(rt, value);
		return data instanceof XObjectDataString ? ((XObjectDataString) data)
				.getString() : null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getDataAs(XRuntime rt, XValue value, Class<T> c) {
		XObjectData data = getData(rt, value);
		return data != null && c.isAssignableFrom(data.getClass()) ? (T) data
				: null;
	}

	public static XObjectData getData(XRuntime rt, XValue value) {
		XObject obj = rt.getObject(value);
		return obj == null ? null : obj.getData();
	}

	public static boolean isInstanceOf(XRuntime rt, XValue instance, XValue type) {
		XValue instanceType = instance.getType(rt);
		return isDerivedOf(rt, type, instanceType);
	}

	public static boolean isDerivedOf(XRuntime rt, XValue base, XValue type) {
		XObject typeObj = rt.getObject(type);
		XObject baseObj = rt.getObject(base);
		if (typeObj == null || !typeObj.isType(rt) || baseObj == null
				|| !baseObj.isType(rt))
			return false;
		return ((XTypeData) typeObj.getData()).getCRO().contains(base);
	}

	public static void rethrow(Throwable e) {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		} else if (e instanceof Error) {
			throw (Error) e;
		}
		throw new RuntimeException(e);
	}

	public static XValue[] wrap(XRuntime rt, Object[] obj) {
		XValue[] values = new XValue[obj.length];
		for (int i = 0; i < obj.length; i++) {
			values[i] = wrap(rt, obj[i]);
		}
		return values;
	}

	public static XValue wrap(XRuntime rt, Object obj) {
		if (obj == null) {
			return XValueNull.NULL;
		} else if (obj instanceof XValue) {
			return (XValue) obj;
		} else if (obj instanceof XObject) {
			XObject object = (XObject) obj;
			if (rt.getObject(object.getPointer()) != object) {
				throw new IllegalArgumentException(
						"XObject is form another Runtime");
			}
			return new XValueObj(object.getPointer());
		} else if (obj instanceof String) {
			return rt.alloc((String) obj);
		} else if (obj instanceof Character) {
			return XValueInt.valueOf(((Character) obj).charValue());
		} else if (obj instanceof Number) {
			if (obj instanceof Byte || obj instanceof Short
					|| obj instanceof Integer || obj instanceof Long
					|| obj instanceof BigInteger) {
				return XValueInt.valueOf(((Number) obj).longValue());
			}
			return new XValueFloat(((Number) obj).doubleValue());
		} else if (obj instanceof Boolean) {
			return XValueBool.valueOf(((Boolean) obj).booleanValue());
		} else if (obj instanceof List) {
			List<?> l = (List<?>) obj;
			List<XValue> list = new ArrayList<XValue>(l.size());
			for (Object o : l) {
				list.add(wrap(rt, o));
			}
			return rt.createList(list);
		} else if (obj instanceof Map) {
			Map<?, ?> m = (Map<?, ?>) obj;
			Map<String, XValue> map = new HashMap<String, XValue>(m.size());
			for (Entry<?, ?> e : m.entrySet()) {
				Object key = e.getKey();
				map.put(key == null ? null : key.toString(),
						wrap(rt, e.getValue()));
			}
			return rt.createMap(map);
		} else if (obj instanceof boolean[]) {
			boolean[] a = (boolean[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(XValueBool.valueOf(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof byte[]) {
			byte[] a = (byte[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(XValueInt.valueOf(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof short[]) {
			short[] a = (short[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(XValueInt.valueOf(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof int[]) {
			int[] a = (int[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(XValueInt.valueOf(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof long[]) {
			long[] a = (long[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(XValueInt.valueOf(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof float[]) {
			float[] a = (float[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(new XValueFloat(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof double[]) {
			float[] a = (float[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(new XValueFloat(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj instanceof char[]) {
			char[] a = (char[]) obj;
			List<XValue> list = new ArrayList<XValue>(a.length);
			for (int i = 0; i < a.length; i++) {
				list.add(XValueInt.valueOf(a[i]));
			}
			return rt.createTuple(list);
		} else if (obj.getClass().isArray()) {
			int l = Array.getLength(obj);
			List<XValue> list = new ArrayList<XValue>(l);
			for (int i = 0; i < l; i++) {
				list.add(wrap(rt, Array.get(obj, i)));
			}
			return rt.createTuple(list);
		}
		throw new IllegalArgumentException();
	}

	public static ScriptException getException(XRuntime rt, XValue exception) {
		String type = XUtils.getDataAs(rt, exception.getType(rt),
				XTypeData.class).getName();
		XValue cause = valide(exception.getRaw(rt, "_cause"));
		XValue message = valide(exception.getRaw(rt, "_message"));
		XValue stackTrace = valide(exception.getRaw(rt, "_stackTrace"));
		String m = XUtils.getString(rt, message);
		if (m == null) {
			m = type;
		} else {
			m = type + ": " + m;
		}
		StackTraceElement[] jstackTrace = null;
		if (stackTrace != null && stackTrace != XValueNull.NULL) {
			List<XValue> list = XUtils.asList(rt, stackTrace);
			if (list != null) {
				jstackTrace = new StackTraceElement[list.size()];
				int i = 0;
				for (XValue v : list) {
					String declaringPath = getString(rt,
							v.getRaw(rt, "_declaringPath"));
					String methodName = getString(rt,
							v.getRaw(rt, "_methodName"));
					String fileName = getString(rt, v.getRaw(rt, "_fileName"));
					int lineNumber = (int) v.getRaw(rt, "_lineNumber").getInt();
					jstackTrace[i++] = new StackTraceElement(declaringPath,
							methodName, fileName, lineNumber);
				}
			}
		}
		String fileName = jstackTrace == null || jstackTrace.length == 0 ? null
				: jstackTrace[0].getFileName();
		int line = jstackTrace == null || jstackTrace.length == 0 ? -1
				: jstackTrace[0].getLineNumber();
		ScriptException e;
		if (cause == null || cause == XValueNull.NULL) {
			e = new XScriptException(m, fileName, line);
		} else {
			ScriptException c = getException(rt, cause);
			e = new XScriptException(m, fileName, line, c);
		}
		if (jstackTrace != null && jstackTrace.length != 0) {
			StackTraceElement[] ste = e.getStackTrace();
			StackTraceElement[] nste = new StackTraceElement[ste.length
					+ jstackTrace.length];
			System.arraycopy(jstackTrace, 0, nste, 0, jstackTrace.length);
			System.arraycopy(ste, 0, nste, jstackTrace.length, ste.length);
			e.setStackTrace(nste);
		}
		return e;
	}

	public static XValue valide(XValue value) {
		if (value instanceof XValueAttr || value instanceof XValueAccess) {
			return null;
		}
		return value;
	}

	public static void throwException(XRuntime rt, XValue exception)
			throws ScriptException {
		throw getException(rt, exception);
	}

	public static void check(XRuntime rt, XValue instance, XValue type) {
		if (instance != XValueNull.NULL && !isInstanceOf(rt, instance, type)) {
			throw new XRuntimeException("TypeError", "Bad type");
		}
	}

	public static void check(XRuntime rt, XValue instance, int type) {
		check(rt, instance, rt.getBaseType(type));
	}

	public static XValue convertStackTrace(XRuntime rt,
			StackTraceElement[] stackTrace) {
		List<XValue> st = new ArrayList<XValue>(stackTrace.length);
		XValue sys = rt.getModule("sys");
		XValue stet = valide(sys.getRaw(rt, "StackTraceElement"));
		for (int i = 0; i < stackTrace.length; i++) {
			StackTraceElement rste = stackTrace[i];
			XValue declaringClass = rt.alloc(rste.getClassName());
			XValue methodName = rt.alloc(rste.getMethodName());
			XValue fileName = rt.alloc(rste.getFileName());
			XValue lineNumber = XValueInt.valueOf(rste.getLineNumber());
			XValue ste = rt.alloc(stet);
			ste.setRaw(rt, "_declaringPath", declaringClass);
			ste.setRaw(rt, "_methodName", methodName);
			ste.setRaw(rt, "_fileName", fileName);
			ste.setRaw(rt, "_lineNumber", lineNumber);
			st.add(ste);
		}
		return rt.createTuple(st);
	}

	public static List<String> dir(XRuntime rt, XValue obj) {
		List<String> list = new ArrayList<String>();

		XValue type = obj.getType(rt);
		XTypeData typeData = getDataAs(rt, type, XTypeData.class);
		List<XValue> classes = typeData.getCRO();

		if (obj instanceof XValueObjSuper) {
			int _super = ((XValueObjSuper) obj).getCastToType();
			boolean found = false;
			for (XValue t : classes) {
				if (found) {
					XObject typeObj = rt.getObject(t);
					Set<String> keys = typeObj.getKeys();
					for (String key : keys) {
						if (typeObj.getRaw(key) instanceof XValueAttr) {
							list.add(key);
						}
					}
				} else {
					if (((XValueObj) t).getPointer() == _super) {
						found = true;
					}
				}
			}
		} else {
			for (XValue t : classes) {
				XObject typeObj = rt.getObject(t);
				Set<String> keys = typeObj.getKeys();
				for (String key : keys) {
					if (typeObj.getRaw(key) instanceof XValueAttr) {
						list.add(key);
					}
				}
			}
		}

		XObject o = rt.getObject(obj);
		if (o != null) {
			Set<String> keys = o.getKeys();
			for (String key : keys) {
				list.add(key);
			}
		}

		return list;
	}

}
