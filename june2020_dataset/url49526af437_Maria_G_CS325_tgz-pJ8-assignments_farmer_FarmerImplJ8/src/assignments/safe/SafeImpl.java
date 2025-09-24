package assignments.safe;

public class SafeImpl implements Safe {

	private char[] display;
	private int index = 0;
	private boolean locked = true;
	
	public SafeImpl(){
		display = Safe.BLANK_DISPLAY.toCharArray();
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	@Override
	public String readDisplay() {
		return String.valueOf(display);
	}

	@Override
	public void enter(char c) {
		if(Character.isDigit(c)){
			display[index++] = c;
		}
		if(String.valueOf(display).equals("123456")){
			locked = false;
			display = Safe.OPEN_DISPLAY.toCharArray();
		}
	}

}
