package xscript;

public class XRuntimeScriptException extends RuntimeException {

	private static final long serialVersionUID = 9093737675607211218L;

	public XRuntimeScriptException(XScriptException cause){
		super(cause);
	}
	
}
