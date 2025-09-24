package xscript.object;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import javax.tools.DiagnosticListener;

import xscript.values.XValue;

public interface XRuntime {

	XValue getBaseType(int type);
	
	XObject getObject(int pointer);

	XObject getObject(XValue pointer);

	boolean delete(XObject object);
	
	XValue alloc(XValue type);
	
	XValue alloc(XValue type, Object...args);

	XValue alloc(String string);

	XValue createTuple(List<XValue> list);
	
	XValue createTuple(XValue...args);

	XValue createList(List<XValue> list);
	
	XValue createMap(Map<String, XValue> map);
	
	XFunctionData getFunction(String name);

	void addNativeMethod(String name, XFunctionData function);

	XValue createFunction(String name);
	
	void gc();

	long getTime();
	
	XValue getBuiltinModule();

	XConstPool loadModule(String name);

	boolean doInit();
	
	byte[] compile(Map<String, Object> options, String source, Reader reader, DiagnosticListener<String> diagnosticListener, boolean interactive);

	XValue getModule(String name);

	XValue tryImportModule(String name);

	PrintStream getOut();
	
	PrintStream getErr();
	
	InputStream getIn();

	void exit(int state);
	
	boolean isRunning();

	Object get(String option);
	
}
