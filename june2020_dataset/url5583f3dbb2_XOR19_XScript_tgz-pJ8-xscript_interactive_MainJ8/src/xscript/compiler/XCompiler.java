package xscript.compiler;

import java.util.Map;

import javax.tools.DiagnosticListener;


public interface XCompiler {

	public byte[] compile(Map<String, Object> options, XFileReader reader, DiagnosticListener<String> diagnosticListener, boolean interactive);
	
}
