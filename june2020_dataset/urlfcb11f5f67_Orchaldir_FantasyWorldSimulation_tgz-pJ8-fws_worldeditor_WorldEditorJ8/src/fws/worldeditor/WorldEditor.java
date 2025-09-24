package fws.worldeditor;

import fws.utility.Color;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import fws.utility.map.*;
import fws.utility.state.*;
import fws.world.*;

public class WorldEditor
{
	public static final int DISPLAY_HEIGHT = 480;
	public static final int DISPLAY_WIDTH = 640;
	
	private StateMgr state_mgr_;
	
	private PlateTectonicsMap tectonics_map_;
	private float tectonics_render_size_;
	private PlateType land_type_;
	private PlateType water_type_;
	
	private WorldGenerationMap world_map_;
	private float world_render_size_;

	public static void main(String[] args)
	{
		WorldEditor main = null;
		try
		{
			System.out.println("Keys:");
			System.out.println("esc   - Exit");
			main = new WorldEditor();
			main.create();
			main.run();
		} catch(Exception ex)
		{
			System.out.println("Exception:" + ex.toString());
		} finally
		{
			if(main != null)
			{
				main.destroy();
			}
		}
	}

	public WorldEditor()
	{
		// plate tectonics
		
		int tec_width = 12;
		int tec_height = 8;
		int tec_cell_size = 50;
		tectonics_render_size_ = 600.0f / tec_width;
		
		land_type_ = new PlateType("Land", 0.75f, new Color(0.0f, 1.0f, 0.0f));
		water_type_ = new PlateType("Water", 0.25f, new Color(0.0f, 0.0f, 1.0f));
		
		tectonics_map_ = new PlateTectonicsMap(tec_width, tec_height, tec_cell_size, water_type_);

		// world map
		
		world_render_size_ = tectonics_render_size_ / tec_cell_size;
		
		world_map_ = tectonics_map_.createWorldGenerationMap(MapType.SQUARE_MAP);
		
		// states
		
		state_mgr_ = new StateMgr();
		state_mgr_.add(new ElevationState(this));
		state_mgr_.add(new TemperatureState(this));
		state_mgr_.add(new RainfallState(this));
		state_mgr_.setActive("Elevation");
		
		// generate map
		
		world_map_.generate();
	}
	
	public PlateTectonicsMap getPlateTectonicsMap()
	{
		return tectonics_map_;
	}

	public float getTectonicsRenderSize()
	{
		return tectonics_render_size_;
	}
	
	public PlateType getLandType()
	{
		return land_type_;
	}
	
	public PlateType getWaterType()
	{
		return water_type_;
	}
	
	public WorldGenerationMap getWorldGenerationMap()
	{
		return world_map_;
	}
	
	public float getWorldRenderSize()
	{
		return world_render_size_;
	}

	public void create() throws LWJGLException
	{
		//Display
		Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
		Display.setFullscreen(false);
		Display.setTitle("Fantasy World Simulation - Map Editor");
		Display.create();

		//Keyboard
		Keyboard.create();

		//Mouse
		Mouse.setGrabbed(false);
		Mouse.create();

		//OpenGL
		initGL();
		resizeGL();
	}

	public void destroy()
	{
		//Methods already check if created before destroying.
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}

	public void initGL()
	{
		//2D Initialization
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
	}

	public void processKeyboard()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			state_mgr_.setActive("Elevation");
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_F2))
		{
			state_mgr_.setActive("Temperature");
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_F3))
		{
			state_mgr_.setActive("Rainfall");
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			world_map_.getElevationAlgo().nextSeed();
			world_map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_T))
		{
			world_map_.getTemperatureAlgo().nextSeed();
			world_map_.generate();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_R))
		{
			world_map_.getRainfallAlgo().nextSeed();
			world_map_.generate();
		}
		
		state_mgr_.processKeyboard();
	}

	public void processMouse()
	{
		state_mgr_.processMouse();
	}

	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		
		state_mgr_.render();
	}

	public void resizeGL()
	{
		//2D Scene
		glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, DISPLAY_WIDTH, 0.0f, DISPLAY_HEIGHT);
		glPushMatrix();

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
	}

	public void run()
	{
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			if(Display.isVisible())
			{
				processKeyboard();
				processMouse();
				update();
				render();
			} else
			{
				if(Display.isDirty())
				{
					render();
				}
				try
				{
					Thread.sleep(100);
				} catch(InterruptedException ex)
				{
				}
			}
			Display.update();
			Display.sync(60);
		}
	}

	public void update()
	{
		state_mgr_.update();
	}

}
