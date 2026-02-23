
public class Wizard extends Enemy {

	public Wizard(int x, int y, int level) {
		super(x, y, 12 + (3 * level), 8 + (3 * level), 15 + (5 * level), 80, 15, 8, 60, "Wizard", GameConstants.WIZARD_TEXTURE);
	}
}
