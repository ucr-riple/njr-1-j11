package data.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelliert den Klartext. Beim Zeichen '#' im Klartext (k) muss eine Schablone
 * eingefügt werden. Die LookUpTable (lut) beschreibt, an welcher Stelle i (i >=
 * 0) im Array die i-te Schablone an der Stelle j im Klartext eingesetzt werden
 * muss. An dieser Stelle j gilt: charAt(j) == '#'
 * 
 * Als Typ von templates und lut bieten sich ArrayListen an (wegen RandomAccess)
 * 
 * @author jdoering
 */
public class Text {

	public final String k;
	public final List<Template> templates;

	private final List<Integer> lut;
	private final int maxLength;

	public Text(String k, List<Template> templates) {
		this.lut = new ArrayList<Integer>(templates.size());

		if (!matches(k, templates)) {
			throw new IllegalArgumentException(
					"Tokenklartext matcht nicht mit den Templates");
		}

		this.k = k;
		this.templates = templates;
		this.maxLength = calcMaxLength();
		
//		System.out.println("Anzahl der Schablonen = " + templates.size());
//		System.out.println("Anzahl der Zeichen im längsten möglichen Text = " + getMaxLength());
	}

	/**
	 * Berechnet die maximale Zeichenlänge des Textes für den Stringbuilder
	 * @return
	 */
	private int calcMaxLength() {
		// Satzlänge abzüglich der Tokens (#)
		int length = k.length() - templates.size();
		
		// Zuzüglich der längeren Schablonenwörter
		for (Template t : templates) 
			length += Math.max(t.key.length(), t.value.length());
		
		return length;
	}
	
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * Prüft Parameter und füllt die lut
	 * 
	 * @param k
	 *            Der Tokenstring (# für Schablone)
	 * @param templates
	 *            Geordnete Folge der Schablonen
	 * @return true <-> Anzahl der Tokens im String entspricht Anzahl der
	 *         Elemente in templates; false <-> sonst
	 */
	private boolean matches(String k, List<Template> templates) {
		int cnt = 0;
		for (int i = 0; i < k.length(); ++i)
			if (k.charAt(i) == '#')
				lut.add(cnt++, i);

		return templates.size() == cnt;
	}

	public int getTemplateCount() {
		return templates.size();
	}

	public int getPosInText(int templateNr) {
		return templateNr < getTemplateCount() ? lut.get(templateNr) : k.length();
	}

	public String getTemplateContent(int templateNr, boolean key) {
		return key ? templates.get(templateNr).key
				: templates.get(templateNr).value;
	}
	
	public int getEndIntervallMask() {
		return (int) (Math.pow(2, getTemplateCount()) - 1);
	}
}
