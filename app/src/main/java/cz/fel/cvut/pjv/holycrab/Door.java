package cz.fel.cvut.pjv.holycrab;

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
    public void interact(CharacterSprite characterSprite) {
        if (isOpened && nextRoom != null) {
            passPlayer(characterSprite);
        }
    }

    void passPlayer(CharacterSprite characterSprite) {
        isUsed = true;
        switch (location) {
            case top:
                characterSprite.setMapCoordinates(4,8);
                break;
            case bottom:
                characterSprite.setMapCoordinates(4, 1);
                break;
            case left:
                characterSprite.setMapCoordinates(8, 4);
                break;
            case right:
                characterSprite.setMapCoordinates(1, 4);
                break;
        }
    }

    public boolean checkIsUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
