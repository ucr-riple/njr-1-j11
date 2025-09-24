package hadoopCode;

import mythrift.Annotation;
import mythrift.Span;

public interface AnnotationChooser {
	
	/**
	 * Function to decide or not to keep annotations from the specific span
	 * @param s the specific span
	 * @return true for keep false for the opposite
	 */
	boolean shouldKeepSpan(Span s);
	
	/**
	 * Function to decide whether to keep the specific annotation or not
	 * @param a the annotation
	 * @return true or false
	 */
	boolean shouldKeepAnnotation(Annotation a);
	
	/**
	 * This function returns the string value used as id for the emited timestamps
	 * @param s The span
	 * @param a The annotation
	 * @return the string id
	 */
	String getId(Span s, Annotation a);
}
