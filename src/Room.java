import java.util.LinkedList;
import mobs.*;
import core.*;
import props.*;

public class Room {
    private Tile[][] grid;
    private String[][] textures;
    private Occupant[][] occupants;
    private LinkedList<Door> doors;
    private LinkedList<Enemy> mobs = new LinkedList<Enemy>();
    private int X;
    private int Y;
    private int level;

    public Room(int sizeX, int sizeY, int level) {
        doors = new LinkedList<Door>();
        grid = new Tile[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        textures = new String[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        occupants = new Occupant[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        generateRoom(sizeX, sizeY);
        X = sizeX;
        Y = sizeY;
        this.level = level;
    }

    private void generateRoom() {
        generateRoom(1, 1);
    }

    private void generateRoom(int xSize, int ySize) {
        for(int i = 0; i < GameConstants.GRID_SIZE; i++) {
            for(int j = 0; j < GameConstants.GRID_SIZE; j++) {
                if(i < xSize || i >= GameConstants.GRID_SIZE - xSize || j < ySize || j >= GameConstants.GRID_SIZE - ySize) {
                    grid[i][j] = Tile.VOID;
                    textures[i][j] = GameConstants.VOID_TEXTURES[GameConstants.getRand(0, GameConstants.VOID_TEXTURES.length - 1)];
                } else if(i == xSize || i == GameConstants.GRID_SIZE - xSize - 1 || j == ySize || j == GameConstants.GRID_SIZE - ySize - 1) {
                    grid[i][j] = Tile.WALL;
                    textures[i][j] = GameConstants.WALL_TEXTURES[GameConstants.getRand(0, GameConstants.WALL_TEXTURES.length - 1)];
                } else {
                    grid[i][j] = Tile.FLOOR;
                    textures[i][j] = GameConstants.FLOOR_TEXTURES[GameConstants.getRand(0, GameConstants.FLOOR_TEXTURES.length - 1)];
                }
            }
        }
    }


    public int[] getRandomWallPosition() {
        for(int attempt = 0; attempt < 20; attempt++) {
            int side = Model.getRand(0, 3);
            int wallX, wallY;
            switch(side) {
            case 0:
                wallX = X;
                wallY = Model.getRand(Y + 1, GameConstants.GRID_SIZE - Y - 2);
                break;
            case 1:
                wallX = GameConstants.GRID_SIZE - X - 1;
                wallY = Model.getRand(Y + 1, GameConstants.GRID_SIZE - Y - 2);
                break;
            case 2:
                wallX = Model.getRand(X + 1, GameConstants.GRID_SIZE - X - 2);
                wallY = Y;
                break;
            default:
                wallX = Model.getRand(X + 1, GameConstants.GRID_SIZE - X - 2);
                wallY = GameConstants.GRID_SIZE - Y - 1;
                break;
            }

            if(grid[wallX][wallY] != Tile.DOOR) {
                return new int[]{wallX, wallY};
            }
        }
        return null;
    }
    
    private int rollTier(int level) {
        int[] startWeight = {80, 10, 5, 3, 2};
        int[] endWeight   = {25, 25, 25, 20, 5};
        int[] weight      = new int[5];
        int total = 0;

        for(int i = 0; i < 5; i++) {
            weight[i] = startWeight[i] + (endWeight[i]-startWeight[i])*level / 10;
            total = total + weight[i];
        }

        int percent = Model.getRand(1, total);
        for(int i = 0; i < weight.length; i++) {
            percent = percent - weight[i];
            if(percent <= 0) {
                return i;
            }
        }

        return weight.length - 1;
    }
    
    public void spawnMobs() {
    	if(!mobs.isEmpty()) {
    		return;
    	}

    	int mobCnt = Model.getRand(0, 2);
    	for(int i = 0; i < mobCnt; i++) {
    		int tier = rollTier(level);
    		int[] pos = getRandomFloorPosition();
    		if(pos != null) {
    			Enemy mob = createEnemy(tier, pos[0], pos[1], level);
    			if(mob != null) {
    				mobs.add(mob);
    				occupants[pos[0]][pos[1]] = mob;
    			}
    		}
    	}
    }

    public int[] getRandomFloorPosition() {
    	for(int attempt = 0; attempt < 30; attempt++) {
    		int rx = Model.getRand(X + 1, GameConstants.GRID_SIZE - X - 2);
    		int ry = Model.getRand(Y + 1, GameConstants.GRID_SIZE - Y - 2);
    		if(grid[rx][ry] != Tile.FLOOR) {
    			continue;
    		}
    		if(rx == GameConstants.PLAYER_START_X && ry == GameConstants.PLAYER_START_Y) {
    			continue;
    		}

    		boolean nearDoor = false;
    		for(int i = 0; i < doors.size(); i++) {
    			int dist = Math.abs(rx - doors.get(i).getX()) + Math.abs(ry - doors.get(i).getY());
    			if(dist < 2) {
    				nearDoor = true;
    				break;
    			}
    		}
    		if(nearDoor) {
    			continue;
    		}
    		if(occupants[rx][ry] != null) {
    			continue;
    		}

    		return new int[]{rx, ry};
    	}
    	return null;
    }

    private Enemy createEnemy(int tier, int x, int y, int level) {
    	int[][] tierTable = {
    		{0, 1},       //Common:    Rat, Slime
    		{2, 3},       //Uncommon:  Orc, Goblin
    		{4, 5},       //Rare:      Troll, Knight
    		{6, 7},       //Epic:      Wizard, Beast
    		{8, 9},       //Legendary: Drake, Demon
    	};

    	int[] pool = tierTable[tier];
    	int id = pool[Model.getRand(0, pool.length - 1)];
    	return buildEnemy(id, x, y, level);
    }

    private Enemy buildEnemy(int id, int x, int y, int level) {
    	switch(id) {
    		case 0:  return new Rat(x, y, level, GameConstants.RAT_TEXTURE);
    		case 1:  return new Slime(x, y, level, GameConstants.SLIME_TEXTURE);
    		case 2:  return new Orc(x, y, level, GameConstants.ORC_TEXTURE);
    		case 3:  return new Goblin(x, y, level, GameConstants.GOBLIN_TEXTURE);
    		case 4:  return new Troll(x, y, level, GameConstants.TROLL_TEXTURE);
    		case 5:  return new Knight(x, y, level, GameConstants.KNIGHT_TEXTURE);
    		case 6:  return new Wizard(x, y, level, GameConstants.WIZARD_TEXTURE);
    		case 7:  return new Beast(x, y, level, GameConstants.BEAST_TEXTURE);
    		case 8:  return new Drake(x, y, level, GameConstants.DRAKE_TEXTURE);
    		case 9:  return new Demon(x, y, level, GameConstants.DEMON_TEXTURE);
    		default: return new Rat(x, y, level, GameConstants.RAT_TEXTURE);
    	}
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public String[][] getTextures() {
        return textures;
    }

    public void addDoor(int x, int y, Door endDoor, Room endRoom) {
    	grid[x][y] = Tile.DOOR;
    	Door newDoor = new Door(x, y, endDoor, endRoom);
    	doors.add(newDoor);
    }

    public LinkedList<Door> getDoors() {
        return doors;
    }

    public LinkedList<Enemy> getMobs() {
        return mobs;
    }

    public int getX() {
    	return X;
    }

    public int getY() {
    	return Y;
    }
    
    public void setOccupant(int x, int y, Occupant o) {
    	occupants[x][y] = o;
    }
    public Occupant getOccupant(int x, int y) {
    	return occupants[x][y];
    }
    public void clearOccupant(int x, int y) {
    	occupants[x][y] = null;
    }
    public Occupant[][] getOccupants() {
    	return occupants;
    }

	public void makeStairs() {
		int[] stairs = getRandomFloorPosition();
		while(stairs == null) {
			stairs = getRandomFloorPosition();
		}
		grid[stairs[0]][stairs[1]] = Tile.STAIRS;
	}
	
	public void makeExit() {
		int[] exit = getRandomFloorPosition();
		while(exit == null) {
			exit = getRandomFloorPosition();
		}
		occupants[exit[0]][exit[1]] = new Exit(exit[0], exit[1]);
	}
	
}
