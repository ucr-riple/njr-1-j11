package minidraw.standard;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/** ImageManager is a singleton class that acts as a centralized
    database of all images to be used in a MiniDraw application. It
    takes care of automatically loading images to be used in the
    JHotDraw framework and builds a Map that maps from image names
    (without extension) to image instances. It assumes the existence
    of a file "preload.lst" located in a directory named "resource" in
    the root of the classpath. This text file must contain a list of
    filenames identifying images files to be loaded. The filenames
    must define the GIF files without extension; one line per file.

    <p>
    Example: if the directory contains
    <pre>
    resource/board.gif
    resource/whitestone.gif
    resource/blackstone.gif
    </pre>
    then the preload.lst file must contain
    <pre>
    board
    whitestone
    blackstone
    </pre>

    <p>
    This class is to be used instead of the IconKit class in the
    standard JHD framework.
    <p>
    As the ImageManager is a singleton class you access it using 
    <tt>ImageManager.getSingleton()</tt>. The MinimalApplication
    creates the ImageManager automatically.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
*/
    
public class ImageManager {

  private static final String RESOURCE_PATH      = "/resource/";
  private static Component aComponent;

  private Map<String,Image> name2Image;
  
  public static ImageManager singleton;

  ImageManager(Component c) {
    aComponent = c;
    registerAllImages();
    singleton = this;
  }

  public static ImageManager getSingleton() {
    if ( singleton == null ) {
      throw new RuntimeException("getSingleton() invoked before ImageManger "
                                 + "has been instantiated." );
    }
    return singleton;
  }

  /** load all gif files in resource folder into the image manager. */
  private void registerAllImages() {
    name2Image = new Hashtable<String,Image>();

    MediaTracker tracker = new MediaTracker(aComponent);

    try {
      java.net.URL _url = getClass().getResource(RESOURCE_PATH);
      if ( _url == null ) {
        throw new RuntimeException("ImageManager: URL/folder '"+
                                   RESOURCE_PATH+
                                   "' does not exist.");
      }
      // Convert URL to a File (from Kohsuke Kawaguchi's Blog)
      File dir;
      try {
        dir = new File(_url.toURI());
      } catch(URISyntaxException e) {
        dir = new File(_url.getPath());
      }
      // create a filter to find .GIF files.
      FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".gif") || name.endsWith(".GIF");
        }
      };
      // Retrieve array of gif file names in resource folder
      String[] gifFileArray = dir.list(filter);
      for ( String s : gifFileArray ) {
        // Load and register the image
        Image img = _registerIn(s);
        if ( img == null ) {
          throw new 
            RuntimeException("ImageManager: Did not find image named "+
                             s );
        }
        tracker.addImage( img, 7 /* not used so why not 7 */ );
        String filenameNoExtension = s.substring(0, s.length()-4);
        name2Image.put( filenameNoExtension, img );        
      }
      // block until all images are loaded
      try {
        tracker.waitForAll();
      } catch (Exception e) {}
    } catch (Exception e) {
      throw new RuntimeException( "Exception caught "+e);
    }
  }

  public Image getImage(String shortName) {
    Image img = (Image) name2Image.get(shortName);
    if ( img == null ) { 
      throw new RuntimeException("ImageManager: No image named "+
                                 shortName+ " has been found in "+
                                 RESOURCE_PATH+ " folder." );
    }
    return img;
  }

  private Image _registerIn(String gif) {
    String name;
    Image img;

    name = RESOURCE_PATH+gif;

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    try {
      java.net.URL url = getClass().getResource(name);
      img = toolkit.createImage((java.awt.image.ImageProducer) 
                                url.getContent());
    } catch (Exception ex) {
      return null;
    }
    return img;
  }

}
