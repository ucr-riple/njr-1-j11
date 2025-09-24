package fr.noxx90.jflam.renderer;

import fr.noxx90.jflam.model.Flame;
import fr.noxx90.jflam.model.Histogram;

public abstract class HistogramRenderer
{
	public abstract Histogram render(Flame flame, int width, int height, int quality, int sampling);
}
