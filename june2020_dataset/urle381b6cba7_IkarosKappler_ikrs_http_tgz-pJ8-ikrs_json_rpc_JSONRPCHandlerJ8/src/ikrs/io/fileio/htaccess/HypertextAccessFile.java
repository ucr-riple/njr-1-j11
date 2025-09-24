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


import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.FileExtensionKeyMap;

/**
 * This is a simple class for reading htaccess files.
 *
 * @author Ikaros Kappler
 * @date 2012-09-12
 * @modified 2013-01-03 [merge method added].
 * @modified 2013-01-15 [ErrorDocument map added].
 * @version 1.0.0
 **/


public class HypertextAccessFile {

    /**
     * The source file itself.
     **/
    private File sourceFile;

    /**
     * This field stores the (single!) value from the "AuthType" directive.
     **/
    private String authType;

    /**
     * This field stores the (single!) value from the "AuthName" directive.
     **/
    private String authName;

    /**
     * This field stores the (single!) value from the "AuthUserFile" directive.
     **/
    private File authUserFile;

    /**
     * This field stores the (single!) value from the "AuthGroupFile" directive.
     **/
    private File authGroupFile;

    /**
     * This list contains the entries from the "Require Group" directive.
     **/
    private List<String> requiredGroups;

    /**
     * This list contains the entries from the "Require User" directive.
     **/
    private List<String> requiredUsers;
   
    /**
     * This flag will be set if the file contains the "Require valid-user" directive.
     **/
    private boolean requiresValidUser;

    /**
     * This map contains the entries from all "AddType" directives.
     **/
    private Map<String,String> typeMap;

    /**
     * This map contains the entries from all "Option" directives.
     **/
    private Map<String,Boolean> optionsMap;

    /**
     * This list contains all handler names that were set using the "SetHandler" directive.
     **/
    private List<String> setHandlers;

    /**
     * This list contains all handler names that were set using the "AddHandler" directive.
     **/
    private Map<String,List<String>> addedHandlers;

    /**
     * This field contains the valud for the 'AddDefaultCharset' directive.
     **/
    private String defaultCharset;

    /**
     * This list contains all added charsets from the 'AddCharset' directives.
     **/
    private Map<String,String> addedCharsets;

    /**
     * This list contains all directory index files for the 'DirectoryIndex' directives.
     **/
    private List<String> directoryIndexList;


    private Map<Integer,String> errorDocumentMap;

    
    /**
     * This class cannot be instantiated directly; it's only meant for reading files.
     *
     * Use the read() method instead.
     **/
    protected HypertextAccessFile() {
	super();


	this.typeMap             = new FileExtensionKeyMap<String>( false,  // Not strict
							       false   // Not case sensitive
							       );
	this.optionsMap          = new TreeMap<String,Boolean>( CaseInsensitiveComparator.sharedInstance );
	this.requiredGroups      = new ArrayList<String>(1);
	this.requiredUsers       = new ArrayList<String>(1);
	this.setHandlers         = new ArrayList<String>(1);
	this.addedHandlers       = new TreeMap<String,List<String>>( CaseInsensitiveComparator.sharedInstance );
	this.addedCharsets       = new TreeMap<String,String>( CaseInsensitiveComparator.sharedInstance );
	this.directoryIndexList  = new ArrayList<String>(1);
	this.errorDocumentMap    = new TreeMap<Integer,String>();
    }


    /**
     * Reads the htaccess configuration from the given file.
     *
     * @param file        The input file (must be a text file in .htaccess format).
     * @param strictMode  If this mode is set to true the reader will NOT ignore any unknown/unexpected 
     *                    irregularities but throw a ParseException. There are even some un-handled keywords
     *                    which still lack some implementation (set to false if you want to ignore them).
     * @throws IOException    If any IO errors occur.
     * @throws ParseException If the passed file cannot be parsed or is not a htaccess file.
     **/
    public static HypertextAccessFile read( File file,
					    boolean strictMode )
	throws IOException,
	       ParseException {

	
	FileInputStream in = new FileInputStream(file);
	HypertextAccessFile htaccess = read( in,
					     strictMode 
					     );
	htaccess.sourceFile = file;
	in.close();
	return htaccess;
    }    


    /**
     * Reads the htaccess configuration from the given inputstream.
     *
     * @param in The input stream (must deliver a text file in .htaccess format).
     * @param strictMode  If this mode is set to true the reader will NOT ignore any unknown/unexpected 
     *                    irregularities but throw a ParseException. There are even some un-handled keywords
     *                    which still lack some implementation (set to false if you want to ignore them).
     * @throws IOException    If any IO errors occur.
     * @throws ParseException If the passed data cannot be parsed or is not a htaccess file.
     **/
    public static HypertextAccessFile read( InputStream in,
					    boolean strictMode
					    )
	throws IOException,
	       ParseException {

	
	return read( new LineNumberReader( new InputStreamReader(in) ),
		     strictMode
		     );
    }    

    /**
     * Reads the htaccess configuration from the given line number reader.
     *
     * @param lnr The line number reader to read the data from (must deliver a text file in .htaccess format).
     * @param strictMode  If this mode is set to true the reader will NOT ignore any unknown/unexpected 
     *                    irregularities but throw a ParseException. There are even some un-handled keywords
     *                    which still lack some implementation (set to false if you want to ignore them).
     * @throws IOException    If any IO errors occur.
     * @throws ParseException If the passed data cannot be parsed or is not a htaccess file.
     **/
    public static HypertextAccessFile read( LineNumberReader lnr,
					    boolean strictMode
					    )
	throws IOException,
	       ParseException {


	
	HypertextAccessFile htaccess = new HypertextAccessFile();
	BufferedReader br = new BufferedReader( lnr );
	
	String line;
	while( (line = br.readLine()) != null ) {

	    line = line.trim();

	    // Ignore empty lines
	    if( line.length() == 0 )
		continue;

	    // Ignore comments
	    if( line.startsWith("#") )
		continue;


	    // Split line at white space sections
	    String[] split = splitLine( line ); // line.split( "(\\s)+" );

	    // There must be AT LEAST one split (the line was not empty)
	    String keyword = split[0];
	    if( keyword.equalsIgnoreCase("AddType") ) {

		htaccess.parse_addType( line, keyword, split, lnr.getLineNumber(), strictMode );		

	    } else if( keyword.equalsIgnoreCase("Options") ) {

		htaccess.parse_options( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("AddHandler") ) {

		htaccess.parse_addHandler( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("SetHandler") ) {

		htaccess.parse_setHandler( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("AuthType") ) {

		htaccess.parse_authType( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("AuthName") ) {

		htaccess.parse_authName( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("AuthUserFile") ) {

		htaccess.parse_authUserFile( line, keyword, split, lnr.getLineNumber(), strictMode );		
	    
	    } else if( keyword.equalsIgnoreCase("AuthGroupFile") ) {

		htaccess.parse_authGroupFile( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("Require") ) {

		htaccess.parse_require( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("AddDefaultCharset") ) {

		htaccess.parse_addDefaultCharset( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("AddCharset") ) {

		htaccess.parse_addCharset( line, keyword, split, lnr.getLineNumber(), strictMode );

	    } else if( keyword.equalsIgnoreCase("DirectoryIndex") ) {

		htaccess.parse_directoryIndex( line, keyword, split, lnr.getLineNumber(), strictMode );	

	    } else if( keyword.equalsIgnoreCase("ErrorDocument") ) {

		htaccess.parse_errorDocument( line, keyword, split, lnr.getLineNumber(), strictMode );		
	    
	    } else if( strictMode ) {

		
		throw new ParseException( "Unknown keyword '"+keyword+"' at line " + lnr.getLineNumber() + ". Line=" + line,
					  lnr.getLineNumber() );

	    }

	}


	return htaccess;
    }


    /**
     * This method returns the source file the htaccess configuration was read from.
     *
     * @return The source file the htaccess configuration was read from.
     **/
    public File getSourceFile() {
	return this.getSourceFile();
    }


    /**
     * This method returns the value from the "AuthType" directive or null if not set.
     *
     * @return The value from the "AuthType" directive or null if not set.
     **/
    public String getAuthType() {
	return this.authType;
    }

    /**
     * This method returns the value from the "AuthName" directive or null if not set.
     *
     * @return The value from the "Authname" directive or null if not set.
     **/
    public String getAuthName() {
	return this.authName;
    }

    /**
     * This method returns the file that was set by the "AuthUserFile" directive (or null if not set).
     *
     * @return The file that was set by the "AuthUserFile" directive (or null if not set).
     **/
    public File getAuthUserFile() {
	return this.authUserFile;
    }

    /**
     * This method returns the file that was set by the "AuthGroupFile" directive (or null if not set).
     *
     * @return The file that was set by the "AuthGroupFile" directive (or null if not set).
     **/
    public File getAuthGroupFile() {
	return this.authGroupFile;
    }

    /**
     * This method returns the file that was set by the "Require Group" directive(s).
     *
     * @return The file that was set by the "Require Group" directive(s). The returned List is never null.
     **/
    public List<String> getRequiredGroups() {
	return this.requiredGroups;
    }

    /**
     * This method returns the file that was set by the "Require User" directive(s).
     *
     * @return The file that was set by the "Require User" directive(s). The returned List is never null.
     **/
    public List<String> getRequiredUsers() {
	return this.requiredUsers;
    }

    /**
     * This method returns the flag that is set by the "Require valid-user" directive.
     *
     * @return The flag that is set by the "Require valid-user" directive.
     **/
    public boolean requiresValidUser() {
	return this.requiresValidUser;
    }

    /**
     * This method returns the file that was set by the "AddType" directive(s). 
     * The mapping is: Extension -> MIME type.
     *
     * @return The file that was set by the "AddType" directive(s). The returned Map is never null.
     **/
    public Map<String,String> getAddedTypes() {
	return this.typeMap;
    }

    /**
     * This method returns the file that was set by the "Option" directive(s). 
     * The mapping is: OptionName -> true|false
     *
     * @return The file that was set by the "Option" directive(s). The returned Map is never null.
     **/
    public Map<String,Boolean> getOptions() {
	return this.optionsMap;
    }

    /**
     * This method returns the name that was set by the "SetHandler" directive(s). If there were
     * more than one 'SetHandeler' directives (only allowed in non-strict mode) the last name is returned.
     *
     * @return The file that was set by the "SetHandler" directive(s) or null if not available.
     **/
    public String getSetHandler() {
	if( this.setHandlers.size() == 0 )
	    return null; 
	else
	    return this.setHandlers.get( this.setHandlers.size()-1 );
    }

    /**
     * This method returns the file that was set by the "AddHandler" directive(s). 
     * The mapping is: HandlerName -> ExtensionList
     *
     * @return The file that was set by the "AddHandler" directive(s). The returned List is never null.
     **/
    public Map<String,List<String>> getAddedHandlers() {
	return this.addedHandlers;
    }

    /**
     * This method returns the charset that was set by the "AddDefaultCharset" directive (or null if not set).
     *
     * @return The charset that was set by the "AddDefaultCharset" directive (or null if not set).
     **/
    public String getDefaultCharset() {
	return this.defaultCharset;
    }

    /**
     * This method returns the file that was set by the "AddCharset" directive(s). 
     * The mapping is: File-Extension -> Charset
     *
     * @return The file that was set by the "AddCharset" directive(s). The returned List is never null.
     **/
    public Map<String,String> getAddedCharsets() {
	return this.addedCharsets;
    }


    /**
     * This method returns the file names that were set by the "DirectoryUndex" directive(s). 
     * 
     * @return The file names that were set by the "DirectoryUndex" directive(s). 
     **/
    public List<String> getDirectoryIndexList() {
	return this.directoryIndexList;
    }

    
    /**
     * This method returns the status code/error document map defined by all "ErrorDocument" directive(s). 
     * 
     * @return The status code/error document map defined by all "ErrorDocument" directive(s).
     **/
    public Map<Integer,String> getErrorDocumentMap() {
	return this.errorDocumentMap;
    }
    

    /**
     * This method merges all non-empty settings from the passed hypertext access file.
     *
     * The settings will be copied from 'mergeFrom' into this object. Existing settings will
     * be overwritten.
     *
     * @param mergeFrom The hypertext access file containing the settings to be merge into
     *                  this (must not be null).
     * @throws NullPointerException If mergeFrom param is null.
     **/
    public void merge( HypertextAccessFile mergeFrom ) 
	throws NullPointerException {

	if( mergeFrom == null )
	    throw new NullPointerException( "Cannot merge null htaccess files." );

	
	if( mergeFrom.getAuthType() != null )
	    this.authType = mergeFrom.getAuthType();

	if( mergeFrom.getAuthName() != null )
	    this.authName = mergeFrom.getAuthName();

	if( mergeFrom.getAuthUserFile() != null )
	    this.authUserFile = mergeFrom.getAuthUserFile();
	
	if( mergeFrom.getAuthGroupFile() != null )
	    this.authGroupFile = mergeFrom.getAuthGroupFile();
	
	if( mergeFrom.getRequiredGroups() != null )
	    this.requiredGroups.addAll( mergeFrom.getRequiredGroups() );

	if( mergeFrom.getRequiredUsers() != null )
	    this.requiredUsers.addAll( mergeFrom.getRequiredUsers() );

	// Note: 'Require Valid-User' cannot be unset directly via htaccess.
	this.requiresValidUser = ( this.requiresValidUser | mergeFrom.requiresValidUser() );

	if( mergeFrom.getAddedTypes() != null )
	    this.typeMap.putAll( mergeFrom.getAddedTypes() );

	if( mergeFrom.getOptions() != null )
	    this.optionsMap.putAll( mergeFrom.getOptions() );

	if( mergeFrom.getSetHandler() != null )
	    this.setHandlers.add( mergeFrom.getSetHandler() ); // only the latest entry has an effect
	
	if( mergeFrom.getAddedHandlers() != null )
	    this.addedHandlers.putAll( mergeFrom.getAddedHandlers() );

	if( mergeFrom.getDefaultCharset() != null )
	    this.defaultCharset = mergeFrom.getDefaultCharset();

	if( mergeFrom.getAddedCharsets() != null )
	    this.addedCharsets.putAll( mergeFrom.getAddedCharsets() );

	if( mergeFrom.getDirectoryIndexList() != null )
	    this.directoryIndexList.addAll( mergeFrom.getDirectoryIndexList() );

	if( mergeFrom.getErrorDocumentMap() != null )
	    this.errorDocumentMap.putAll( mergeFrom.getErrorDocumentMap() );

    }



    /**
     * This method parses the "AddType" directive.
     **/
    private void parse_addType( String line,
				String keyword,
				String[] split,
				int lineNumber,
				boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "AddType" <MIMEType> <FileExtension>
	if( split.length != 3 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 2 arguments, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	// Type mapping already exists?
	if( strictMode && this.typeMap.get(split[2]) != null ) {

	    throw new ParseException( "Unexpected '"+keyword+"' collision " + lineNumber + ". Duplicate directives. Line=" + line,
				      lineNumber );
	    
	}

	this.typeMap.put( split[2],    // The file extension
			  split[1]     // The MIME type
			  );

    }

    /**
     * This method parses the "Options" directive.
     **/
    private void parse_options( String line,
				String keyword,
				String[] split,
				int lineNumber,
				boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "Options" {+|-}<ModuleName>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	Boolean optionMode;
	if( split[1].startsWith("+") ) {

	    optionMode = new Boolean(true);

	} else if( split[1].startsWith("-") ) {

	    optionMode = new Boolean(false);

	} else {

	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". '+' or '-' expected, found: '" + split[1].charAt(0) + "'. Line=" + line,
				      lineNumber );
	}

		
	if( split[1].length() <= 1 ) {
		    
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Module name missing. Line=" + line,
				     lineNumber );

	}

	String moduleName = split[1].substring(1);

	// Type mapping already exists?
	if( strictMode && this.optionsMap.get(moduleName) != null ) {

	    throw new ParseException( "Unexpected '"+keyword+"' collision " + lineNumber + ". Duplicate directives. Line=" + line,
				      lineNumber );
	    
	}


	this.optionsMap.put( moduleName,   // The module name
			     optionMode               // + or - (true or false)
			     );

    }



    /**
     * This method parses the "AddHandler" directive.
     **/
    private void parse_addHandler( String line,
				   String keyword,
				   String[] split,
				   int lineNumber,
				   boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "AddHandler" <HandlerName> [<Extension0> [<Extension1> [<Extension2> [...]]]]
	if( split.length < 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected at least 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	String handlerName = split[1];
	List<String> boundExtensions = this.addedHandlers.get( handlerName );
	// Handler already bound?
	if( boundExtensions == null ) {

	    boundExtensions = new ArrayList<String>(1);
	    this.addedHandlers.put( split[1], boundExtensions );

	}


	addToList( boundExtensions, split, 2 );
    }


    /**
     * This method parses the "SetHandler" directive.
     **/
    private void parse_setHandler( String line,
				   String keyword,
				   String[] split,
				   int lineNumber,
				   boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "SetHandler" <HandlerName>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	this.setHandlers.add( split[1] );
    }

    
    /**
     * This method parses the "AuthType" directive.
     **/
    private void parse_authType( String line,
				 String keyword,
				 String[] split,
				 int lineNumber,
				 boolean strictMode ) 
	throws ParseException {
	
	// Line has the format:
	// "AuthType" <Type>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	// Already set previously?
	if( strictMode && this.authType != null ) {
	    throw new ParseException( "Unexpected '"+keyword+"' declaration at line " + lineNumber + ". Duplicate entries. Line=" + line,
				      lineNumber );
	}

	this.authType = split[1];

    }


    /**
     * This method parses the "AuthName" directive.
     **/
    private void parse_authName( String line,
				 String keyword,
				 String[] split,
				 int lineNumber,
				 boolean strictMode ) 
	throws ParseException {
	
	// Line has the format:
	// "AuthName" <Name>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	// Already set previously?
	if( strictMode && this.authName != null ) {
	    throw new ParseException( "Unexpected '"+keyword+"' declaration at line " + lineNumber + ". Duplicate entries. Line=" + line,
				      lineNumber );
	}

	this.authName = split[1];

    }


    /**
     * This method parses the "AuthUserFile" directive.
     **/
    private void parse_authUserFile( String line,
				     String keyword,
				     String[] split,
				     int lineNumber,
				     boolean strictMode ) 
	throws ParseException {
	
	// Line has the format:
	// "AuthUserFile" <AbsoluteFilePath>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	// Already set previously?
	if( strictMode && this.authUserFile != null ) {
	    throw new ParseException( "Unexpected '"+keyword+"' declaration at line " + lineNumber + ". Duplicate entries. Line=" + line,
				      lineNumber );
	}

	// OK. We do NOT know how many white spaces hat been between the single path tokens.
	//  -> extract path from full line!

	int index = line.indexOf(" ");

	this.authUserFile = new File( line.substring(index+1).trim() );
    }


    /**
     * This method parses the "AuthGroupFile" directive.
     **/
    private void parse_authGroupFile( String line,
				      String keyword,
				      String[] split,
				      int lineNumber,
				      boolean strictMode ) 
	throws ParseException {
	
	// Line has the format:
	// "AuthGroupFile" <AbsoluteFilePath>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	// Already set previously?
	if( strictMode && this.authGroupFile != null ) {
	    throw new ParseException( "Unexpected '"+keyword+"' declaration at line " + lineNumber + ". Duplicate entries. Line=" + line,
				      lineNumber );
	}

	// OK. We do NOT know how many white spaces hat been between the single path tokens.
	//  -> extract path from full line!

	int index = line.indexOf(" ");

	this.authGroupFile = new File( line.substring(index+1).trim() ); 
    }


    /**
     * This method parses the "Require" directive.
     **/
    private void parse_require( String line,
				String keyword,
				String[] split,
				int lineNumber,
				boolean strictMode ) 
	throws ParseException {
	
	// Line has the format:
	// "Require" {"Group"|"User"} [UserList|GroupList]
	if( split.length < 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected at least 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}
	
	String affects = split[1];

	if( affects.equalsIgnoreCase("User") ) {

	    addToList( this.requiredUsers, split, 2 );

	} else if( affects.equalsIgnoreCase("Group") ) {

	    addToList( this.requiredGroups, split, 2 );

	} else if( affects.equalsIgnoreCase("valid-user") ) {

	    this.requiresValidUser = true;

	} else {

	    throw new ParseException( "Unknown '"+keyword+"' declaration argument at line " + lineNumber + ": " + affects + " (expected 'Group' or 'User'). Line=" + line,
				      lineNumber );

	}
    }


    /**
     * This method parses the "AddDefaultCharset" directive.
     **/
    private void parse_addDefaultCharset( String line,
					  String keyword,
					  String[] split,
					  int lineNumber,
					  boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "SetHandler" <HandlerName>
	if( split.length != 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	if( split[1].equalsIgnoreCase("On") ) {

	    // See: http://httpd.apache.org/docs/2.2/de/mod/core.html
	    // this.defaultCharset = "iso-8859-1";
	    this.defaultCharset = java.nio.charset.StandardCharsets.ISO_8859_1.name();

	} else if( split[1].equalsIgnoreCase("Off") ) {

	    // Default setting! :)
	    this.defaultCharset = null;

	} else {
	    
	    this.defaultCharset = split[1];

	}
	
    }


    /**
     * This method parses the "AddCharset" directive.
     **/
    private void parse_addCharset( String line,
				   String keyword,
				   String[] split,
				   int lineNumber,
				   boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "AddCharset" <Charset> [<Extension0> [<Extension1> [<Extension2> [...]]]]
	if( split.length < 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected at least 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	String charset = split[1];
	


	addToMap( this.addedCharsets, 
		  split,    // keys
		  charset,  // value
		  2 );
    }


    /**
     * This method parses the "DirectoryIndex" directive.
     **/
    private void parse_directoryIndex( String line,
				       String keyword,
				       String[] split,
				       int lineNumber,
				       boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "DirectoryIndex" [<Filename0> [<Filename1> [<Filename2> [...]]]]
	if( split.length < 2 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected at least 1 argument, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	String handlerName = split[1];

	addToList( this.directoryIndexList, split, 1 );
    }


    /**
     * This method parses the "ErrorDocument" directive.
     **/
    private void parse_errorDocument( String line,
				      String keyword,
				      String[] split,
				      int lineNumber,
				      boolean strictMode ) 
	throws ParseException {

	// Line has the format:
	// "ErrorDocument" <StatusCode> <ErrorDocumentURI>
	if( split.length < 3 ) {
	    throw new ParseException( "Invalid '"+keyword+"' declaration at line " + lineNumber + ". Expected at least 3 arguments, found " + (split.length-1) + ". Line=" + line,
				      lineNumber );
	}

	String str_statusCode   = split[1];
	String errorDocumentURI = split[2];
	try {

	    this.errorDocumentMap.put( Integer.parseInt( str_statusCode ),
				       errorDocumentURI );

	} catch( NumberFormatException e ) {

	    if( strictMode ) {
		throw new ParseException( "Invalid status code '" + str_statusCode + "' for '" + keyword + "' directive. Line=" + line,
					  lineNumber );
	    }
	    
	}
    }

    private void addToList( List<String> list,
			    String[] items,
			    int startIndex ) {
	
	for( int i = startIndex; i < items.length; i++ ) {

	    list.add( items[i] );

	}
	
    }

    private void addToMap( Map<String,String> map,
			   String[] keys,
			   String value,
			   int startIndex ) {
	
	for( int i = startIndex; i < keys.length; i++ ) {

	    map.put( keys[i], value );

	}
	
    }


    private static String[] splitLine( String line ) {

	List<String> matchList = new ArrayList<String>();

	// This regex works but includes the quotes in the final result.
	Pattern regex = Pattern.compile( "[^\\s\"']+|\"[^\"]*\"|'[^']*'" );

	// This regex removes the quotes (does it really??)
	// Pattern regex = Pattern.compile( "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'" );

	Matcher regexMatcher = regex.matcher( line );
	while (regexMatcher.find()) {

	    String token = regexMatcher.group();
	    // Remove quotes?	    
	    if( token.length() >= 2 
		&& ( ( token.startsWith("\"") && token.endsWith("\"") )
		     ||
		     ( token.startsWith("'") && token.endsWith("'") ) 
		     ) ) {

		token = token.substring( 1, token.length()-1 );
	    }
	    

	    matchList.add( token );
	} 
	
	return matchList.toArray( new String[matchList.size()] );
    }


    public String toString() {
	return toString( new StringBuffer() ).toString();
    }

    public StringBuffer toString( StringBuffer b ) {

	b.append( getClass().getName() ).append( "=[" ).
	    append( " authType=" ).append( this.authType ).
	    append( ", authName=" ).append( this.authName ).
	    append( ", authUserFile=" ).append( this.authUserFile ).
	    append( ", authGroupFile=" ).append( this.authGroupFile ).
	    append( ", requiredGroups=" ).append( this.requiredGroups ).
	    append( ", requiredUsers=" ).append( this.requiredUsers ).
	    append( ", requirsValidUser=" ).append( this.requiresValidUser ).
	    append( ", typeMap=" ).append( this.typeMap ).
	    append( ", optionsMap=" ).append( this.optionsMap ).
	    append( ", setHandlers=" ).append( this.setHandlers ).
	    append( ", addedHandlers=" ).append( this.addedHandlers ).
	    append( ", defaultCharset=" ).append( this.defaultCharset ).
	    append( ", addedCharsets=" ).append( this.addedCharsets ).
	    append( " ]" );


	return b;

    }


    /**
     * For testing only.
     **/
    public static void main( String[] argv ) {

	try {

	    HypertextAccessFile htaccess = read( new File("document_root_alpha/secret/.htaccess") ,
						 true   // strictMode
						 );
	    
	    System.out.println( htaccess );

	} catch( Exception e ) {

	    e.printStackTrace();

	}

    }


}