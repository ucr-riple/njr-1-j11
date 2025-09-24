package fws.worldeditor;

import fws.utility.map.*;
import fws.utility.state.State;
import fws.utility.Color;
import fws.world.*;
import fws.world.continent.GrowingAlgorithm;
import fws.world.generation.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ElevationState extends State
{
	private WorldGenerationMap map_;
	
	private ColorRenderer renderer_;
	
	// plate tectonics
	
	private PlateTectonicsMap tectonics_map_;
	private PlateType land_type_;
	private PlateType water_type_;
	
	private GrowingAlgorithm continent_algoithm_;
	
	private ColorRenderer<PlateTectonicsCell> tectonics_renderer_;
	private ColorPlateTectonics color_tectonics_;
	
	// elevation
	
	private ColorRenderer<WorldGenerationCell> elevation_renderer_;
	private ColorLandAndWater color_elevation_;
	
	private PlateTectonicsAlgorithm elevation_algo_tectonics_;
	private NoiseAlgorithm elevation_algo_noise_;
	private AddAlgorithms elevation_algo_sum_;
	
	private NoiseAlgorithm elevation_algo_noise_2_;
	
	public ElevationState(WorldEditor editor)
	{
		tectonics_map_ = editor.getPlateTectonicsMap();
		map_ = editor.getWorldGenerationMap();
		
		// plate tectonics
		
		float tec_render_size = editor.getTectonicsRenderSize();
		float tec_render_border = 1.0f;
		
		land_type_ = editor.getLandType();
		water_type_ = editor.getWaterType();
		
		continent_algoithm_ = new GrowingAlgorithm(tectonics_map_, land_type_);
		
		color_tectonics_ = new ColorPlateTectonics();
		tectonics_renderer_ = new ColorRenderer(tectonics_map_.getMap(), tec_render_size, tec_render_border, color_tectonics_);
		
		// elevation
		
		float world_render_size = editor.getWorldRenderSize();
		float noise_scale = 0.25f / tectonics_map_.getCellSize();
		
		elevation_algo_tectonics_ = new PlateTectonicsAlgorithm(tectonics_map_);
		elevation_algo_noise_ = new NoiseAlgorithm(4, 0.3f, noise_scale*3.0f, 0.0f, 0.2f);
		
		elevation_algo_sum_ = new AddAlgorithms();
		elevation_algo_sum_.addAlgorithm(elevation_algo_tectonics_);
		elevation_algo_sum_.addAlgorithm(elevation_algo_noise_);
		
		elevation_algo_noise_2_ = new NoiseAlgorithm(3, 0.3f, noise_scale);
		
		map_.setElevationAlgo(elevation_algo_tectonics_);
		
		color_elevation_ = new ColorLandAndWater(map_);
		elevation_renderer_ = new ColorRenderer(map_.getMap(), world_render_size, color_elevation_);
		
		renderer_ = tectonics_renderer_;
	}
	
	@Override
	public String getName()
	{
		return "Elevation";
	}
	
	@Override
	public void processKeyboard()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_1))
		{
			renderer_ = tectonics_renderer_;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_2))
		{
			renderer_ = elevation_renderer_;
			map_.setElevationAlgo(elevation_algo_tectonics_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_3))
		{
			renderer_ = elevation_renderer_;
			map_.setElevationAlgo(elevation_algo_sum_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_4))
		{
			renderer_ = elevation_renderer_;
			map_.setElevationAlgo(elevation_algo_noise_2_);
			map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_C))
		{
			tectonics_map_.clear();
			continent_algoithm_.update();
			map_.generate();
		}
		
	}
	
	@Override
	public  void processMouse()
	{
		if(Mouse.isButtonDown(0)) // left button
		{
			processClick(land_type_);
		}
		else if(Mouse.isButtonDown(1)) // right button
		{
			processClick(water_type_);
		}
	}
	
	public void processClick(PlateType type)
	{
		int x = Mouse.getX();
		int y = Mouse.getY();
		PlateTectonicsCell cell = tectonics_renderer_.getCell(x, y);
		
		if(cell != null)
		{
			cell.type_ = type;
			map_.generate();
		}
	}
	
	@Override
	public void render()
	{
		renderer_.render();
	}
}
