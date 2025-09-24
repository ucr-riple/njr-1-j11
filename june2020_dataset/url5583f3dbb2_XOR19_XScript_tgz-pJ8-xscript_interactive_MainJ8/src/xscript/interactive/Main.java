package xscript.interactive;

import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import xscript.XScriptEngineFactory;
import xscript.XScriptLang;
import xscript.executils.Localizer;
import xscript.executils.Log;
import xscript.executils.Utils;

public class Main {

	public static void main(String[] args){
		System.exit(run(args));
	}
	
	public static int run(String[] args){
		Utils.initConsoleIfNeeded(Utils.getOwnName(Main.class));
		ScriptEngineManager sem = new ScriptEngineManager();
		sem.registerEngineName(XScriptLang.ENGINE_NAME, new XScriptEngineFactory());
		ScriptEngine se = sem.getEngineByName(XScriptLang.ENGINE_NAME);
		ResourceBundle lang = ResourceBundle.getBundle("xscript.interactive.lang");
		Log log = new Log(new Localizer(lang));
		if(args==null || args.length==0){
			return new Interactive(se, log).run();
		}
		return new Exec(se, log, args).run();
	}
	
}
