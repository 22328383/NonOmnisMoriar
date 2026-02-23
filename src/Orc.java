
public class Orc extends Enemy {
	
	public Orc(int x, int y, int level) {
		super(x, y, 10 + (2 * level), 1 + (1 * level), 5 + (2 * level), 50, 3, 5, 75, "Orc", GameConstants.ORC_TEXTURE);
	}
}
