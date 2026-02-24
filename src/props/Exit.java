package props;

import core.*;

public class Exit implements Occupant {

	private int x;
	private int y;

	public Exit(int x, int y) {
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
		return "A way out of this mess";
	}
	public String getTexture() {
		return GameConstants.EXIT_TEXTURE1;
	}
}
