
public class Demon extends Enemy {

	public Demon(int x, int y, int level) {
		super(x, y, 40 + (8 * level), 12 + (5 * level), 50 + (10 * level), 85, 20, "Demon", GameConstants.DEMON_TEXTURE);
	}
}
