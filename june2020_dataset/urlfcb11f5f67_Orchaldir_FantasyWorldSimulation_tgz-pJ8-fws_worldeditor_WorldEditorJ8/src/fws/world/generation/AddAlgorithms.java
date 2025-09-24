package fws.world.generation;

import fws.utility.map.Cell;
import java.util.ArrayList;
import java.util.List;

public class AddAlgorithms<T extends Cell> implements GenerationAlgorithm<T>
{
	private List<GenerationAlgorithm<T>> algorithms_ = new ArrayList();

	public void addAlgorithm(GenerationAlgorithm<T> algorithm)
	{
		algorithms_.add(algorithm);
	}
	
	@Override
	public void nextSeed()
	{
		for(GenerationAlgorithm<T> algorithm : algorithms_)
		{
			algorithm.nextSeed();
		}
	}

	@Override
	public void update()
	{
		for(GenerationAlgorithm<T> algorithm : algorithms_)
		{
			algorithm.update();
		}
	}

	@Override
	public float generate(float x, float y, T cell)
	{
		float sum = 0.0f;
		
		for(GenerationAlgorithm<T> algorithm : algorithms_)
		{
			sum += algorithm.generate(x, y, cell);
		}
		
		return sum;
	}
}
