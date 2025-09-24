package xscript.compiler.main;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import xscript.compiler.XFileReader;
import xscript.compiler.XInternCompiler;
import xscript.executils.ArgReader;
import xscript.executils.Localizer;
import xscript.executils.Log;
import xscript.executils.ArgReader.RecuresiveFileException;
import xscript.executils.Log.Kind;
import xscript.executils.Utils;


public class Main {
	
	public static void main(String[] args){
		System.exit(process(args));
	}
	
	public static int process(String[] args){
		return new Main(args).process();
	}

	private final ArgReader args;
	
	private final Log log;
	
	private final String commandLine;
	
	private final String ownName;
	
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
		public boolean addSourceDir(File file) {
			if(!file.exists()){
				error("err.dir.not.found", file);
				return false;
			}
			if(!file.isDirectory()){
				error("err.file.not.directory", file);
				return false;
			}
			sourceDirs.add(file);
			return true;
		}
		
		@Override
		public boolean setOutputDir(File file){
			if(!file.exists()){
				error("err.dir.not.found", file);
				return false;
			}
			if(!file.isDirectory()){
				error("err.file.not.directory", file);
				return false;
			}
			outputTo = file;
			return true;
		}

		@Override
		public void error(String key, Object...args) {
			Main.this.error(key, args);
		}

		@Override
		public void putOption(String key, String value) {
			compilerOptions.put(key, value);
		}
		
	};
	
	private boolean errored;
	
	private File outputTo;
	
	private List<File> sourceDirs = new LinkedList<File>();
	
	private Set<String> compiled = new HashSet<String>();
	
	private final Map<String, Object> compilerOptions = new HashMap<String, Object>();
	
	private Main(String[] args) {
		if(args==null||args.length==0){
			this.args = null;
		}else{
			this.args = new ArgReader(args);
		}
		ResourceBundle lang = ResourceBundle.getBundle("xscript.compiler.main.lang");
		log = new Log(new Localizer(lang));
		sourceDirs.add(new File("."));
		commandLine = Utils.getCommandLineRebuild(Main.class);
		ownName = Utils.getOwnName(Main.class);
	}
	
	private int process() {
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
						processFile(arg);
					}
				}
			}
			
			return errored?Utils.ERROR:Utils.OK;
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
	
	private void processFile(String file) throws IOException{
		String[] split = file.split("[\\./\\\\]");
		for(File sources:sourceDirs){
			check(sources, split, 0, null);
		}
	}
	
	private void check(File f, String[] splits, int i, String file) throws IOException{
		if(splits.length-1==i){
			String s = splits[i];
			s = "\\Q"+s+".xsm\\E";
			final Pattern pattern = Pattern.compile(s.replace("*", "\\Q.*\\E").replace("?", "\\Q.\\E"));
			File[] files = f.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile()){
						return pattern.matcher(pathname.getName()).matches();
					}
					return false;
				}
			});
			for(File f2:files){
				String file2;
				String n = f2.getName();
				int index = n.lastIndexOf('.');
				if(index!=-1){
					n = n.substring(0, index);
				}
				if(file==null){
					file2 = n;
				}else{
					file2 = file + "." + n;
				}
				if(compiled.contains(file2))
					continue;
				compiled.add(file2);
				processFile(file, file2, f2);
			}
		}else{
			f = new File(f, splits[i]);
			if(file==null){
				file = splits[i];
			}else{
				file += "."+splits[i];
			}
			if(f.exists()){
				check(f, splits, i+1, file);
			}
		}
	}
	
	private void processFile(String file, String sourceName, File in) throws IOException{
		Reader r = null;
		OutputStream os = null;
		try{
			r = new FileReader(in);
			XFileReader reader = new XFileReader(sourceName, r);
			byte[] compiled = XInternCompiler.COMPILER.compile(compilerOptions, reader, log, false);
			try{
				r.close();
			}catch(IOException e){}
			r = null;
			File f = file==null?outputTo:new File(outputTo, file);
			f.mkdirs();
			int index = sourceName.lastIndexOf('.');
			if(index==-1){
				f = new File(f, sourceName+".xcm");
			}else{
				f = new File(f, sourceName.substring(index+1)+".xcm");
			}
			os = new FileOutputStream(f);
			os.write('X');
			os.write('S');
			os.write('C');
			os.write('M');
			os.write(compiled);
		}finally{
			if(r!=null){
				try {
					r.close();
				} catch (IOException e) {}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {}
			}
		}
	}
	
}
