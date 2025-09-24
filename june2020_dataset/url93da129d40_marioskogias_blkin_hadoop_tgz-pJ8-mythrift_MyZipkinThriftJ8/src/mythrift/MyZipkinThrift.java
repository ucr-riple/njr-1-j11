package mythrift;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.commons.codec.binary.Base64;

/*
 * This is just an example
 */

public class MyZipkinThrift {

	public static void main(String[] args) {
		
		String foo = "CgABMnsjxmQ8mGkLAAMAAAAOcGFyZW50IHByb2Nlc3MKAARmM0hzdLDcUQ8ABgwAAAABCgABAAT8"
				+ "P9Do0mwLAAIAAAAMcGFyZW50IHN0YXJ0DAADCAABAAAAAAYAAgABCwADAAAABnBhcmVudAAADwAI"
				+ "DAAAAAAA";
		
		TMemoryBuffer trans = new TMemoryBuffer(10);
		byte[] decoded = Base64.decodeBase64(foo.getBytes());       
		trans.write(decoded, 0, decoded.length);
		
		TBinaryProtocol tbp = new TBinaryProtocol(trans);
		Span s = new Span();
		try {
			s.read(tbp);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(s.name);
		for (Annotation a : s.annotations) {
			System.out.print(a.value + " ");
			System.out.println(a.timestamp);			
		}
	}

}
