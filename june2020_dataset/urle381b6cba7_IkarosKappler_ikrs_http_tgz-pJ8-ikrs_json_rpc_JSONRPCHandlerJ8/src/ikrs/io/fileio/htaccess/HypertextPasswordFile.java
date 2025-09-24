package ikrs.io.fileio.htaccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import java.text.ParseException;
import java.text.ParsePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a simple class for checking/reading htpassword files.
 *
 * @author Ikaros Kappler
 * @date 2012-09-12
 * @version 1.0.0
 **/

public class HypertextPasswordFile {


    /**
     * This class is not meant to be instantiated directly.
     **/
    private HypertextPasswordFile() {

    }


    /**
     * Tries to load the password data for the given user.
     *
     * If the desired user cannot be found in the file this method will return null.
     *
     * @param file        The input file (must be a text file in .htpasswd format).
     * @param username    The user's name.
     * @param strictMode  If this mode is set to true the reader will NOT ignore any unknown/unexpected 
     *                    irregularities but throw a ParseException.
     * @throws IOException    If any IO errors occur.
     * @throws ParseException If the passed file cannot be parsed or is not a htpasswd file.
     **/
    public static char[] getPasswordData( File file,
					  String username,
					  boolean strictMode )
	throws IOException,
	       ParseException {

	
	FileInputStream in = new FileInputStream(file);
	char[] passData = getPasswordData( in,
					   username,
					   strictMode 
					   );
	in.close();
	return passData;
    }    


    /**
     * Tries to load the password data for the given user.
     *
     * If the desired user cannot be found in the file this method will return null.
     *
     * @param file        The input file (must be a text file in .htpasswd format).
     * @param username    The user's name.
     * @param strictMode  If this mode is set to true the reader will NOT ignore any unknown/unexpected 
     *                    irregularities but throw a ParseException.
     * @throws IOException    If any IO errors occur.
     * @throws ParseException If the passed file cannot be parsed or is not a htpasswd file.
     **/
    public static char[] getPasswordData( InputStream in,
					  String username,
					  boolean strictMode
					  )
	throws IOException,
	       ParseException {

	
	return getPasswordData( new LineNumberReader( new InputStreamReader(in) ),
				username,
				strictMode
				);
    }    


    /**
     * Tries to load the password data for the given user.
     *
     * If the desired user cannot be found in the file this method will return null.
     *
     * @param file        The input file (must be a text file in .htpasswd format).
     * @param username    The user's name.
     * @param strictMode  If this mode is set to true the reader will NOT ignore any unknown/unexpected 
     *                    irregularities but throw a ParseException.
     * @throws IOException    If any IO errors occur.
     * @throws ParseException If the passed file cannot be parsed or is not a htpasswd file.
     **/
    public static char[] getPasswordData( LineNumberReader lnr,
					  String username,
					  boolean strictMode
					  )
	throws IOException,
	       ParseException {


	
	HypertextAccessFile htaccess = new HypertextAccessFile();
	BufferedReader br = new BufferedReader( lnr );
	
	String line;
	while( (line = br.readLine()) != null ) {

	    line = line.trim();

	    // Ignore blank lines
	    if( line.length() == 0 )
		continue;

	    // Is comment?
	    if( line.startsWith("#") )
		continue;


	    // Each line hat the format:
	    // <username> ":" {<PasswordMD5> | <Password>}

	    int index = line.indexOf( ":" );

	    if( index == -1 ) {

		if( strictMode )
		    throw new ParseException( "Colon (:) missing in line " + lnr.getLineNumber() + ". Line: " + line, lnr.getLineNumber() );
		else
		    continue;  // ignore corrupt line

	    }


	    // Colon at beginning of line?
	    if( index == 0 ) {

		if( strictMode )
		    throw new ParseException( "Username missing in line " + lnr.getLineNumber() + ". Line: " + line, lnr.getLineNumber() );
		else
		    continue;  // ignore corrupt line

	    }


	    String tmpUsername = line.substring(0,index).trim();
	    
	    // Compare case sensitive???
	    if( !username.equals(tmpUsername) )
		continue; // Wrong user -> try next line


	    
	    // Colon at end of line?
	    if( index+1 >= line.length() ) {

		// Return empty password data.
		return new char[0];

	    } else {

		String passwordData = line.substring(index+1).trim();
		return passwordData.toCharArray();

	    }

	}


	// Implies: not found
	return null;
    }




    /**
     * For testing only.
     **/
    public static void main( String[] argv ) {

	try {

	    File file       = new File("document_root_alpha/secret/.htpasswd");
	    String username = "keel";
	    
	    System.out.println( "Looking up password data for user '" + username + "' ...");
	    char[] passData = HypertextPasswordFile.getPasswordData( file, username, true );

	    if( passData == null )
		System.out.println( "Not found." );
	    else
		System.out.println( "Found: " + new String(passData) );

	} catch( Exception e ) {

	    e.printStackTrace();

	}

    }



}