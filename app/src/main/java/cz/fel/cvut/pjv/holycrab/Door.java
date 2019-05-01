package cz.fel.cvut.pjv.holycrab;

public class Door {

    private Room currentRoom;
    private Room nextRoom;
    private boolean isOpened;
    private Location location;

    public Door(Location location, Room currentRoom, Room nextRoom) {
        this.location = location;
        this.currentRoom = currentRoom;
        this.nextRoom = nextRoom;
        isOpened = false;
    }

    public void open() {
        isOpened = true;
    }

    public void close() {
        isOpened = false;
    }



    public enum Location {
        top,
        bottom,
        left,
        right;
    }
}
