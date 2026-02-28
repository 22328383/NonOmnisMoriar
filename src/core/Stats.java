package core;

public class Stats {
	public int maxHP;
	public int damageLow;
	public int damageHigh;
	public int accuracy;
	public int critChance;
	public int critModifier;
	public int dodgeChance;
	public int goldModifier;

	public Stats(int maxHP, int damageLow, int damageHigh, int accuracy, int critChance, int critModifier, int dodgeChance, int goldModifier) {
		this.maxHP         = maxHP;
		this.damageLow     = damageLow;
		this.damageHigh    = damageHigh;
		this.accuracy      = accuracy;
		this.critChance    = critChance;
		this.critModifier  = critModifier;
		this.dodgeChance   = dodgeChance;
		this.goldModifier  = goldModifier;
	}

	public Stats() {
		this(0, 0, 0, 0, 0, 0, 0, 0);
	}

	public Stats add(Stats other) {
		return new Stats(
			maxHP + other.maxHP,
			damageLow + other.damageLow,
			damageHigh + other.damageHigh,
			accuracy + other.accuracy,
			critChance + other.critChance,
			critModifier + other.critModifier,
			dodgeChance + other.dodgeChance,
			goldModifier + other.goldModifier
		);
	}
	
	@Override
	public String toString() {
	    return "HP:" + maxHP +
	           " DMG:" + damageLow + "-" + damageHigh +
	           " ACC:" + accuracy +
	           " CRIT%:" + critChance +
	           " CRITPWR:" + critModifier +
	           " DODGE:" + dodgeChance +
	           " GOLD:" + goldModifier;
	}

	public String toShortString() {
	    String s = "";
	    
	    if(maxHP != 0) {
	        if (maxHP > 0) {
	            s += "+" + maxHP + " HP  ";
	        } else {
	            s += maxHP + " HP  ";
	        }
	    }
	    if(damageLow != 0 || damageHigh != 0) {
	        if (damageLow > 0) {
	            s += "+" + damageLow + "-" + damageHigh + " DMG  ";
	        } else {
	            s += damageLow + "-" + damageHigh + " DMG  ";
	        }
	    }
	    if(accuracy != 0) {
	        if (accuracy > 0) {
	            s += "+" + accuracy + " ACC  ";
	        } else {
	            s += accuracy + " ACC  ";
	        }
	    }   
	    if(critChance != 0) {
	        if (critChance > 0) {
	            s += "+" + critChance + "% CRIT  ";
	        } else {
	            s += critChance + "% CRIT  ";
	        }
	    }    
	    if(critModifier != 0) {
	        if (critModifier > 0) {
	            s += "+" + critModifier + "x CRIT  ";
	        } else {
	            s += critModifier + "x CRIT  ";
	        }
	    } 
	    if(dodgeChance != 0) {
	        if (dodgeChance > 0) {
	            s += "+" + dodgeChance + " DODGE  ";
	        } else {
	            s += dodgeChance + " DODGE  ";
	        }
	    }  
	    if(goldModifier != 0) {
	        if (goldModifier > 0) {
	            s += "+" + goldModifier + " GOLD  ";
	        } else {
	            s += goldModifier + " GOLD  ";
	        }
	    } 
	    return s.trim();
	}

}
