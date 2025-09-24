package shyview;

import java.awt.Frame;
import java.util.NoSuchElementException;

public class TitleInformer implements IPicInfo {
	private static final String NOPICTURES = "No pictures available";
	
	private TitleInformer() {};
	private static TitleInformer _instance;
	public static TitleInformer getInstance() {
		if (_instance == null) _instance = new TitleInformer();
		return _instance;
	}
	
	Frame window;
	String startTitle;
	String listName;
	int listSize;
	int picPosition;
	String picName;
	String process;
	
	public void setFrame(Frame fr) {
		window = fr;
		startTitle = window.getTitle();
	}

	private void redraw() {
		StringBuilder title = new StringBuilder();
		title.append(startTitle+" - ");
		if (listName != null && picName != null) {
			title.append(picPosition+"/"+listSize);
			title.append(" - ");
			title.append(listName);
			title.append(" - ");
			String filename = picName;
			if (filename.length() > 20) filename = ".."+filename.substring(filename.length()-20);
			title.append(filename);
		} else {
			title.append(NOPICTURES);
		}

		if (process != null) {
			title.append(" - ");
			title.append(process);
		}
		window.setTitle(title.toString());
	}

	@Override
	public void update(IPicList list) {
		listName = list.getName();
		listSize = list.size();
		try {
			picName = list.current().getName();
			picPosition = list.getIndex()+1;
		} catch (NoSuchElementException e) {}
		redraw();
	}

	@Override
	public void clear() {
		listName = null;
		picName = null;
		redraw();
	}

	@Override
	public void pushProcess(String info) {
		if (process == info) return;
		process = info;
		redraw();
	}

	@Override
	public void finishProcess() {
		process = null;
		redraw();
	}

}
