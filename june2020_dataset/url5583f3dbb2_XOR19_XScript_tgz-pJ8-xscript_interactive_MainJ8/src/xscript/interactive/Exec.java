package xscript.interactive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import xscript.XScriptEngine;
import xscript.XScriptLang;
import xscript.compiler.XInternCompiler;
import xscript.executils.ArgReader;
import xscript.executils.ArgReader.RecuresiveFileException;
import xscript.executils.Log;
import xscript.executils.Log.Kind;
import xscript.executils.Utils;
import xscript.values.XValue;

public class Exec {

	private final XScriptEngine se;
	
	private final ArgReader args;
	
	private final Log log;
	
	private final String ownName;
	
	private final String commandLine;
	
	private boolean needFile = true;
	
	private List<String> searchPaths;
	
	private final OptionHelper helper = new OptionHelper() {
		
		@Override
		public Log getLog() {
			return log;
		}

		@Override
		public String getOwnName() {
			return ownName;
		}

		@Override
		public String getCommandLine() {
			return commandLine;
		}
		
		@Override
		public ScriptEngine getScriptEngine() {
			return se;
		}

		@Override
		public void noFileNeeded() {
			needFile = false;
		}

		@Override
		public boolean addSearchPath(File file) {
			if(!file.exists()){
				error("err.dir.not.found", file);
				return false;
			}
			if(!file.isDirectory()){
				error("err.file.not.directory", file);
				return false;
			}
			searchPaths.add(file.getAbsolutePath());
			return true;
		}
		
	};
	
	@SuppressWarnings("unchecked")
	public Exec(ScriptEngine se, Log log, String[] args) {
		this.se = (XScriptEngine) se;
		this.args = new ArgReader(args);
		this.log = log;
		this.commandLine = Utils.getCommandLineRebuild(Main.class);
		this.ownName = Utils.getOwnName(Main.class);
		searchPaths = (List<String>) this.se.get(XScriptLang.ENGINE_ATTR_FILE_SYSTEM_ROOTS);
	}

	public int run() {
		try{
			if(args==null){
				Option.HELP.process(helper, null);
				return Utils.CMDERR;
			}
			
			String arg;
			while((arg = args.next())!=null){
				if(!arg.isEmpty()){
					if(arg.charAt(0)=='-'){
						if(!processCommand(arg)){
							return Utils.CMDERR;
						}
					}else{
						List<XValue> vmargs = new ArrayList<XValue>();
						String a;
						while((a = args.next())!=null){
							vmargs.add(se.alloc(a));
						}
						return processFile(arg, vmargs.toArray());
					}
				}
			}
			if(needFile){
				error("err.no.file");
				return Utils.CMDERR;
			}
			return Utils.OK;
		}catch(RecuresiveFileException e){
			log.println("err.recursive.file", e.f, e.opend);
			return Utils.SYSERR;
		}catch(IOException e){
			log.println("msg.io");
			e.printStackTrace(log.getWriter(Kind.NOTICE));
			return Utils.SYSERR;
		}catch (OutOfMemoryError e) {
			log.println("msg.resource");
			e.printStackTrace(log.getWriter(Kind.NOTICE));
            return Utils.SYSERR;
        } catch (StackOverflowError e) {
        	log.println("msg.resource");
			e.printStackTrace(log.getWriter(Kind.NOTICE));
            return Utils.SYSERR;
        } catch(Throwable e){
			log.println("msg.bug", XInternCompiler.VERSION);
			e.printStackTrace(log.getWriter(Kind.NOTICE));
			return Utils.ABNORMAL;
		}finally{
			log.flush();
		}
	}
	
	private boolean processCommand(String arg) throws IOException, RecuresiveFileException{
		Option o = Option.getOption(arg);
		if(o==null){
			error("err.invalid.flag", arg);
			return false;
		}else{
			String a;
			if(o.hasArg()){
				a = args.next();
				if(a==null){
					error("err.req.arg", arg);
					return false;
				}
			}else{
				a = null;
			}
			return o.process(helper, a);
		}
	}
	
	void error(String msg, Object... args){
		log.println(msg, args);
		log.println("msg.usage", helper.getCommandLine());
	}
	
	private int processFile(String file, Object[] args) throws IOException{
		Utils.setConsoleTitle(file);
		Object o2 = null;
		try {
			o2 = se.invokeFunction(file+".<init>", args);
		} catch (NoSuchMethodException e) {
			throw new FileNotFoundException(file);
		} catch (ScriptException e) {
			log.rawprintln(Kind.ERROR, e.getMessage());
		}
		while(se.isRunning() && se.hasThreads()){
			se.run();
		}
		Object o = se.get(XScriptLang.ENGINE_ATTR_EXIT_STATE);
		return o instanceof Integer?(Integer)o:o2 instanceof Number?((Number)o2).intValue():Utils.OK;
	}

}
