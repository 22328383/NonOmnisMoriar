package mobs;
import core.*;

public abstract class Enemy implements Occupant {

    protected int x;
    protected int y;
    protected int health;
    protected int damage;
    protected int gold;
    protected int accuracy;
    protected int critChance;
    protected int vision;
    protected int speed;
    protected String name;
    protected String texture;
    protected String[] sounds;

    public Enemy(int x, int y, int health, int damage, int gold, int accuracy, int critChance, int vision, int speed, String name, String texture, String[] sounds) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        this.gold = gold;
        this.accuracy = accuracy;
        this.critChance = critChance;
        this.vision = vision;
        this.speed = speed;
        this.name = name;
        this.texture = texture;
        this.sounds = sounds;
    }

    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getTexture() {
		return texture;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public int getHealth() {
		return health;
	}

	public void takeDamage(int amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int distToPlayer(int playerX, int playerY) {
    	return (Math.abs(x - playerX) + Math.abs(y - playerY));
    }

    public int getVision() {
    	return vision;
    }

    public int getSpeed() {
    	return speed;
    }
    
    public int getGold() {
    	return gold;
    }

    public int getAccuracy() {
    	return accuracy;
    }

    public int getCritChance() {
    	return critChance;
    }

    public String getSound() {
    	return sounds[GameConstants.getRand(0, sounds.length - 1)];
    }

}
