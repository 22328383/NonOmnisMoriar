import java.util.LinkedList;

import mobs.*;
import core.*;
import props.*;
import items.*;

public class TutorialLevel {
	private LinkedList<Level> floors = new LinkedList<Level>();
	private int currFloor = 0;

	public TutorialLevel() {
		Level floor0 = buildFloor0();
		Level floor1 = buildFloor1();
		floors.add(floor0);
		floors.add(floor1);
	}

	private Level buildFloor0() {
		LinkedList<Room> rooms = new LinkedList<Room>();

		Room roomA = new Room(3, 3, 0);
		Room roomB = new Room(2, 2, 0);
		Room roomC = new Room(1, 1, 0);
		Room roomD = new Room(2, 2, 0);
		Room roomE = new Room(3, 3, 0);

		rooms.add(roomA);
		rooms.add(roomB);
		rooms.add(roomC);
		rooms.add(roomD);
		rooms.add(roomE);

		connectRooms(roomA, roomB);
		connectRooms(roomB, roomC);
		connectRooms(roomC, roomD);
		connectRooms(roomD, roomE);

		Rat rat = new Rat(10, 8, 0, GameConstants.RAT_TEXTURE);
		roomB.getMobs().add(rat);
		roomB.setOccupant(10, 8, rat);

		Chest chest = new Chest(10, 8, 0);
		chest.getInside().clear();
		chest.getInside().add(new Potion("Health Potion", GameConstants.POTION_HEALTH, 0, new Stats()));
		chest.getInside().add(Gear.generate(Slot.WEAPON, 1));
		chest.getInside().add(Loot.generate(1));
		roomC.setOccupant(10, 8, chest);
		
		Shop shop = new Shop(10,8);
		roomD.setOccupant(10, 8, shop);

		roomE.makeStairs();

		return new Level(rooms);
	}

	private Level buildFloor1() {
		LinkedList<Room> rooms = new LinkedList<Room>();
		Room exitRoom = new Room(3, 3, 0);
		exitRoom.makeExit();
		rooms.add(exitRoom);
		return new Level(rooms);
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

	public LinkedList<Level> getFloors() {
		return floors;
	}

	public int getCurrFloor() {
		return currFloor;
	}

	public void setCurrFloor(int floor) {
		this.currFloor = floor;
	}
}
