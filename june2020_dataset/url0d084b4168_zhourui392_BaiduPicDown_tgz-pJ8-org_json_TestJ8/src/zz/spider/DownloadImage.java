package zz.spider;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.nutz.img.Images;

import zz.config.ZZConfig;
import zz.http.HttpUtil;

public class DownloadImage implements Runnable {
    private String imageSrc;
    private String imageName;
     
    private String mSavePath;
     
    public DownloadImage(String imageSrc, String savePath) {
        this.imageSrc = imageSrc;
        mSavePath = savePath;
    }
     
    private boolean isImageExists() {
        File dir = new File(mSavePath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
         
        String[] splits = imageSrc.split("/");
        imageName = splits[splits.length - 1];
         
        return new File(dir + File.separator + imageName).exists();
    }
     
	@Override
    public void run() {
        if(isImageExists()) {
            return ;
        }
         
        InputStream in = null;
        FileOutputStream fos = null;
        File imageFile = new File(mSavePath + File.separator + imageName);
        
        System.out.println("开始下载美女：" + imageName);
        
        try {
            in = HttpUtil.getStream(imageSrc);
            if(null != in) {
                
                byte[] by = new byte[1024];
                int len = -1;
                fos = new FileOutputStream(imageFile);
                while(-1 != (len = in.read(by))) {
                    fos.write(by, 0, len);
                }
                fos.flush();
            }
            System.out.println( "图片" + imageName + "下载完成");
        }catch(Exception e) {
            System.err.println("正在删除图片" + imageName);
            new File(mSavePath + File.separator + imageName).delete();
            return;
        }finally{
        	 try {
             	if (in != null){
             		 in.close();
             	}
             	if (fos != null){
             		fos.close();
             	}
             } catch (Exception e1) {
            	 
             }
        }
        BufferedImage bfImage = Images.read(imageFile);
        if (bfImage.getWidth() < ZZConfig.MIN_IMAGE_WIDTH){
        	System.out.println("正在删除不合规的图片"+imageName);
        	imageFile.delete();
        }
        
    }
}
