import java.util.LinkedList;

public class Room {
    private Tile[][] grid;
    private LinkedList<Door> doors;

    public Room() {
        doors = new LinkedList<Door>();
        grid = new Tile[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public LinkedList<Door> getDoors() {
        return doors;
    }
}
