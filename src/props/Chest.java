package props;
import java.util.LinkedList;
import core.*;
import items.Gear;
import items.Item;
import items.Loot;
import items.Potion;

public class Chest implements Occupant {
	private boolean opened = false;
	private int x;
	private int y;
	private int level;
	private LinkedList<Item> inside = new LinkedList<Item>();

	public Chest(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		generateLoot();
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getName() {
		return "A chest, maybe with something inside.";
	}
	public String getTexture() {
		if(opened) {
			return GameConstants.CHEST_TEXTURE2;
		} else {
			return GameConstants.CHEST_TEXTURE1;
		}
	}
	
	public LinkedList<Item> open() {
		if(opened != true) {
			opened = true;
		}
		return inside;
	}

	public LinkedList<Item> getInside() {
		return inside;
	}
	
	private void generateLoot() {
		int itemCnt = GameConstants.getRand(0, 3);
		Slot[] gearOptions = {Slot.HELMET, Slot.ARMOR, Slot.WEAPON, Slot.CHARM};
		for(int i = 0; i < itemCnt; i++) {
			int roll = GameConstants.getRand(0, 2);
			if(roll == 0) {
				Gear newItem = Gear.generate(gearOptions[GameConstants.getRand(0, gearOptions.length - 1)], this.level);
				inside.add(newItem);
			} else if(roll == 1) {
				inside.add(Loot.generate(this.level));
			} else {
				inside.add(Potion.generate(this.level));
			}
		}
	}
}
