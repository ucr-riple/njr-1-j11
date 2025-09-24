package data.structures;

public class HashBitmapPair {
	private final byte[] hash;
	private final long bitmap;
	
	public HashBitmapPair(byte[] hash, long bitmap) {
		this.hash = hash;
		this.bitmap = bitmap;
	}
	
	public byte[] getHash() {
		return this.hash;
	}
	
	public long getBitmap() {
		return this.bitmap;
	}
}
