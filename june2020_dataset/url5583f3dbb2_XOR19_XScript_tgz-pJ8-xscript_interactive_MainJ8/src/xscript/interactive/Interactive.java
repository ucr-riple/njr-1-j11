package xscript.interactive;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import xscript.XScriptEngine;
import xscript.XScriptLang;
import xscript.XUtils;
import xscript.executils.Log;
import xscript.executils.Utils;
import xscript.executils.Log.Kind;
import xscript.values.XValue;

public class Interactive {

	private static Pattern p = Pattern.compile("\\n[ \\t\\x0B\\f\\r]*\\n[ \\t\\x0B\\f\\r]*\\z");
	
	private final XScriptEngine se;
	
	private final Log log;
	
	public Interactive(ScriptEngine se, Log log) {
		this.se = (XScriptEngine)se;
		this.log = log;
	}

	public int run() {
		Utils.setConsoleTitle(log.localize("interactive.cmd.title"));
		log.println("interactive.header", se.getFactory().getEngineName(), se.getFactory().getEngineVersion());
		se.put(XScriptLang.ENGINE_ATTR_INTERACTIVE, true);
		se.put(XScriptLang.ENGINE_ATTR_SOURCE_FILE, ".interactive");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while(se.isRunning()){
			log.print("interactive.cmd.prfix");
			log.flush();
			String script = sc.nextLine();
			script = script.trim();
			if(script.equals("exit")){
				return Utils.OK;
			}else if(script.equals("help")){
				log.println("msg.help");
				continue;
			}
			while(!finished(script)){
				log.print("interactive.cmd.resume");
				log.flush();
				script += "\n"+sc.nextLine();
			}
			try {
				Object ret = se.eval(script);
				ret = ((Invocable)se).invokeFunction("__builtin__.___str__", ret);
				log.rawprintln(XUtils.getString(se, (XValue)ret));
			} catch (ScriptException e) {
				log.rawprintln(Kind.ERROR, e.getMessage());
			} catch (NoSuchMethodException e) {}
		}
		log.flush();
		return (Integer)se.get(XScriptLang.ENGINE_ATTR_EXIT_STATE);
	}
	
	private static boolean finished(String script){
		boolean inStr = false;
		if(p.matcher(script).find()){
			return true;
		}
		List<Character> opens = new ArrayList<Character>();
		for(int i=0; i<script.length(); i++){
			char c = script.charAt(i);
			switch(c){
			case '\\':
				if(inStr)
					i++;
				break;
			case '"':
				inStr = !inStr;
				break;
			case '\n':
				if(inStr)
					return true;
				break;
			case '{':
			case '(':
			case '[':
				opens.add(c);
				break;
			case '}':
				if(opens.isEmpty() || opens.remove(opens.size()-1)!='{'){
					return true;
				}
				break;
			case ')':
				if(opens.isEmpty() || opens.remove(opens.size()-1)!='('){
					return true;
				}
				break;
			case ']':
				if(opens.isEmpty() || opens.remove(opens.size()-1)!='['){
					return true;
				}
				break;
			}
		}
		return opens.isEmpty();
	}
	
	
}
