package test.properties;

import java.io.InputStream;
import java.util.ResourceBundle;

public class PropertyLoader {

	public void testLoader() {
		InputStream is = this.getClass().getResourceAsStream(
				"props/package.properties");
		if (null != is) {
			System.out.println("Located Class.getResource.....");
		}
		is=this.getClass().getClassLoader().getResourceAsStream("test/properties/package.properties");
		if (null != is) {
			System.out.println("Located ClassLoader.getResource.....");
		}
		
		ResourceBundle rb=ResourceBundle.getBundle(this.getClass().getName());
		System.out.println(rb.getString("1"));
	}

	public static void main(String[] args) {
		new PropertyLoader().testLoader();
	}
}
