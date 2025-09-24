package data.structures;

public interface SchemaInterface extends Iterable<Long>{
	public SchemaInterface[] split(int parts);
	public String getSentence(long bitbelegung);
}
