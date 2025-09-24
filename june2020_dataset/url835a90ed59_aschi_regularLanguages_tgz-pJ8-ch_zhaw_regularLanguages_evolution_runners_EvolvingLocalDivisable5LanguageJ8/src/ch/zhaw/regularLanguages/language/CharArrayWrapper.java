package ch.zhaw.regularLanguages.language;

import java.util.Arrays;

import ch.zhaw.regularLanguages.helpers.PublicCloneable;

public class CharArrayWrapper implements PublicCloneable{
	private char[] data;

	public CharArrayWrapper(char[] data) {
		this.data = data;
	}
	
	public char[] getData(){
		return data;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CharArrayWrapper)) {
			return false;
		}
		return Arrays.equals(data, ((CharArrayWrapper) other).data);
	}

	@Override
	public String toString(){
		StringBuffer rv = new StringBuffer("[");
		for(char c : data){
			rv.append(c);
			rv.append(',');
		}
		rv.deleteCharAt(rv.length()-1);
		rv.append("]");
		return rv.toString();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}
	
	@Override
	public Object clone(){
		return new CharArrayWrapper(Arrays.copyOf(getData(), getData().length));
	}
}
