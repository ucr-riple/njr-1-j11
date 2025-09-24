package fws.world.generation;

import fws.utility.SimplexNoise;
import fws.utility.map.Cell;
import java.util.Random;

public class NoiseAlgorithm<T extends Cell> implements GenerationAlgorithm<T>
{
	private static final int MAX_RANDOM = 20000;
	
	private int octaves_;
	private float roughness_;
	private float scale_;
	
	private int seed_;
	private Random random_ = new Random();
	
	private int offset_x_;
	private int offset_y_;
	
	private float mean_ = 0.5f;
	private float amplitude_ = 0.5f;
	
	public NoiseAlgorithm(int octaves, float roughness, float scale)
	{
		octaves_ = octaves;
		roughness_ = roughness;
		scale_ = scale;
	}
	
	public NoiseAlgorithm(int octaves, float roughness, float scale, float mean, float amplitude)
	{
		octaves_ = octaves;
		roughness_ = roughness;
		scale_ = scale;
		mean_ = mean;
		amplitude_ = amplitude;
	}
	
	public NoiseAlgorithm(int octaves, float roughness, float scale, int seed)
	{
		this(octaves, roughness, scale);
		
		seed_= seed;
	}
	
	@Override
	public void nextSeed()
	{
		seed_++;
	}
	
	@Override
	public void update()
	{
		random_.setSeed(seed_);
		offset_x_ = random_.nextInt(MAX_RANDOM);
		offset_y_ = random_.nextInt(MAX_RANDOM);
	}
	
	@Override
	public float generate(float x, float y, T cell)
	{
		float ax = x + offset_x_;
		float ay = y + offset_y_;
				
		float noise = (float)SimplexNoise.getOctavedNoise(ax, ay, octaves_, roughness_, scale_);
		
		return mean_ + noise * amplitude_;
	}
}
