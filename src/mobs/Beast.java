package mobs;
import core.GameConstants;

public class Beast extends Enemy {

	public Beast(int x, int y, int level, String texture) {
		super(x, y, 25 + (5 * level), 7 + (3 * level), 20 + (6 * level), 60, 8, 5, 85, "Beast", texture, GameConstants.SFX_BEAST);
	}
}
