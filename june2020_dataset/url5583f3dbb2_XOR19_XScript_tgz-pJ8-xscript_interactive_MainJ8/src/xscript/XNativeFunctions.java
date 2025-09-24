package xscript;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticCollector;

import xscript.compiler.XFileReader;
import xscript.object.XConstPoolImpl;
import xscript.object.XFunction;
import xscript.object.XFunctionData;
import xscript.object.XObjectDataModule;
import xscript.object.XRuntime;
import xscript.values.XValue;
import xscript.values.XValueNull;

class XNativeFunctions {

	private static final Map<String, XFunctionData> functions;

	static {
		Map<String, XFunctionData> map = new HashMap<String, XFunctionData>();
		map.put("__builtin__.__getModule", new XFunctionData(new __builtin__.__getModule(), "module"));
		map.put("__builtin__.__importParent", new XFunctionData(new __builtin__.__importParent(), "module"));
		map.put("__builtin__.__importModule", new XFunctionData(new __builtin__.__importModule(), "module"));
		map.put("__builtin__.__initModule", new XFunctionData(new __builtin__.__initModule(), "module"));
		map.put("__builtin__.__fillStackTrace", new XFunctionData(new __builtin__.__fillStackTrace()));
		map.put("__builtin__.__print", new XFunctionData(new __builtin__.__print(), "string", "nl", "err"));
		map.put("__builtin__.__pollInput", new XFunctionData(new __builtin__.__pollInput()));
		map.put("__builtin__.__sleep", new XFunctionData(new __builtin__.__sleep(), "time"));
		map.put("__builtin__.__exit", new XFunctionData(new __builtin__.__exit(), "state"));
		map.put("__builtin__.__dir", new XFunctionData(new __builtin__.__dir(), "obj"));
		map.put("__builtin__.__exec", new XFunctionData(new __builtin__.__exec(), "code"));
		map.put("__builtin__.__linkNative", new XFunctionData(new __builtin__.__linkNative(), "name"));
		functions = Collections.unmodifiableMap(map);
	}

	static Map<String, XFunctionData> getFunctions() {
		return functions;
	}

	private static final class __builtin__ {

		private __builtin__() {
		}

		private static class __getModule implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				return runtime.getModule(XUtils.getString(runtime, params[0]));
			}

		}

		private static class __importParent implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				String module = XUtils.getString(runtime, params[0]);
				int i = module.lastIndexOf('.');
				if (module.endsWith(".__init__")) {
					i = module.lastIndexOf('.', i - 1);
				}
				if (i == -1) {
					return XValueNull.NULL;
				} else {
					module = module.substring(0, i) + ".__init__";
					XValue __import = runtime.getBuiltinModule().getRaw(runtime, "__import");
					XValue m = runtime.alloc(module);
					List<XValue> l = new ArrayList<XValue>();
					l.add(m);
					exec.call(__import, null, l, null);
					return NO_PUSH;
				}
			}

		}

		private static class __importModule implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				String module = XUtils.getString(runtime, params[0]);
				return runtime.tryImportModule(module);
			}

		}

		private static class __initModule implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				XValue module = params[0];
				XValue constPool = XUtils.getDataAs(runtime, module, XObjectDataModule.class).getConstPool();
				XValue method = runtime.alloc(runtime.getBaseType(XUtils.FUNC), "<init>", new String[0], -1, -1, -1, XValueNull.NULL, module, constPool, XValueNull.NULL, 0, new XClosure[0]);
				exec.call(method, null, Collections.<XValue> emptyList(), null);
				return NO_PUSH;
			}

		}

		private static class __fillStackTrace implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				return XUtils.convertStackTrace(runtime, exec.getStackTrace(true));
			}

		}

		private static class __print implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				String s = XUtils.getString(runtime, params[0]);
				PrintStream ps;
				if (params[2].noneZero()) {
					ps = runtime.getErr();
				} else {
					ps = runtime.getOut();
				}
				if (params[1].noneZero()) {
					ps.println(s);
				} else {
					ps.print(s);
				}
				return XValueNull.NULL;
			}

		}

		private static class __pollInput implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				byte[] buffer = new byte[1024];
				int num = runtime.getIn().read(buffer);
				String in = new String(buffer, 0, num);
				XValue ret = runtime.alloc(in);
				return ret;
			}

		}

		private static class __sleep implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				exec.setWait(params[0].getInt());
				return NO_PUSH;
			}

		}

		private static class __exit implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				int i = (int) params[0].getInt();
				runtime.exit(i);
				return null;
			}

		}

		private static class __dir implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				XValue value = params[0];
				if (value == XValueNull.NULL || value == null) {
					XCallFrame callFrame = exec.getCallFrame();
					value = callFrame.getModule();
					if (value == runtime.getModule("sys")) {
						value = callFrame.getParent().getModule();
					}
				}
				List<String> li = XUtils.dir(runtime, value);
				List<XValue> l = new ArrayList<XValue>();
				for (String s : li) {
					l.add(runtime.alloc(s));
				}
				return runtime.createTuple(l);
			}

		}

		private static class __exec implements XFunction {

			@SuppressWarnings("unchecked")
			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				String source = XUtils.getString(runtime, params[0]);
				XFileReader reader = new XFileReader(".exec", new StringReader(source));
				DiagnosticCollector<String> diagnosticCollector = new DiagnosticCollector<String>();
				Throwable thr = null;
				byte[] bytes = null;
				try {
					bytes = runtime.compile((Map<String, Object>) runtime.get(XScriptLang.COMPILER_OPT_COMPILER), ".exec", reader, diagnosticCollector, true);
				} catch (Throwable e) {
					thr = e;
				}
				for (Diagnostic<? extends String> diagnostic : diagnosticCollector.getDiagnostics()) {
					if (diagnostic.getKind() == Kind.ERROR) {
						throw new XScriptException(diagnostic.getMessage(Locale.US), diagnostic.getSource(), (int) diagnostic.getLineNumber());
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
				XValue constPool = runtime.alloc(runtime.getBaseType(XUtils.CONST_POOL), constpool);
				XCallFrame callFrame = exec.getCallFrame();
				XValue m = callFrame.getModule();
				if (m == runtime.getModule("sys")) {
					m = callFrame.getParent().getModule();
				}
				XValue method = runtime.alloc(runtime.getBaseType(XUtils.FUNC), "<init>", new String[0], -1, -1, -1, XValueNull.NULL, m, constPool, XValueNull.NULL, 0, new XClosure[0]);
				exec.call(method, XValueNull.NULL, Collections.<XValue> emptyList(), null);
				return NO_PUSH;
			}

		}

		private static class __linkNative implements XFunction {

			@Override
			public XValue invoke(XRuntime runtime, XExec exec, XValue thiz, XValue[] params, List<XValue> list, Map<String, XValue> map) throws Throwable {
				String name = XUtils.getString(runtime, params[0]);
				return runtime.createFunction(name);
			}

		}

	}

}
