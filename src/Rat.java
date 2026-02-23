public class Rat extends Enemy {
	
	public Rat(int x, int y, int level) {
		super(x, y, 5, 1, 1 + (1 * level), 10, 1, 3, 85, "Rat", GameConstants.RAT_TEXTURE);
	}
}
