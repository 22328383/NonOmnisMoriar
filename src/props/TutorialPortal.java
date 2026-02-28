package props;

import core.*;

public class TutorialPortal implements Occupant {

	private int x;
	private int y;

	public TutorialPortal(int x, int y) {
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
		return "A mysterious portal.";
	}
	public String getTexture() {
		return GameConstants.TUTORIAL_PORTAL_TEXTURE;
	}
}
