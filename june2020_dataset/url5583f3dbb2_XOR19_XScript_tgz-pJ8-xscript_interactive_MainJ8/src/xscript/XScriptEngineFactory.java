package xscript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class XScriptEngineFactory implements ScriptEngineFactory {

	private static final Map<String, Object> PARAMETERS = new HashMap<String, Object>();

	private static XScriptEngineFactory defaultEngineFactory;

	static {
		PARAMETERS.put(ScriptEngine.ENGINE, XScriptLang.ENGINE_NAME);
		PARAMETERS.put(ScriptEngine.ENGINE_VERSION, XScriptLang.ENGINE_VERSION);
		PARAMETERS.put(ScriptEngine.LANGUAGE, XScriptLang.LANG_NAME);
		PARAMETERS.put(ScriptEngine.LANGUAGE_VERSION, XScriptLang.LANG_VERSION);
		PARAMETERS.put(ScriptEngine.NAME, XScriptLang.NAMES);
	}

	public static XScriptEngineFactory getDefaultEngineFactory() {
		if (defaultEngineFactory == null) {
			defaultEngineFactory = new XScriptEngineFactory();
		}
		return defaultEngineFactory;
	}

	public static void setDefaultEngineFactory(
			XScriptEngineFactory scriptEngineFactory) {
		if (scriptEngineFactory == null)
			throw new NullPointerException();
		defaultEngineFactory = scriptEngineFactory;
	}

	public XScriptEngineFactory() {
		if (defaultEngineFactory == null) {
			defaultEngineFactory = this;
		}
	}

	@Override
	public String getEngineName() {
		return XScriptLang.ENGINE_NAME;
	}

	@Override
	public String getEngineVersion() {
		return XScriptLang.ENGINE_VERSION;
	}

	@Override
	public List<String> getExtensions() {
		return XScriptLang.EXTENSIONS;
	}

	@Override
	public String getLanguageName() {
		return XScriptLang.LANG_NAME;
	}

	@Override
	public String getLanguageVersion() {
		return XScriptLang.LANG_VERSION;
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		int l = args.length;
		int size = 2 + m.length();
		if (obj != null) {
			size += obj.length() + 1;
		}
		if (l > 0) {
			size += (l - 1) * 2;
		}
		for (String arg : args) {
			size += arg.length();
		}
		StringBuilder sb = new StringBuilder(size);
		if (obj != null) {
			sb.append(obj);
			sb.append('.');
		}
		sb.append(m);
		sb.append('(');
		if (l > 0) {
			sb.append(args[0]);
			for (int i = 1; i < l; i++) {
				sb.append(',');
				sb.append(' ');
				sb.append(args[0]);
			}
		}
		sb.append(')');
		return sb.toString();
	}

	@Override
	public List<String> getMimeTypes() {
		return XScriptLang.MIME_TYPES;
	}

	@Override
	public List<String> getNames() {
		return XScriptLang.NAMES;
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		int l = toDisplay.length();
		int dl = l * 2 + 11;
		if (dl < 0)
			dl = l;
		StringBuilder sb = new StringBuilder(dl);
		sb.append("println(\"");
		for (int i = 0; i < l; i++) {
			char c = toDisplay.charAt(i);
			switch (c) {
			case '\\':
				sb.append('\\');
				sb.append('\\');
				break;
			case '\n':
				sb.append('\\');
				sb.append('n');
				break;
			case '\r':
				sb.append('\\');
				sb.append('r');
				break;
			case '\t':
				sb.append('\\');
				sb.append('t');
				break;
			case '\'':
				sb.append('\\');
				sb.append('\'');
				break;
			case '"':
				sb.append('\\');
				sb.append('"');
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append("\")");
		return sb.toString();
	}

	@Override
	public Object getParameter(String key) {
		return PARAMETERS.get(key);
	}

	@Override
	public String getProgram(String... statements) {
		int l = statements.length;
		int size = l * 2 - 1;
		for (String statement : statements) {
			size += statement.length();
		}
		StringBuilder sb = new StringBuilder(size);
		if (l > 0) {
			sb.append(statements[0]);
			for (int i = 1; i < l; i++) {
				sb.append(";\n");
				sb.append(statements[i]);
			}
			sb.append(";");
		}
		return sb.toString();
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new XScriptEngine(this);
	}

}
