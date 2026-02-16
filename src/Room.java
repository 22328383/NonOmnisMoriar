import java.util.LinkedList;

public class Room {
    private Tile[][] grid;
    private LinkedList<Door> doors;

    public Room(int size) {
        doors = new LinkedList<Door>();
        grid = new Tile[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        generateRoom(size);
    }
    
    private void generateRoom() {
        generateRoom(1);
    }

    private void generateRoom(int roomSize) {
        for(int i = 0; i < GameConstants.GRID_SIZE; i++) {
            for(int j = 0; j < GameConstants.GRID_SIZE; j++) {
                if(i < roomSize || i >= GameConstants.GRID_SIZE - roomSize || j < roomSize || j >= GameConstants.GRID_SIZE - roomSize) {
                    grid[i][j] = Tile.VOID;
                } else if(i == roomSize || i == GameConstants.GRID_SIZE - roomSize - 1 || j == roomSize || j == GameConstants.GRID_SIZE - roomSize - 1) {
                    grid[i][j] = Tile.WALL;
                } else {
                    grid[i][j] = Tile.FLOOR;
                }
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }
    
    public void addDoor(int x, int y, Door endDoor, Room endRoom) {
    	grid[x][y] = Tile.DOOR;
    	Door newDoor = new Door(x, y, endDoor, endRoom);
    	doors.add(newDoor);
    }

    public LinkedList<Door> getDoors() {
        return doors;
    }
}
