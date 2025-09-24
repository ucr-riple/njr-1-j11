package zz;

import zz.config.ZZConfig;
import zz.spider.Spider;

public class MainModule {
	
	public static void main(String[] args) {
		//初始化
		ZZConfig.initConfig();
		
        new Thread(new Spider(ZZConfig.loaclFileDir)).start();
		
	}
}