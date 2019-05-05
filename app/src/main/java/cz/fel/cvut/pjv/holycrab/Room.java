package cz.fel.cvut.pjv.holycrab;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {

    public final static int topWall = 57;
    public final static int bottomWall = 215;
    public final static int leftWall = 181;
    public final static int rightWall = 171;

    private MapSprite mapSprite;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private Interactable[][] objectsOnMap;
    private CharacterSprite characterSprite;
    private HashMap<Location, Door> doors = new HashMap<>();
    private int totalEnemies;

    public Room(GameView gameView, int[][] mapArray) {
        mapSprite = new MapSprite(mapArray, mapArray.length, mapArray[0].length);
        if (gameView.getCharacter() == null) {
            gameView.setCharacterSprite(new CharacterSprite(this, 4, 4));
        }
        characterSprite = gameView.getCharacter();
        objectsOnMap = new Interactable[mapArray.length][mapArray[0].length];
        initializeDoors();
        SkeletonSprite skeletonSprite = new SkeletonSprite(this, 2, 1);
        gameObjects.add(skeletonSprite);
        objectsOnMap[2][1] = skeletonSprite;
        totalEnemies++;
        //Create another enemy
        MageSprite mageSprite = new MageSprite(this, 6, 5);
        gameObjects.add(mageSprite);
        objectsOnMap[6][5] = mageSprite;
        totalEnemies++;

        Key key = new Key(5, 5, mapSprite);
        gameObjects.add(0, key);
        objectsOnMap[5][5] = key;

        Sword sword = new Sword(8, 1, mapSprite);
        gameObjects.add(0, sword);
        objectsOnMap[8][1] = sword;

    }

    private void initializeDoors() {
        // TODO: do something with coordinates in objectsOnMap
        if (mapSprite.getMapArray()[0][4] != topWall) {
            initializeDoor(WoodenDoor.topClosedFirstPart, 0, 4, Location.top);
        }
        if (mapSprite.getMapArray()[9][4] != bottomWall) {
            initializeDoor(WoodenDoor.bottomClosedFirstPart, 9, 4, Location.bottom);
        }
        if (mapSprite.getMapArray()[4][0] != leftWall) {
            initializeDoor(WoodenDoor.leftClosedFirstPart, 4, 0, Location.left);
        }
        if (mapSprite.getMapArray()[4][9] != rightWall) {
            initializeDoor(WoodenDoor.rightClosedFirstPart, 4, 9, Location.right);
        }
    }

    private void initializeDoor(int firstWoodenPart, int x, int y, Location location) {
        Door door;
        int tileNumber = mapSprite.getMapArray()[x][y];
        if (tileNumber == firstWoodenPart) {
            door = new WoodenDoor(location, this, null);
        } else {
            door = new SteelDoor(location, this, null);
            if (tileNumber == Door.topOpenedFirstPart || tileNumber == Door.bottomOpenedFirstPart
                    || tileNumber == Door.leftOpenedFirstPart
                    || tileNumber == Door.rightOpenedFirstPart) {
                door.open();
            }
        }
        objectsOnMap[y][x] = door;
        x = x == 4 ? x + 1 : x;
        y = y == 4 ? y + 1 : y;
        objectsOnMap[y][x] = door;
        doors.put(location, door);
    }

    public void openDoors() {
        for (Map.Entry<Location, Door> doorEntry: doors.entrySet()) {
            if (doorEntry.getValue() instanceof SteelDoor) {
                doorEntry.getValue().open();
                openDoor(doorEntry.getValue());
            }
            else {
                ((WoodenDoor)doorEntry.getValue()).setAvailable(true);
            }
        }
    }

    public void openDoor(Door door) {
        switch (door.location) {
            case top:
                changeDoorTile(Door.topOpenedFirstPart, Door.topOpenedSecondPart, 0, 4);
                break;
            case bottom:
                changeDoorTile(Door.bottomOpenedFirstPart, Door.bottomOpenedSecondPart, 9, 4);
                break;
            case left:
                changeDoorTile(Door.leftOpenedFirstPart, Door.leftOpenedSecondPart, 4, 0);
                break;
            case right:
                changeDoorTile(Door.rightOpenedFirstPart, Door.rightOpenedSecondPart, 4, 9);
                break;
        }
    }

    public void changeDoorTile(int firstPart, int secondPart, int x, int y) {
        mapSprite.changeMapArray(x, y, firstPart);
        x = x == 4 ? x + 1 : x;
        y = y == 4 ? y + 1 : y;
        mapSprite.changeMapArray(x, y, secondPart);
    }

    public MapSprite getMap() {
        return mapSprite;
    }

    public CharacterSprite getCharacter() {
        return characterSprite;
    }

    public void draw(Canvas canvas) {
        mapSprite.draw(canvas);
        for (GameObject gameObject: gameObjects) {
            gameObject.draw(canvas);
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Interactable[][] getObjectsOnMap() {
        return objectsOnMap;
    }

    public void setNextRoom(Room nextRoom, Location location) {
        doors.get(location).setNextRoom(nextRoom);
    }

    public boolean checkEnemiesDead() {
        return totalEnemies == 0;
    }

    public void reduceEnemiesCount() {
        totalEnemies--;
    }

    public void openWoodenDoor(Door door) {

    }
}
