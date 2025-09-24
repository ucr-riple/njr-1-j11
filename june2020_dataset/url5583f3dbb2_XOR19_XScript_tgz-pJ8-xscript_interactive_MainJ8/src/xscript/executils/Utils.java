package xscript.executils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

import javax.swing.UIManager;

import xscript.executils.console.Console;

public class Utils {

	public static final int OK = 0;
	public static final int ERROR = 1;
	public static final int CMDERR = 2;
	public static final int SYSERR = 3;
	public static final int ABNORMAL = 4;
	
	private static Console console;
	
	public static void initConsoleIfNeeded(String title){
		if(console!=null)
			return;
		try{
			System.in.available();
			return;
		}catch(IOException e){}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {}
		console = new Console(title);
		MultiplexOutputStream sysout = new MultiplexOutputStream();
		sysout.addOutputStream(console.getOut());
		sysout.addOutputStream(System.out);
		MultiplexOutputStream syserr = new MultiplexOutputStream();
		syserr.addOutputStream(console.getErr());
		syserr.addOutputStream(System.err);
		InputStream sysin = new InputToOutputStream(console.getIn(), System.out);
		System.setOut(new PrintStream(sysout));
		System.setErr(new PrintStream(syserr));
		System.setIn(sysin);
		console.setDefaultCloseOperation(Console.EXIT_ON_CLOSE);
	}
	
	public static String getCommandLineRebuild(Class<?> mainClass){
		String userDir = System.getProperty("user.dir")+System.getProperty("file.separator");
		try {
			ProtectionDomain protectionDomain = mainClass.getProtectionDomain();
			CodeSource codeSource = protectionDomain.getCodeSource();
			if(codeSource!=null){
				try {
					String path = codeSource.getLocation().toURI().getPath();
					File file = new File(path);
					if(file.isFile()){
						path = file.getAbsolutePath();
						if(path.startsWith(userDir)){
							path = path.substring(userDir.length());
						}
						if(path.indexOf(' ')!=-1){
							path = "\""+path+"\"";
						}
						return "java -jar "+path;
					}
				} catch (URISyntaxException e) {}
			}
			URL url = mainClass.getClassLoader().getResource(mainClass.getName().replace('.', '/')+".class");
			if(url!=null){
				String surl = url.toString();
				if(surl.startsWith("jar:")){
					int index = surl.lastIndexOf('!');
					surl = surl.substring(4, index);
					try {
						url = new URL(surl);
						String path = url.toURI().getPath();
						File file = new File(path);
						if(file.isFile()){
							path = file.getAbsolutePath();
							if(path.startsWith(userDir)){
								path = path.substring(userDir.length());
							}
							if(path.indexOf(' ')!=-1){
								path = "\""+path+"\"";
							}
							return "java -jar "+path;
						}
					} catch (MalformedURLException e) {} 
					catch (URISyntaxException e) {}
				}else{
					String seperator = System.getProperty("path.separator");
					String[] paths = System.getProperty("java.class.path").split(Pattern.quote(seperator));
					String path = "";
					for(int i=0; i<paths.length; i++){
						File file = new File(paths[i]);
						String p = file.getAbsolutePath();
						if(p.startsWith(userDir)){
							p = p.substring(userDir.length());
						}
						if(i!=0){
							path += seperator;
						}
						path += p;
					}
					return "java -cp "+path+" "+mainClass.getName();
				}
			}
		} catch(SecurityException e){}
		return "java -jar <jarfile>";
	}
	
	public static String getOwnName(Class<?> mainClass){
		try {
			ProtectionDomain protectionDomain = mainClass.getProtectionDomain();
			CodeSource codeSource = protectionDomain.getCodeSource();
			if(codeSource!=null){
				try {
					String path = codeSource.getLocation().toURI().getPath();
					File file = new File(path);
					if(file.isFile()){
						return file.getName();
					}
				} catch (URISyntaxException e) {}
			}
			URL url = mainClass.getClassLoader().getResource(mainClass.getName().replace('.', '/')+".class");
			if(url!=null){
				String surl = url.toString();
				if(surl.startsWith("jar:")){
					int index = surl.lastIndexOf('!');
					surl = surl.substring(4, index);
					try {
						url = new URL(surl);
						String path = url.toURI().getPath();
						File file = new File(path);
						if(file.isFile()){
							return file.getName();
						}
					} catch (MalformedURLException e) {} 
					catch (URISyntaxException e) {}
				}
			}
		} catch(SecurityException e){}
		return mainClass.getName();
	}

	public static void setConsoleTitle(String title) {
		if(console!=null){
			console.setTitle(title);
		}
	}
	
}
