package props;

import core.*;

public class Shop implements Occupant {

	private int x;
	private int y;

	public Shop(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getName() {
		return "A merchant. Bump into him to sell your loot.";
	}
	public String getTexture() {
		return GameConstants.SHOP_TEXTURE;
	}
}
