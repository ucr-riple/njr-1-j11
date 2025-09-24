package fws.world;

import fws.utility.map.Cell;

public class PlateTectonicsCell extends Cell
{
	public PlateType type_;

	public PlateTectonicsCell(int id, PlateType type)
	{
		super(id);
		type_ = type;
	}

}
