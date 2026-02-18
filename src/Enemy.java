public abstract class Enemy {

    protected int x;
    protected int y;
    protected int health;
    protected int damage;
    protected int gold;
    protected int accuracy;
    protected int critChance;
    protected String name;
    protected String texture;

    public Enemy(int x, int y, int health, int damage, int gold, int accuracy, int critChance, String name, String texture) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        this.gold = gold;
        this.accuracy = accuracy;
        this.critChance = critChance;
        this.name = name;
        this.texture = texture;
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

	public void takeDamage(int amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }
    
}
