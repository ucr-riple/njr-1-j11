package xscript;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xscript.object.XFunction;
import xscript.object.XObjectData;
import xscript.object.XObjectDataFunc;
import xscript.object.XObjectDataNativeFunc;
import xscript.object.XRuntime;
import xscript.object.XTypeData;
import xscript.values.XValue;
import xscript.values.XValueBool;
import xscript.values.XValueClosure;
import xscript.values.XValueFloat;
import xscript.values.XValueInt;
import xscript.values.XValueNull;
import xscript.values.XValueObj;
import xscript.values.XValueObjSuper;

public class XExec {

	private static final List<XValue> EMPTY_LIST = Collections.emptyList();

	private XRuntime rt;

	private boolean generator;

	private XValue[] stack;

	private int stackpointer;

	private XClosure closures;

	private XCallFrame callFrame;

	private XValue exception;

	private XExec caller;

	private XExec calling;

	private long timeout;

	private long lastTime;

	public XExec(XRuntime rt, boolean generator, int stacksize, XValue function, XValue thiz, XValue... args) {
		this.rt = rt;
		this.generator = generator;
		stack = new XValue[stacksize];
		XObjectDataFunc func = XUtils.getDataAs(rt, function, XObjectDataFunc.class);
		String[] paramNames = func.getParamNames();
		if (paramNames.length != args.length)
			throw new IllegalArgumentException();
		call(function, thiz, args, true);
	}

	public XClosure getClosure(int index) {
		if (index < 0)
			throw new IllegalArgumentException();
		index += callFrame.basepointer;
		if (index >= stackpointer)
			throw new IllegalArgumentException();
		XValue value = stack[index];
		if (value instanceof XValueClosure) {
			return ((XValueClosure) value).closure.incRef();
		}
		XClosure closure = new XClosure(this, closures);
		closure.value = stack[index] = new XValueClosure(index, stack[index], closure);
		return closures = closure;
	}

	public XValue pop() {
		if (callFrame != null && stackpointer == callFrame.basepointer)
			throw new IllegalStateException();
		XValue value = stack[--stackpointer];
		if (value instanceof XValueClosure) {
			XValueClosure closure = (XValueClosure) value;
			return closure.closure.value = closure.value;
		}
		return value;
	}

	public void pop(int count) {
		if (count < 0)
			throw new IllegalArgumentException();
		for (int i = 0; i < count; i++) {
			pop();
		}
	}

	public void push(XValue value) {
		if (value == null)
			value = XValueNull.NULL;
		stack[stackpointer++] = value;
	}

	public XRuntime getRuntime() {
		return rt;
	}

	public XValue get(int index) {
		if (index < 0)
			throw new IllegalArgumentException();
		index += callFrame.basepointer;
		if (index >= stackpointer)
			throw new IllegalArgumentException();
		XValue value = stack[index];
		return value instanceof XValueClosure ? ((XValueClosure) value).value : value;
	}

	public XValue getTop(int index) {
		if (index < 0)
			throw new IllegalArgumentException();
		index = stackpointer - 1 - index;
		if (index < callFrame.basepointer)
			throw new IllegalArgumentException();
		XValue value = stack[index];
		return value instanceof XValueClosure ? ((XValueClosure) value).value : value;
	}

	public void set(int index, XValue value) {
		if (index < 0)
			throw new IllegalArgumentException();
		index += callFrame.basepointer;
		if (index >= stackpointer)
			throw new IllegalArgumentException();
		XValue v = stack[index];
		if (v instanceof XValueClosure) {
			((XValueClosure) v).value = value;
		}
		stack[index] = value;
	}

	public void setTop(int index, XValue value) {
		if (index < 0)
			throw new IllegalArgumentException();
		index = stackpointer - 1 - index;
		if (index < callFrame.basepointer)
			throw new IllegalArgumentException();
		XValue v = stack[index];
		if (v instanceof XValueClosure) {
			((XValueClosure) v).value = value;
		}
		stack[index] = value;
	}

	private boolean handles__getItem__(XValue value) {
		return XUtils.isOverwritten(rt, value, "__getItem__");
	}

	private void callSingleOperator(String op, String method, XValue value) {
		if (handles__getItem__(value)) {
			callHelper("_" + method, value);
		} else {
			XValue m = XUtils.lookupTry(rt, value, method, XValue.REF_NONE);
			if (m == null) {
				methodNotFound(op, value);
			}
			call(m, value, EMPTY_LIST, null);
		}
	}

	private void callSingleOperator2(String op, String method, String alterantive, XValue value) {
		if (handles__getItem__(value)) {
			callHelper("_" + method, value);
		} else {
			XValue m = XUtils.lookupTry(rt, value, method, XValue.REF_NONE);
			if (m == null) {
				m = XUtils.lookupTry(rt, value, alterantive, XValue.REF_NONE);
				if (m == null) {
					methodNotFound(op, value);
				}
			}
			call(m, value, EMPTY_LIST, null);
		}
	}

	private void callBiOperator(String op, String method, String invertMethod, XValue left, XValue right) {
		if (handles__getItem__(left)) {
			callHelper("_" + method, left, right);
		} else {
			XValue m = XUtils.lookupTry(rt, left, method, XValue.REF_NONE);
			if (m == null) {
				if (handles__getItem__(right)) {
					callHelper("_" + invertMethod, right, left);
					return;
				} else {
					m = XUtils.lookupTry(rt, right, invertMethod, XValue.REF_NONE);
					if (m == null) {
						methodNotFound(op, left, right);
					}
					call(m, right, new XArrayList<XValue>(left), null);
				}
			} else {
				call(m, left, new XArrayList<XValue>(right), null);
			}
		}
	}

	private void callBiOperator2(String op, String method, String invertMethod, String alterantive, String invertAlterantive, XValue left, XValue right) {
		if (handles__getItem__(left)) {
			callHelper("_" + method, left, right);
		} else {
			XValue m = XUtils.lookupTry(rt, left, method, XValue.REF_NONE);
			if (m == null) {
				if (handles__getItem__(right)) {
					callHelper("_" + invertMethod, right, left);
					return;
				} else {
					m = XUtils.lookupTry(rt, right, invertMethod, XValue.REF_NONE);
					if (m == null) {
						m = XUtils.lookupTry(rt, left, alterantive, XValue.REF_NONE);
						if (m == null) {
							m = XUtils.lookupTry(rt, right, invertAlterantive, XValue.REF_NONE);
							if (m == null) {
								methodNotFound(op, left, right);
							} else {
								call(m, right, new XArrayList<XValue>(left), null);
							}
						} else {
							call(m, left, new XArrayList<XValue>(right), null);
						}
					} else {
						call(m, right, new XArrayList<XValue>(left), null);
					}
				}
			} else {
				call(m, left, new XArrayList<XValue>(right), null);
			}
		}
	}

	private String getTypeName(XValue value) {
		XValue type = value.getType(rt);
		return XUtils.getDataAs(rt, type, XTypeData.class).getName();
	}

	private String __getErrorMessage2(String op, XValue left, XValue right) {
		return "Operator " + op + " not applicable for " + getTypeName(left) + " and " + getTypeName(right);
	}

	private String __getErrorMessage1(String op, XValue value) {
		return "Operator " + op + " not applicable for " + getTypeName(value);
	}

	private void methodNotFound(String op, XValue left, XValue right) {
		throw new XRuntimeException("TypeError", __getErrorMessage2(op, left, right));
	}

	private void methodNotFound(String op, XValue value) {
		throw new XRuntimeException("TypeError", __getErrorMessage1(op, value));
	}

	private void callHelper(String method, XValue... args) {
		XValue module = rt.getBuiltinModule();
		XValue m = XUtils.lookup(rt, module, method, XValue.REF_INTERN);
		XObjectDataFunc func = XUtils.getDataAs(rt, m, XObjectDataFunc.class);
		String[] paramNames = func.getParamNames();
		if (paramNames.length != args.length)
			throw new IllegalArgumentException();
		call(m, XValueNull.NULL, args, false);
	}

	private void callHelper2(String method, List<XValue> params, Map<String, XValue> map) {
		XValue module = rt.getBuiltinModule();
		XValue m = XUtils.lookup(rt, module, method, XValue.REF_INTERN);
		call(m, XValueNull.NULL, params, map, false);
	}

	@SuppressWarnings("unchecked")
	private void call(XValue m, XValue thiz, XValue[] args, String[] kws, XValue list, XValue map) {
		List<XValue> params = new ArrayList<XValue>();
		Map<String, XValue> ma = XUtils.getDataAs(rt, map, Map.class);
		if (ma == null) {
			ma = new HashMap<String, XValue>();
		} else {
			ma = new HashMap<String, XValue>(ma);
		}
		if (kws == null) {
			for (int i = 0; i < args.length; i++) {
				params.add(args[i]);
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				if (kws[i] == null) {
					params.add(args[i]);
				} else {
					if (ma.containsKey(kws[i])) {
						throw new XRuntimeException("TypeError", "Duplicated keyword '%s'", kws[i]);
					}
					ma.put(kws[i], args[i]);
				}
			}
		}
		if (list != null && list != XValueNull.NULL) {
			params.addAll(XUtils.getDataAs(rt, list, Collection.class));
		}
		call(m, thiz, params, ma);
	}

	public void call(XValue m, XValue thiz, List<XValue> params, Map<String, XValue> map) {
		call(m, thiz, params, map, true);
	}

	public void call(XValue m, XValue thiz, List<XValue> params, Map<String, XValue> map, boolean visibleToStacktrace) {
		XObjectData data = XUtils.getData(rt, m);
		if (data instanceof XObjectDataNativeFunc) {
			XObjectDataNativeFunc func = (XObjectDataNativeFunc) data;
			XFunction function = func.getFunction();
			String[] paramNames = func.getParamNames();
			int defStart = func.getDefStart();
			boolean useList = func.getUseList();
			boolean useMap = func.getUseMap();
			XValue[] args = new XValue[paramNames.length];
			int s = params.size();
			boolean kws = false;
			List<XValue> list;
			if (useList) {
				if (s > paramNames.length) {
					list = new ArrayList<XValue>(params.subList(paramNames.length, s));
				} else {
					list = new ArrayList<XValue>();
				}
			} else {
				list = null;
				if (s > paramNames.length) {
					throw new XRuntimeException("TypeError", "To many args");
				}
			}
			if (map == null) {
				for (int i = 0; i < args.length; i++) {
					if (s > i && !kws) {
						args[i] = params.get(i);
					} else if (defStart == -1 || defStart > i) {
						throw new XRuntimeException("TypeError", "Not enougth args");
					}
				}
			} else {
				map = new HashMap<String, XValue>(map);
				for (int i = 0; i < args.length; i++) {
					String n = paramNames[i];
					XValue mn = map.remove(n);
					if (s > i && !kws) {
						args[i] = params.get(i);
					} else if (mn != null) {
						args[i] = mn;
						mn = null;
						kws = true;
					} else if (defStart == -1 || defStart > i) {
						throw new XRuntimeException("TypeError", "Not enougth args");
					}
					if (mn != null) {
						throw new XRuntimeException("TypeError", "Duplicated keyword");
					}
				}
				if (!map.isEmpty() && !useMap) {
					throw new XRuntimeException("TypeError", "To many keyword");
				}
			}

			XValue ret;
			try {
				ret = function.invoke(rt, this, thiz, args, list, map);
			} catch (Throwable e) {
				XUtils.rethrow(e);
				return;
			}
			if (ret == null) {
				push(XValueNull.NULL);
			} else if (ret != XFunction.NO_PUSH) {
				push(ret);
			}
		} else if (data instanceof XObjectDataFunc) {
			XObjectDataFunc func = (XObjectDataFunc) data;
			String[] paramNames = func.getParamNames();
			int kwParam = func.getKwParam();
			int listParam = func.getListParam();
			int defStart = func.getDefStart();
			@SuppressWarnings("unchecked")
			List<XValue> defs = defStart == -1 ? null : XUtils.getDataAs(getRuntime(), func.getDef(), List.class);
			XValue[] args = new XValue[paramNames.length];
			int s = params.size();
			boolean kws = false;
			XValue listP = null;
			if (listParam == -1) {
				if ((kwParam == -1 ? s : kwParam) > paramNames.length) {
					throw new XRuntimeException("TypeError", "To many args");
				}
			} else {
				listP = rt.createList(new ArrayList<XValue>(params.subList(listParam, s)));
			}
			if (map == null) {
				for (int i = 0; i < args.length; i++) {
					if (i == listParam) {
						args[i] = listP;
						kws = true;
					} else if (i == kwParam) {
						args[i] = rt.createMap(new HashMap<String, XValue>());
						kws = true;
					} else if (s > i && !kws) {
						args[i] = params.get(i);
					} else {
						int defPos = i - defStart;
						if (defStart == -1 || defPos < 0)
							throw new XRuntimeException("TypeError", "Not enougth args");
						args[i] = defs.get(defPos);
					}
				}
			} else {
				map = new HashMap<String, XValue>(map);
				for (int i = 0; i < args.length; i++) {
					String n = paramNames[i];
					XValue mn = map.remove(n);
					if (i == listParam) {
						args[i] = listP;
						kws = true;
					} else if (i == kwParam) {
						kws = true;
					} else if (s > i && !kws) {
						args[i] = params.get(i);
					} else if (mn != null) {
						args[i] = mn;
						mn = null;
					} else {
						int defPos = i - defStart;
						if (defStart == -1 || defPos < 0)
							throw new XRuntimeException("TypeError", "Not enougth args");
						args[i] = defs.get(defPos);
						kws = true;
					}
					if (mn != null) {
						throw new XRuntimeException("TypeError", "Duplicated keyword");
					}
				}
				if (kwParam == -1) {
					if (!map.isEmpty()) {
						throw new XRuntimeException("TypeError", "To many keyword");
					}
				} else {
					args[kwParam] = rt.createMap(map);
				}
			}
			call(m, thiz, args, visibleToStacktrace);
		} else if (XUtils.isInstanceOf(rt, m, rt.getBaseType(XUtils.TYPE))) {
			params.add(0, m);
			callHelper2("___new__", params, map);
		} else {
			if (handles__getItem__(m)) {
				callHelper2("___call__", params, map);
			} else {
				XValue newMethod = XUtils.lookup(rt, m, "__call__", XValue.REF_NONE);
				call(newMethod, m, params, map);
			}
		}
	}

	private void call(XValue m, XValue thiz, XValue[] args, boolean visibleToStacktrace) {
		callFrame = new XCallFrame(rt, callFrame, m, stackpointer, visibleToStacktrace);
		push(thiz);
		for (XValue arg : args) {
			push(arg);
		}
	}

	public boolean updateWaiting() {
		if (timeout > 0) {
			long time = rt.getTime();
			timeout += lastTime - time;
			lastTime = time;
			if (timeout <= 0) {
				timeout = 0;
				push(XValueInt.valueOf(1));
			}
		}
		return timeout == 0;
	}

	public int run(int instrs) {
		if (instrs == 0)
			return 0;
		while ((instrs == -1 || instrs > 0) && timeout == 0 && rt.isRunning()) {
			if (instrs > 0)
				instrs--;
			while (callFrame.isFinished()) {
				if (stackpointer == callFrame.basepointer) {
					push(XValueNull.NULL);
				} else if (stackpointer > callFrame.basepointer + 1) {
					XValue ret = pop();
					pop(stackpointer - callFrame.basepointer);
					push(ret);
				}
				callFrame = callFrame.getParent();
				if (callFrame == null)
					return instrs;
			}
			if (exception != null) {
				XCatchHandler catchHandler = callFrame.getCatchHandler();
				while (catchHandler == null) {
					callFrame = callFrame.getParent();
					if (callFrame == null)
						return instrs;
					catchHandler = callFrame.getCatchHandler();
				}
				int stackpointer = catchHandler.getStackPointer();
				pop(this.stackpointer - stackpointer);
				push(exception);
				exception = null;
				callFrame.jumpTo(catchHandler.getInstructionPointer());
			}
			try {
				exec();
			} catch (XRuntimeException e) {
				XValue value = rt.getModule("sys");
				XValue type = value.getRaw(rt, e.getType());
				XValue exc = rt.alloc(type);
				XValue message = rt.alloc(e.getMessage());
				XValue stackTrace = XUtils.convertStackTrace(rt, getStackTrace(false));
				exc.setRaw(rt, "_message", message);
				exc.setRaw(rt, "_cause", XValueNull.NULL);
				exc.setRaw(rt, "_stackTrace", stackTrace);
				exception = exc;
			} catch (Throwable e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String message = sw.toString();
				XValue value = rt.getModule("sys");
				XValue type = value.getRaw(rt, "NativeError");
				XValue exc = rt.alloc(type);
				XValue m = rt.alloc(message);
				XValue stackTrace = XUtils.convertStackTrace(rt, getStackTrace(false));
				exc.setRaw(rt, "_message", m);
				exc.setRaw(rt, "_cause", XValueNull.NULL);
				exc.setRaw(rt, "_stackTrace", stackTrace);
				exception = exc;
			}
		}
		return instrs;
	}

	private void exec() {
		int inst = callFrame.readUByte();
		XOpcode opcode = XOpcode.values()[inst];
		XValue v1, v2, v3, v4;
		int i1;
		String s;
		if (XFlags.DEBUG) {
			System.out.println("exec:" + callFrame.getMethodName() + ":" + opcode + ":" + Arrays.toString(Arrays.copyOf(stack, stackpointer)));
		}
		switch (opcode) {
		case ADD:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() + v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() + v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("+", "__add__", "__add2__", v2, v1);
			}
			break;
		case AND:
			v1 = pop();
			v2 = pop();
			if ((v1.isInt() || v1.isBool()) && (v2.isInt() || v2.isBool())) {
				if (v1.isBool() && v2.isBool()) {
					v3 = XValueBool.valueOf(v2.getBool() && v1.getBool());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() & v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("&", "__and__", "__and2__", v2, v1);
			}
			break;
		case CALL: {
			i1 = callFrame.readUByte();
			XValue[] params = new XValue[i1];
			for (int i = i1 - 1; i >= 0; i--) {
				params[i] = pop();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, null, null, null);
			break;
		}
		case CALL_KW: {
			i1 = callFrame.readUByte();
			XValue[] params = new XValue[i1];
			for (int i = i1 - 1; i >= 0; i--) {
				params[i] = pop();
			}
			String[] kws = new String[i1];
			int i2 = callFrame.readUByte();
			for (int i = i2; i < i1; i++) {
				kws[i] = callFrame.readStringP();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, kws, null, null);
			break;
		}
		case CALL_LIST: {
			i1 = callFrame.readUByte() - 1;
			XValue[] params = new XValue[i1];
			v3 = pop();
			for (int i = i1 - 1; i >= 0; i--) {
				params[i] = pop();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, null, v3, null);
			break;
		}
		case CALL_LIST_KW: {
			i1 = callFrame.readUByte() - 1;
			int i2 = callFrame.readUByte();
			v3 = null;
			XValue[] params = new XValue[i1];
			for (int i = i1, j = i1 - 1; i >= 0; i--, j--) {
				if (i == i2) {
					v3 = pop();
					i--;
					continue;
				}
				params[j] = pop();
			}
			String[] kws = new String[i1];
			int i3 = callFrame.readUByte();
			for (int i = 0; i <= i1 - i3; i++) {
				kws[i] = callFrame.readStringP();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, kws, v3, null);
			break;
		}
		case CALL_LIST_MAP: {
			i1 = callFrame.readUByte() - 2;
			XValue[] params = new XValue[i1];
			v4 = pop();
			v3 = pop();
			for (int i = i1 - 1; i >= 0; i--) {
				params[i] = pop();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, null, v3, v4);
			break;
		}
		case CALL_LIST_MAP_KW: {
			i1 = callFrame.readUByte();
			int i2 = callFrame.readUByte();
			int i3 = callFrame.readUByte();
			v3 = null;
			v4 = null;
			XValue[] params = new XValue[i1 - 2];
			for (int i = i1 - 1, j = i1 - 3; i >= 0; i--, j--) {
				if (i == i2) {
					v3 = pop();
					i--;
					continue;
				}
				if (i == i3) {
					v4 = pop();
					i--;
					continue;
				}
				params[j] = pop();
			}
			String[] kws = new String[i1 - 2];
			int i4 = callFrame.readUByte();
			for (int i = 0; i <= i1 - 2 - i4; i++) {
				kws[i] = callFrame.readStringP();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, kws, v3, v4);
			break;
		}
		case CALL_MAP: {
			i1 = callFrame.readUByte() - 1;
			XValue[] params = new XValue[i1];
			v3 = pop();
			for (int i = i1 - 1; i >= 0; i--) {
				params[i] = pop();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, null, null, v3);
			break;
		}
		case CALL_MAP_KW: {
			i1 = callFrame.readUByte() - 1;
			int i2 = callFrame.readUByte();
			v3 = null;
			XValue[] params = new XValue[i1];
			for (int i = i1, j = i1 - 1; i >= 0; i--, j--) {
				if (i == i2) {
					v3 = pop();
					i--;
					continue;
				}
				params[j] = pop();
			}
			String[] kws = new String[i1];
			int i3 = callFrame.readUByte();
			for (int i = 0; i <= i1 - i3; i++) {
				kws[i] = callFrame.readStringP();
			}
			v2 = pop();
			v1 = pop();
			call(v2, v1, params, kws, null, v3);
			break;
		}
		case COMPARE:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					i1 = Double.compare(v2.getFloat(), v1.getFloat());
				} else {
					i1 = Long.compare(v2.getInt(), v1.getInt());
				}
				v3 = XValueInt.valueOf(i1);
				push(v3);
			} else {
				callBiOperator("<=>", "__compare__", "__compare2__", v2, v1);
			}
			break;
		case COPY:
			v1 = pop();
			if (v1.isObject()) {
				callSingleOperator("<:", "__copy__", v1);
			} else {
				push(v1);
			}
			break;
		case DEC:
			v1 = pop();
			if (v1.isNumber()) {
				if (v1.isFloat()) {
					v2 = new XValueFloat(v1.getFloat() - 1);
				} else {
					v2 = XValueInt.valueOf(v1.getInt() - 1);
				}
				push(v2);
			} else {
				callSingleOperator("--", "__dec__", v1);
			}
			break;
		case DEL_ATTR:
			s = callFrame.readStringP();
			v1 = pop();
			if (handles__getItem__(v1)) {
				v2 = XUtils.lookup(rt, v1, "__delItem__", XValue.REF_NONE);
				v3 = rt.alloc(s);
				call(v2, v1, new XArrayList<XValue>(v3), null);
			} else {
				XUtils.del(rt, v1, s, XValue.REF_NONE);
			}
			break;
		case DEL_GLOBAL:
			s = callFrame.readStringP();
			v1 = callFrame.getModule();
			XUtils.del(rt, v1, s, XValue.REF_NONE);
			break;
		case DEL_INDEX:
			v1 = pop();
			v2 = pop();
			v3 = XUtils.lookup(rt, v2, "__delIndex__", XValue.REF_NONE);
			call(v3, v2, new XArrayList<XValue>(v1), null);
			break;
		case DIV:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				v3 = new XValueFloat(v2.getFloat() / v1.getFloat());
				push(v3);
			} else {
				callBiOperator("/", "__div__", "__div2__", v2, v1);
			}
			break;
		case DUP:
			v1 = getTop(0);
			push(v1);
			break;
		case END_FINALLY:
			v1 = pop();
			v2 = pop();
			i1 = (int) v1.getInt();
			if (i1 == 0) {
				_throw(v2);
			} else if (i1 == 1) {
				List<XValue> list = XUtils.asList(rt, v2);
				if (list != null) {
					if (list.size() == 1) {
						XValue nextJump = list.get(0);
						int jump = (int) nextJump.getInt();
						callFrame.jumpTo(jump);
					} else {
						list = new ArrayList<XValue>(list);
						XValue nextJump = list.remove(list.size() - 1);
						v3 = rt.createTuple(list);
						push(v3);
						push(v1);
						int jump = (int) nextJump.getInt();
						callFrame.jumpTo(jump);
					}
				}
			} else if (i1 == 2) {
				List<XValue> list = XUtils.asList(rt, v2);
				if (list.size() == 1) {
					push(list.get(0));
					callFrame.exit();
				} else {
					list = new ArrayList<XValue>(list);
					XValue nextJump = list.remove(list.size() - 1);
					v3 = rt.createTuple(list);
					push(v3);
					push(v1);
					int jump = (int) nextJump.getInt();
					callFrame.jumpTo(jump);
				}
			} else {
				throw new AssertionError();
			}
			break;
		case END_TRY: {
			XCatchHandler catchHandler = callFrame.getCatchHandler();
			if (stackpointer != catchHandler.getStackPointer()) {
				throw new IllegalStateException();
			}
			break;
		}
		case EQUAL:
			v1 = pop();
			v2 = pop();
			if ((v1.isNumber() || v1.isBool()) && (v2.isNumber() || v2.isBool())) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueBool.valueOf(v2.getFloat() == v1.getFloat());
				} else {
					v3 = XValueBool.valueOf(v2.getInt() == v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("===", "__equal__", "__equal2__", v2, v1);
			}
			break;
		case GETBOTTOM1:
			v1 = get(callFrame.readUByte());
			push(v1);
			break;
		case GETBOTTOM2:
			v1 = get(callFrame.readUShort());
			push(v1);
			break;
		case GETTOP1:
			v1 = getTop(callFrame.readUByte());
			push(v1);
			break;
		case GETTOP2:
			v1 = getTop(callFrame.readUShort());
			push(v1);
			break;
		case GET_ATTR:
			s = callFrame.readStringP();
			v1 = pop();
			if (handles__getItem__(v1)) {
				v2 = XUtils.lookup(rt, v1, "__getItem__", XValue.REF_NONE);
				v3 = rt.alloc(s);
				call(v2, v1, new XArrayList<XValue>(v3), null);
			} else {
				v2 = XUtils.lookup(rt, v1, s, XValue.REF_NONE);
				push(v2);
			}
			break;
		case GET_CLOSURE:
			i1 = callFrame.readUByte();
			v1 = callFrame.getClosure(i1).getValue();
			push(v1);
			break;
		case GET_GLOBAL:
			s = callFrame.readStringP();
			v1 = callFrame.getModule();
			v2 = XUtils.lookup(rt, v1, s, XValue.REF_NONE);
			if (v2 == null)
				v2 = XValueNull.NULL;
			push(v2);
			break;
		case GET_INDEX:
			v1 = pop();
			v2 = pop();
			v3 = XUtils.lookup(rt, v2, "__getIndex__", XValue.REF_NONE);
			call(v3, v2, new XArrayList<XValue>(v1), null);
			break;
		case GREATER:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueBool.valueOf(v2.getFloat() > v1.getFloat());
				} else {
					v3 = XValueBool.valueOf(v2.getInt() > v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator(">", "__greater__", "__greater2__", v2, v1);
			}
			break;
		case GREATER_EQUAL:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueBool.valueOf(v2.getFloat() >= v1.getFloat());
				} else {
					v3 = XValueBool.valueOf(v2.getInt() >= v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator(">=", "__greaterEqual__", "__greaterEqual2__", v2, v1);
			}
			break;
		case IDIV:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueInt.valueOf((long) (v2.getFloat() / v1.getFloat()));
				} else {
					v3 = XValueInt.valueOf(v2.getInt() / v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("\\", "__idiv__", "__idiv2__", v2, v1);
			}
			break;
		case IMPORT:
			s = callFrame.readStringP();
			v1 = rt.alloc(s);
			callHelper("__import", v1);
			break;
		case IMPORT_SAVE:
			s = callFrame.readStringP();
			v1 = pop();
			v2 = pop();
			XUtils.putAtMapItem(rt, v1, s, v2);
			break;
		case INC:
			v1 = pop();
			if (v1.isNumber()) {
				if (v1.isFloat()) {
					v2 = new XValueFloat(v1.getFloat() + 1);
				} else {
					v2 = XValueInt.valueOf(v1.getInt() + 1);
				}
				push(v2);
			} else {
				callSingleOperator("++", "__inc__", v1);
			}
			break;
		case INSTANCEOF:
			v1 = pop();
			v2 = pop();
			v3 = XValueBool.valueOf(XUtils.isInstanceOf(rt, v2, v1));
			push(v3);
			break;
		case INVERT:
			v1 = pop();
			if (v1.isInt()) {
				v2 = XValueInt.valueOf(~v1.getInt());
				push(v2);
			} else if (v1.isBool()) {
				v2 = XValueBool.valueOf(!v1.getBool());
				push(v2);
			} else {
				callSingleOperator("~", "__invert__", v1);
			}
			break;
		case ISHR:
			v1 = pop();
			v2 = pop();
			if (v1.isInt() && v2.isInt()) {
				v3 = XValueInt.valueOf(v2.getInt() >>> v1.getInt());
				push(v3);
			} else {
				callBiOperator(">>>", "__ishr__", "__ishr2__", v2, v1);
			}
			break;
		case ISDERIVEDOF:
			v1 = pop();
			v2 = pop();
			v3 = XValueBool.valueOf(XUtils.isDerivedOf(rt, v2, v1));
			push(v3);
			break;
		case JUMP:
			callFrame.jumpTo(callFrame.readUShort());
			break;
		case JUMP_IF_NON_ZERO:
			i1 = callFrame.readUShort();
			v1 = pop();
			if (v1.noneZero()) {
				callFrame.jumpTo(i1);
			}
			break;
		case JUMP_IF_ZERO:
			i1 = callFrame.readUShort();
			v1 = pop();
			if (!v1.noneZero()) {
				callFrame.jumpTo(i1);
			}
			break;
		case LADD:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() + v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() + v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("+", "__ladd__", "__ladd2__", "__add__", "__add2__", v2, v1);
			}
			break;
		case LAND:
			v1 = pop();
			v2 = pop();
			if ((v1.isInt() || v1.isBool()) && (v2.isInt() || v2.isBool())) {
				if (v1.isBool() && v2.isBool()) {
					v3 = XValueBool.valueOf(v2.getBool() && v1.getBool());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() & v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("&", "__land__", "__land2__", "__and__", "__and2__", v2, v1);
			}
			break;
		case LDIV:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				v3 = new XValueFloat(v2.getFloat() / v1.getFloat());
				push(v3);
			} else {
				callBiOperator2("/", "__ldiv__", "__ldiv2__", "__div__", "__div2__", v2, v1);
			}
			break;
		case LIDIV:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueInt.valueOf((long) (v2.getFloat() / v1.getFloat()));
				} else {
					v3 = XValueInt.valueOf(v2.getInt() / v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("\\", "__lidiv__", "__lidiv2__", "__idiv__", "__idiv2__", v2, v1);
			}
			break;
		case LINE1:
			callFrame.line = callFrame.readUByte();
			break;
		case LINE2:
			callFrame.line = callFrame.readUShort();
			break;
		case LINE4:
			callFrame.line = callFrame.readUInt();
			break;
		case LISHR:
			v1 = pop();
			v2 = pop();
			if (v1.isInt() && v2.isInt()) {
				v3 = XValueInt.valueOf(v2.getInt() >>> v1.getInt());
				push(v3);
			} else {
				callBiOperator2(">>>", "__lishr__", "__lishr2__", "__ishr__", "__ishr2__", v2, v1);
			}
			break;
		case LMOD:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() % v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() % v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("%", "__lmod__", "__lmod2__", "__mod__", "__mod2__", v2, v1);
			}
			break;
		case LMUL:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() * v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() * v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("*", "__lmul__", "__lmul2__", "__mul__", "__mul2__", v2, v1);
			}
			break;
		case LOADB:
			i1 = callFrame.readByte();
			v1 = XValueInt.valueOf(i1);
			push(v1);
			break;
		case LOADD:
			v1 = new XValueFloat(callFrame.readDoubleP());
			push(v1);
			break;
		case LOADD_0:
			push(new XValueFloat(0));
			break;
		case LOADD_1:
			push(new XValueFloat(1));
			break;
		case LOADD_2:
			push(new XValueFloat(2));
			break;
		case LOADD_M1:
			push(new XValueFloat(-1));
			break;
		case LOADF:
			v1 = new XValueFloat(callFrame.readFloatP());
			push(v1);
			break;
		case LOADI:
			i1 = callFrame.readIntP();
			v1 = new XValueInt(i1);
			push(v1);
			break;
		case LOADI_0:
			push(XValueInt.valueOf(0));
			break;
		case LOADI_1:
			push(XValueInt.valueOf(1));
			break;
		case LOADI_2:
			push(XValueInt.valueOf(2));
			break;
		case LOADI_M1:
			push(XValueInt.valueOf(-1));
			break;
		case LOADL:
			v1 = new XValueInt(callFrame.readLongP());
			push(v1);
			break;
		case LOADN:
			push(XValueNull.NULL);
			break;
		case LOADS:
			i1 = callFrame.readShort();
			v1 = new XValueInt(i1);
			push(v1);
			break;
		case LOADT:
			s = callFrame.readStringP();
			v1 = rt.alloc(s);
			push(v1);
			break;
		case LOADT_E:
			v1 = rt.alloc("");
			push(v1);
			break;
		case LOAD_FALSE:
			push(XValueBool.FALSE);
			break;
		case LOAD_TRUE:
			push(XValueBool.TRUE);
			break;
		case LOR:
			v1 = pop();
			v2 = pop();
			if ((v1.isInt() || v1.isBool()) && (v2.isInt() || v2.isBool())) {
				if (v1.isBool() && v2.isBool()) {
					v3 = XValueBool.valueOf(v2.getBool() || v1.getBool());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() | v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("|", "__lor__", "__lor2__", "__or__", "__or2__", v2, v1);
			}
			break;
		case LPOW:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(Math.pow(v2.getFloat(), v1.getFloat()));
				} else {
					v3 = XValueInt.valueOf((long) Math.pow(v2.getInt(), v1.getInt()));
				}
				push(v3);
			} else {
				callBiOperator2("**", "__lpow__", "__lpow2__", "__pow__", "__pow2__", v2, v1);
			}
			break;
		case LSHL:
			v1 = pop();
			v2 = pop();
			if (v1.isInt() && v2.isInt()) {
				v3 = XValueInt.valueOf(v2.getInt() << v1.getInt());
				push(v3);
			} else {
				callBiOperator2("<<", "__lshl__", "__lshl2__", "__shl__", "__shl2__", v2, v1);
			}
			break;
		case LSHR:
			v1 = pop();
			v2 = pop();
			if (v1.isInt() && v2.isInt()) {
				v3 = XValueInt.valueOf(v2.getInt() >> v1.getInt());
				push(v3);
			} else {
				callBiOperator2(">>", "__lshr__", "__lshr2__", "__shr__", "__shr2__", v2, v1);
			}
			break;
		case LSUB:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() - v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() - v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("-", "__lsub__", "__lsub2__", "__sub__", "__sub2__", v2, v1);
			}
			break;
		case LXOR:
			v1 = pop();
			v2 = pop();
			if ((v1.isInt() || v1.isBool()) && (v2.isInt() || v2.isBool())) {
				if (v1.isBool() && v2.isBool()) {
					v3 = XValueBool.valueOf(v2.getBool() != v1.getBool());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() ^ v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator2("^", "__lxor__", "__lxor2__", "__xor__", "__xor2__", v2, v1);
			}
			break;
		case MAKE_CLASS:
			s = callFrame.readStringP();
			v1 = pop();
			v2 = rt.alloc(rt.getBaseType(XUtils.TYPE), s, v1);
			push(v2);
			break;
		case MAKE_FUNC: {
			s = callFrame.readStringP();
			int index = callFrame.readUShort();
			int size = callFrame.readUByte();
			String[] params = new String[size];
			for (int i = 0; i < size; i++) {
				params[i] = callFrame.readStringP();
			}
			int kwParam = callFrame.readByte();
			int listParam = callFrame.readByte();
			int defStart = callFrame.readByte();
			size = callFrame.readUByte();
			XClosure[] closures = new XClosure[size];
			for (int i = 0; i < size; i++) {
				if (callFrame.readByte() == 0) {
					i1 = callFrame.readUByte();
					closures[i] = callFrame.getClosure(i1);
				} else {
					i1 = callFrame.readUByte();
					closures[i] = getClosure(i1);
				}
			}

			v2 = pop();
			v3 = callFrame.getModule();
			XValue constPool = callFrame.getConstPool();
			v1 = rt.alloc(rt.getBaseType(XUtils.FUNC), s, params, kwParam, listParam, defStart, v2, v3, constPool, XValueNull.NULL, index, closures);
			push(v1);
			break;
		}
		case MAKE_LIST:
			i1 = callFrame.readUShort();
			{
				List<XValue> list = new ArrayList<XValue>(i1);
				for (int j = 0; j < i1; j++) {
					list.add(0, pop());
				}
				v1 = rt.createList(list);
			}
			push(v1);
			break;
		case MAKE_MAP:
			i1 = callFrame.readUShort();
			{
				Map<String, XValue> map = new HashMap<String, XValue>(i1);
				for (int j = 0; j < i1; j++) {
					XValue value = pop();
					XValue key = pop();
					String k = XUtils.getString(rt, key);
					map.put(k, value);
				}
				v1 = rt.createMap(map);
			}
			push(v1);
			break;
		case MAKE_METH: {
			s = callFrame.readStringP();
			int index = callFrame.readUShort();
			int size = callFrame.readUByte();
			String[] params = new String[size];
			for (int i = 0; i < size; i++) {
				params[i] = callFrame.readStringP();
			}
			int kwParam = callFrame.readByte();
			int listParam = callFrame.readByte();
			int defStart = callFrame.readByte();
			size = callFrame.readUByte();
			XClosure[] closures = new XClosure[size];
			for (int i = 0; i < size; i++) {
				if (callFrame.readByte() == 0) {
					i1 = callFrame.readUByte();
					closures[i] = callFrame.getClosure(i1);
				} else {
					i1 = callFrame.readUByte();
					closures[i] = getClosure(i1);
				}
			}
			v4 = pop();
			v2 = pop();
			v3 = callFrame.getModule();
			XValue constPool = callFrame.getConstPool();
			v1 = rt.alloc(rt.getBaseType(XUtils.FUNC), s, params, kwParam, listParam, defStart, v2, v3, constPool, v4, index, closures);
			push(v1);
			break;
		}
		case MAKE_TUPLE:
			i1 = callFrame.readUShort();
			{
				List<XValue> list = new ArrayList<XValue>(i1);
				for (int j = 0; j < i1; j++) {
					list.add(0, pop());
				}
				v1 = rt.createTuple(list);
			}
			push(v1);
			break;
		case MOD:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() % v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() % v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("%", "__mod__", "__mod2__", v2, v1);
			}
			break;
		case MUL:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() * v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() * v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("*", "__mul__", "__mul2__", v2, v1);
			}
			break;
		case NEG:
			v1 = pop();
			if (v1.isNumber()) {
				if (v1.isFloat()) {
					v2 = new XValueFloat(-v1.getFloat());
				} else {
					v2 = XValueInt.valueOf(-v1.getInt());
				}
				push(v2);
			} else {
				callSingleOperator("-", "__neg__", v1);
			}
			break;
		case NOP:
			break;
		case NOT:
			v1 = pop();
			if (v1.isNumber() || v1.isBool()) {
				v2 = XValueBool.valueOf(!v1.noneZero());
				push(v2);
			} else {
				callSingleOperator("!", "__not__", v1);
			}
			break;
		case NOT_EQUAL:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueBool.valueOf(v2.getFloat() != v1.getFloat());
				} else {
					v3 = XValueBool.valueOf(v2.getInt() != v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("!==", "__notEqual__", "__notEqual2__", v2, v1);
			}
			break;
		case NOT_SAME:
			v1 = pop();
			v2 = pop();
			v3 = XValueBool.valueOf(!v2.equals(v1));
			push(v3);
			break;
		case OR:
			v1 = pop();
			v2 = pop();
			if ((v1.isInt() || v1.isBool()) && (v2.isInt() || v2.isBool())) {
				if (v1.isBool() && v2.isBool()) {
					v3 = XValueBool.valueOf(v2.getBool() || v1.getBool());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() | v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("|", "__or__", "__or2__", v2, v1);
			}
			break;
		case POP:
			i1 = callFrame.readUByte();
			pop(i1);
			break;
		case POP_1:
			pop();
			break;
		case POS:
			v1 = pop();
			if (v1.isNumber()) {
				push(v1);
			} else {
				callSingleOperator("+", "__pos__", v1);
			}
			break;
		case POW:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(Math.pow(v2.getFloat(), v1.getFloat()));
				} else {
					v3 = XValueInt.valueOf((long) Math.pow(v2.getInt(), v1.getInt()));
				}
				push(v3);
			} else {
				callBiOperator("**", "__pow__", "__pow2__", v2, v1);
			}
			break;
		case RETN:
			push(XValueNull.NULL);
		case RET:
			callFrame.exit();
			break;
		case SAME:
			v1 = pop();
			v2 = pop();
			v3 = XValueBool.valueOf(v2.equals(v1));
			push(v3);
			break;
		case SDEC:
			v1 = pop();
			if (v1.isNumber()) {
				if (v1.isFloat()) {
					v2 = new XValueFloat(v1.getFloat() - 1);
				} else {
					v2 = XValueInt.valueOf(v1.getInt() - 1);
				}
				push(v2);
			} else {
				callSingleOperator2("--", "__sdec__", "__dec__", v1);
			}
			break;
		case SETBOTTOM1:
			i1 = callFrame.readUByte();
			v1 = pop();
			set(i1, v1);
			break;
		case SETBOTTOM2:
			i1 = callFrame.readUShort();
			v1 = pop();
			set(i1, v1);
			break;
		case SETTOP1:
			i1 = callFrame.readUByte();
			v1 = pop();
			setTop(i1, v1);
			break;
		case SETTOP2:
			i1 = callFrame.readUShort();
			v1 = pop();
			setTop(i1, v1);
			break;
		case SET_ATTR:
			s = callFrame.readStringP();
			v1 = pop();
			v2 = pop();
			if (handles__getItem__(v1)) {
				v3 = XUtils.lookup(rt, v2, "__setItem__", XValue.REF_NONE);
				v4 = rt.alloc(s);
				call(v3, v2, new XArrayList<XValue>(v4, v1), null);
			} else {
				XUtils.set(rt, v2, s, v1, XValue.REF_NONE);
				push(v1);
			}
			break;
		case SET_CLOSURE:
			i1 = callFrame.readUByte();
			v1 = pop();
			callFrame.getClosure(i1).setValue(v1);
			break;
		case SET_GLOBAL:
			s = callFrame.readStringP();
			v1 = callFrame.getModule();
			v2 = pop();
			XUtils.set(rt, v1, s, v2, XValue.REF_NONE);
			break;
		case SET_INDEX:
			v1 = pop();
			v2 = pop();
			v3 = pop();
			v4 = XUtils.lookup(rt, v3, "__setIndex__", XValue.REF_NONE);
			call(v4, v3, new XArrayList<XValue>(v2, v1), null);
			break;
		case SHL:
			v1 = pop();
			v2 = pop();
			if (v1.isInt() && v2.isInt()) {
				v3 = XValueInt.valueOf(v2.getInt() << v1.getInt());
				push(v3);
			} else {
				callBiOperator("<<", "__shl__", "__shl2__", v2, v1);
			}
			break;
		case SHR:
			v1 = pop();
			v2 = pop();
			if (v1.isInt() && v2.isInt()) {
				v3 = XValueInt.valueOf(v2.getInt() >> v1.getInt());
				push(v3);
			} else {
				callBiOperator(">>", "__shr__", "__shr2__", v2, v1);
			}
			break;
		case SINC:
			v1 = pop();
			if (v1.isNumber()) {
				if (v1.isFloat()) {
					v2 = new XValueFloat(v1.getFloat() + 1);
				} else {
					v2 = XValueInt.valueOf(v1.getInt() + 1);
				}
				push(v2);
			} else {
				callSingleOperator2("++", "__sinc__", "__inc__", v1);
			}
			break;
		case SMALLER:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueBool.valueOf(v2.getFloat() < v1.getFloat());
				} else {
					v3 = XValueBool.valueOf(v2.getInt() < v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("<", "__smaller__", "__smaller2__", v2, v1);
			}
			break;
		case SMALLER_EQUAL:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = XValueBool.valueOf(v2.getFloat() <= v1.getFloat());
				} else {
					v3 = XValueBool.valueOf(v2.getInt() <= v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("<=", "__smallerEqual__", "__smallerEqual2__", v2, v1);
			}
			break;
		case START_TRY:
			i1 = callFrame.readUShort();
			callFrame.addCatchHandler(stackpointer, i1);
			break;
		case SUB:
			v1 = pop();
			v2 = pop();
			if (v1.isNumber() && v2.isNumber()) {
				if (v1.isFloat() || v2.isFloat()) {
					v3 = new XValueFloat(v2.getFloat() - v1.getFloat());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() - v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("-", "__sub__", "__sub2__", v2, v1);
			}
			break;
		case SUPER:
			v1 = callFrame.getMethodClass();
			v2 = get(0);
			v3 = getSuper(v1, v2);
			push(v3);
			break;
		case SWAP:
			v1 = pop();
			v2 = pop();
			push(v1);
			push(v2);
			break;
		case SWITCH:
			v1 = pop();
			if (v1 == XValueNull.NULL) {
				callFrame.instructionpointer += 2;
				callFrame.jumpTo(callFrame.readUShort());
			} else if (v1 == XValueBool.TRUE) {
				callFrame.instructionpointer += 4;
				callFrame.jumpTo(callFrame.readUShort());
			} else if (v1 == XValueBool.FALSE) {
				callFrame.instructionpointer += 6;
				callFrame.jumpTo(callFrame.readUShort());
			} else if (v1.isInt()) {
				int def = callFrame.readUShort();
				callFrame.instructionpointer += 6;
				i1 = callFrame.readUShort();
				callFrame.instructionpointer += i1 * 5;
				i1 = callFrame.readUShort();
				callFrame.instructionpointer += i1 * 4;
				int type = callFrame.readUByte();
				if (type >= 0 && type < 4) {
					long v = v1.getInt();
					long min = type == 0 ? callFrame.readByte() : type == 1 ? callFrame.readShort() : type == 2 ? callFrame.readIntP() : callFrame.readLongP();
					int size = callFrame.readUShort();
					v -= min;
					if (v < 0 || v > size) {
						callFrame.jumpTo(def);
					} else {
						callFrame.instructionpointer += v * 2;
						i1 = callFrame.readUShort();
						callFrame.jumpTo(i1);
					}
				} else if (type == 4) {
					long v = v1.getInt();
					int size = callFrame.readUShort();
					int left = 0;
					int right = size - 1;
					int base = callFrame.instructionpointer;
					while (left <= right) {
						int m = (left + right) / 2;
						callFrame.instructionpointer = base + m * 5;
						int t = callFrame.readUByte();
						long cv;
						if (t == 0) {
							cv = callFrame.readUShort();
						} else if (t == 1) {
							cv = callFrame.readIntP();
						} else if (t == 2) {
							cv = callFrame.readLongP();
						} else {
							throw new AssertionError();
						}
						if (cv == v) {
							i1 = callFrame.readUShort();
							callFrame.jumpTo(i1);
							return;
						} else if (cv < v) {
							left = m + 1;
						} else {
							right = m - 1;
						}
					}
					callFrame.jumpTo(def);
				} else {
					callFrame.jumpTo(def);
				}
			} else if (v1.isFloat()) {
				int def = callFrame.readUShort();
				callFrame.instructionpointer += 6;
				int size = callFrame.readUShort();
				double v = v1.getFloat();
				int left = 0;
				int right = size - 1;
				int base = callFrame.instructionpointer;
				while (left <= right) {
					int m = (left + right) / 2;
					callFrame.instructionpointer = base + m * 5;
					int t = callFrame.readUByte();
					double cv;
					if (t == 0) {
						cv = callFrame.readFloatP();
					} else if (t == 1) {
						cv = callFrame.readDoubleP();
					} else {
						throw new AssertionError();
					}
					if (cv == v) {
						i1 = callFrame.readUShort();
						callFrame.jumpTo(i1);
						return;
					} else if (cv < v) {
						left = m + 1;
					} else {
						right = m - 1;
					}
				}
				callFrame.jumpTo(def);
			} else if (XUtils.isInstanceOf(rt, v1, rt.getBaseType(XUtils.STRING))) {
				s = XUtils.getString(rt, v1);
				int def = callFrame.readUShort();
				callFrame.instructionpointer += 6;
				i1 = callFrame.readUShort();
				callFrame.instructionpointer += i1 * 5;
				int size = callFrame.readUShort();
				int left = 0;
				int right = size - 1;
				int base = callFrame.instructionpointer;
				while (left <= right) {
					int m = (left + right) / 2;
					callFrame.instructionpointer = base + m * 4;
					String cs = callFrame.readStringP();
					int cmp = s == cs ? 0 : s.compareTo(cs);
					if (cmp == 0) {
						i1 = callFrame.readUShort();
						callFrame.jumpTo(i1);
						return;
					} else if (cmp < 0) {
						left = m + 1;
					} else {
						right = m - 1;
					}
				}
				callFrame.jumpTo(def);
			}
			break;
		case THROW:
			v1 = pop();
			_throw(v1);
			break;
		case TYPEOF:
			v1 = pop();
			v2 = v1.getType(rt);
			push(v2);
			break;
		case XOR:
			v1 = pop();
			v2 = pop();
			if ((v1.isInt() || v1.isBool()) && (v2.isInt() || v2.isBool())) {
				if (v1.isBool() && v2.isBool()) {
					v3 = XValueBool.valueOf(v1.getBool() != v2.getBool());
				} else {
					v3 = XValueInt.valueOf(v2.getInt() ^ v1.getInt());
				}
				push(v3);
			} else {
				callBiOperator("^", "__xor__", "__xor2__", v2, v1);
			}
			break;
		case YIELD:
			v1 = pop();
			caller.yieldReturn(v1);
			caller = null;
			break;
		default:
			throw new XRuntimeException("TypeError", "Unknown opcode %s", opcode);
		}
	}

	private void yieldReturn(XValue v1) {
		calling = null;
	}

	private void _throw(XValue v1) {
		exception = v1;
	}

	void removeClosure(XValueClosure closure) {
		stack[closure.stackptr] = closure.value;
	}

	XClosure getOrReplaceFirstClosure(XClosure closure) {
		if (closures == closure) {
			closures = closures.getNext();
			return null;
		}
		return closures;
	}

	public void setWait() {
		timeout = -1;
	}

	public void setWait(long timeout) {
		if (timeout <= 0)
			throw new IllegalArgumentException();
		this.timeout = timeout;
		this.lastTime = rt.getTime();
	}

	public void resume() {
		timeout = 0;
		push(XValueInt.valueOf(0));
	}

	public StackTraceElement[] getStackTrace(boolean ifExcNotInit) {
		List<StackTraceElement> stackTrace = new ArrayList<StackTraceElement>();
		fillStackTrace(stackTrace, ifExcNotInit);
		return stackTrace.toArray(new StackTraceElement[stackTrace.size()]);
	}

	public void fillStackTrace(List<StackTraceElement> stackTrace, boolean ifExcNotInit) {
		if (calling != null) {
			calling.fillStackTrace(stackTrace, ifExcNotInit);
		} else {
			fillStackTrace0(stackTrace, ifExcNotInit);
			if (caller != null) {
				caller.fillStackTrace(stackTrace, false);
			}
		}
	}

	private void fillStackTrace0(List<StackTraceElement> stackTrace, boolean ifExcNotInit) {
		XCallFrame cf = callFrame;
		if (ifExcNotInit && cf.getModule().equals(rt.getModule("sys")) && cf.getMethodName().equals("__init__") && cf.getMethodClass().equals(rt.getModule("sys").getRaw(rt, "Exception"))) {
			while (cf != null && cf.getMethodName().equals("__init__")) {
				cf = cf.getParent();
			}
		}
		while (cf != null) {
			cf.addToStackTrace(rt, stackTrace);
			cf = cf.getParent();
		}
	}

	public State getState() {
		if (timeout == 0) {
			if (callFrame == null) {
				if (exception == null) {
					return State.TERMINATED;
				}
				return State.ERRORED;
			}
			if (generator && caller == null) {
				return State.YIELDING;
			}
			if (calling != null) {
				return State.GENERATOR_WAIT;
			}
			return State.RUNNING;
		} else if (timeout > 0) {
			return State.WAITING_TIMED;
		}
		return State.WAITING;
	}

	public enum State {
		WAITING, WAITING_TIMED, ERRORED, TERMINATED, RUNNING, YIELDING, GENERATOR_WAIT;
	}

	public XValue getException() {
		return exception;
	}

	public void setVisible() {
		for (int i = 0; i < stackpointer; i++) {
			stack[i].setVisible(rt);
		}
		if (exception != null) {
			exception.setVisible(rt);
		}
		if (callFrame != null) {
			callFrame.setVisible(rt);
		}
	}

	public boolean isGenerator() {
		return generator;
	}

	private XValue getSuper(XValue type, XValue obj) {
		return new XValueObjSuper(XValue.unpackContainer(obj), ((XValueObj) type).getPointer());
	}

	XCallFrame getCallFrame() {
		return callFrame;
	}

}
