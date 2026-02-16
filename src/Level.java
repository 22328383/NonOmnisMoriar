import java.util.LinkedList;

public class Level {
    private LinkedList<Room> allRooms;
    private Room currentRoom;
    private int levelNumber;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        generateRooms();
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
    
    private void generateRooms() {
    	Room room1 = new Room(1);
    	Room room2 = new Room(3);
    	allRooms = new LinkedList<Room>();
    	allRooms.add(room1);
    	allRooms.add(room2);
    	room1.addDoor(18, 10, null, null);
    	room2.addDoor(3, 10, null, null);
    	room1.getDoors().get(0).link(room2.getDoors().get(0), room2);
    	room2.getDoors().get(0).link(room1.getDoors().get(0), room1);
    	currentRoom = room1;
    }
}
