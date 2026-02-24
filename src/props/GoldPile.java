package props;

import core.*;

public class GoldPile implements Occupant {

	private int x;
	private int y;
	private int gold;

	public GoldPile(int x, int y, int gold) {
		this.x = x;
		this.y = y;
		this.gold = gold;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getName() {
		return "Gold Pile (" + gold + "g)";
	}
	public String getTexture() {
		return GameConstants.GOLD_TEXTURE;
	}
	public int getGold() {
		return gold;
	}
}
