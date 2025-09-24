package startrekj.hpbasic;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.TreeMap;

public class ProgramLines {
	private TreeMap<Integer, Statement> lines = new TreeMap<Integer, Statement>();
		
	public void add(Line...lines) {
		for (Line line: lines)
			add(line.getLineNumber(), line.getStatement());
	}
	private void add(int lineNumber, Statement statement) {
		if (statement == null)
			throw new NullPointerException("null statement at line number " + lineNumber);
		lines.put(lineNumber, statement);
	}
	@Override
	public String toString() {
		StringWriter sourceCode = new StringWriter();
		PrintWriter out = new PrintWriter(sourceCode);
		for(Integer lineNumber: lines.keySet()) {
			out.println(lineNumber + " " + getStatementAtLineNumber(lineNumber));
		}
		return sourceCode.toString();
	}
	public Statement getStatementAtLineNumber(int lineNumber) {
		return lines.get(lineNumber);
	}
	public Integer getNextLineNumberAfter(int lineNumber) {
		return lines.higherKey(lineNumber);
	}
	public int getFirstLineNumber() {
		return lines.firstKey();
	}
	public int getLastLineNumber() {
		return lines.lastKey();
	}
}
