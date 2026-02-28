package items;

import core.GameConstants;
import core.Slot;
import core.Stats;

public class Potion extends Item {

	private int duration;
	private boolean isActive = false;
	private Stats boost;

	public Potion(String name, String texture, int duration, Stats boost) {
		super(name, texture, Slot.CONSUMABLE, 0, new Stats());
		this.duration = duration;
		this.boost = boost;
	}

	public boolean isActive() {
		return isActive;
	}

	public int getDuration() {
		return duration;
	}

	public Stats getBoost() {
		return boost;
	}

	public void activate(int maxHP, int currentHP) {
		if(duration == 0) {
		} else {
			isActive = true;
		}
	}

	public boolean tick() {
		if(!isActive) return true;
		duration = duration - 1;
		if(duration <= 0) {
			isActive = false;
			return false;
		}
		return true;
	}

	public boolean isInstant() {
		return (boost.maxHP == 0) && (boost.damageLow == 0) && (boost.damageHigh == 0) && (boost.accuracy == 0) && (boost.critChance == 0) && (boost.critModifier == 0) && (boost.dodgeChance == 0) && (boost.goldModifier == 0) && (duration == 0);
	}

	public String getEffectText() {
		if(getName().contains("Health")) {
			return "Heals 30% max HP";
		} else if(getName().contains("Unknown")) {
			return "??? effect for " + duration + " turns";
		} else {
			return boost.toShortString() + " for " + duration + " turns";
		}
	}

	public static Potion generate(int level) {
		int roll = GameConstants.getRand(0, 3);
		switch(roll) {
		case 0:
			return new Potion("Health Potion", GameConstants.POTION_HEALTH, 0, new Stats());
		case 1:
			return new Potion("Might Potion", GameConstants.POTION_MIGHT, 10,
				new Stats(0, 3, 3, 0, 0, 0, 0, 0));
		case 2:
			return new Potion("Greed Potion", GameConstants.POTION_GREED, 10,
				new Stats(0, 0, 0, 0, 0, 0, 0, 2));
		default:
			Stats random = new Stats();
			random.damageLow += GameConstants.getRand(-1, 1);
			random.damageHigh += GameConstants.getRand(-1, 1);
			random.accuracy += (10*GameConstants.getRand(-1, 1));
			random.critChance += (5*GameConstants.getRand(-1, 1));
			random.critModifier += GameConstants.getRand(-1, 1);
			random.dodgeChance += (5*GameConstants.getRand(-1, 1));
			random.goldModifier += GameConstants.getRand(-1, 1);
			return new Potion("Unknown Potion", GameConstants.POTION_UNKNOWN, 10, random);
		}
	}
}
