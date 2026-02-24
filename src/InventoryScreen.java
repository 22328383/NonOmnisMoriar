import java.awt.Graphics;

public class InventoryScreen {
	private Player player;
	private TextureCache textureCache;

	public InventoryScreen(Player player, TextureCache textureCache) {
		this.player = player;
		this.textureCache = textureCache;
	}

	// Called by Viewer each frame when in INV state
	public void draw(Graphics g) {
		// TODO: draw your inventory screen here
	}

	// Called by Model each frame when in INV state
	// Return true to close inventory (switch back to PLAY)
	public boolean handleInput(Controller controller) {
		if(controller.isKeyIPressed()) {
			Controller.getInstance().setKeyIPressed(false);
			return true;
		}

		// TODO: handle cursor, equip, use, drop etc.

		return false;
	}
}
