package cz.fel.cvut.pjv.holycrab.Environment.Doors;

import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.Location;

public class SteelDoor extends Door {
    public static final int topClosedFirstPart = 155;
    public static final int topClosedSecondPart = 156;
    public static final int bottomClosedFirstPart = 178;
    public static final int bottomClosedSecondPart = 179;
    public static final int leftClosedFirstPart = 221;
    public static final int leftClosedSecondPart = 240;
    public static final int rightClosedFirstPart = 175;
    public static final int rightClosedSecondPart = 194;

    /**
     * @param location Location in the room
     * @param currentRoom Room which contains door
     * @param nextRoom Room after the door
     */
    public SteelDoor(Location location, Room currentRoom, Room nextRoom) {
        super(location, currentRoom, nextRoom);
    }
}
