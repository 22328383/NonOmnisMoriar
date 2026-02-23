
public class Troll extends Enemy {
	
	public Troll(int x, int y, int level) {
		super(x, y, 15 + (2 * level), 5 + (2 * level), 10 + (4 * level), 30, 3, 4, 50, "Troll", GameConstants.TROLL_TEXTURE);
	}
}
