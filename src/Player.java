import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player {

    private GameObject sprite;
    private int maxHP = 25;
    private int hitPoints = 25;
    private int damage = 5;
    private int tileX;
    private int tileY;

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

    public int getMaxHP() {
        return maxHP;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDamage() {
        return damage;
    }

    public GameObject getSprite() {
        return sprite;
    }
}
