package ui;

import java.awt.Image;
import java.awt.Toolkit;

public class FabricaImagenCasilla {
	private static final int imagesize = 50;
	private static final Image blackDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/imagenes/negra.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image whiteDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/imagenes/blanca.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image emptyDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/imagenes/casillaVacia.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image pssblBlkDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/imagenes/posibleNegra.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image pssblWhtDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/imagenes/posibleBlanca.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image wallDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/imagenes/muro.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);

	public enum TipoCasilla {

		BLACK,
		WHITE,
		EMPTY,
		PSSBLBLK,
		PSSBLWHT,
		WALL;
	}

	public static ComponenteImagen construirCasilla(TipoCasilla tipo) {
		switch (tipo) {
			case BLACK:
				return new ComponenteImagen(blackDiscImg);
			case WHITE:
				return new ComponenteImagen(whiteDiscImg);
			case PSSBLBLK:
				return new ComponenteImagen(pssblBlkDiscImg);
			case PSSBLWHT:
				return new ComponenteImagen(pssblWhtDiscImg);
			case EMPTY:
				return new ComponenteImagen(emptyDiscImg);
			case WALL:
				return new ComponenteImagen(wallDiscImg);
			default:
				return null;
		}
	}
}
