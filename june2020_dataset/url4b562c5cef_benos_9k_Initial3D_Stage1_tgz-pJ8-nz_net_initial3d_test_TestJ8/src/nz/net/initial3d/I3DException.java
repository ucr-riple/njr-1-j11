package nz.net.initial3d;

public class I3DException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public I3DException() {

	}

	public I3DException(String arg0) {
		super(arg0);
	}

	public I3DException(Throwable arg0) {
		super(arg0);
	}

	public I3DException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
