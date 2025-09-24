package startrekj.hpbasic;

public class StringVariable implements StringExpression {
	private String name;
	StringBuilder value = new StringBuilder();
	
	public StringVariable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void insert(int offset, String value) {
		this.value.insert(offset-1, value);
	}

	private void clear() {
		value.delete(0, value.length());
	}

	public String getValue() {
		return value.toString();
	}

	public void initialize(int size) {
		clear();
		for(int i=0; i<size; ++i)
			value.append(' ');
	}

	public void setValue(String value) {
		clear();
		this.value.insert(0, value);
	}

	public void replace(int from, int to, String value) {
		this.value.replace(from-1, to, value);
	}

	public String evaluate() {
		return value.toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
	public StringExpression chunk(int from, int to) {
		return chunk(new CONST(from), new CONST(to));
	}
	public StringExpression chunk(NumericExpression from, NumericExpression to) {
		return new StringChunk(this, from, to);
	}

}
