package startrekj.hpbasic;

public class Line implements Comparable<Line> {
	private int lineNumber;
	private Statement statement;

	public Line(int lineNumber, Statement statement) {
		this.lineNumber =  lineNumber;
		this.statement = statement;
	}

	public static Line line(int lineNumber, Statement statement) {
		return new Line(lineNumber, statement);
	}

	public Statement getStatement() {
		return statement;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int compareTo(Line otherLine) {
		return lineNumber - otherLine.lineNumber;
	}
	@Override
	public String toString() {
		return lineNumber + " " + statement;
	}
}
