package xscript;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;

class XFakeObjectInput extends DataInputStream implements ObjectInput {

	public XFakeObjectInput(InputStream in) {
		super(in);
	}

	@Override
	public Object readObject() throws ClassNotFoundException, IOException {
		throw new UnsupportedOperationException();
	}

}
