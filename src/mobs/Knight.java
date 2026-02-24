package mobs;

public class Knight extends Enemy {

	public Knight(int x, int y, int level, String texture) {
		super(x, y, 20 + (4 * level), 6 + (2 * level), 15 + (5 * level), 70, 10, 6, 90, "Knight", texture);
	}
}
