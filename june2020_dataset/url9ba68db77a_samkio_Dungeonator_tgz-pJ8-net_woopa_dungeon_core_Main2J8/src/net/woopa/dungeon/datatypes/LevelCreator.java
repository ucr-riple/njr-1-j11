package net.woopa.dungeon.datatypes;


public interface LevelCreator {
	public Vector2D levelStart();

	public Vector2D levelEnd();

	public Grid generate(Vector2D size, Vector2D startLocation, Direction startDirection);

	public Direction endDirection();

	public Direction startDirection();

	public void clean();
}
