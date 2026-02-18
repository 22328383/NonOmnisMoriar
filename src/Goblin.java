
public class Goblin extends Enemy {
	
	public Goblin(int x, int y, int level) {
		super(x, y, 5 + (2 * level), 2 + (1 * level), 5 + (2 * level), 60, 5, "Goblin", GameConstants.GOBLIN_TEXTURE);
	}
}
