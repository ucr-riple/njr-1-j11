import java.io.IOException;



public class Main {

	public static void main(String[] args) throws IOException{
		/*ScriptEngineManager manager = new ScriptEngineManager();
		manager.registerEngineName(XScriptLang.ENGINE_NAME, new XScriptEngineFactory());
		ScriptEngine engine = manager.getEngineByName(XScriptLang.ENGINE_NAME);
		engine.put(XScriptLang.ENGINE_ATTR_SOURCE_FILE, "Test.xsm");
		try{
			System.out.println(engine.eval(new FileReader("bin/Test.xsm")));
		}catch(ScriptException e){
			e.printStackTrace();
		}*/
		xscript.compiler.main.Main.main(new String[]{"bin/__builtin__", "bin/sys"});
	}
	
}
