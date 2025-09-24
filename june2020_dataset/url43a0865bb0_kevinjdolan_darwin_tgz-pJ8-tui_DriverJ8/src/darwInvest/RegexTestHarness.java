package darwInvest;

import java.util.Scanner;  
import java.util.regex.Pattern;  
import java.util.regex.Matcher;  

public class RegexTestHarness {  

	public static void main(String[] args){  

		Scanner scanner = new Scanner(System.in);  

		while (true) {  

			System.out.print("\nEnter your regex: ");  
			Pattern pattern = Pattern.compile(scanner.next());  

			System.out.print("Enter input string to search: ");  
			Matcher matcher = pattern.matcher(scanner.next());  

			boolean found = false;  
			while (matcher.find()) {  

				System.out.format("I found the text \"%s\" starting at " +  
						"index %d and ending at index %d.%n",  
						matcher.group(), matcher.start(), matcher.end());  
				found = true;  
			}  
			if(!found){  
				System.out.format("No match found.%n");  
			}  
		}  
	}  
}  