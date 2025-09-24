package zz.imageweb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 处理URL
 */
public abstract class ImageInterface {
	protected String htmlurl;
	protected String dirName;
	
	//创建一个可重用固定线程数的线程池

    protected ExecutorService pool = Executors.newFixedThreadPool(20);
	
	public abstract void init();
	public abstract void next();
	
	public String getHtmlURL(){
		return htmlurl;
	}
	public String getDirName(){
		return dirName;
	}
	public abstract void downImage(String savePath, String content);
}
