import java.util.LinkedList;
import java.util.Random;

import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Model {
    private Player player;
    private Controller controller = Controller.getInstance();
    private int score = 0;
    private boolean gameOver = false;
    private int currLevel = 0;
    private Room room;
	private LinkedList<Level> dungeon = new LinkedList<Level>();
	private LinkedList<String> log = new LinkedList<String>();
	static private Random r = new Random();

    public Model() {
        player = new Player();
        addLog("Welcome to Non Omnis Moriar...");
        Level newLevel = new Level(currLevel);
        dungeon.add(newLevel);
        room = newLevel.getCurrentRoom();
    }

    public void gamelogic() {
    	clickLogic();
    	if(gameOver) {
    		return;
    	}
    	if(player.isDead()) {
    		addLog("You are dead.");
    		gameOver = true;
    		return;
    	}
        boolean playerMoved = playerLogic();
        if(playerMoved) {
        	enemyLogic();
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

    		LinkedList<Enemy> mobs = room.getMobs();
    		for(int i = 0; i < mobs.size(); i++) {
    			if(mobs.get(i).getX() == cx && mobs.get(i).getY() == cy) {
    				Enemy mob = mobs.get(i);
    				addLog("You see a " + mob.getName() + ". Has " + mob.getHealth() + " health.");
    				return;
    			}
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
    		if(mobs.get(i).distToPlayer(playerX, playerY) <= mobs.get(i).getVision()) {
    			if(getRand(1, 100) > mobs.get(i).getSpeed()) {
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
    		addLog(mob.getName() + " hits you for " + mob.getDamage() + " damage.");
    		player.takeDamage(mob.getDamage());
    		return;
    	}
    	if(room.getGrid()[newX][newY] != Tile.FLOOR) {
    		return;
    	}

    	for(int j = 0; j < mobs.size(); j++) {
    		if(mobs.get(j) != mob && mobs.get(j).getX() == newX && mobs.get(j).getY() == newY) {
    			return;
    		}
    	}
    	mob.setX(newX);
    	mob.setY(newY);
    }

    public Tile roomRight() {
        player.computeLocation();
        if(player.getTileX() + 1 < GameConstants.GRID_SIZE) {
            return room.getGrid()[player.getTileX()+1][player.getTileY()];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomLeft() {
        player.computeLocation();
        if(player.getTileX() - 1 >= 0) {
            return room.getGrid()[player.getTileX()-1][player.getTileY()];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomDown() {
        player.computeLocation();
        if(player.getTileY() + 1 < GameConstants.GRID_SIZE) {
            return room.getGrid()[player.getTileX()][player.getTileY()+1];
        } else {
            return Tile.NOTHING;
        }
    }

    public Tile roomUp() {
        player.computeLocation();
        if(player.getTileY() - 1 >= 0) {
            return room.getGrid()[player.getTileX()][player.getTileY()-1];
        } else {
            return Tile.NOTHING;
        }
    }

    private boolean playerLogic() {
    	boolean moveMade = false;
    	player.computeLocation();

        if(controller.isKeyAPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeyAPressed(false);
            switch(roomLeft()) {
            case FLOOR:
            	player.move(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.move(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            	}
            	break;
            case DOOR:
            	player.move(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            	doorLogic();
            	break;
            case STAIRS:
            	currLevel++;
                Level newLevel = new Level(currLevel);
                dungeon.add(newLevel);
                room = newLevel.getCurrentRoom();
                player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
                addLog("You descend to level " + currLevel + "...");
                break;
            default :
            	break;
            }
        }

        if(controller.isKeyDPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeyDPressed(false);
            switch(roomRight()) {
            case FLOOR:
            	player.move(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.move(new Vector3f(-GameConstants.TILE_SIZE, 0, 0));
            	}
            	break;
            case DOOR:
            	player.move(new Vector3f(GameConstants.TILE_SIZE, 0, 0));
            	doorLogic();
            	break;
            case STAIRS:
            	currLevel++;
                Level newLevelD = new Level(currLevel);
                dungeon.add(newLevelD);
                room = newLevelD.getCurrentRoom();
                player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
                addLog("You descend to level " + currLevel + "...");
                break;
            default :
            	break;
            }
        }

        if(controller.isKeyWPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeyWPressed(false);
            switch(roomUp()) {
            case FLOOR:
            	player.move(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.move(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            	}
            	break;
            case DOOR:
            	player.move(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            	doorLogic();
            	break;
            case STAIRS:
            	currLevel++;
                Level newLevelW = new Level(currLevel);
                dungeon.add(newLevelW);
                room = newLevelW.getCurrentRoom();
                player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
                addLog("You descend to level " + currLevel + "...");
                break;
            default :
            	break;
            }
        }

        if(controller.isKeySPressed()) {
        	moveMade = true;
            Controller.getInstance().setKeySPressed(false);
            switch(roomDown()) {
            case FLOOR:
            	player.move(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            	int mobIdx = encounterMob();
            	if(mobIdx >= 0) {
            		attackMob(mobIdx);
            		player.move(new Vector3f(0, GameConstants.TILE_SIZE, 0));
            	}
            	break;
            case DOOR:
            	player.move(new Vector3f(0, -GameConstants.TILE_SIZE, 0));
            	doorLogic();
            	break;
            case STAIRS:
            	currLevel++;
                Level newLevelS = new Level(currLevel);
                dungeon.add(newLevelS);
                room = newLevelS.getCurrentRoom();
                player.setPosition(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y);
                addLog("You descend to level " + currLevel + "...");
                break;
            default :
            	break;
            }
        }

        if(controller.isKeySpacePressed()) {
        	moveMade = true;
            Controller.getInstance().setKeySpacePressed(false);
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
    		addLog(target.getName() + " dies!");
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
