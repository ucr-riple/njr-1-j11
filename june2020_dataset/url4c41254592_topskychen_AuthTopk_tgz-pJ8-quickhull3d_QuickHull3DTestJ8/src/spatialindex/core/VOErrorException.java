package spatialindex.core;

public class VOErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5622803302826889235L;
	String errInfo = "";

	public VOErrorException(String string) {
		errInfo = string;
	}

	@Override
	public String toString() {
		return "VOErrorException:" + errInfo;
	}
}
