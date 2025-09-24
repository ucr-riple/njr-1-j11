package enumtest;

public class EnumTest {
public static void main(String[] args) {
	System.out.println(new Object().hashCode());
	Day[] days=Day.values();
	for(Day day:days){
		System.out.println(day.hashCode());
	}
}
}
