package Data_Storage;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Game_World.GameWorld;

/**
 * Records a GameWorld object to an .xml file. XMLWriter does not write
 * the image field of a Tileset object.
 * @author Alix Schultze 300170774 hoschurayn
 *
 */
public class XMLWriter {
	
	private BufferedOutputStream output;
	private FileOutputStream fileStream;
	private XMLEncoder encoder;

	/**
	 * Writes to a file specified by a String
	 * @param fileName	this constructor will append '.xml' to the fileName
	 * @throws FileNotFoundException 
	 * @throws IntrospectionException 
	 */
	public XMLWriter(String fileName) throws FileNotFoundException, IntrospectionException{
		// excludes Tileset's image field from being saved
		BeanInfo biTileset = Introspector.getBeanInfo(Graphics_Renderer.Tileset.class);
		PropertyDescriptor[] pds = biTileset.getPropertyDescriptors();
		for(int i = 0; i < pds.length; i++){
			PropertyDescriptor desc = pds[i];
			if(desc.getName().equals("image")){
				desc.setValue("transient", Boolean.TRUE);
			}
		}
		// '.xml' is automatically appended to the fileName
		fileStream = new FileOutputStream(fileName + ".xml");
		output = new BufferedOutputStream(fileStream);
		encoder = new XMLEncoder(output);
	}
	
	/**
	 * This XMLWriter writes to a specified File instead of taking a String
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IntrospectionException
	 */
	public XMLWriter(File file) throws FileNotFoundException, IntrospectionException{	
		// excludes Tileset's image field from being saved
		BeanInfo biTileset = Introspector.getBeanInfo(Graphics_Renderer.Tileset.class);
		PropertyDescriptor[] pds = biTileset.getPropertyDescriptors();
		for(int i = 0; i < pds.length; i++){
			PropertyDescriptor desc = pds[i];
			if(desc.getName().equals("image")){
				desc.setValue("transient", Boolean.TRUE);
			}
		}
		fileStream = new FileOutputStream(file);
		output = new BufferedOutputStream(fileStream);
		encoder = new XMLEncoder(output);
	}
	
	/**
	 * Writes the given GameWorld to the file.
	 * @param gw 	this GameWorld will be recorded as XML
	 */
	public void write(GameWorld gw){
		encoder.writeObject(gw);
	}
	
	public void write(Object o){
		encoder.writeObject(o);
	}
	
	/**
	 * Calls the XMLEncoder's close method. How redundant.
	 */
	public void close(){
		encoder.close();
	}
}
