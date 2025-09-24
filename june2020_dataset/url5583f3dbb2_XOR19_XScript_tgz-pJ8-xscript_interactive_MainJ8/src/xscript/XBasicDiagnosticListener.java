package xscript;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticListener;

public class XBasicDiagnosticListener implements DiagnosticListener<String> {

	private Diagnostic<? extends String> firstError;
	
	@Override
	public void report(Diagnostic<? extends String> diagnostic) {
		if(diagnostic.getKind()==Kind.ERROR){
			firstError = diagnostic;
		}
	}
	
	public Diagnostic<? extends String> getFirstError(){
		return firstError;
	}

}
