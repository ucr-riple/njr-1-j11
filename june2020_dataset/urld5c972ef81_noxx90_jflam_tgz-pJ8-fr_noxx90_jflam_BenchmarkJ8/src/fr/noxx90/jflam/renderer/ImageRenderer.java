package fr.noxx90.jflam.renderer;

import java.awt.image.BufferedImage;

import fr.noxx90.jflam.model.Histogram;

public abstract class ImageRenderer
{
	public abstract BufferedImage render(Histogram histogram);
}
