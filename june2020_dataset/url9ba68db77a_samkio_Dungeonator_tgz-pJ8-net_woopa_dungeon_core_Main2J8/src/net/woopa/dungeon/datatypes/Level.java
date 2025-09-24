package net.woopa.dungeon.datatypes;


public class Level {
	private final LevelCreator levelCreator;
	private final Vector2D start, end;
	private final Direction startDir, endDir;
	private Grid grid;

	public Level(LevelCreator lc, Vector2D levelSize, Vector2D startLocation,
			Direction startDirection) {
		this.levelCreator = lc;
		this.setGrid(this.levelCreator.generate(levelSize, startLocation,
				startDirection));
		this.start = this.levelCreator.levelStart();
		this.end = this.levelCreator.levelEnd();
		this.startDir = this.levelCreator.startDirection();
		this.endDir = this.levelCreator.endDirection();
		this.levelCreator.clean();
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Vector2D getLevelEnd() {
		return end;
	}

	public Vector2D getLevelStart() {
		return start;
	}

	public void show() {
		grid.show();
		System.out.println(" Start" + start.toString() + ":" + startDir
				+ " End" + end + ":" + endDir + " Rooms=" // TODO
				+ " Grid:" + grid);
	}
}
