package core;

import java.util.Random;

public class GameConstants {
	public static final long SEED = 67; //or System.currentTimeMillis()
	private static Random rand = new Random(SEED);

	public static int getRand(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

	public static void resetRandom() {
		rand = new Random(SEED);
	}

	public static final int LOG_HEIGHT    = 200;
    public static final int WINDOW_WIDTH  = 500;
    public static final int WINDOW_HEIGHT = LOG_HEIGHT + 500;

    public static final int GRID_SIZE = 20;
    public static final int TILE_SIZE = WINDOW_WIDTH / GRID_SIZE;

    public static final int TARGET_FPS = 60;

    public static final int PLAYER_START_X = 10;
    public static final int PLAYER_START_Y = 10;
    
    public static final String BG_MUSIC = "res/audio/dungeon002.wav";

    public static final String SFX_SWORD1    = "res/audio/battle/sword.wav";
    public static final String SFX_SWORD2    = "res/audio/battle/sword2.wav";
    public static final String SFX_SWORD3    = "res/audio/battle/sword3.wav";
    public static final String SFX_SWORD4    = "res/audio/battle/sword4.wav";
    public static final String SFX_SWORD5    = "res/audio/battle/sword5.wav";
    public static final String SFX_MISS1     = "res/audio/battle/miss.wav";
    public static final String SFX_MISS2     = "res/audio/battle/miss2.wav";
    public static final String SFX_MISS3     = "res/audio/battle/miss3.wav";
    public static final String SFX_DEATH     = "res/audio/battle/death.wav";
    public static final String[] SFX_SWORDS  = {SFX_SWORD1, SFX_SWORD2, SFX_SWORD3, SFX_SWORD4, SFX_SWORD5};
    public static final String[] SFX_MISSES  = {SFX_MISS1, SFX_MISS2, SFX_MISS3};

    public static final String SFX_RODENT1   = "res/audio/NPC/rodent/bite-small.wav";
    public static final String SFX_RODENT2   = "res/audio/NPC/rodent/bite-small2.wav";
    public static final String SFX_RODENT3   = "res/audio/NPC/rodent/bite-small3.wav";
    public static final String[] SFX_RODENT  = {SFX_RODENT1, SFX_RODENT2, SFX_RODENT3};

    public static final String SFX_SLIME1    = "res/audio/NPC/slime/slime1.wav";
    public static final String SFX_SLIME2    = "res/audio/NPC/slime/slime2.wav";
    public static final String SFX_SLIME3    = "res/audio/NPC/slime/slime3.wav";
    public static final String[] SFX_SLIME   = {SFX_SLIME1, SFX_SLIME2, SFX_SLIME3};

    public static final String SFX_ORC1      = "res/audio/NPC/orc/ogre1.wav";
    public static final String SFX_ORC2      = "res/audio/NPC/orc/ogre2.wav";
    public static final String SFX_ORC3      = "res/audio/NPC/orc/ogre3.wav";
    public static final String[] SFX_ORC     = {SFX_ORC1, SFX_ORC2, SFX_ORC3};

    public static final String SFX_GIANT1    = "res/audio/NPC/orc/giant1.wav";
    public static final String SFX_GIANT2    = "res/audio/NPC/orc/giant2.wav";
    public static final String SFX_GIANT3    = "res/audio/NPC/orc/giant3.wav";
    public static final String[] SFX_GIANT   = {SFX_GIANT1, SFX_GIANT2, SFX_GIANT3};

    public static final String SFX_BEAST1    = "res/audio/NPC/beast/mnstr1.wav";
    public static final String SFX_BEAST2    = "res/audio/NPC/beast/mnstr2.wav";
    public static final String SFX_BEAST3    = "res/audio/NPC/beast/mnstr3.wav";
    public static final String[] SFX_BEAST   = {SFX_BEAST1, SFX_BEAST2, SFX_BEAST3};

    public static final String SFX_UNDEAD1   = "res/audio/NPC/undead/shade1.wav";
    public static final String SFX_UNDEAD2   = "res/audio/NPC/undead/shade2.wav";
    public static final String SFX_UNDEAD3   = "res/audio/NPC/undead/shade3.wav";
    public static final String[] SFX_UNDEAD  = {SFX_UNDEAD1, SFX_UNDEAD2, SFX_UNDEAD3};

    public static final String SFX_GOLD1     = "res/audio/inventory/gold.wav";
    public static final String SFX_GOLD2     = "res/audio/inventory/gold2.wav";
    public static final String SFX_GOLD3     = "res/audio/inventory/gold3.wav";
    public static final String[] SFX_GOLD    = {SFX_GOLD1, SFX_GOLD2, SFX_GOLD3};

    public static final String SFX_EQUIP1    = "res/audio/inventory/equip1.wav";
    public static final String SFX_EQUIP2    = "res/audio/inventory/equip2.wav";
    public static final String SFX_EQUIP3    = "res/audio/inventory/equip3.wav";
    public static final String[] SFX_EQUIP   = {SFX_EQUIP1, SFX_EQUIP2, SFX_EQUIP3};

    public static final String SFX_DOOR      = "res/audio/world/door.wav";
    public static final String SFX_UI1       = "res/audio/interface/interface1.wav";
    public static final String SFX_UI2       = "res/audio/interface/interface2.wav";
    public static final String SFX_UI3       = "res/audio/interface/interface3.wav";
    public static final String[] SFX_UI      = {SFX_UI1, SFX_UI2, SFX_UI3};

    public static final String FONT = "res/assets/fonts/rct2.otf";

    public static final String VOID_TEXTURE   = "res/assets/dungeon/wall/brick_dark_0.png";
    public static final String FLOOR_TEXTURE  = "res/assets/dungeon/wall/stone_brick_1.png";
    public static final String WALL_TEXTURE   = "res/assets/dungeon/wall/catacombs_0.png";
    public static final String DOOR_TEXTURE   = "res/assets/dungeon/doors/closed_door.png";
    public static final String STAIRS_TEXTURE = "res/assets/dungeon/gateways/stone_stairs_down.png";
    public static final String EXIT_TEXTURE1   = "res/assets/dungeon/gateways/exit.png";
    public static final String EXIT_TEXTURE2   = "res/assets/dungeon/gateways/exit_flickering.png";
    public static final String CHEST_TEXTURE1   = "res/assets/dungeon/chest_2_closed.png";
    public static final String CHEST_TEXTURE2   = "res/assets/dungeon/chest_2_open.png";

    public static final String SHOP_TEXTURE  = "res/assets/dungeon/shops/enter_shop.png";
    public static final String GOLD_TEXTURE =  "res/assets/item/gold/gold_pile_9.png";

    public static final String POTION_HEALTH  = "res/assets/item/potion/ruby_old.png";
    public static final String POTION_MIGHT   = "res/assets/item/potion/emerald.png";
    public static final String POTION_GREED   = "res/assets/item/potion/yellow_old.png";
    public static final String POTION_UNKNOWN = "res/assets/item/potion/unknown.png";

    public static final String HERO_TEXTURE   = "res/assets/monster/human.png";
    public static final String ORC_TEXTURE    = "res/assets/monster/orc_old.png";
    public static final String RAT_TEXTURE    = "res/assets/monster/animals/rat.png";
    public static final String GOBLIN_TEXTURE = "res/assets/monster/goblin_new.png";
    public static final String SLIME_TEXTURE = "res/assets/monster/jelly.png";
    public static final String TROLL_TEXTURE = "res/assets/monster/troll.png";
    public static final String KNIGHT_TEXTURE = "res/assets/monster/orc_knight_old.png";
    public static final String WIZARD_TEXTURE = "res/assets/monster/orc_wizard_new.png";
    public static final String BEAST_TEXTURE  = "res/assets/monster/demons/beast.png";
    public static final String DRAKE_TEXTURE = "res/assets/monster/dragons/dragon.png";
    public static final String DEMON_TEXTURE  = "res/assets/monster/demons/balrug_new.png";

    public static final int DEFAULT_MAX_HP        = 25;
    public static final int DEFAULT_DAMAGE_LOW    = 3;
    public static final int DEFAULT_DAMAGE_HIGH   = 5;
    public static final int DEFAULT_ACCURACY      = 100;
    public static final int DEFAULT_CRIT_CHANCE   = 5;
    public static final int DEFAULT_CRIT_MODIFIER = 2;
    public static final int DEFAULT_DODGE_CHANCE  = 0;
    public static final int DEFAULT_GOLD_MODIFIER = 1;
}
