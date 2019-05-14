package cz.fel.cvut.pjv.holycrab.Environment.Doors;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.Interactable;
import cz.fel.cvut.pjv.holycrab.Location;

public class Door implements Interactable {

    public static final int topOpenedFirstPart = 116;
    public static final int topOpenedSecondPart = 117;
    public static final int bottomOpenedFirstPart = 140;
    public static final int bottomOpenedSecondPart = 141;
    public static final int leftOpenedFirstPart = 181;
    public static final int leftOpenedSecondPart = 200;
    public static final int rightOpenedFirstPart = 171;
    public static final int rightOpenedSecondPart = 190;

    protected Room currentRoom;
    protected Room nextRoom;
    protected boolean isOpened;
    protected Location location;
    protected boolean isUsed;

    public Door(Location location, Room currentRoom, Room nextRoom) {
        this.location = location;
        this.currentRoom = currentRoom;
        this.nextRoom = nextRoom;
        isOpened = false;
        isUsed = false;
    }

    public void open() {
        isOpened = true;
    }

    public void close() {
        isOpened = false;
    }

    public Room getNextRoom() {
        return nextRoom;
    }

    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }

    @Override
    public void interact(Player player) {
        player.setMoveOver(true);
        if (isOpened && nextRoom != null) {
            passPlayer(player);
        }
    }

    void passPlayer(Player player) {
        isUsed = true;
        switch (location) {
            case top:
                player.setMapCoordinates(4,8);
                break;
            case bottom:
                player.setMapCoordinates(4, 1);
                break;
            case left:
                player.setMapCoordinates(8, 4);
                break;
            case right:
                player.setMapCoordinates(1, 4);
                break;
        }
    }

    public boolean checkIsUsed() {
        return isUsed;
    }

    public boolean checkOpened() {
        return isOpened;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public Location getLocation() {
        return location;
    }
}
