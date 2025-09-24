package xscript;

import java.util.List;

import xscript.compiler.XCompilerOptions;

public final class XScriptLang {

	public static final String ENGINE_NAME = "XScriptEngine";
	public static final String ENGINE_VERSION = "1.0.0";
	public static final int ENGINE_VERSION_INT = 0;
	public static final List<String> EXTENSIONS = new XArrayList<String>("xsc");
	public static final String LANG_NAME = "XScript";
	public static final String LANG_VERSION = "1.0.0";
	public static final List<String> MIME_TYPES = new XArrayList<String>();
	public static final List<String> NAMES = new XArrayList<String>(ENGINE_NAME, LANG_NAME);
	
	public static final String ENGINE_ATTR_SOURCE_FILE = "SOURCE";
	public static final String ENGINE_ATTR_FUNCTIONS_BINDING = "FUNCTIONS";
	public static final String ENGINE_ATTR_COMPILER_MAP = "COMPILERS";
	public static final String ENGINE_ATTR_FILE_SYSTEM = "FILE_SYSTEM";
	public static final String ENGINE_ATTR_FILE_SYSTEM_ROOTS = "FILE_SYSTEM_ROOTS";
	public static final String ENGINE_ATTR_OUT = "OUT";
	public static final String ENGINE_ATTR_ERR = "ERR";
	public static final String ENGINE_ATTR_IN = "IN";
	public static final String ENGINE_ATTR_INTERACTIVE = "INTERACTIVE";
	public static final String ENGINE_ATTR_INSTS_TO_RUN_ON_DIRECT_INVOKE = "INSTS_TO_RUN_ON_DIRECT_INVOKE";
	public static final String ENGINE_ATTR_TIMEOUT_DIRECT_INVOKE = "TIMEOUT_DIRECT_INVOKE";
	public static final String ENGINE_ATTR_INSTS_TO_RUN_ON_BLOCK = "INSTS_TO_RUN_ON_BLOCK";
	public static final String ENGINE_ATTR_BLOCKS_TO_RUN_ON_INVOKE = "BLOCKS_TO_RUN_ON_INVOKE";
	public static final String ENGINE_ATTR_EXIT_STATE = "EXIT_STATE";
	
	public static final String COMPILER_OPT_COMPILER = "COMPILER";
	public static final String COMPILER_OPT_REMOVE_LINES = XCompilerOptions.COMPILER_OPT_REMOVE_LINES;
	public static final String COMPILER_OPT_REMOVE_ASSERTS = XCompilerOptions.COMPILER_OPT_REMOVE_ASSERTS;
	public static final String COMPILER_OPT_OPTIMIZATION_LEVEL = XCompilerOptions.COMPILER_OPT_OPTIMIZATION_LEVEL;
	
	private XScriptLang(){}
	
}
