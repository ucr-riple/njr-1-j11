package startrekj.hpbasic.statements;

import startrekj.hpbasic.Statement;

public class IMAGE implements Statement {
	private StringBuilder imageString = new StringBuilder();
	private StringBuilder formatString = new StringBuilder();
	
	private IMAGE() {
	}
	
	public static IMAGE IMAGE() {
		return new IMAGE();
	}
//	public IMAGE FORMAT(String imageString) {
//		this.imageString.append(imageString);
//		formatString.append(convertToFormatString(imageString));
//		return this;
//	}
	public IMAGE STRING(String imageString) {
		return append(imageString, imageString);
	}
	public String getFormatString() {
		return formatString.toString();
	}
	private String convertToFormatString(String imageString) {
		String formatString = imageString;
		formatString = formatString.replace("8(X,3A)", " %3s %3s %3s %3s %3s %3s %3s %3s");
		formatString = formatString.replace("8X", "        ");
		formatString = formatString.replace("9X", "         ");
		formatString = formatString.replace("11X", "           ");
		formatString = formatString.replace("5D", "%5s");
		formatString = formatString.replace("6A", "%6s");
		formatString = formatString.replace("6D", "%6s");
		formatString = formatString.replace("3D", "%3s");
		formatString = formatString.replace("D", "%1s");
		return formatString;
	}

	@Override
	public String toString() {
		return "IMAGE " + imageString;
	}

	public void execute() {
	}

	public String getImageString() {
		return imageString.toString();
	}

	public IMAGE FORMAT_D() {
		return append("D","%1s");
	}
	private IMAGE append(String imageString, String formatString) {
		if(this.imageString.length() > 0)
			this.imageString.append(",");
		this.imageString.append(imageString);
		
		this.formatString.append(formatString);
		return this;
	}

	public Statement FORMAT_3_3D_STRING(String string) {
		String imageString = "3(3D,\"" + string + "\")";
		String formatString = times(3, "%3s" + string);
		return append(imageString, formatString);
	}

	private String times(int n, String string) {
		StringBuilder buffer = new StringBuilder();
		for(int i = 0; i < n; ++i)
			buffer.append(string);
		return buffer.toString();
	}
	public IMAGE FORMAT_3D() {
		return append("3D", "%3s");
	}
	public IMAGE FORMAT_4D() {
		return append("4D", "%4s");
	}
	public IMAGE FORMAT_5D() {
		return append("5D", "%5s");
	}
	public IMAGE FORMAT_6D() {
		return append("6D", "%6s");
	}
	public IMAGE FORMAT_6A() {
		return append("6A", "%6s");
	}
	public IMAGE FORMAT_15X() {
		return append("15X", "%15s");
	}
	public IMAGE FORMAT_11X() {
		return append("11X", times(11, " "));
	}
	public IMAGE FORMAT_9X() {
		return append("9X", " ");
	}
	public IMAGE FORMAT_8_X_3A() {
		return append("8(X,3A)", times(8, " %s"));
	}
	public IMAGE FORMAT_8_3X_3D() {
		return append("8(3X,3D)", times(8, "   %3s"));
	}
	public IMAGE FORMAT_8X() {
		return append("8X", times(8, " "));
	}
}
