package fws.world.generation;

import fws.utility.map.Cell;

public interface GenerationAlgorithm<T extends Cell>
{
	void nextSeed();
	void update();
	float generate(float x, float y, T cell);
}
