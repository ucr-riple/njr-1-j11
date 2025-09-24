package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.HashMap;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.json.JSONObject;
import org.json.XML;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class TestXML2JSON {
	CompiledScript compiledScript;
    public static void main(String[] args) throws Exception{
    	TestXML2JSON xml2Json=new TestXML2JSON();
    	String xml=xml2Json.readXML();
    	System.out.println(xml);
    	System.out.println("Length : "+xml.length());
    	long startTime=System.currentTimeMillis();
        JSONObject json=XML.toJSONObject(xml);
        long endTime=System.currentTimeMillis();
        System.out.println(json);
        System.out.println("Length : "+json.toString().length());
        System.out.println(endTime-startTime);
        xml2Json.initialize("c:\\script\\mxml.curr.fxd.fxd.js");
        HashMap<String,Object> context = new HashMap<String,Object>() ;
        HashMap<String,Object> contextReturn = new HashMap<String,Object>() ;
		context.put("message",json.toString()) ;
		context.put("tradeIndex", 2);
		context.put("mxmlData",contextReturn) ;
		int count=1;
		//Thread.sleep(10000);
		while (count>0) {
			xml2Json.process(context);
			//Thread.sleep(10000);
			count--;
		}
        for(String key:contextReturn.keySet())
        System.out.println(contextReturn.get(key));
        
    }
    
   private String readXML() throws IOException{
	   FileReader reader=new FileReader(new File("c:\\mxml.xml"));
	   StringBuffer xmlData=new StringBuffer();
	   char data[]=new char[1024];	   
	   while(reader.read(data)!=-1){
		   xmlData.append(data);
		   data=new char[1024];
	   }	   
	return xmlData.toString();   
   }
   
	public void initialize(String scriptPath) throws Exception {
		try {
			ScriptEngineManager manager = new ScriptEngineManager() ;
			ScriptEngine scriptEngine = manager.getEngineByName("js") ;

			if (scriptEngine == null){
				throw new Exception("ScriptProcessor engine not found") ;
			}
			if (! (scriptEngine instanceof Compilable)){
				throw new Exception("ScriptProcessor engine not found") ;
			}
			Compilable compilable = (Compilable) scriptEngine ;

			InputStream input = null ;
			try {
				input = new FileInputStream(new File(scriptPath));
				if (input == null){
					throw new Exception("Script not found") ;
				}
				InputStreamReader reader = new InputStreamReader(input) ;
				compiledScript = compilable.compile(reader) ;
			} catch (ScriptException x){
				throw new Exception("Script cannot be compiled") ;
			} finally {
				try {
					if (input != null){
						input.close() ;
					}
				} catch (Exception x) { }
			}
		} catch (Exception e){
			throw e ;
		}
	}
	/**
	 * Execute the script against the context.
	 * 
	 * @param context
	 */
	public void process(HashMap<String,Object> context) throws Exception {
		try {
			SimpleBindings bindings = new SimpleBindings(context) ;
			compiledScript.eval(bindings) ;
		} catch (ScriptException e){
			throw new Exception("Runtime script exception in service "+e) ;
		}
	}  
}
