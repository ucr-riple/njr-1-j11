package fws.world;

import fws.utility.map.*;

public class ColorPlateTectonics<T extends PlateTectonicsCell> implements ColorSelector<T>
{

	@Override
	public void selectColor(T cell)
	{
		cell.type_.getColor().apply();
	}

}
