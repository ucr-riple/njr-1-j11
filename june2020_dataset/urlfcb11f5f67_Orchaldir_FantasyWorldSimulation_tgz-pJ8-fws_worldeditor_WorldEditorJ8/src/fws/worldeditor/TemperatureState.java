package fws.worldeditor;

import fws.utility.map.ColorRenderer;
import fws.utility.state.State;
import fws.world.*;
import fws.world.generation.*;
import org.lwjgl.input.Keyboard;

public class TemperatureState extends State
{
	WorldGenerationMap map_;
	
	private ColorRenderer<WorldGenerationCell> renderer_;
	private ColorTemperature color_temperature_;
	
	private ModifiedByElevationAlgorithm temperature_algo_elevation_;
	private LinearGradientAlgorithm temperature_algo_linear_;
	private NoiseAlgorithm temperature_algo_noise_;
	private RadialGradientAlgorithm temperature_algo_radial_;
	private AddAlgorithms temperature_algo_sum0_;
	private AddAlgorithms temperature_algo_sum1_;
	
	public TemperatureState(WorldEditor editor)
	{
		map_ = editor.getWorldGenerationMap();
		
		int width = map_.getMap().getWidth();
		int height = map_.getMap().getHeight();
		float hw = width / 2.0f;
		float hh = height / 2.0f;
		
		float noise_scale = 0.25f / editor.getPlateTectonicsMap().getCellSize();
		
		// generation
		
		temperature_algo_elevation_ = new ModifiedByElevationAlgorithm(map_, 0.0f, -0.5f);
		temperature_algo_linear_= new LinearGradientAlgorithm(20.0f, width, 1.0f, 0.0f);
		temperature_algo_noise_ = new NoiseAlgorithm(3, 0.3f, noise_scale, 100);
		temperature_algo_radial_ = new RadialGradientAlgorithm(hw, hh, hw, 0.0f, 1.0f);
		
		temperature_algo_sum0_ = new AddAlgorithms();
		temperature_algo_sum0_.addAlgorithm(temperature_algo_linear_);
		temperature_algo_sum0_.addAlgorithm(temperature_algo_elevation_);
		
		temperature_algo_sum1_ = new AddAlgorithms();
		temperature_algo_sum1_.addAlgorithm(temperature_algo_radial_);
		temperature_algo_sum1_.addAlgorithm(temperature_algo_elevation_);
		
		map_.setTemperatureAlgo(temperature_algo_sum1_);
		
		// rendering
		
		color_temperature_ = new ColorTemperature();
		renderer_ = new ColorRenderer(map_.getMap(), editor.getWorldRenderSize(), color_temperature_);
	}
	
	@Override
	public String getName()
	{
		return "Temperature";
	}
	
	@Override
	public void processKeyboard()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_1))
		{
			map_.setTemperatureAlgo(temperature_algo_noise_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_2))
		{
			map_.setTemperatureAlgo(temperature_algo_linear_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_3))
		{
			map_.setTemperatureAlgo(temperature_algo_sum0_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_4))
		{
			map_.setTemperatureAlgo(temperature_algo_radial_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_5))
		{
			map_.setTemperatureAlgo(temperature_algo_sum1_);
			map_.generate();
		}
	}
	
	@Override
	public void render()
	{
		renderer_.render();
	}
}
