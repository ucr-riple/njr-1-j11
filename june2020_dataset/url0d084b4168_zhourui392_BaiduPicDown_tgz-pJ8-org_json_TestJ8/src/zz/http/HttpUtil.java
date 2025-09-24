package zz.http;

import java.io.InputStream;

import org.nutz.http.Http;
import org.nutz.http.Response;

/**
 * 获取到请求链接的返回
 */
public class HttpUtil {
	/**
	 * 获取到请求链接的返回
	 */
	public static String getStr(String url){
		String resultStr = null;
		Response resp = Http.get(url);
    	if (resp.isOK()) {
    		resultStr = resp.getContent();
    	}
		return resultStr;
	}
	
	public static InputStream getStream(String url){
		InputStream resultIS = null;
		Response resp = Http.get(url);
    	if (resp.isOK()) {
    		resultIS = resp.getStream();
    	}
		return resultIS;
	}
	
	public static void main(String[] args) {
		String url = "http://image.baidu.com/channel?c=%E7%BE%8E%E5%A5%B3&t=%E8%AF%B1%E6%83%91&s=0";
		System.out.println(getStr(url));
	}
}
