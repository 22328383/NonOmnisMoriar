
public class Door {
    private int x;
    private int y;
    private Door endDoor;
    private Room endRoom;

    public Door(int doorX, int doorY, Door end, Room endRoom) {
        this.x = doorX;
        this.y = doorY;
        this.endDoor = end;
        this.endRoom = endRoom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Door getEndDoor() {
    	return endDoor;
    }

    public Room getEndRoom() {
        return endRoom;
    }

    public void link(Door endDoor, Room endRoom) {
    	this.endDoor = endDoor;
        this.endRoom = endRoom;
    }
    
}
