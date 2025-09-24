package net.woopa.dungeon.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.woopa.dungeon.datatypes.Direction;
import net.woopa.dungeon.datatypes.Grid;
import net.woopa.dungeon.datatypes.Material;
import net.woopa.dungeon.datatypes.Schematic;

public enum CoreSchematic implements Schematic {
	CIRCLE7x7(new String[] {
	        "  ###  ",
	        " #...# ",
	        "#.....#",
	        "#.....#",
	        "#.....#",
	        " #...# ",
	        "  ###  "
	      }),
	      CHANTING9x9(new String[]{
	    		  "#########",
			      "#t.....t#",
			      "#.KKKKK.#",
			      "#.K,,,K.#",
			      "#.K,e,K.#",
         	      "#.K,,,K.#",
		          "#.KK.KK.#",
			      "#t.....t#",
			      "#########"
	      });
	private final String[] map;
	private Map<Direction,List<Integer>> accessPoints = new EnumMap<Direction,List<Integer>>(Direction.class); 
    private final Grid grid;
	CoreSchematic(String[] map) {
		this.map = map;
		this.grid = new Grid(map);
	    initialize_access(Direction.NORTH);
	    initialize_access(Direction.EAST);
	    initialize_access(Direction.SOUTH);
	    initialize_access(Direction.WEST);
	}
	@Override
	public String[] getMap() {
		return this.map;
	}
	@Override
	public int sx(Direction dir) {
	    if(dir.isHorizontal())
	      return grid.getSize().getY();
	    return grid.getSize().getX();
	  }
	@Override
	  public int sy(Direction dir) {
	    if(dir.isHorizontal())
	      return grid.getSize().getX();
	    return grid.getSize().getY();
	  }
	@Override
	  public Material get(int x,int y,Direction dir) {
	    int sx = grid.getSize().getX()-1;
	    int sy = grid.getSize().getY()-1;
	    switch(dir) {
	      case NORTH: return grid.get(x,y);
	      case EAST:  return grid.get(sx-y,x);
	      case SOUTH: return grid.get(sx-x,sy-y);
	      case WEST:  return grid.get(y,sy-x);
		default:
			break;
	    }
	    return grid.get(x,y);
	  }
	
	private void initialize_access(Direction dir) {
	    List<Integer> tmp = new ArrayList<Integer>();
	    accessPoints.put(dir,tmp);
	    int x = (dir.isVertical())?1:((dir==Direction.WEST)?0:grid.getSize().getX()-1);
	    int y = (dir.isVertical())?((dir==Direction.SOUTH)?0:grid.getSize().getY()-1):1;
	    int sz = (dir.isVertical())?grid.getSize().getX():grid.getSize().getY();
	    for(int o=1;o<sz-1;o++) {
	      if(grid.get(x, y)==CoreMaterial.DOWN) {
	        tmp.add(o);
	      } else if(grid.get(x,y)==CoreMaterial.WALL &&
	         grid.isWall(dir.left_x(x),dir.left_y(y)) &&
	         grid.isWall(dir.right_x(x),dir.right_y(y)) &&
	         // It's only a valid access point if the first square in the room is also floor
	         grid.isFloor(dir.backwards_x(x),dir.backwards_y(y))
	              ) {
	        tmp.add(o);
	      }
	      if(dir == Direction.NORTH || dir == Direction.WEST) {
	        x = dir.right_x(x);
	        y = dir.right_y(y);
	      } else {
	        x = dir.left_x(x);
	        y = dir.left_y(y);        
	      }
	    }  
	  }
	  
	
	    
	  // Get a legal random room access location on the given side
	  //  return -1 if there isn't one
	@Override
	  public int getAccess(Direction oside,Direction dir) {
	    Direction side = oside;
	    switch(dir) {
	      case NORTH: break;
	      case EAST:  side = side.rotate270(); break;
	      case SOUTH: side = side.rotate180(); break;
	      case WEST:  side = side.rotate90(); break;
		default:
			break;
	    }
	    List<Integer> tmp = accessPoints.get(side);
	    if(!tmp.isEmpty()) {
	      int offset = tmp.get(RandomUtil.nextInt(tmp.size()));
	      int max = (side.isVertical())?grid.getSize().getX()-1:grid.getSize().getY()-1;
	      Boolean reflect = false;
	      switch(dir) {
	        case NORTH: break;
	        case EAST:  reflect = (oside.isHorizontal()); break;
	        case SOUTH: reflect = true; break;
	        case WEST:  reflect = (oside.isVertical()); break;
		default:
			break;
	      }      
	      return (reflect)?max-offset:offset;
	    }
	    return -1;
	  }
	  

}
