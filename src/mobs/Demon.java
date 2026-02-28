package mobs;
import core.GameConstants;

public class Demon extends Enemy {

	public Demon(int x, int y, int level, String texture) {
		super(x, y, 40 + (8 * level), 12 + (5 * level), 50 + (10 * level), 85, 20, 10, 90, "Demon", texture, GameConstants.SFX_BEAST);
	}
}
