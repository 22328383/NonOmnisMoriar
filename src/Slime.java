
public class Slime extends Enemy {
	
	public Slime(int x, int y, int level) {
		super(x, y, 4 + (2 * level), 1 + (1 * level), 3 + (2 * level), 10, 5, "Slime", GameConstants.SLIME_TEXTURE);
	}
}
