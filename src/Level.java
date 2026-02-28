import java.util.LinkedList;

import props.Chest;
import props.Shop;

public class Level {
    private LinkedList<Room> allRooms;
    private Room currentRoom;
    private int levelNumber;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        int roomCnt = Model.getRand(2, 8);
        generateRooms(roomCnt);
        for(int i = 0; i < allRooms.size(); i++) {
        	allRooms.get(i).spawnMobs();
        }
        spawnChest();
        spawnShop();
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
    
    public void resetRoom() {
    	this.currentRoom = allRooms.get(0);
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    private void connectRooms(Room roomA, Room roomB) {
        int[] posA = roomA.getRandomWallPosition();
        int[] posB = roomB.getRandomWallPosition();
        if(posA == null || posB == null) {
            return;
        }
        roomA.addDoor(posA[0], posA[1], null, null);
        roomB.addDoor(posB[0], posB[1], null, null);
        Door doorA = roomA.getDoors().getLast();
        Door doorB = roomB.getDoors().getLast();
        doorA.link(doorB, roomB);
        doorB.link(doorA, roomA);
    }

    private void generateRooms(int roomCnt) {
        allRooms = new LinkedList<Room>();
        for(int i = 0; i < roomCnt; i++) {
            int x = Model.getRand(1, 6);
            int y = Model.getRand(1, 6);
            Room room = new Room(x, y, levelNumber);
            allRooms.add(room);
        }
        currentRoom = allRooms.get(0);

        for(int i = 0; i < roomCnt - 1; i++) {
            connectRooms(allRooms.get(i), allRooms.get(i + 1));
        }
        for(int i = 0; i < roomCnt; i++) {
            for(int j = i + 2; j < roomCnt; j++) {
                if(Model.getRand(1, 100) <= 15) {
                    connectRooms(allRooms.get(i), allRooms.get(j));
                }
            }
        }

        allRooms.getLast().makeStairs();
        boolean exitPlaced = false;
        for(int i = 0; i < roomCnt; i++) {
            if(!exitPlaced) {
                int exitPlace = Model.getRand(1, roomCnt);
                if(exitPlace <= (i + 1)) {
                    allRooms.get(i).makeExit();
                    exitPlaced = true;
                }
            }
        }
    }
    
    private void spawnShop() {
    	Room room = allRooms.get(Model.getRand(0, allRooms.size() - 1));
    	int[] pos = room.getRandomFloorPosition();
    	if(pos != null) {
    		room.setOccupant(pos[0], pos[1], new Shop(pos[0], pos[1]));
    	}
    }

    private void spawnChest() {
    	int roomCnt = allRooms.size();
        for(int i = 0; i < roomCnt; i++) {
            if(Model.getRand(1, 100) <= 15) {
            	int[] pos = allRooms.get(i).getRandomFloorPosition();
            	if(pos != null) {
            		allRooms.get(i).setOccupant(pos[0], pos[1], new Chest(pos[0], pos[1], levelNumber));
            	}
            }
        }
    }
}
