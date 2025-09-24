package ikrs.io.fileio.inifile;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Comparator;

import ikrs.typesystem.BasicStringType;
import ikrs.typesystem.BasicType;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.DefaultEnvironmentFactory;
import ikrs.util.Environment;
import ikrs.util.EnvironmentFactory;
import ikrs.util.KeyValuePair;
import ikrs.util.TreeMapFactory;

/**
 * Ths IniFile has basic input/ouput method for reading and writing ini files.
 *
 * I needed this urgently so its functionality is very reduced. A future version should be configurable:
 *  - section format
 *  - key-value separator format
 *  - comment format
 *
 *
 * @author Ikaros Kappler
 * @date 2012-12-12
 * @version 1.0.0
 **/


public class IniFile {

    /**
     * The IniFile's internal environment factory.
     **/
    private EnvironmentFactory<String,BasicType> environmentFactory;


    /**
     * Create a new default IniFile.
     * The default implementation uses a case-insensitive key comparator and a default environment factory
     * with a TreeMapFactory inside.
     *
     **/
    public IniFile() {
	this( CaseInsensitiveComparator.sharedInstance );
    }

    /**
     * Create a new IniFile with the given key comparator (for section names and key names both).
     *
     * @param keyComparator The key name (and section name) comparator; must not be null.
     * @throws NullPointerException If keyComparator is null.
     **/
    public IniFile( Comparator<String> keyComparator ) 
	throws NullPointerException {

	this( new DefaultEnvironmentFactory<String,BasicType>( new TreeMapFactory<String,BasicType>( keyComparator ) ) );	
    }

    /**
     * Create a new IniFile with the given environment factory.
     *
     * @param environmentFactory The environment factory to use (must not be null).
     * @throws NullPointerException If environmentFactory is null.
     **/
    public IniFile( EnvironmentFactory<String,BasicType> environmentFactory ) 
	throws NullPointerException {

	if( environmentFactory == null )
	    throw new NullPointerException( "Cannot create a IniFile with null-environmentFactory." );

	this.environmentFactory = environmentFactory;
    }
    

    public Environment<String,BasicType> read( File file ) 
	throws IOException {

	
	Environment<String,BasicType> mainSection = this.environmentFactory.create();
	read( file, mainSection );
	return mainSection;

    }

    public void read( File file,
		      Environment<String,BasicType> mainSection ) 
	throws IOException {

	
	FileInputStream fin = new FileInputStream( file );
	read( new BufferedReader( new InputStreamReader(fin) ),
	      mainSection );	
	fin.close();
    }

    public void read( BufferedReader reader,
		      Environment<String,BasicType> mainSection ) 
	throws IOException {


	Environment<String,BasicType> currentEnvironment = mainSection;
	
	String line;
	while( (line = reader.readLine()) != null ) {

	    line = line.trim();

	    // Skip empty lines
	    if( line.length() == 0 )
		continue;

	    // Skip comments
	    if( line.startsWith("#") || line.startsWith(";") )
		continue;

	    // Case A:
	    if( line.startsWith("[") && line.endsWith("]") ) {

		// Make a new section	
		String sectionName = line.substring( 1, line.length()-1 );
		currentEnvironment = mainSection.createChild( sectionName );

	    } else {

		// Parse a key-value-pair
		KeyValuePair<String,String> pair = KeyValuePair.splitLine( line, 
									   "=",   // separator
									   true   // trim
									   );
		currentEnvironment.put( pair.getKey(),
					new BasicStringType(pair.getValue()) );

	    }

	}
	 
    }


    /**
     * This is for testing purposes only.
     **/
    public static void main( String[] argv ) {

	if( argv.length == 0 ) {

	    System.err.println( "Pass a test ini file, please." );
	    System.exit( 1 );

	}
	    

	try {

	    IniFile iniFile = new IniFile();

	    Environment<String,BasicType> ini = iniFile.read( new File(argv[0]) );
	    System.out.println( "Ini file read: " + ini.toString() );

	} catch( Exception e ) {

	    e.printStackTrace();

	}

    }

}