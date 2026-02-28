package items;

import core.GameConstants;
import core.Slot;
import core.Stats;

public class Loot extends Item {

	// { name, texture, baseValue }
	static final Object[][] LOOT_TABLE = {
		{ "Ruby Ring",       "res/assets/item/amulet/ring_red.png",        5  },
		{ "Emerald Ring",    "res/assets/item/amulet/ring_green.png",      5  },
		{ "Sapphire Ring",   "res/assets/item/amulet/ring_cyan.png",       5  },
		{ "Gold Necklace",   "res/assets/item/amulet/celtic_yellow.png",   8  },
		{ "Silver Necklace", "res/assets/item/amulet/celtic_blue.png",     6  },
		{ "Goblet",          "res/assets/item/misc/misc_phial.png",        4  },
		{ "Crystal Orb",     "res/assets/item/misc/misc_orb.png",          7  },
		{ "Cultist Idol",    "res/assets/item/amulet/face_1_gold.png",     10 },
		{ "Cultist Amulet",  "res/assets/item/amulet/cameo_blue.png",      10 },
		{ "Pearl Pendant",   "res/assets/item/amulet/stone_1_pink.png",    7  },
		{ "Jade Figurine",   "res/assets/item/amulet/stone_2_green.png",   6  },
		{ "Magic Lamp",      "res/assets/item/misc/magic_lamp.png",        9  },
		{ "Ancient Coin",    "res/assets/item/gold/gold_pile_1.png",       3  },
		{ "Ornate Mirror",   "res/assets/item/misc/mirror.png",            5  },
		{ "Decorative Horn", "res/assets/item/misc/misc_horn.png",         8  },
	};

	static final String[] QUALITIES  = { "Cracked", "Flawed", "Fine", "Perfect" };
	static final int[] MULTIPLIERS   = { 1, 2, 3, 4 };
	static final int[] THRESHOLDS    = { 60, 75, 90, 100};

	public Loot(String name, String texture, int value) {
		super(name, texture, Slot.LOOT, value, new Stats());
	}

	public static Loot generate(int level) {
		int idx = GameConstants.getRand(0, LOOT_TABLE.length - 1);
		Object[] row = LOOT_TABLE[idx];
		String baseName = (String) row[0];
		String texture  = (String) row[1];
		int baseValue   = (int) row[2];

		int roll = GameConstants.getRand(0, 99);
		int tier = 0;
		for(int i = 0; i < THRESHOLDS.length; i++) {
			if(roll < THRESHOLDS[i]) {
				tier = i;
				break;
			}
		}

		int value = (baseValue + level) * MULTIPLIERS[tier];
		String name = QUALITIES[tier] + " " + baseName;
		return new Loot(name, texture, value);
	}
}
