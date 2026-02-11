import java.util.LinkedList;

public class Level {
    private LinkedList<Room> allRooms;
    private Room currentRoom;
    private int levelNumber;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        allRooms = new LinkedList<Room>();
    }

    public LinkedList<Room> getAllRooms() {
        return allRooms;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
