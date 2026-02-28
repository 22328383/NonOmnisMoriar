package items;

import core.*;

public abstract class Item {
	private String name;
	private String texture;
	private Slot slot;
	private int value;
	private Stats stats;

	public Item(String name, String texture, Slot slot, int value, Stats stats) {
		this.name = name;
		this.texture = texture;
		this.slot = slot;
		this.value = value;
		this.stats = stats;
	}

	public String getName() { return name; }
	public String getTexture() { return texture; }
	public Slot getSlot() { return slot; }
	public int getValue() { return value; }
	public Stats getStats() { return stats; }
}
