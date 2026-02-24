import java.util.LinkedList;
import java.util.Random;

import mobs.*;
import core.*;
import props.*;
import util.GameObject;
import util.Point3f;
import util.Vector3f;


public class Model {
	private int highScore = 0;
	private GameState state = GameState.PLAY;
    private Player player;
    private Controller controller = Controller.getInstance();
    private int currLevel = 0;
    private Room room;
    private Room deathRoom = null;
    private int deathX = -1;
    private int deathY = -1;
	private LinkedList<Level> dungeon = new LinkedList<Level>();
	private LinkedList<String> log = new LinkedList<String>();
	static private Random r = new Random();

	public enum GameState {
		PLAY,
		INV
	}
	
    public Model() {
        player = new Player();
        addLog("Welcome to Non Omnis Moriar...");
        Level newLevel = new Level(currLevel);
        dungeon.add(newLevel);
        room = newLevel.getCurrentRoom();
    }

    public void gamelogic() {
    	switch(state) {
    	case INV:
            if(controller.isKeyIPressed()) {
            	state = GameState.PLAY;
            	Controller.getInstance().setKeyIPressed(false);
            }
    		break;
    	default:
        	clickLogic();
        	if(player.isDead()) {
        		if(deathRoom != null) {
        			deathRoom.clearOccupant(deathX, deathY);
        		}
        		deathRoom = room;
        		deathX = player.getTileX();
        		deathY = player.getTileY();
        		room.setOccupant(deathX, deathY, new GoldPile(deathX, deathY, player.getGold()));
        		addLog("You have fallen. Non Omnis Moriar.");
        		player.reset();
        		player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
        		currLevel = 0;
        		room = dungeon.get(0).getAllRooms().get(0);
        		dungeon.get(0).setCurrentRoom(room);
        	}
            boolean playerMoved = playerLogic();
            if(playerMoved) {
            	enemyLogic();
            }
            break;
    	}
    }
    
    private void clickLogic() {
    	if(controller.isClicked()) {
    		controller.setClicked(false);
    		int cx = controller.getClickX();
    		int cy = controller.getClickY();

    		player.computeLocation();
    		if(cx == player.getTileX() && cy == player.getTileY()) {
    			addLog("It is you. You have " + player.getHitPoints() + "/" + player.getMaxHP() + " health.");
    			return;
    		}

    		Occupant occ = room.getOccupant(cx, cy);
    		if(occ != null) {
    		    if(occ instanceof Enemy) {
    		        Enemy mob = (Enemy)occ;
    		        addLog("You see a " + mob.getName() + ". Seems to have " + mob.getHealth() + " health.");
    		    } else if(occ instanceof Exit) {
    		        addLog("A way out of this mess. Walk into it to escape with your gold.");
    		    } else if(occ instanceof GoldPile) {
    		        GoldPile pile = (GoldPile)occ;
    		        addLog("A pile of " + pile.getGold() + " gold. Walk over it to reclaim.");
    		    }
    		    return;
    		}

    		for(int i = 0; i < room.getDoors().size(); i++) {
    			if(room.getDoors().get(i).getX() == cx && room.getDoors().get(i).getY() == cy) {
    				addLog("A door to another room.");
    				return;
    			}
    		}

    		if(cx >= 0 && cx < GameConstants.GRID_SIZE && cy >= 0 && cy < GameConstants.GRID_SIZE) {
    			switch(room.getGrid()[cx][cy]) {
    			case WALL:
    				addLog("You see a solid wall.");
    				break;
    			case FLOOR:
    				addLog("You see some stone flooring.");
    				break;
    			case STAIRS:
    				addLog("You see stairs. They lead to some place deeper.");
    				break;
    			case VOID:
    				addLog("You see nothing but darkness.");
    				break;
    			default:
    				break;
    			}
    		}
    	}
    }

    private void enemyLogic() {
    	LinkedList<Enemy> mobs = room.getMobs();
    	player.computeLocation();
    	int playerX = player.getTileX();
    	int playerY = player.getTileY();
    	for(int i = 0; i < mobs.size(); i++) {
    		int dist = mobs.get(i).distToPlayer(playerX, playerY);
    		if(dist <= mobs.get(i).getVision()) {
    			if(dist > 1 && getRand(1, 100) > mobs.get(i).getSpeed()) {
    				continue;
    			}
    			int xDist = mobs.get(i).getX() - playerX;
    			int yDist = mobs.get(i).getY() - playerY;
    			if(Math.abs(xDist) - Math.abs(yDist) >= 0) {
    				if(xDist >= 0) {
    					tryMoveMob(mobs.get(i), mobs.get(i).getX() - 1, mobs.get(i).getY(), mobs);
    				} else {
    					tryMoveMob(mobs.get(i), mobs.get(i).getX() + 1, mobs.get(i).getY(), mobs);
    				}
    			} else {
    				if(yDist >= 0) {
    					tryMoveMob(mobs.get(i), mobs.get(i).getX(), mobs.get(i).getY() - 1, mobs);
    				} else {
    					tryMoveMob(mobs.get(i), mobs.get(i).getX(), mobs.get(i).getY() + 1, mobs);
    				}
    			}
    		} else {
    			int step = getRand(1, 100);
    			if(step <= 25) {
    				int direction = getRand(1,4);
    				switch(direction) {
    				case 1:
    					tryMoveMob(mobs.get(i), mobs.get(i).getX(), mobs.get(i).getY() - 1, mobs);
    					break;
    				case 2:
    					tryMoveMob(mobs.get(i), mobs.get(i).getX() + 1, mobs.get(i).getY(), mobs);
    					break;
    				case 3:
    					tryMoveMob(mobs.get(i), mobs.get(i).getX(), mobs.get(i).getY() + 1, mobs);
    					break;
    				default:
    					tryMoveMob(mobs.get(i), mobs.get(i).getX() - 1, mobs.get(i).getY(), mobs);
    					break;
    				}
    			}
     		}
    	}
    }

    private void tryMoveMob(Enemy mob, int newX, int newY, LinkedList<Enemy> mobs) {
    	if(newX < 0 || newX >= GameConstants.GRID_SIZE || newY < 0 || newY >= GameConstants.GRID_SIZE) {
    		return;
    	}

    	if(newX == player.getTileX() && newY == player.getTileY()) {
    		if(getRand(1, 100) > mob.getAccuracy()) {
    			addLog(mob.getName() + " tries to hit you but misses!");
    		} else if(getRand(1, 100) <= mob.getCritChance()) {
    			int critDmg = mob.getDamage() * 2;
    			addLog(mob.getName() + " crits you for " + critDmg + " damage!");
    			player.takeDamage(critDmg);
    		} else {
    			addLog(mob.getName() + " hits you for " + mob.getDamage() + " damage.");
    			player.takeDamage(mob.getDamage());
    		}
    		return;
    	}
    	if(room.getGrid()[newX][newY] != Tile.FLOOR) {
    		return;
    	}

    	if(room.getOccupant(newX, newY) != null) {
    		return;
    	}
    	room.clearOccupant(mob.getX(), mob.getY());
    	mob.setX(newX);
    	mob.setY(newY);
    	room.setOccupant(newX, newY, mob);
    }

    private Tile checkTile(int dx, int dy) {
        int nx = player.getTileX() + dx;
        int ny = player.getTileY() + dy;
        if(nx >= 0 && nx < GameConstants.GRID_SIZE && ny >= 0 && ny < GameConstants.GRID_SIZE) {
            return room.getGrid()[nx][ny];
        }
        return Tile.NOTHING;
    }

    private void handleMove(Tile targetTile, Vector3f moveDir) {
    	switch(targetTile) {
    	case FLOOR:
    		player.move(moveDir);
    		Occupant occupant = room.getOccupant(player.getTileX(), player.getTileY());
    		if(occupant instanceof GoldPile) {
    		    GoldPile pile = (GoldPile) occupant;
    		    player.addGold(pile.getGold());
    		    room.clearOccupant(player.getTileX(), player.getTileY());
    		    deathRoom = null;
    		    addLog("You reclaimed " + pile.getGold() + " gold! Non Omnis Moriar.");
    		} else if(occupant instanceof Exit) {
    		    cashOut();
    		    break;
    		}
    		int mobIdx = encounterMob();
    		if(mobIdx >= 0) {
    			attackMob(mobIdx);
    			player.move(new Vector3f(-moveDir.getX(), -moveDir.getY(), 0));
    		}
    		break;
    	case DOOR:
    		player.move(moveDir);
    		doorLogic();
    		break;
    	case STAIRS:
    		descendLevel();
    		break;
    	default:
    		break;
    	}
    }

    private void descendLevel() {
    	dungeon.get(currLevel).resetRoom();
    	currLevel++;
    	if(currLevel < dungeon.size()) {
    		room = dungeon.get(currLevel).getCurrentRoom();
    	} else {
    		Level newLevel = new Level(currLevel);
    		dungeon.add(newLevel);
    		room = newLevel.getCurrentRoom();
    	}
    	player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
    	addLog("You descend to level " + currLevel + "...");
    }

    private void cashOut() {
    	highScore += player.getGold();
    	if(deathRoom != null) {
    		deathRoom.clearOccupant(deathX, deathY);
    	}
    	deathRoom = null;
    	deathX = -1;
    	deathY = -1;
    	addLog("You escape with your life... and " + player.getGold() + " gold!");
    	player.reset();
    	player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
    	currLevel = 0;
    	dungeon.clear();
    	Level newLevel = new Level(currLevel);
    	dungeon.add(newLevel);
    	room = newLevel.getCurrentRoom();
    }

    private boolean playerLogic() {
    	boolean moveMade = false;
    	player.computeLocation();

        if(controller.isKeyAPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeyAPressed(false);
            handleMove(checkTile(-1, 0), new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
        }

        if(controller.isKeyDPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeyDPressed(false);
            handleMove(checkTile(1, 0), new Vector3f(GameConstants.TILE_SIZE, 0, 0));
        }

        if(controller.isKeyWPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeyWPressed(false);
            handleMove(checkTile(0, -1), new Vector3f(0, GameConstants.TILE_SIZE, 0));
        }

        if(controller.isKeySPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeySPressed(false);
            handleMove(checkTile(0, 1), new Vector3f(0, -GameConstants.TILE_SIZE, 0));
        }

        if(controller.isKeySpacePressed()) {
        	moveMade = true;
            Controller.getInstance().setKeySpacePressed(false);
        }
        
        if(controller.isKeyIPressed()) {
        	state = GameState.INV;
        	Controller.getInstance().setKeyIPressed(false);
        }

        return moveMade;
    }

    private int encounterMob() {
    	LinkedList<Enemy> mobs = room.getMobs();
    	player.computeLocation();
    	for(int i = 0; i < mobs.size(); i++) {
    		if( (player.getTileX() == mobs.get(i).getX()) && (player.getTileY() == mobs.get(i).getY()) ) {
    			return i;
    		}
    	}
    	return -1;
    }

    private void attackMob(int mobIdx) {
    	LinkedList<Enemy> mobs = room.getMobs();
    	Enemy target = mobs.get(mobIdx);
    	int dmg = player.getDamage();
    	target.takeDamage(dmg);
    	addLog("You hit the " + target.getName() + " for " + dmg + " damage.");
    	if(target.isDead()) {
    		room.clearOccupant(target.getX(), target.getY());
    		addLog(target.getName() + " dies!");
    		player.addGold(target.getGold() * (currLevel+1));
    		mobs.remove(mobIdx);
    	}
    }

    private void doorLogic() {
    	player.computeLocation();
    	int onDoor = isPlayerOnDoor();
    	if(onDoor >= 0) {
    		doorTransition(onDoor);
    	}
    }

    private int isPlayerOnDoor() {
    	for(int i = 0; i < room.getDoors().size(); i++) {
    		if( (player.getTileX() == room.getDoors().get(i).getX()) && (player.getTileY() == room.getDoors().get(i).getY()) ) {
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
		player.setPosition(endDoor.getX(), endDoor.getY());
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

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getRoom() {
        return room.getGrid();
    }

    public Occupant[][] getOccupants() {
        return room.getOccupants();
    }

    public LinkedList<Enemy> getMobs() {
        return room.getMobs();
    }

    static public int getRand(int min, int max) {
    	return r.nextInt(max - min + 1) + min;
    }
    
    public GameState getState() {
    	return state;
    }
}
