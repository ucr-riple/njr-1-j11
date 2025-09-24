package mLibrary;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import br.com.innovatium.mumps2java.todo.TODO;

public class mPage extends mClass {

	public static Object EscapeURL(Object url){		
		try {
			return URLEncoder.encode(String.valueOf(url),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
		}
	}
	@TODO
	public static String Encrypt(String string){
		return string;//TODO REVISAR
	}
	@TODO
	public static String Decrypt(String string){
		return string;//TODO REVISAR
	}
	@TODO
	public static String HyperEventCall(String method , String args, Integer type){
		return "\"\"\"cspHttpServerMethod(\"\"\"\""+method+"\"\"\"\","+args+")\"\"\"";
	}

	public static Object UnescapeURL(Object url) {
		try {
			return URLDecoder.decode(String.valueOf(url),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
		}
	}

	public static Object QuoteJS(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}

