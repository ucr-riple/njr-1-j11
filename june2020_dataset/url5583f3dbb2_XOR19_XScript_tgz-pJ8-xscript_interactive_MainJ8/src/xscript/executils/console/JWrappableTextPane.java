package xscript.executils.console;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.GlyphView;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class JWrappableTextPane extends JTextPane {

	private static final long serialVersionUID = 4320550904273991208L;

	boolean wrap;
	boolean word;

	public JWrappableTextPane() {
		
	}

	public JWrappableTextPane(StyledDocument doc) {
		super(doc);
	}

	protected EditorKit createDefaultEditorKit() {
		return new WrapEditorKit(new WrapColumnFactory());
	}

	public void setLineWrap(boolean wrap) {
		if (this.wrap != wrap) {
			this.wrap = wrap;
			invalidate();
		}
	}

	public boolean getLineWrap() {
        return wrap;
    }
	
	public void setWrapStyleWord(boolean word) {
		if (this.word != word) {
			this.word = word;
			if (wrap)
				invalidate();
		}
	}
	
	public boolean getWrapStyleWord() {
        return word;
    }

	private static class WrapEditorKit extends StyledEditorKit {

		private static final long serialVersionUID = -7453613128479009974L;

		private ViewFactory factory;

		public WrapEditorKit(ViewFactory factory) {
			this.factory = factory;
		}

		public ViewFactory getViewFactory() {
			return factory;
		}

	}

	private class WrapColumnFactory implements ViewFactory {

		public View create(Element elem) {
			String kind = elem.getName();
			if (kind != null) {
				if (kind.equals(AbstractDocument.ContentElementName)) {
					return new WrapLabelView(elem);
				} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
					return new ParagraphView(elem);
				} else if (kind.equals(AbstractDocument.SectionElementName)) {
					return new BoxView(elem, View.Y_AXIS);
				} else if (kind.equals(StyleConstants.ComponentElementName)) {
					return new ComponentView(elem);
				} else if (kind.equals(StyleConstants.IconElementName)) {
					return new IconView(elem);
				}
			}
			return new WrapLabelView(elem);
		}

	}

	private class WrapLabelView extends LabelView {

		public WrapLabelView(Element elem) {
			super(elem);
		}

		public float getMinimumSpan(int axis) {
			switch (axis) {
			case View.X_AXIS:
				if (wrap)
					return 0;
			default:
				return super.getMinimumSpan(axis);
			}
		}

		public int getBreakWeight(int axis, float pos, float len) {
			if (axis == View.X_AXIS && wrap && !word) {
				return View.GoodBreakWeight;
			}
			return super.getBreakWeight(axis, pos, len);
		}

		public View breakView(int axis, int p0, float pos, float len) {
			if (axis == View.X_AXIS && wrap && !word) {
				checkPainter();
				int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos,
						len);
				if (p0 == getStartOffset() && p1 == getEndOffset()) {
					return this;
				}
				GlyphView v = (GlyphView) createFragment(p0, p1);
				v.getTabbedSpan(pos, getTabExpander());
				return v;
			}
			return super.breakView(axis, p0, pos, len);
		}

	}

}
