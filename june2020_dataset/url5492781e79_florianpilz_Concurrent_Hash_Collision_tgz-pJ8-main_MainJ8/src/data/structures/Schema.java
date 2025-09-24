package data.structures;

import java.util.Iterator;

public class Schema implements SchemaInterface {
	private final Text text;
	private final long startMask;
	private final long endMask;
	private final long rest;

	/**
	 * @param text
	 *            Der konkrete Text (K oder K')
	 * @param startMask
	 *            Intervallstart (binärcodiert)
	 * @param endMask
	 *            Intervallende (binärcodiert)
	 * @param endMask
	 *            Immer gleich bleibende Restbelegung
	 */
	private Schema(Text text, long startMask, long endMask, long rest) {
		this.text = text;
		this.startMask = startMask;
		this.endMask = endMask;
		this.rest = rest;
	}

	/**
	 * @param Das
	 *            Textobjekt
	 * @param Anzahl
	 *            der ersten Templates, die verändert werden sollen
	 * @param Binärcodierte
	 *            Belegung der _letzten_ Templates, falls diese Belegung in die
	 *            übergebene Anzahl der zu verwendeten Schemas reinreicht, ist
	 *            das blöde!
	 */
	public Schema(Text text, int templateCnt, long rest) {
		this.text = text;
		this.startMask = 0;
		this.endMask = (long) Math.pow(2, templateCnt) - 1;
		this.rest = transformRest(rest);
	}

	/**
	 * Transformiert so, dass rest die Codierung der letzten Templates ist
	 * 
	 * @param rest
	 * @return
	 */
	private long transformRest(long rest) {
		final int cnt = text.getTemplateCount();
		long newRest = 0;

		for (int i = cnt - 1; rest > 0; --i) {
			if (rest % 2 != 0)
				newRest += Math.pow(2, i);
			
			rest /= 2;
		}

		return newRest;
	}

	@Override
	public Iterator<Long> iterator() {
		return new Iterator<Long>() {
			long curPos = startMask;

			@Override
			public boolean hasNext() {
				return curPos <= endMask;
			}

			@Override
			public Long next() {
				return curPos++ + rest;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException(
						"Iterator darf nichts löschen!");
			}
		};
	}

	@Override
	public SchemaInterface[] split(int parts) {
		final int intervallSize = Math.round((endMask - startMask) / parts);

		SchemaInterface[] newSchemes = new SchemaInterface[parts];

		for (int i = 0; i < parts - 1; ++i)
			newSchemes[i] = new Schema(text, startMask + i * intervallSize,
					startMask + (i + 1) * intervallSize - 1, rest);

		// Verbleibendes Intervall (eventuell kleiner)
		newSchemes[parts - 1] = new Schema(text, startMask + (parts - 1)
				* intervallSize, endMask, rest);

		return newSchemes;
	}

	@Override
	public String getSentence(long bitbelegung) {
		final int n = text.getTemplateCount();
		final StringBuilder s = new StringBuilder(text.getMaxLength());

		// Vom Satzanfang bis zur ersten Schablone
		s.append(text.k.substring(0, text.getPosInText(0)));

		for (int i = 0; i < n; ++i) {
			// i-te Schablone ersetzen
			s.append(text.getTemplateContent(i, bitbelegung % 2 == 0));

			// Inhalt von i-ten Token bis (i + 1)-ten Token (oder Satzende)
			s.append(text.k.substring(text.getPosInText(i) + 1,
					text.getPosInText(i + 1)));

			// bitbelegung auf nächste Stelle setzen
			bitbelegung /= 2;
		}

		return s.toString();
	}
}
