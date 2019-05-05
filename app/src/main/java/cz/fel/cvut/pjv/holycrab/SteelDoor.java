package cz.fel.cvut.pjv.holycrab;

public class SteelDoor extends Door {
    public static final int topClosedFirstPart = 155;
    public static final int topClosedSecondPart = 156;
    public static final int bottomClosedFirstPart = 178;
    public static final int bottomClosedSecondPart = 179;
    public static final int leftClosedFirstPart = 221;
    public static final int leftClosedSecondPart = 240;
    public static final int rightClosedFirstPart = 175;
    public static final int rightClosedSecondPart = 194;

    public SteelDoor(Location location, Room currentRoom, Room nextRoom) {
        super(location, currentRoom, nextRoom);
    }
}
