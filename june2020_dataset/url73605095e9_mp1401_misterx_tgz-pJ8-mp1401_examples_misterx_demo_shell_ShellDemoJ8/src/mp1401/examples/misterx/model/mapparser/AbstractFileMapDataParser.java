package mp1401.examples.misterx.model.mapparser;

public abstract class AbstractFileMapDataParser implements MapDataParser {
	
	private String fileName;
	
	public AbstractFileMapDataParser(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public final MapData getMapData() {
		try {
			openFile(fileName);
			return parseFile();
		} finally {
			closeFile();
		}
	} 
	
	protected abstract void openFile(String fileName);
	
	protected abstract MapData parseFile();

	protected abstract void closeFile();
}
