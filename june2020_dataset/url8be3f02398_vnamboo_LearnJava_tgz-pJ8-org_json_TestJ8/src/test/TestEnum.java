package test;

public class TestEnum {
public static void main(String[] args) {
	OrderState state= OrderState.REJECTED;
	System.out.println(state);;
	System.out.println(OrderState.DRAFT.isTerminal());;
	System.out.println(state.isTerminal());
	new TestEnum();
	System.out.println(java.util.TimeZone.getTimeZone("GMT"));
}

public TestEnum(){
	System.out.println("Hello");
}
}
