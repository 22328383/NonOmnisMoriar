
public class Drake extends Enemy {

	public Drake(int x, int y, int level) {
		super(x, y, 30 + (6 * level), 10 + (4 * level), 25 + (8 * level), 75, 12, 7, 80, "Drake", GameConstants.DRAKE_TEXTURE);
	}
}
