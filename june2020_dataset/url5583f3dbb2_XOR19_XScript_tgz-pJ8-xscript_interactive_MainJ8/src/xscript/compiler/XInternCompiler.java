package xscript.compiler;

import java.util.Map;

import javax.tools.DiagnosticListener;

import xscript.compiler.parser.XTokenizer;
import xscript.compiler.tree.XTree;
import xscript.compiler.treemaker.XTreeMaker;

public class XInternCompiler implements XCompiler {

	public static final XInternCompiler COMPILER = new XInternCompiler();
	
	public static final String VERSION = "1.0.0";
	
	private XInternCompiler(){}
	
	@Override
	public byte[] compile(Map<String, Object> o, XFileReader reader, DiagnosticListener<String> diagnosticListener, boolean interactive) {
		XCompilerOptions options = new XCompilerOptions();
		if(o!=null)
			options.from(o);
		XTokenizer tokenizer = new XTokenizer(reader, diagnosticListener);
		XTreeMaker treeMaker = new XTreeMaker(tokenizer, diagnosticListener);
		XTree tree = interactive?treeMaker.makeModuleInteractive():treeMaker.makeModule();
		XTreeCompiler treeCompiler = new XTreeCompiler(diagnosticListener, options);
		tree.accept(treeCompiler);
		return treeCompiler.getBytes();
	}
	
}
