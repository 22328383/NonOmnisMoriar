package mobs;
import core.GameConstants;

public class Rat extends Enemy {

	public Rat(int x, int y, int level, String texture) {
		super(x, y, 5, 1, 1 + (1 * level), 10, 1, 3, 85, "Rat", texture, GameConstants.SFX_RODENT);
	}
}
