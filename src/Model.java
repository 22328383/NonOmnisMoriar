import java.util.LinkedList;
import java.util.Random;

import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Model {
    private GameObject player;
    private Controller controller = Controller.getInstance();
    private int score = 0;
    private int currLevel = 0;
    private Room room;
    private int playerX;
    private int playerY;
	private LinkedList<Level> dungeon = new LinkedList<Level>();
	private LinkedList<String> log = new LinkedList<String>();
	static private Random r = new Random();

    public Model() {
        player = new GameObject(
                GameConstants.HERO_TEXTURE,
                GameConstants.TILE_SIZE,
                GameConstants.TILE_SIZE,
                new Point3f(
                    GameConstants.PLAYER_START_X * GameConstants.TILE_SIZE,
                    GameConstants.PLAYER_START_Y * GameConstants.TILE_SIZE,
                    0
                )
        );
        addLog("Welcome to Non Omnis Moriar");
        Level newLevel = new Level(currLevel);
        dungeon.add(newLevel);
        room = newLevel.getCurrentRoom();
    }

    public void gamelogic() {
        playerLogic();
    }

    public void computeLocation() {
        playerX = (int) (player.getCentre().getX() / GameConstants.TILE_SIZE);
        playerY = (int) (player.getCentre().getY() / GameConstants.TILE_SIZE);
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public Tile roomRight() {
        computeLocation();
        if(playerX + 1 < GameConstants.GRID_SIZE) {
            return room.getGrid()[playerX+1][playerY];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomLeft() {
        computeLocation();
        if(playerX - 1 >= 0) {
            return room.getGrid()[playerX-1][playerY];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomDown() {
        computeLocation();
        if(playerY + 1 < GameConstants.GRID_SIZE) {
            return room.getGrid()[playerX][playerY+1];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomUp() {
        computeLocation();
        if(playerY - 1 >= 0) {
            return room.getGrid()[playerX][playerY-1];
        } else {
            return Tile.NOTHING;
        }
    }

    private void playerLogic() {
    	computeLocation();
    	
        if(controller.isKeyAPressed()) {
            Controller.getInstance().setKeyAPressed(false);
            switch(roomLeft()) {
            case FLOOR:
            	player.getCentre().ApplyVector(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.getCentre().ApplyVector(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            	}
            	break;
            case DOOR:
            	player.getCentre().ApplyVector(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            	doorLogic();
            	break;
            default :
            	break;
            }
        }

        if(controller.isKeyDPressed()) {
            Controller.getInstance().setKeyDPressed(false);
            switch(roomRight()) {
            case FLOOR:
            	player.getCentre().ApplyVector(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.getCentre().ApplyVector(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            	}
            	break;
            case DOOR:
            	player.getCentre().ApplyVector(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            	doorLogic();
            	break;
            default :
            	break;
            }
        }

        if(controller.isKeyWPressed()) {
            Controller.getInstance().setKeyWPressed(false);
            switch(roomUp()) {
            case FLOOR:
            	player.getCentre().ApplyVector(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.getCentre().ApplyVector(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            	}
            	break;
            case DOOR:
            	player.getCentre().ApplyVector(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            	doorLogic();
            	break;
            default :
            	break;
            }
        }

        if(controller.isKeySPressed()) {
            Controller.getInstance().setKeySPressed(false);
            switch(roomDown()) {
            case FLOOR:
            	player.getCentre().ApplyVector(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.getCentre().ApplyVector(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            	}
            	break;
            case DOOR:
            	player.getCentre().ApplyVector(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            	doorLogic();
            	break;
            default :
            	break;
            }
        }

        if(controller.isKeySpacePressed()) {
            Controller.getInstance().setKeySpacePressed(false);
        }
    }
    
    private int encounterMob() {
    	LinkedList<Enemy> mobs = room.getMobs();
    	computeLocation();
    	for(int i = 0; i < mobs.size(); i++) {
    		if( (playerX == mobs.get(i).getX()) && (playerY == mobs.get(i).getY()) ) {
    			return i;
    		}
    	}
    	return -1;
    }

    private void attackMob(int mobIdx) {
    	LinkedList<Enemy> mobs = room.getMobs();
    	Enemy target = mobs.get(mobIdx);
    	int dmg = 5; //TODO replace with player damage stat later
    	target.takeDamage(dmg);
    	addLog("You hit the " + target.getName() + " for " + dmg + " damage.");
    	if(target.isDead()) {
    		addLog("The " + target.getName() + " dies!");
    		mobs.remove(mobIdx);
    	}
    }
    
    private void doorLogic() {
    	computeLocation();
    	int onDoor = isPlayerOnDoor();
    	if(onDoor >= 0) {
    		doorTransition(onDoor);
    	}
    }
    
    private int isPlayerOnDoor() {
    	for(int i = 0; i < room.getDoors().size(); i++) {
    		if( (playerX == room.getDoors().get(i).getX()) && (playerY == room.getDoors().get(i).getY()) ) {
    			return i;
    		}
    	}
    	return -1;
    	
    }
    
    private void doorTransition(int onDoor) {
		Room endRoom = room.getDoors().get(onDoor).getEndRoom();
		Door endDoor = room.getDoors().get(onDoor).getEndDoor();
		room = endRoom;
		String logTransition = "You enter room " + currLevel + "-" + (char)('A' + dungeon.get(currLevel).getAllRooms().indexOf(endRoom)) + ".";
		addLog(logTransition);
		dungeon.get(currLevel).setCurrentRoom(endRoom);
		player.getCentre().setX(endDoor.getX() * GameConstants.TILE_SIZE);
		player.getCentre().setY(endDoor.getY() * GameConstants.TILE_SIZE);
    }
    
    public void addLog(String string) {
    	if(log.size() >= 8) {
    		log.removeFirst();
    	}
    	log.add(string);
    }
    
    public LinkedList<String> getLog() {
    	return log;
    }

    public GameObject getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public Tile[][] getRoom() {
        return room.getGrid();
    }

    public LinkedList<Enemy> getMobs() {
        return room.getMobs();
    }
    
    static public int getRand(int min, int max) {
    	return r.nextInt(max - min + 1) + min;
    }
}
