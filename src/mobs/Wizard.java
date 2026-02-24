package mobs;

public class Wizard extends Enemy {

	public Wizard(int x, int y, int level, String texture) {
		super(x, y, 12 + (3 * level), 8 + (3 * level), 15 + (5 * level), 80, 15, 8, 60, "Wizard", texture);
	}
}
