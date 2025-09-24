package terrain;

public enum TerrainEnum {
	CLEAR(0, "src/hex/graphics/Grassland1-Z4.png", null, new ClearTerrain()), 
	TREES(1, "src/hex/graphics/Grassland1-Z4.png", "src/hex/graphics/Trees1-Z4.png", new TreesTerrain()),
	T_GRASS(2, "src/hex/graphics/HighGrass1-Z4.png", null, new TallGrassTerrain()), 
	INVALID(3, "src/hex/graphics/Pavement-Z44.png", null, new InvalidTerrain());
	
	public final int id;			
	public final String backgroundFile;	// Graphics file name for background
	public final String foregroundFile;	// Graphics file name for foreground
	public final TerrainType tType;
	public static TerrainEnum loadEnum(int i) {
		switch (i) {
		case 0: 
			return TerrainEnum.CLEAR;			
		case 1:
			return TerrainEnum.TREES;			
		case 2:
			return TerrainEnum.T_GRASS;			
		case 3:
			return TerrainEnum.INVALID;			
		default:
			return TerrainEnum.INVALID;
		}			
	}
	
	TerrainEnum(int i, String bg, String fg, TerrainType tt) {
		this.id = i;
		this.backgroundFile = bg;
		this.foregroundFile = fg;
		this.tType = tt;
	}
	

}
