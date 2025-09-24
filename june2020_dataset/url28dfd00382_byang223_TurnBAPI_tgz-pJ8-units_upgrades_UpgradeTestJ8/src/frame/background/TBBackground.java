package frame.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.util.ImageUtil;

/**
 * Our own version of background that does not use GTGE's variety of backgrounds
 * Ran into technical difficulties dealing with resizing of GTGE's plethora of
 * different backgrounds
 * 
 * @author bryanyang
 * 
 */
public class TBBackground extends Sprite implements java.io.Serializable {
	
	public TBBackground(BufferedImage x){
		this.setImage(x);
	}
	
	public void resize(int x, int y){
		setImage(ImageUtil.resize(this.getImage(), x, y));
		this.height = y;
		this.width = x;
	}

	public void render(Graphics2D g){
		super.render(g);
	}
	
	public void render(Graphics2D g, int x, int y){
		super.render(g, x, y);
	}

}
