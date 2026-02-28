import java.util.HashMap;
import java.util.LinkedList;

import core.*;
import items.Item;
import items.Loot;
import items.Potion;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player {

    private GameObject sprite;
    private Stats baseStats = defaultStats();
    private int hitPoints = GameConstants.DEFAULT_MAX_HP;
    private int tileX;
    private int tileY;
    private int gold = 0;
    private HashMap<Slot, Item> equipped = new HashMap<Slot, Item>();
    private LinkedList<Loot> lootBag = new LinkedList<Loot>();
    private LinkedList<Potion> potions = new LinkedList<Potion>();

    public HashMap<Slot, Item> getEquipped() {
		return equipped;
	}

    public LinkedList<Loot> getLootBag() {
		return lootBag;
	}

    public LinkedList<Potion> getPotions() {
		return potions;
	}

    public void heal(int amount) {
		hitPoints += amount;
		if(hitPoints > getMaxHP()) {
			hitPoints = getMaxHP();
		}
	}

    public void tickPotions() {
		for(int i = potions.size() - 1; i >= 0; i--) {
			if(!potions.get(i).tick()) {
				potions.remove(i);
			}
		}
	}

    public int sellLoot() {
		int total = 0;
		for(Loot l : lootBag) {
			total += l.getValue();
		}
		lootBag.clear();
		return total;
	}

	public Player() {
        sprite = new GameObject(
            GameConstants.HERO_TEXTURE,
            GameConstants.TILE_SIZE,
            GameConstants.TILE_SIZE,
            new Point3f(
                GameConstants.PLAYER_START_X * GameConstants.TILE_SIZE,
                GameConstants.PLAYER_START_Y * GameConstants.TILE_SIZE,
                0
            )
        );
        computeLocation();
    }

    public void computeLocation() {
        tileX = (int) (sprite.getCentre().getX() / GameConstants.TILE_SIZE);
        tileY = (int) (sprite.getCentre().getY() / GameConstants.TILE_SIZE);
    }

    public void move(Vector3f direction) {
        sprite.getCentre().ApplyVector(direction);
        computeLocation();
    }

    public void setPosition(int tileX, int tileY) {
        sprite.getCentre().setX(tileX * GameConstants.TILE_SIZE);
        sprite.getCentre().setY(tileY * GameConstants.TILE_SIZE);
        computeLocation();
    }

    public void takeDamage(int amount) {
        hitPoints -= amount;
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public Stats getBaseStats() {
        return baseStats;
    }

    public Stats getEffectiveStats() {
    	Stats effective = new Stats();
    	for(Item gear : equipped.values()) {
    	    if(gear != null) {
    	    	effective = effective.add(gear.getStats());
    	    }
    	}
    	for(Potion p : potions) {
    		if(p.isActive()) {
    			effective = effective.add(p.getBoost());
    		}
    	}
    	effective = effective.add(baseStats);
        return effective;
    }

    public int getMaxHP() {
        return getEffectiveStats().maxHP;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDamageLow() {
        return getEffectiveStats().damageLow;
    }

    public int getDamageHigh() {
        return getEffectiveStats().damageHigh;
    }

    public int getAccuracy() {
        return getEffectiveStats().accuracy;
    }

    public int getCritChance() {
        return getEffectiveStats().critChance;
    }

    public int getCritModifier() {
        return getEffectiveStats().critModifier;
    }

    public int getDodgeChance() {
        return getEffectiveStats().dodgeChance;
    }

    public int getGoldModifier() {
        return getEffectiveStats().goldModifier;
    }

    public GameObject getSprite() {
        return sprite;
    }

	public int getGold() {
		return gold;
	}

	public void addGold(int gold) {
		this.gold = this.gold + gold;
	}

	public void reset() {
		this.gold      = 0;
		this.baseStats = defaultStats();
		this.hitPoints = GameConstants.DEFAULT_MAX_HP;
		this.equipped  = new HashMap<Slot, Item>();
		this.lootBag   = new LinkedList<Loot>();
		this.potions   = new LinkedList<Potion>();
	}

	private static Stats defaultStats() {
		return new Stats(
			GameConstants.DEFAULT_MAX_HP,
			GameConstants.DEFAULT_DAMAGE_LOW,
			GameConstants.DEFAULT_DAMAGE_HIGH,
			GameConstants.DEFAULT_ACCURACY,
			GameConstants.DEFAULT_CRIT_CHANCE,
			GameConstants.DEFAULT_CRIT_MODIFIER,
			GameConstants.DEFAULT_DODGE_CHANCE,
			GameConstants.DEFAULT_GOLD_MODIFIER
		);
	}
}
