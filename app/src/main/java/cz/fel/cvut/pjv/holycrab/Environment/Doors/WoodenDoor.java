package cz.fel.cvut.pjv.holycrab.Environment.Doors;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.Location;

public class WoodenDoor extends Door {

    public static final int topClosedFirstPart = 152;
    public static final int topClosedSecondPart = 153;
    public static final int bottomClosedFirstPart = 215;
    public static final int bottomClosedSecondPart = 216;
    public static final int leftClosedFirstPart = 219;
    public static final int leftClosedSecondPart = 248;
    public static final int rightClosedFirstPart = 173;
    public static final int rightClosedSecondPart = 192;

    private boolean isAvailable;
    /**
     * @param location Location in the room
     * @param currentRoom Room which contains door
     * @param nextRoom Room after the door
     */
    public WoodenDoor(Location location, Room currentRoom, Room nextRoom) {
        super(location, currentRoom, nextRoom);
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!super.checkOpened() && isAvailable && player.hasKey()) {
            player.useKey();
            open();
        }
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
