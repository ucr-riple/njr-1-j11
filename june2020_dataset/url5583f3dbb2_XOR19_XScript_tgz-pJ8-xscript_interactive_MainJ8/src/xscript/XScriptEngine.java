package xscript;

import java.io.ByteArrayInputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Proxy;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;

import xscript.XExec.State;
import xscript.compiler.XCompiler;
import xscript.compiler.XFileReader;
import xscript.compiler.XInternCompiler;
import xscript.object.XConstPoolImpl;
import xscript.object.XFunctionData;
import xscript.object.XObject;
import xscript.object.XObjectDataFunc;
import xscript.object.XRuntime;
import xscript.object.XTypeData;
import xscript.object.XTypeDataBool;
import xscript.object.XTypeDataConstPool;
import xscript.object.XTypeDataFloat;
import xscript.object.XTypeDataFunc;
import xscript.object.XTypeDataInt;
import xscript.object.XTypeDataList;
import xscript.object.XTypeDataMap;
import xscript.object.XTypeDataModule;
import xscript.object.XTypeDataNull;
import xscript.object.XTypeDataNumber;
import xscript.object.XTypeDataString;
import xscript.object.XTypeDataTuple;
import xscript.object.XTypeDataWeakRef;
import xscript.values.XValue;
import xscript.values.XValueAccess;
import xscript.values.XValueNull;
import xscript.values.XValueObj;

public class XScriptEngine extends AbstractScriptEngine implements Invocable,
		Externalizable, XRuntime, Runnable {

	private static final int MAGIC_NUMBER_SAVE = 'X' << 24 | 'S' << 16
			| 'C' << 8 | 'S';
	private static final int MAGIC_NUMBER_COMPILED_MODULE = 'X' << 24
			| 'S' << 16 | 'C' << 8 | 'M';

	private final XScriptEngineFactory factory;

	private final XStaticInvocationHandler staticInvocationHandler = new XStaticInvocationHandler(
			this);

	private final XValue[] baseTypes = new XValue[XUtils.NUM_BASE_TYPES];

	private XObject[][] memory = new XObject[1][1024];

	private HashMap<String, XValue> modules = new HashMap<String, XValue>();

	private List<XExec> threads = new ArrayList<XExec>();

	private int nextThread;

	private boolean doInit = false;

	private boolean running = true;

	public XScriptEngine() {
		this(XScriptEngineFactory.getDefaultEngineFactory());
	}

	public XScriptEngine(XScriptEngineFactory factory) {
		this.factory = factory;
		for (int i = 0; i < baseTypes.length; i++) {
			baseTypes[i] = new XValueObj(i);
		}
		try {
			Bindings functions = createBindings();
			put(XScriptLang.ENGINE_ATTR_FUNCTIONS_BINDING, functions);
			functions.putAll(XNativeFunctions.getFunctions());

			put(XScriptLang.ENGINE_ATTR_FILE_SYSTEM, FileSystems.getDefault());
			List<String> roots = new ArrayList<String>();
			roots.add(new File("").getAbsolutePath());
			put(XScriptLang.ENGINE_ATTR_FILE_SYSTEM_ROOTS, roots);

			put(XScriptLang.ENGINE_ATTR_OUT, System.out);
			put(XScriptLang.ENGINE_ATTR_ERR, System.err);
			put(XScriptLang.ENGINE_ATTR_IN, System.in);

			put(XScriptLang.ENGINE_ATTR_INSTS_TO_RUN_ON_DIRECT_INVOKE, -1);
			put(XScriptLang.ENGINE_ATTR_TIMEOUT_DIRECT_INVOKE, true);

			put(XScriptLang.ENGINE_ATTR_INSTS_TO_RUN_ON_BLOCK, 10);
			put(XScriptLang.ENGINE_ATTR_BLOCKS_TO_RUN_ON_INVOKE, -1);

			put(XScriptLang.ENGINE_ATTR_INTERACTIVE, false);

			try {
				memory[0][XUtils.OBJECT] = new XObject(this, XUtils.OBJECT,
						baseTypes[XUtils.TYPE], null);
				memory[0][XUtils.TYPE] = new XObject(this, XUtils.TYPE,
						baseTypes[XUtils.TYPE], null);
				memory[0][XUtils.NATIVE_FUNC] = new XObject(this,
						XUtils.NATIVE_FUNC, baseTypes[XUtils.TYPE], null);
				doInit = true;
				((XTypeData) memory[0][XUtils.OBJECT].getData()).init(this,
						memory[0][XUtils.OBJECT]);
				((XTypeData) memory[0][XUtils.TYPE].getData()).init(this,
						memory[0][XUtils.TYPE]);
				((XTypeData) memory[0][XUtils.NATIVE_FUNC].getData()).init(
						this, memory[0][XUtils.NATIVE_FUNC]);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			memory[0][XUtils.BOOL] = new XObject(this, XUtils.BOOL,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataBool.FACTORY });
			memory[0][XUtils.STRING] = new XObject(this, XUtils.STRING,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataString.FACTORY });
			memory[0][XUtils.NUMBER] = new XObject(this, XUtils.NUMBER,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataNumber.FACTORY });
			memory[0][XUtils.NULL] = new XObject(this, XUtils.NULL,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataNull.FACTORY });
			memory[0][XUtils.INT] = new XObject(this, XUtils.INT,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataInt.FACTORY });
			memory[0][XUtils.FLOAT] = new XObject(this, XUtils.FLOAT,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataFloat.FACTORY });
			memory[0][XUtils.LIST] = new XObject(this, XUtils.LIST,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataList.FACTORY });
			memory[0][XUtils.MAP] = new XObject(this, XUtils.MAP,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataMap.FACTORY });
			memory[0][XUtils.MODULE] = new XObject(this, XUtils.MODULE,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataModule.FACTORY });
			memory[0][XUtils.TUPLE] = new XObject(this, XUtils.TUPLE,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataTuple.FACTORY });
			memory[0][XUtils.WEAK_REF] = new XObject(this, XUtils.WEAK_REF,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataWeakRef.FACTORY });
			memory[0][XUtils.FUNC] = new XObject(this, XUtils.FUNC,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataFunc.FACTORY });
			memory[0][XUtils.CONST_POOL] = new XObject(this, XUtils.CONST_POOL,
					baseTypes[XUtils.TYPE],
					new Object[] { XTypeDataConstPool.FACTORY });
			XValue constPool = alloc(baseTypes[XUtils.CONST_POOL],
					loadModule("__builtin__"));
			XValue __builtin__ = alloc(baseTypes[XUtils.MODULE], constPool,
					"__builtin__");
			modules.put("__builtin__", __builtin__);
			for (String nativeName : XNativeFunctions.getFunctions().keySet()) {
				if (nativeName.startsWith("__builtin__.")) {
					String name = nativeName.substring("__builtin__.".length());
					__builtin__.setRaw(this, name, createFunction(nativeName));
				}
			}
			XValue method = alloc(getBaseType(XUtils.FUNC), "<init>",
					new String[0], -1, -1, -1, XValueNull.NULL, __builtin__,
					constPool, XValueNull.NULL, 0, new XClosure[0]);
			XExec exec = new XExec(this, false, 128, method, XValueNull.NULL);
			exec.run(-1);
			memory[0][XUtils.TUPLE].setRaw("__str__", new XValueAccess(
					(XValueObj) __builtin__.getRaw(this, "__tuple___str__")));
			memory[0][XUtils.LIST].setRaw("__str__", new XValueAccess(
					(XValueObj) __builtin__.getRaw(this, "__list___str__")));
			memory[0][XUtils.MAP].setRaw("__str__", new XValueAccess(
					(XValueObj) __builtin__.getRaw(this, "__map___str__")));
			constPool = alloc(baseTypes[XUtils.CONST_POOL], loadModule("sys"));
			XValue sys = alloc(baseTypes[XUtils.MODULE], constPool, "sys");
			modules.put("sys", sys);
			for (int i = 0; i < XUtils.NUM_BASE_TYPES; i++) {
				sys.setRaw(this,
						((XTypeData) memory[0][i].getData()).getName(),
						new XValueObj(i));
			}
			method = alloc(getBaseType(XUtils.FUNC), "<init>", new String[0],
					-1, -1, -1, XValueNull.NULL, sys, constPool,
					XValueNull.NULL, 0, new XClosure[0]);
			exec = new XExec(this, false, 128, method, XValueNull.NULL);
			exec.run(-1);
			__builtin__
					.setRaw(this, "TypeError", sys.getRaw(this, "TypeError"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		return eval(new StringReader(script), context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		Object obj = get(XScriptLang.ENGINE_ATTR_SOURCE_FILE);
		String source = obj instanceof String ? (String) obj : ".intern";
		DiagnosticCollector<String> diagnosticCollector = new DiagnosticCollector<String>();
		Throwable thr = null;
		boolean interactive = (Boolean) get(XScriptLang.ENGINE_ATTR_INTERACTIVE);
		byte[] bytes = null;
		try {
			bytes = compile(
					(Map<String, Object>) get(XScriptLang.COMPILER_OPT_COMPILER),
					source, reader, diagnosticCollector, interactive);
		} catch (Throwable e) {
			thr = e;
		}
		for (Diagnostic<? extends String> diagnostic : diagnosticCollector
				.getDiagnostics()) {
			if (diagnostic.getKind() == Kind.ERROR) {
				throw new XScriptException(diagnostic.getMessage(Locale.US),
						diagnostic.getSource(),
						(int) diagnostic.getLineNumber());
			}
		}
		if (thr != null) {
			throw new XScriptException(thr);
		}
		if (XFlags.DEBUG)
			System.out.println(Arrays.toString(bytes));
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		XConstPoolImpl constpool;
		try {
			XFakeObjectInput ois = new XFakeObjectInput(bais);
			constpool = new XConstPoolImpl(ois);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		if (XFlags.DEBUG)
			System.out.println(constpool);
		XValue constPool = alloc(getBaseType(XUtils.CONST_POOL), constpool);
		XValue m = modules.get(source);
		if (m == null) {
			modules.put(source,
					m = alloc(getBaseType(XUtils.MODULE), constPool, source));
		}
		XValue method = alloc(getBaseType(XUtils.FUNC), "<init>",
				new String[0], -1, -1, -1, XValueNull.NULL, m, constPool,
				XValueNull.NULL, 0, new XClosure[0]);
		return createThreadAndInvoke(method, -2, null);
	}

	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return factory;
	}

	@Override
	public Object invokeMethod(Object thiz, String name, Object... args)
			throws ScriptException, NoSuchMethodException {
		return invokeMethod(thiz, -2, name, args);
	}

	public Object invokeMethod(Object thiz, int stepsToExecute, String name,
			Object... args) throws ScriptException, NoSuchMethodException {
		XValue obj = XUtils.wrap(this, thiz);
		XValue method = XUtils.lookupTry(this, obj, name, XValue.REF_NONE);
		if (method == null) {
			throw new NoSuchMethodException();
		}
		return createThreadAndInvoke(method, stepsToExecute, obj,
				XUtils.wrap(this, args));
	}

	@Override
	public Object invokeFunction(String name, Object... args)
			throws ScriptException, NoSuchMethodException {
		return invokeFunction(name, -2, args);
	}

	public Object invokeFunction(String name, int stepsToExecute,
			Object... args) throws ScriptException, NoSuchMethodException {
		if (name.endsWith(".<init>")) {
			name = name.substring(0, name.length() - 7);
			XValue module = getModule(name);
			if (module == null) {
				XValue constPool = alloc(baseTypes[XUtils.CONST_POOL],
						loadModule(name));
				module = alloc(baseTypes[XUtils.MODULE], constPool, name);
				module.setRaw(this, "__args__",
						createTuple(XUtils.wrap(this, args)));
				XValue method = alloc(getBaseType(XUtils.FUNC), "<init>",
						new String[0], -1, -1, -1, XValueNull.NULL, module,
						constPool, XValueNull.NULL, 0, new XClosure[0]);
				return createThreadAndInvoke(method, -2, null);
			} else {
				return null;
			}
		}
		int i = name.lastIndexOf('.');
		String moduleName;
		if (i == -1) {
			throw new NoSuchMethodException("No Module specified");
		} else {
			moduleName = name.substring(0, i);
			name = name.substring(i + 1);
		}
		XValue module = getModule(moduleName);
		XValue method = XUtils.lookupTry(this, module, name, XValue.REF_NONE);
		if (method == null) {
			throw new NoSuchMethodException();
		}
		return createThreadAndInvoke(method, stepsToExecute, XValueNull.NULL,
				XUtils.wrap(this, args));
	}

	@Override
	public XValue getModule(String name) {
		return modules.get(name);
	}

	private Object createThreadAndInvoke(XValue method, int stepsToExecute,
			XValue thiz, XValue... args) throws ScriptException {
		XObjectDataFunc func = XUtils.getDataAs(this, method,
				XObjectDataFunc.class);
		String[] params = func.getParamNames();
		if (params.length != args.length)
			throw new IllegalArgumentException();
		XExec exec = new XExec(this, false, 128, method, thiz, args);
		threads.add(exec);
		int instrs;
		if (stepsToExecute != -2) {
			instrs = stepsToExecute;
		} else {
			instrs = (Integer) get(XScriptLang.ENGINE_ATTR_INSTS_TO_RUN_ON_DIRECT_INVOKE);
		}
		if (running)
			exec.run(instrs);
		State state = exec.getState();
		if (state == State.TERMINATED) {
			XValue ret = exec.pop();
			threads.remove(exec);
			return ret;
		} else if (state == State.ERRORED) {
			XValue exception = exec.getException();
			threads.remove(exec);
			XUtils.throwException(this, exception);
		}
		if ((Boolean) get(XScriptLang.ENGINE_ATTR_TIMEOUT_DIRECT_INVOKE)) {
			throw new XScriptException(new TimeoutException());
		}
		return null;
	}

	@Override
	public void run() {
		int blocksToRun = (Integer) get(XScriptLang.ENGINE_ATTR_BLOCKS_TO_RUN_ON_INVOKE);
		int instsToRun = (Integer) get(XScriptLang.ENGINE_ATTR_INSTS_TO_RUN_ON_BLOCK);
		run(blocksToRun, instsToRun);
	}

	public void run(int blocksToRun, int instsToRun) {
		if (instsToRun <= 0)
			throw new IllegalArgumentException();
		while ((blocksToRun == -1 || blocksToRun > 0) && running && !threads.isEmpty()) {
			if (blocksToRun > 0)
				blocksToRun--;
			nextThread %= threads.size();
			int start = nextThread;
			XExec t;
			do {
				t = threads.get(nextThread);
				nextThread = (nextThread + 1) % threads.size();
			} while (!t.updateWaiting() && nextThread != start);
			if (t.getState() != State.RUNNING)
				return;
			t.run(instsToRun);
			switch (t.getState()) {
			case ERRORED:
				threads.remove(t);
				break;
			case TERMINATED:
				threads.remove(t);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public <T> T getInterface(Class<T> clasz) {
		Object proxy = Proxy.newProxyInstance(clasz.getClassLoader(),
				new Class[] { clasz }, staticInvocationHandler);
		return clasz.cast(proxy);
	}

	@Override
	public <T> T getInterface(Object thiz, Class<T> clasz) {
		Object proxy = Proxy.newProxyInstance(clasz.getClassLoader(),
				new Class[] { clasz }, new XInvocationHandler(this, thiz));
		return clasz.cast(proxy);
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(MAGIC_NUMBER_SAVE);
		out.writeShort(XScriptLang.ENGINE_VERSION_INT);
		out.writeShort(memory.length);
		for (int i = 0; i < memory.length; i++) {
			out.writeShort(memory[i] == null ? 0 : memory[i].length);
		}
		for (int i = 0; i < memory.length; i++) {
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = 0; j < mem.length; j++) {
					mem[j].save(out);
				}
			}
		}
		for (int i = 0; i < memory.length; i++) {
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = 0; j < mem.length; j++) {
					if (mem[j].isType(this)) {
						mem[j].saveData(out);
					}
				}
			}
		}
		for (int i = 0; i < memory.length; i++) {
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = 0; j < mem.length; j++) {
					if (!mem[j].isType(this)) {
						mem[j].saveData(out);
					}
				}
			}
		}

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		int numer = in.readInt();
		if (numer != MAGIC_NUMBER_SAVE) {
			throw new IOException("Bad file");
		}
		int version = in.readUnsignedShort();
		if (version > XScriptLang.ENGINE_VERSION_INT) {
			throw new IOException("Version to fair ahead");
		}
		int size = in.readUnsignedShort();
		memory = new XObject[size][];
		for (int i = 0; i < size; i++) {
			int s = in.readUnsignedShort();
			if (s > 0) {
				memory[i] = new XObject[s];
			}
		}
		for (int i = 0; i < size; i++) {
			int ptr = i << 16;
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = 0; j < mem.length; j++) {
					mem[j] = new XObject(ptr | j, in);
				}
			}
		}
		for (int i = 0; i < size; i++) {
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = 0; j < mem.length; j++) {
					if (mem[j].isType(this)) {
						mem[j].loadData(this, in);
					}
				}
			}
		}
		for (int i = 0; i < size; i++) {
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = 0; j < mem.length; j++) {
					if (!mem[j].isType(this)) {
						mem[j].loadData(this, in);
					}
				}
			}
		}

	}

	@Override
	public XValue getBaseType(int index) {
		return baseTypes[index];
	}

	@Override
	public XObject getObject(int pointer) {
		int mem = pointer >>> 16;
		return memory[mem][pointer & 0xFFFF];
	}

	@Override
	public XObject getObject(XValue pointer) {
		pointer = XValue.unpackContainer(pointer);
		if (pointer instanceof XValueObj) {
			return getObject(((XValueObj) pointer).getPointer());
		}
		return null;
	}

	@Override
	public boolean delete(XObject object) {
		int pointer = object.getPointer();
		int mem = pointer >>> 16;
		memory[mem][pointer & 0xFFFF] = null;
		return true;
	}

	@Override
	public XValue alloc(XValue clasz) {
		return alloc(clasz, new Object[0]);
	}

	private int searchFreePointer() {
		for (int i = 0; i < memory.length; i++) {
			XObject[] mem = memory[i];
			if (mem != null) {
				for (int j = i == 0 ? XUtils.NUM_BASE_TYPES : 0; j < mem.length; j++) {
					if (mem[j] == null) {
						return i << 16 | j;
					}
				}
			}
		}
		return -1;
	}

	@Override
	public XValue alloc(XValue type, Object... args) {
		int pointer = searchFreePointer();
		if (pointer == -1) {
			gc();
			pointer = searchFreePointer();
			if (pointer == -1) {
				throw new XRuntimeException("OutOfMemory", "OutOfMemory");
			}
		}
		XObject obj = new XObject(this, pointer, type, args);
		int mem = pointer >>> 16;
		memory[mem][pointer & 0xFFFF] = obj;
		return new XValueObj(pointer);
	}

	@Override
	public XValue alloc(String string) {
		return alloc(getBaseType(XUtils.STRING), string);
	}

	@Override
	public XValue createTuple(List<XValue> list) {
		return alloc(getBaseType(XUtils.TUPLE), list);
	}

	@Override
	public XValue createTuple(XValue... args) {
		List<XValue> list = new ArrayList<XValue>(args.length);
		for (XValue value : args) {
			list.add(value);
		}
		return createTuple(list);
	}

	@Override
	public XValue createList(List<XValue> list) {
		return alloc(getBaseType(XUtils.LIST), list);
	}

	@Override
	public XValue createMap(Map<String, XValue> map) {
		return alloc(getBaseType(XUtils.MAP), map);
	}

	@Override
	public XFunctionData getFunction(String name) {
		Bindings functions = (Bindings) get(XScriptLang.ENGINE_ATTR_FUNCTIONS_BINDING);
		return (XFunctionData) functions.get(name);
	}

	@Override
	public void addNativeMethod(String name, XFunctionData function) {
		Bindings functions = (Bindings) get(XScriptLang.ENGINE_ATTR_FUNCTIONS_BINDING);
		if (functions == null) {
			put(XScriptLang.ENGINE_ATTR_FUNCTIONS_BINDING,
					functions = createBindings());
		}
		functions.put(name, function);
	}

	@Override
	public XValue createFunction(String name) {
		return alloc(getBaseType(XUtils.NATIVE_FUNC), name);
	}

	@Override
	public void gc() {
		for (XObject[] m : memory) {
			for (XObject o : m) {
				o.resetVisible();
			}
		}
		for (XValue baseType : baseTypes) {
			baseType.setVisible(this);
		}
		for (XValue module : modules.values()) {
			module.setVisible(this);
		}
		for (XExec exec : threads) {
			exec.setVisible();
		}
		for (XObject[] m : memory) {
			for (int i = 0; i < m.length; i++) {
				if (!m[i].isVisible()) {
					m[i].delete(this, false);
				}
			}
		}
		// TODO visited all the objects?
	}

	private Object writeReplace() throws ObjectStreamException {
		return new Save(this);
	}

	private static class Save implements Serializable, Externalizable {

		private static final long serialVersionUID = -7988390441980350547L;

		private XScriptEngine scriptEngine;

		@SuppressWarnings("unused")
		Save() {
			scriptEngine = new XScriptEngine();
		}

		Save(XScriptEngine scriptEngine) {
			this.scriptEngine = scriptEngine;
		}

		private Object readResolve() throws ObjectStreamException {
			return scriptEngine;
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			scriptEngine.writeExternal(out);
		}

		@Override
		public void readExternal(ObjectInput in) throws IOException,
				ClassNotFoundException {
			scriptEngine.readExternal(in);
		}

	}

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public XValue getBuiltinModule() {
		return getModule("__builtin__");
	}

	@SuppressWarnings("unchecked")
	@Override
	public XConstPoolImpl loadModule(String name) {
		if (name.equals("sys") || name.equals("__builtin__")) {
			InputStream is = XScriptEngine.class.getClassLoader()
					.getResourceAsStream(name + ".xcm");
			if (is == null) {
				return null;
			}
			XFakeObjectInput in = new XFakeObjectInput(is);
			try {
				if (in.readInt() != MAGIC_NUMBER_COMPILED_MODULE) {
					return null;
				}
				return new XConstPoolImpl(in);
			} catch (IOException e) {
			} finally {
				try {
					is.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
		FileSystem fileSystem = (FileSystem) get(XScriptLang.ENGINE_ATTR_FILE_SYSTEM);
		List<String> roots = (List<String>) get(XScriptLang.ENGINE_ATTR_FILE_SYSTEM_ROOTS);
		Iterator<String> i = roots.iterator();
		String root;
		if (i.hasNext()) {
			root = i.next();
		} else {
			root = "";
		}
		String seperator = fileSystem.getSeparator();
		InputStream is = null;
		InputStream is2 = null;
		XFakeObjectInput in = null;
		name = name.replace(".", seperator);
		try {
			do {
				if (root == null) {
					root = i.next();
				}
				if (!root.isEmpty() && !root.endsWith(seperator)) {
					root += seperator;
				}
				String path = root + name;
				Path p = fileSystem.getPath(path + ".xsm");
				try {
					is = fileSystem.provider().newInputStream(p,
							StandardOpenOption.READ);
					Reader reader = new InputStreamReader(is);
					XBasicDiagnosticListener basicDiagnosticListener = new XBasicDiagnosticListener();
					byte[] bytes = compile(
							(Map<String, Object>) get(XScriptLang.COMPILER_OPT_COMPILER),
							path + ".xsm", reader, basicDiagnosticListener,
							false);
					Diagnostic<? extends String> diagnostic = basicDiagnosticListener
							.getFirstError();
					if (diagnostic != null)
						throw new XRuntimeScriptException(new XScriptException(
								diagnostic.getMessage(Locale.US),
								diagnostic.getSource(),
								(int) diagnostic.getLineNumber()));
					is2 = new ByteArrayInputStream(bytes);
					in = new XFakeObjectInput(is2);
				} catch (FileNotFoundException e) {
				} catch (NoSuchFileException e) {
				}
				if (in == null) {
					try {
						p = fileSystem.getPath(path + ".xcm");
						is2 = fileSystem.provider().newInputStream(p,
								StandardOpenOption.READ);
						in = new XFakeObjectInput(is2);
						if (in.readInt() != MAGIC_NUMBER_COMPILED_MODULE) {
							System.out.println("MAGIC_NUMBER_COMPILED_MODULE");
							return null;
						}
					} catch (FileNotFoundException e) {
					} catch (NoSuchFileException e) {
					}
				}
				root = null;
			} while (in == null && i.hasNext());
		} catch (IOException e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
				}
			}
		}
		if (is2 == null)
			return null;
		XConstPoolImpl module;
		try {
			module = new XConstPoolImpl(in);
		} catch (IOException e) {
			try {
				is2.close();
			} catch (IOException e1) {
			}
			return null;
		}
		return module;
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] compile(Map<String, Object> options, String source,
			Reader reader, DiagnosticListener<String> diagnosticListener,
			boolean interactive) {
		XFileReader fileReader = new XFileReader(source, reader);
		XCompiler compiler;
		if (options == null
				|| (compiler = (XCompiler) options
						.get(XScriptLang.COMPILER_OPT_COMPILER)) == null) {
			int i = source.lastIndexOf('.');
			if (i == -1) {
				return XInternCompiler.COMPILER.compile(options, fileReader,
						diagnosticListener, interactive);
			}
			String extension = source.substring(i + 1);
			Object obj = get(XScriptLang.ENGINE_ATTR_COMPILER_MAP);
			compiler = null;
			if (obj instanceof Map) {
				Map<String, Object> compilers = (Map<String, Object>) obj;
				compiler = (XCompiler) compilers.get(extension);
			}
			if (compiler == null) {
				return XInternCompiler.COMPILER.compile(options, fileReader,
						diagnosticListener, interactive);
			}
		}
		return compiler.compile(options, fileReader, diagnosticListener,
				interactive);
	}

	@Override
	public boolean doInit() {
		return doInit;
	}

	@Override
	public XValue tryImportModule(String name) {
		try {
			XValue constPool = alloc(baseTypes[XUtils.CONST_POOL],
					loadModule(name));
			XValue module = alloc(baseTypes[XUtils.MODULE], constPool, name);
			modules.put(name, module);
			return module;
		} catch (Throwable e) {
			throw new XRuntimeException("TypeError", "Module '%s' not found",
					name);
		}
	}

	@Override
	public PrintStream getOut() {
		return (PrintStream) get(XScriptLang.ENGINE_ATTR_OUT);
	}

	@Override
	public PrintStream getErr() {
		PrintStream ps = (PrintStream) get(XScriptLang.ENGINE_ATTR_ERR);
		return ps == null ? getOut() : ps;
	}

	@Override
	public InputStream getIn() {
		return (InputStream) get(XScriptLang.ENGINE_ATTR_IN);
	}

	@Override
	public void exit(int state) {
		running = false;
		put(XScriptLang.ENGINE_ATTR_EXIT_STATE, state);
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	public boolean hasThreads() {
		return !threads.isEmpty();
	}

}
