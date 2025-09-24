
package cz.mff.dpp.args.examples;

import cz.mff.dpp.args.*;
import java.util.List;

public class Example2 {

	@Option(name = "-l", aliases = {"--list"}, description = "List of many values")
	List<String> list;


	enum Animal {
		DOG, CAT, PIG
	};

	@Option(name = "--animal", description = "Your favorite animal")
	private void setAnimal(Animal animal) {
		//only enum constants can be use with this option
		//otherwise ParseException will be produced
		switch (animal) {
			case CAT:
				System.out.println("MEOW");
				break;
			case DOG:
				System.out.println("WOOF");
				break;
			case PIG:
				System.out.println("CHRO");
				break;
			default:
				System.out.println("UNKNOWN ANIMAL");
		}
	}
	@Constraint(min = "0", max = "65535")
	@Option(name = "-n", description = "Number of ...")
	Integer n;

	@Constraint(regexp = ".*:.*")
	@Option(name = "--doubleString", factory = MyClass.class)
	//if MyClass have method valueOf(String),
	//this field will be set correctly
	public MyClass myClass;

	public static void main(String[] args) {
		//Set values into args manually
		args = new String[]{"--list", "v1", "v2",
		"v3", "--animal", "CAT", "-n", "1000", "--doubleString=aa:bb"};

		Example2 ex = new Example2();
		Parser parser = new Parser(ex);
		try {
			parser.parse(args);
		} catch (ParseException ex1) {
			parser.usage();
			System.exit(0);
		}

		//some code here
	}
	
	public static class MyClass {

		public String s1;
		public String s2;

		public MyClass(String s1, String s2) {
			this.s1 = s1;
			this.s2 = s2;
		}
		//factory method must have only one String param
		public static MyClass valueOf(String expr) {
			String[] values = expr.split(":");
			return new MyClass(values[0], values[1]);

		}
	}
}


