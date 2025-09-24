package xscript.compiler;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.tools.Diagnostic;

public class XDiagnostic implements Diagnostic<String> {
	
	private static final Map<String, Kind> kinds = new HashMap<String, Kind>();
	
	private static final ResourceBundle lang;
	
	static{
		ResourceBundle _lang = null;
		try{
			_lang = ResourceBundle.getBundle("xscript.compiler.lang");
		}catch(MissingResourceException e){}
		lang = _lang;
		kinds.put("label.unused", Kind.WARNING);
		kinds.put("code.dead", Kind.WARNING);
		kinds.put("code.empty", Kind.WARNING);
	}
	
	private XPosition position;
	private long start;
	private long end;
	private String code;
	private Object[] args;
	
	public XDiagnostic(XPosition position, String code, Object...args) {
		this.position = position;
		this.start = position.pos;
		this.end = position.pos;
		this.code = code;
		this.args = args;
	}
	
	public XDiagnostic(XPosition position, long start, String code, Object...args) {
		this.position = position;
		this.start = start;
		this.end = position.pos;
		this.code = code;
		this.args = args;
	}
	
	public XDiagnostic(XPosition position, long start, long end, String code, Object...args) {
		this.position = position;
		this.start = start;
		this.end = end;
		this.code = code;
		this.args = args;
	}

	public void setEnd(long end){
		this.end = end;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public long getColumnNumber() {
		return position.column;
	}

	@Override
	public long getEndPosition() {
		return end;
	}

	@Override
	public Kind getKind() {
		Kind kind = kinds.get(code);
		return kind==null?Kind.ERROR:kind;
	}

	@Override
	public long getLineNumber() {
		return position.line;
	}

	private String localize(String key, Object...args){
		if(lang==null){
			return key;
		}else{
			String msg;
			try{
				msg = lang.getString(key);
			}catch(MissingResourceException e){
				msg = "compiler message file broken: key=" + key + " arguments={0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}";
			}
			return MessageFormat.format(msg, args);
		}
	}
	
	@Override
	public String getMessage(Locale loc) {
		return localize(code, args);
	}

	@Override
	public long getPosition() {
		return position.pos;
	}

	@Override
	public String getSource() {
		return position.source;
	}

	@Override
	public long getStartPosition() {
		return start;
	}

	@Override
	public String toString() {
		return position+":"+start+"->"+end+":"+getMessage(null);
	}

	public Object[] getArgs(){
		return args;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Arrays.hashCode(args);
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (int) (end ^ (end >>> 32));
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + (int) (start ^ (start >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XDiagnostic other = (XDiagnostic) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (end != other.end)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (start != other.start)
			return false;
		return true;
	}
	
	public LogRecord asLogRecord(){
		Level level;
		switch(getKind()){
		case ERROR:
			level = Level.SEVERE;
			break;
		case MANDATORY_WARNING:
		case WARNING:
			level = Level.WARNING;
			break;
		case NOTE:
		case OTHER:
		default:
			level = Level.INFO;
			break;
		}
		LogRecord logRecord = new LogRecord(level, code);
		logRecord.setSourceClassName(position.source);
		logRecord.setResourceBundle(lang);
		logRecord.setResourceBundleName("script.compiler.lang");
		Object[] params = new Object[args.length+6];
		System.arraycopy(args, 0, params, 6, args.length);
		params[0] = position.source;
		params[1] = position.pos;
		params[2] = position.line;
		params[3] = position.column;
		params[4] = start;
		params[5] = end;
		logRecord.setParameters(params);
		return logRecord;
	}
	
}