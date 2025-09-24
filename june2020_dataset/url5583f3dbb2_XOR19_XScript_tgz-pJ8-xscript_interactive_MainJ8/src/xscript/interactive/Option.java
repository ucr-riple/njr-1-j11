package xscript.interactive;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import xscript.executils.Log;

enum Option {
	SEARCHPATH("-searchpath", "opt.arg.directory", "opt.searchpath", "-s"){
		@Override
		boolean process(OptionHelper helper, String arg) {
			File file = new File(arg);
			return helper.addSearchPath(file);
		}
	},
	VERSION("-version", null, "opt.version", "-v"){
		@Override
		boolean process(OptionHelper helper, String arg) {
			helper.noFileNeeded();
			Log log = helper.getLog();
	        log.println("msg.version", helper.getOwnName(), helper.getScriptEngine().getFactory().getEngineVersion());
			return true;
		}
	},
	HELP("-help", null, "opt.help", "-h", "-?") {
		@Override
		boolean process(OptionHelper helper, String arg) {
			helper.noFileNeeded();
			Log log = helper.getLog();
			log.println("msg.usage.header", helper.getCommandLine());
			for(Option c:Option.values()){
				c.help(log);
			}
			log.rawprintln("   %-26s %s", "@"+log.localize("opt.arg.file"), log.localize("opt.AT"));
			log.println();
			return true;
		}
	};
	private final String name;
	private final String argNameKey;
	private final String descrKey;
	Option(String name, String argName, String descrKey, String...extraNames){
		this.name = name;
		this.argNameKey = argName;
		this.descrKey = descrKey;
		Options.OPTIONS.put(name, this);
		for(String name2:extraNames){
			Options.OPTIONS.put(name2, this);
		}
	}
	Option(String name, String argName, String descrKey){
		this.name = name;
		this.argNameKey = argName;
		this.descrKey = descrKey;
		Options.OPTIONS.put(name, this);
	}
	abstract boolean process(OptionHelper helper, String arg);
	void help(Log log){
		log.rawprintln("   %-26s %s", helpSynopsis(log), log.localize(descrKey));
	}
	private String helpSynopsis(Log log) {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if(argNameKey!=null){
        	sb.append(' ');
        	sb.append(log.localize(argNameKey));
        }
        return sb.toString();
    }
	public boolean hasArg() {
		return argNameKey!=null;
	}
	public static Option getOption(String name){
		return Options.OPTIONS.get(name);
	}
	
	private static final class Options{
		static final Map<String, Option> OPTIONS = new HashMap<String, Option>();
	}
	
}
