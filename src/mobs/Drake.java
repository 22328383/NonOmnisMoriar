package mobs;

public class Drake extends Enemy {

	public Drake(int x, int y, int level, String texture) {
		super(x, y, 30 + (6 * level), 10 + (4 * level), 25 + (8 * level), 75, 12, 7, 80, "Drake", texture);
	}
}
