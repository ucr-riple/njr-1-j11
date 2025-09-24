package zz.config;

public class ZZConfig {
	
	public static String remoteIPAddress;
	
	public static String loaclFileDir;
	
	/**
	 * 百度图片标签
	 */
	public static String BAIDUTAG = "美女";		//大标签
	public static String BAIDUTAG2 = "写真";		//小标签
	
	/**
	 * 图片最小宽度，太小的直接删除
	 */
	public static int MIN_IMAGE_WIDTH = 400;
	
	public static void initConfig(){
		loaclFileDir = "I://image";
	}
}
