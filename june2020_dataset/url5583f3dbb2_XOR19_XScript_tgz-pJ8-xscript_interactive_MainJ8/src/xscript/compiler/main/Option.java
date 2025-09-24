package xscript.compiler.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import xscript.compiler.XCompilerOptions;
import xscript.compiler.XInternCompiler;
import xscript.executils.Log;

enum Option {
	O("-o", "opt.arg.o.level", "opt.o"){

		@Override
		boolean process(OptionHelper helper, String arg) {
			helper.putOption(XCompilerOptions.COMPILER_OPT_OPTIMIZATION_LEVEL, arg);
			return true;
		}
		
	},
	RL("-rl", "opt.arg.onoff", "opt.rl"){

		@Override
		boolean process(OptionHelper helper, String arg) {
			helper.putOption(XCompilerOptions.COMPILER_OPT_OPTIMIZATION_LEVEL, arg);
			return true;
		}
		
	},
	RA("-ra", "opt.arg.onoff", "opt.ra"){

		@Override
		boolean process(OptionHelper helper, String arg) {
			helper.putOption(XCompilerOptions.COMPILER_OPT_OPTIMIZATION_LEVEL, arg);
			return true;
		}
		
	},
	SOURCEPATH("-sourcepath", "opt.arg.directory", "opt.sourcepath", "-s"){
		@Override
		boolean process(OptionHelper helper, String arg) {
			File file = new File(arg);
			return helper.addSourceDir(file);
		}
	},
	D("-d", "opt.arg.directory", "opt.d"){
		@Override
		boolean process(OptionHelper helper, String arg) {
			File file = new File(arg);
			return helper.setOutputDir(file);
		}
	},
	VERSION("-version", null, "opt.version", "-v"){
		@Override
		boolean process(OptionHelper helper, String arg) {
			Log log = helper.getLog();
	        log.println("msg.version", helper.getOwnName(), XInternCompiler.VERSION);
			return true;
		}
	},
	HELP("-help", null, "opt.help", "-h", "-?") {
		@Override
		boolean process(OptionHelper helper, String arg) {
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
