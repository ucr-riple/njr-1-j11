package mp1401.examples.misterx.model.mapparser;

public class XMLMapDataParser extends AbstractFileMapDataParser {

	public XMLMapDataParser(String fileName) {
		super(fileName);
	}

	@Override
	protected void openFile(String fileName) {
		// implement open file action
	}

	@Override
	protected MapData parseFile() {
		// implement XML-parse action
		return new MapData();
	}

	@Override
	protected void closeFile() {
		// implement close file action
	}
}
