package shyview;

import javax.swing.JMenuItem;

public class ShyviewMenu extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	IPicList reference;
	public ShyviewMenu(IPicList ref) {
		setText(ref.getName());
		reference = ref;
	}
	
	public IPicList getList() {
		return reference;
	}
}
