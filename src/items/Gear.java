package items;

import core.GameConstants;
import core.Slot;
import core.Stats;

public class Gear extends Item {

	// { name, texture, goldValue, maxHP, dmgLow, dmgHigh, accuracy, critChance, critMod, dodge, goldMod }
	static final Object[][] WEAPONS = {
		//swords
		{ "Cutlass",           "res/assets/item/weapon/cutlass_1.png",           8,  0, 2, 4, 0, 0, 0, 0, 0 },
		{ "Falchion",          "res/assets/item/weapon/falchion_3.png",          10, 0, 3, 5, 0, 0, 0, 0, 0 },
		{ "Longsword",         "res/assets/item/weapon/long_sword_1_old.png",    12, 0, 3, 6, 0, 0, 0, 0, 0 },
		{ "Rapier",            "res/assets/item/weapon/rapier_1.png",            11, 0, 1, 3, 5, 5, 0, 0, 0 },
		{ "Shortsword",        "res/assets/item/weapon/short_sword_1_new.png",   7,  0, 2, 3, 5, 0, 0, 0, 0 },
		{ "Zweihander",        "res/assets/item/weapon/two_handed_sword.png",    18, 0, 5, 8,-5, 0, 0,-5, 0 },
		//blunt
		{ "Club",              "res/assets/item/weapon/club_old.png",            4,  0, 2, 5,-5, 0, 0, 0, 0 },
		{ "Flanged Mace",      "res/assets/item/weapon/mace_1_old.png",          10, 0, 3, 6,-5, 0, 0, 0, 0 },
		{ "Morningstar",       "res/assets/item/weapon/morningstar_3.png",       14, 0, 4, 7,-10,0, 0, 0, 0 },
		{ "Quarterstaff",      "res/assets/item/weapon/quarterstaff_new.png",    5,  0, 1, 3, 0, 0, 0, 5, 0 },
		{ "Warhammer",         "res/assets/item/weapon/hammer_1_old.png",        15, 0, 4, 7,-5, 0, 0,-5, 0 },
		{ "Warmaul",           "res/assets/item/weapon/mace_large_3.png",        20, 0, 5, 9,-10,0, 0,-10,0 },
		//daggers
		{ "Kris Dagger",       "res/assets/item/weapon/dagger_3.png",            9,  0, 1, 2, 0, 10,1, 5, 0 },
		{ "Castillon Dagger",  "res/assets/item/weapon/dagger_6.png",            8,  0, 1, 3, 0, 8, 1, 0, 0 },
		{ "Rondel Dagger",     "res/assets/item/weapon/dagger_7.png",            10, 0, 1, 2, 5, 12,1, 0, 0 },
		{ "Stiletto Dagger",   "res/assets/item/weapon/orcish_dagger.png",       11, 0, 1, 2, 0, 15,1, 5, 0 },
		//spearlikes
		{ "Bardiche",          "res/assets/item/weapon/glaive_1.png",            14, 0, 4, 7, 0, 0, 0,-5, 0 },
		{ "Halberd",           "res/assets/item/weapon/trishula.png",            16, 0, 4, 8,-5, 0, 0,-5, 0 },
		{ "Spear",             "res/assets/item/weapon/spear_2_new.png",         9,  0, 3, 5, 5, 0, 0, 0, 0 },
		//axes
		{ "Battleaxe",         "res/assets/item/weapon/broad_axe_1.png",         14, 0, 4, 7,-5, 5, 0, 0, 0 },
		{ "Hatchet",           "res/assets/item/weapon/hand_axe_3.png",          7,  0, 2, 4, 0, 5, 0, 0, 0 },
		{ "Double Axe",        "res/assets/item/weapon/executioner_axe.png",     19, 0, 5, 8,-10,0, 0,-5, 0 },
		//misc
		{ "Magic Staff",       "res/assets/item/staff/staff_mage.png",           12, 0, 2, 4, 0, 5, 0, 0, 1 },
		{ "Gauntlets",         "res/assets/item/armor/hands/gauntlet_1.png",     8,  0, 1, 3, 10,5, 0, 5, 0 },
	};

	static final Object[][] HELMETS = {
		//heavy
		{ "Armet",             "res/assets/item/armor/headgear/helmet_1_visored.png",  12, 6, 0, 0, 0, 0, 0,-5, 0 },
		{ "Barbute",           "res/assets/item/armor/headgear/helmet_2.png",          10, 5, 0, 0, 0, 0, 0,-3, 0 },
		{ "Chapel De Fer",     "res/assets/item/armor/headgear/helmet_4.png",          8,  4, 0, 0, 0, 0, 0,-2, 0 },
		{ "Steel Helm",        "res/assets/item/armor/headgear/helmet_1.png",          14, 7, 0, 0, 0, 0, 0,-5, 0 },
		{ "Hounskull",         "res/assets/item/armor/headgear/helmet_4_visor.png",    11, 6, 0, 0,-5, 0, 0,-3, 0 },
		{ "Kettle Hat",        "res/assets/item/armor/headgear/helmet_5.png",          7,  4, 0, 0, 0, 0, 0, 0, 0 },
		{ "Sallet",            "res/assets/item/armor/headgear/helmet_2_etched.png",   9,  5, 0, 0, 0, 0, 0,-2, 0 },
		//light
		{ "Bycocket",          "res/assets/item/armor/headgear/hat_2.png",             4,  1, 0, 0, 0, 0, 0, 5, 0 },
		{ "Coif",              "res/assets/item/armor/headgear/cap_2.png",             5,  3, 0, 0, 0, 0, 0, 2, 0 },
		{ "Hat",               "res/assets/item/armor/headgear/hat_1.png",             3,  1, 0, 0, 0, 0, 0, 3, 0 },
		{ "Hood",              "res/assets/item/armor/headgear/elven_leather_helm.png", 5, 2, 0, 0, 0, 0, 0, 5, 0 },
		{ "Bonnet",            "res/assets/item/armor/headgear/cap_1.png",             3,  1, 0, 0, 0, 0, 0, 3, 0 },
		{ "Ninja Cowl",        "res/assets/item/armor/headgear/green_mask.png",        8,  1, 0, 0, 0, 5, 0, 8, 0 },
		{ "Ranger Hood",       "res/assets/item/armor/headgear/hat_3.png",             6,  2, 0, 0, 5, 0, 0, 5, 0 },
		{ "Rogue Cowl",        "res/assets/item/armor/headgear/green_mask.png",        7,  1, 0, 0, 0, 8, 0, 6, 0 },
		{ "Cloth Mask",        "res/assets/item/armor/headgear/green_mask.png",        4,  1, 0, 0, 0, 3, 0, 3, 0 },
		{ "Straw Hat",         "res/assets/item/armor/headgear/hat_1.png",             3,  1, 0, 0, 0, 0, 0, 2, 1 },
		//magic
		{ "Cultist Mask",      "res/assets/item/armor/headgear/cornuthaum.png",        7,  2, 0, 0, 0, 5, 1, 0, 0 },
		{ "Jester Cap",        "res/assets/item/armor/headgear/cap_jester.png",        5,  1, 0, 0, 0, 3, 0, 3, 1 },
		{ "Wizard Hat",        "res/assets/item/armor/headgear/wizard_hat_1.png",      8,  3, 0, 0, 0, 5, 1, 0, 0 },
	};

	static final Object[][] ARMORS = {
		//heavy
		{ "Crusader Armor",    "res/assets/item/armor/torso/plate_mail_1.png",         25, 10,0, 0, 0, 0, 0,-8, 0 },
		{ "Cuirass",           "res/assets/item/armor/torso/plate_1.png",              20, 8, 0, 0, 0, 0, 0,-5, 0 },
		{ "Plate Armor",       "res/assets/item/armor/torso/plate_mail_2.png",         30, 12,0, 0,-5, 0, 0,-10,0 },
		{ "Brigandine",        "res/assets/item/armor/torso/splint_mail_1.png",        16, 7, 0, 0, 0, 0, 0,-3, 0 },
		{ "Templar Armor",     "res/assets/item/armor/torso/chain_mail_3.png",         22, 9, 0, 0, 0, 0, 0,-5, 0 },
		//medium
		{ "Gambeson",          "res/assets/item/armor/torso/leather_armor_2.png",      10, 5, 0, 0, 0, 0, 0, 0, 0 },
		{ "Aketon",            "res/assets/item/armor/torso/leather_armor_1.png",      8,  4, 0, 0, 0, 0, 0, 0, 0 },
		{ "Doublet",           "res/assets/item/armor/torso/leather_armor_3.png",      7,  3, 0, 0, 0, 0, 0, 2, 0 },
		{ "Pourpoint",         "res/assets/item/armor/torso/studded_leather_armor.png",8,  3, 0, 0, 5, 0, 0, 0, 0 },
		{ "Warden Outfit",     "res/assets/item/armor/torso/ring_mail_2_new.png",      9,  4, 0, 0, 0, 0, 0, 3, 0 },
		{ "Marauder Outfit",   "res/assets/item/armor/torso/animal_skin_3.png",        8,  3, 0, 0, 0, 3, 0, 3, 0 },
		//light
		{ "Commoner Tunic",    "res/assets/item/armor/torso/robe_1_new.png",           3,  2, 0, 0, 0, 0, 0, 3, 0 },
		{ "Wizard Garb",       "res/assets/item/armor/torso/robe_ego_1.png",           7,  2, 0, 0, 0, 5, 1, 0, 0 },
		{ "Woven Robe",        "res/assets/item/armor/torso/robe_2_new.png",           4,  2, 0, 0, 0, 0, 0, 2, 0 },
		{ "Courtly Dress",     "res/assets/item/armor/torso/robe_3.png",               5,  1, 0, 0, 0, 0, 0, 5, 1 },
		{ "Frock",             "res/assets/item/armor/torso/robe_1_old.png",           3,  2, 0, 0, 0, 0, 0, 3, 0 },
		{ "Vestments",         "res/assets/item/armor/torso/robe_art_1.png",           6,  3, 0, 0, 0, 3, 0, 0, 0 },
		{ "Cultist Robe",      "res/assets/item/armor/torso/robe_ego_2.png",           6,  2, 0, 0, 0, 5, 1, 0, 0 },
		{ "Cultist Tunic",     "res/assets/item/armor/torso/robe_2_old.png",           5,  2, 0, 0, 0, 3, 0, 0, 0 },
		{ "Jester Outfit",     "res/assets/item/armor/torso/robe_art_2.png",           4,  1, 0, 0, 0, 3, 0, 5, 1 },
		{ "Vagabond Attire",   "res/assets/item/armor/torso/animal_skin_1_new.png",    4,  2, 0, 0, 0, 0, 0, 5, 0 },
	};

	static final Object[][] CHARMS = {
		{ "Lucky Coin",              "res/assets/item/gold/gold_pile_1.png",           6,  0, 0, 0, 0, 5, 0, 5, 2 },
		{ "Letter from Home",        "res/assets/item/scroll/scroll_old.png",          2,  3, 0, 0, 5, 0, 0, 0, 0 },
		{ "Picture of a Loved One",  "res/assets/item/misc/runes/generic.png",         2,  5, 0, 0, 0, 0, 0, 3, 0 },
		{ "Book",                    "res/assets/item/book/cloth_new.png",         	   4,  0, 0, 0, 5, 5, 1, 0, 0 },
		{ "Cultist Mark",            "res/assets/item/misc/misc_rune.png",             5,  2, 0, 1, 0, 3, 0, 3, 0 },
		{ "Elf Ear Necklace",        "res/assets/item/amulet/bone_gray.png",           7,  0, 0, 0, 0, 0, 0, 8, 1 },
		{ "Wolf Fang",               "res/assets/item/amulet/cylinder_gray.png",       6,  0, 2, 3, 0, 3, 0, 0, 0 },
		{ "Rabbit Foot",             "res/assets/item/amulet/celtic_yellow.png",       5, -3, 0, 0, 0, 0, 0, 10,2 },
		{ "Crucifix",                "res/assets/item/amulet/crystal_white.png",       6,  5, 0, 0, 5, 0, 0, 0, 0 },
		{ "Pair of Dice",            "res/assets/item/misc/misc_stone_new.png",        5,  0, 0, 0, 0, 8, 0, 0, 3 },
		{ "Four Leaf Clover",        "res/assets/item/amulet/celtic_blue.png",         4,  0, 0, 0, 0, 0, 0, 5, 2 },
		{ "Snowberry Branch",        "res/assets/item/wand/gem_iron.png",              5,  0, 1, 1, 0, 8, 1, 0, 0 },
		{ "Dragon Scale",            "res/assets/item/amulet/stone_3_green.png",       10, 5, 0, 0, 0, 0, 0, 0, 0 },
		{ "Penance Shackle",         "res/assets/item/amulet/ring_red.png",            6, -2, 0, 0, 0, 0, 0, 10,2 },
		{ "Demon Tail",              "res/assets/item/amulet/eye_magenta.png",         8, -5, 3, 4, 0, 5, 1, 0, 0 },
	};

	static final Object[][] SUFFIXES = {
		{ "Rage",          -3, 2, 3, -5, 0, 0, -5, 0 },
		{ "Fury",           0, 1, 2,  0, 5, 0, -3, 0 },
		{ "Slaughter",     -5, 3, 4, -10,0, 0, -5, 0 },
		{ "Precision",      0, 0, 1, 10, 5, 0,  0, 0 },
		{ "the Hawk",       0, 0, 0, 10, 8, 1,  0, 0 },
		{ "the Fortress",   8, 0, 0,  0, 0, 0, -5, 0 },
		{ "Endurance",      5, 0, 0,  0, 0, 0,  3, 0 },
		{ "the Wall",      10, 0, 0, -5, 0, 0,-10, 0 },
		{ "Evasion",       -2, 0, 0,  0, 0, 0, 10, 0 },
		{ "the Wind",       0, 0, 0,  5, 0, 0,  8, 0 },
		{ "Greed",         -3, 0, 0,  0, 0, 0,  0, 3 },
		{ "Fortune",        0, 0, 0,  0, 0, 0,  5, 2 },
		{ "the Merchant",  -5, 0, 0, -5, 0, 0,  0, 4 },
		{ "Lethality",      0, 0, 0,  0, 10,1, -5, 0 },
		{ "the Creed",     -3, 1, 1,  0, 12,1,  5, 0 },
		{ "the Owl",        3, 0, 0,  5, 3, 0,  3, 0 },
		{ "Thorns",         0, 1, 2,  0, 0, 0,  0, 0 },
		{ "the Fallen",    -5, 2, 3,  0, 5, 1, -3, 0 },
		{ "Sacrifice",     -8, 3, 5,  0, 8, 1,  0, 2 },
		{ "Balance",        2, 1, 1,  2, 2, 0,  2, 0 }
	};

	static final Object[][] PREFIXES = {
		{ "Blessed",        3, 0, 0,  0, 0, 0,  0, 0 },
		{ "Sturdy",         2, 0, 0,  0, 0, 0,  2, 0 },
		{ "Swift",          0, 0, 0,  3, 0, 0,  3, 0 },
		{ "Keen",           0, 0, 1,  0, 3, 0,  0, 0 },
		{ "Gilded",         0, 0, 0,  0, 0, 0,  0, 2 },
		{ "Lucky",          0, 0, 0,  0, 5, 0,  3, 0 },
		{ "Sharpened",      0, 1, 1,  0, 0, 0,  0, 0 },
		{ "Hardened",       3, 0, 0,  0, 0, 0, -2, 0 },
		{ "Unholy",        -3, 0, 0,  0, 5, 1,  0, 0 },
		{ "Rusty",          0,-1, 0, -3, 0, 0,  0, 0 },
		{ "Abandoned",     -2, 0, 0, -2, 0, 0,  0, 0 },
		{ "Cursed",        -4, 1, 2,  0, 3, 0, -3, 0 },
		{ "Tattered",      -1, 0, 0, -2, 0, 0, -2, 0 },
		{ "Wicked",        -2, 2, 2,  0, 0, 0, -2, 0 },
		{ "Ancient",        1, 0, 1,  0, 2, 0,  0, 0 },
		{ "Ornate",         0, 0, 0,  0, 0, 0,  0, 1 }
	};

	public Gear(String name, String texture, Slot slot, int value, Stats stats) {
		super(name, texture, slot, value, stats);
	}

	private static Stats modifierFromRow(Object[] row) {
		return new Stats((int)row[1], (int)row[2], (int)row[3], (int)row[4], (int)row[5], (int)row[6], (int)row[7], (int)row[8]);
	}

	private static Stats statsFromRow(Object[] row) {
		return new Stats((int)row[3], (int)row[4], (int)row[5], (int)row[6], (int)row[7], (int)row[8], (int)row[9], (int)row[10]);
	}

	private static Object[][] getTable(Slot slot) {
		switch(slot) {
		case WEAPON:
			return WEAPONS;
		case HELMET:
			return HELMETS;
		case ARMOR:
			return ARMORS;
		case CHARM:
			return CHARMS;
		default:
			return null;
		}
	}
	
	private static Stats levelScale(Stats original, int level) {
	    Stats bonus = new Stats();
	    if(original.maxHP > 0) {
	        bonus.maxHP = level;
	    }
	    if(original.maxHP < 0) {
	        bonus.maxHP = -level;
	    }
	    if(original.damageLow > 0) {
	        bonus.damageLow = level;
	    }
	    if(original.damageHigh > 0) {
	        bonus.damageHigh = level;
	    }
	    if(original.accuracy != 0) {
	        if(original.accuracy > 0) {
	            bonus.accuracy = level * 1;
	        } else {
	            bonus.accuracy = level * -1;
	        }
	    }
	    if(original.critChance > 0) {
	        bonus.critChance = level;
	    }
	    if(original.critModifier > 0) {
	        bonus.critModifier = level / 3;
	    }
	    if(original.dodgeChance != 0) {
	        if(original.dodgeChance > 0) {
	            bonus.dodgeChance = level * 1;
	        } else {
	            bonus.dodgeChance = level * -1;
	        }
	    }
	    if(original.goldModifier > 0) {
	        bonus.goldModifier = level / 3;
	    }
	    return original.add(bonus);
	}
	
	public static Gear generate(Slot slot, int level) {
		Object[][] table = getTable(slot);
		int pickedIdx = GameConstants.getRand(0, table.length - 1);
		Object[] row = table[pickedIdx];
		String baseName = (String)row[0];
		String texture = (String)row[1];
		int goldValue = (int)row[2] + level * 2;
		Stats stats = statsFromRow(row);

		stats = levelScale(stats, level);

		int suffixIdx = GameConstants.getRand(0, SUFFIXES.length - 1);
		String suffix = (String) SUFFIXES[suffixIdx][0];
		stats = stats.add(modifierFromRow(SUFFIXES[suffixIdx]));

		String prefix = null;
		if(GameConstants.getRand(0, 99) >= 15) {
			int prefixIdx = GameConstants.getRand(0, PREFIXES.length - 1);
			prefix = (String) PREFIXES[prefixIdx][0];
			stats = stats.add(modifierFromRow(PREFIXES[prefixIdx]));
		}

		String name = baseName + " of " + suffix;
		if(prefix != null) {
			name = prefix + " " + name;
		}

		return new Gear(name, texture, slot, goldValue, stats);
	}
}
