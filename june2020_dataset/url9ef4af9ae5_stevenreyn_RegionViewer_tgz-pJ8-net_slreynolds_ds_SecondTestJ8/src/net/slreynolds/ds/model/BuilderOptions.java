
package net.slreynolds.ds.model;

/**
 * Predefined keys for build options.
 */
public class BuilderOptions {
    
	/**
	 * Key for option that determines if Strings should be in-lined. 
	 * Should contain a Boolean value. Default is false.
	 */
	public static final String INLINE_STRINGS = "INLINE_STRINGS";
	public static final boolean DEFAULT_INLINE_STRINGS = false;
	
	/**
	 * Key for option that determines if numbers should be in-lined. 
	 * Should contain a Boolean value. Default is false.
	 */
	public static final String INLINE_NUMBERS = "INLINE_NUMBERS";
	public static final boolean DEFAULT_INLINE_NUMBERS = false;
	
	/**
	 * Key for option that determines if system hashcode should be 
	 * shown. Should contain a Boolean value.
	 */
	public static final String SHOW_SYSHASH = "SHOW_SYSHASH";
	public static final boolean DEFAULT_SHOW_SYSHASH = false;
	
	/**
	 * Key for option that controls the maximum nesting level.
	 * Should contain an Integer value.
	 */
	public static final String MAX_NESTING = "MAX_NESTING";
	public static final int DEFAULT_MAX_NESTING = 10;
	
	/**
	 * Key for option that tells the builder internal classes which generation to use for this
	 * region of objects. This option is set by the builder itself and shouldnot be set
	 * by users. Should contain an Integer.
	 */
	public static final String GENERATION = "GENERATION"; 
	
	/**
	 * Key for option that controls maximum number of array elements that
	 * will be shown. Should contain an Integer value.
	 */
	public static final String MAX_ARRAY_LENGTH = "MAX_ARRAY_LENGTH";
	public static final int DEFAULT_MAX_ARRAY_LENGTH = 129;
	
	
	/** Don't build an instance of one of these */
    private BuilderOptions() {
        
    }
    
}
