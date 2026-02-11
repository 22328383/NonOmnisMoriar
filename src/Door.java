
public class Door {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Room endRoom;

    public Door(int startX, int startY, int endX, int endY, Room endRoom) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.endRoom = endRoom;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public Room getEndRoom() {
        return endRoom;
    }

    public void setEndRoom(Room endRoom) {
        this.endRoom = endRoom;
    }
}
