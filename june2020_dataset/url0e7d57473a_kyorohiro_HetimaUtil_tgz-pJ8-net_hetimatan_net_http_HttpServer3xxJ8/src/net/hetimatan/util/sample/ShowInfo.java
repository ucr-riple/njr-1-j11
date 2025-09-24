package net.hetimatan.util.sample;

import java.util.Properties;

public class ShowInfo {
	public static void main(String[] args) {
		Properties prop = System.getProperties();
		System.out.println(System.getProperty("java.io.tmpdir"));
		prop.list(System.out);
	}
}
