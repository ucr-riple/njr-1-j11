package org.swiftgantt.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JComponent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.png.PNGImageWriter;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;

/**
 * Swing Component Image Generator.
 * @author Yuxing Wang
 *
 */
public class SwingComImageGenerator {
	private static SwingComImageGenerator instance = new SwingComImageGenerator();
	private Logger logger;
	
	private SwingComImageGenerator() {
		logger = LogManager.getLogger(this.getClass());
	}
	
	/**
	 * 
	 * @return
	 */
	public static SwingComImageGenerator getInstance(){
		return instance;
	}
	/**
	 * 
	 * @param component
	 * @param filePath
	 * @throws Exception
	 */
	public void genJPEGImage(JComponent component, String filePath) throws IOException{
		Rectangle bounds = component.getBounds();
		this.genJPEGImage(new Dimension(bounds.width, bounds.height), new JComponent[]{component}, new Rectangle[]{bounds}, filePath);
	}
	
	/**
	 * 
	 * @param size
	 * @param components
	 * @param areas
	 * @param filePath
	 * @throws IOException
	 */
	public void genJPEGImage(Dimension size, JComponent[] components, Rectangle[] areas, String filePath) throws IOException{
		logger.debug("Generate JPEG image file");
		if(components.length == 0 || components.length != areas.length){
			throw new RuntimeException("The specified components is not fulfil the requirement");
		}
		if(filePath == null){
			String dir = System.getProperty("user.dir") + System.getProperty("file.separator") + "image";
			filePath = dir + System.getProperty("file.separator") + "ganttchart.jpg";
		}
		checkAndCreateDirForFilePath(filePath);
		BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		Graphics imageGraphic = image.getGraphics();
		// Background is white.
		imageGraphic.setColor(Color.WHITE);
		imageGraphic.fillRect(0, 0, size.width, size.height);

		for (int i = 0; i < components.length; i++) {
			JComponent curCom = components[i];
			Rectangle bounds = areas[i];
			if (logger.isDebugEnabled()) {
				logger.debug("generateJPEGImage(Dimension, JComponent[], Rectangle[], String) - Start to paint component " + curCom.getClass().getName() + " to image:" + bounds); //$NON-NLS-1$ //$NON-NLS-2$
			}
			curCom.paint(imageGraphic);
			if(bounds.x > 0 || bounds.y > 0){
				// Copy may be not effective.
				imageGraphic.copyArea(0, 0, bounds.width, bounds.height, bounds.x, bounds.y);
			}
		}
		try{
			File imgFile = new File(filePath);
			FileOutputStream fos = new FileOutputStream(imgFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
			if (logger.isDebugEnabled()) {
				logger.debug("generateJPEGImage(Dimension, JComponent[], Rectangle[], String) - Image:" + filePath + " generated"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}catch (Exception ex) {
			logger.error("generateJPEGImage(Dimension, JComponent[], Rectangle[], String)", ex); //$NON-NLS-1$
			throw new IOException(ex.getMessage() + ex.getStackTrace());
		}
	}
	
	
	/**
	 * 
	 * @param component
	 * @param filePath
	 * @throws Exception
	 */
	public void genPNGImage(JComponent component, String filePath) throws IOException{
		Rectangle bounds = component.getBounds();
		this.genPNGImage(new Dimension(bounds.width, bounds.height), new JComponent[]{component}, new Rectangle[]{bounds}, filePath);
	}

	/**
	 * 
	 * @param size
	 * @param components
	 * @param areas
	 * @param filePath
	 * @throws IOException
	 */
	public void genPNGImage(Dimension size, JComponent[] components, Rectangle[] areas, String filePath) throws IOException{
		logger.debug("Generate JPEG image file");
		if(components.length != areas.length){
			throw new RuntimeException("The specified components is not fulfil the requirement");
		}
		if(filePath == null){
			String dir = System.getProperty("user.dir") + System.getProperty("file.separator") + "image";
			filePath = dir + System.getProperty("file.separator") + "ganttchart.jpg";
		}
		checkAndCreateDirForFilePath(filePath);
		BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		Graphics imageGraphic = image.getGraphics();
		// Background is white.
		imageGraphic.setColor(Color.WHITE);
		imageGraphic.fillRect(0, 0, size.width, size.height);

		for (int i = 0; i < components.length; i++) {
			JComponent curCom = components[i];
			Rectangle bounds = areas[i];
			if (logger.isDebugEnabled()) {
				logger.debug("Start to paint component " + curCom.getClass().getName() + " to image:" + bounds); //$NON-NLS-1$ //$NON-NLS-2$
			}
			curCom.paint(imageGraphic);
			if(bounds.x > 0 || bounds.y > 0){
				// Copy may be not effective.
				imageGraphic.copyArea(0, 0, bounds.width, bounds.height, bounds.x, bounds.y);
			}
		}
		try{
			File imgFile = new File(filePath);
			ImageOutputStream ios = new FileImageOutputStream(imgFile);
			ImageWriterSpi iwspi = new PNGImageWriterSpi();
			PNGImageWriter pngImageWriter = new PNGImageWriter(iwspi);
			pngImageWriter.setOutput(ios);
			pngImageWriter.write(image);
			if (logger.isDebugEnabled()) {
				logger.debug("Image:" + filePath + " generated"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			ios.close();
		}catch (Exception ex) {
			logger.error("generatePNGImage(Dimension, JComponent[], Rectangle[], String)", ex); //$NON-NLS-1$
			throw new IOException(ex.getMessage() + ex.getStackTrace());
		}
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public static void checkAndCreateDirForFilePath(String filePath){
		File file = new File(filePath);
		File dir = file.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	public static void main(String[] args){
		checkAndCreateDirForFilePath("e:\\temp\\image\\hello.txt");	
	}
}

