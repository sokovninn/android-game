package cz.fel.cvut.pjv.holycrab.Environment;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Mage;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.NPC;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Skeleton;
import cz.fel.cvut.pjv.holycrab.Environment.Doors.Door;
import cz.fel.cvut.pjv.holycrab.Environment.Doors.SteelDoor;
import cz.fel.cvut.pjv.holycrab.Environment.Doors.WoodenDoor;
import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Interactable;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Chest;
import cz.fel.cvut.pjv.holycrab.Location;

public class Room {

    private final static int topWall = 57;
    private final static int bottomWall = 78;
    private final static int leftWall = 76;
    private final static int rightWall = 77;
    private static int roomsAmount = 0;

    private Map map;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private Interactable[][] objectsOnMap;
    protected Player player;
    private HashMap<Location, Door> doors = new HashMap<>();
    private int totalEnemies;
    private int totalItems;
    private int totalNPCs;
    private int roomNumber;

    /**
     * @param gameView Game view
     * @param mapArray Array with numbers of tiles
     */
    public Room(GameView gameView, int[][] mapArray) {
        roomsAmount++;
        roomNumber = roomsAmount;
        map = new Map(mapArray, mapArray.length, mapArray[0].length);
        if (gameView.getPlayer() == null) {
            gameView.setPlayer(new Player(this, 4, 4));
        }
        player = gameView.getPlayer();
        objectsOnMap = new Interactable[mapArray.length][mapArray[0].length];
        initializeDoors();

    }

    /**
     * @param gameObject Game object to add
     * @param isItem True if object is Item
     */
    public void addGameObject(GameObject gameObject, boolean isItem) {
        Point mapCoordinates = gameObject.getMapCoordinates();
        if (isItem) {
            gameObjects.add(0, gameObject);
            totalItems++;
        } else {
            gameObjects.add(gameObject);
            if (!(gameObject instanceof NPC)) {
                totalEnemies++;
            }
            else {
                totalNPCs++;
            }
        }
        objectsOnMap[mapCoordinates.x][mapCoordinates.y] = gameObject;
    }

    void removeGameObject(GameObject gameObject, boolean isItem) {
        Point mapCoordinates = gameObject.getMapCoordinates();
        if (isItem) {
            totalItems--;
        } else {
            totalEnemies--;
        }
        gameObjects.remove(gameObject);
        objectsOnMap[mapCoordinates.x][mapCoordinates.y] = null;
    }

    private void initializeDoors() {
        // TODO: do something with coordinates in objectsOnMap
        if (map.getMapArray()[0][4] != topWall) {
            initializeDoor(WoodenDoor.topClosedFirstPart, 0, 4, Location.top);
        }
        if (map.getMapArray()[9][4] != bottomWall) {
            initializeDoor(WoodenDoor.bottomClosedFirstPart, 9, 4, Location.bottom);
        }
        if (map.getMapArray()[4][0] != leftWall) {
            initializeDoor(WoodenDoor.leftClosedFirstPart, 4, 0, Location.left);
        }
        if (map.getMapArray()[4][9] != rightWall) {
            initializeDoor(WoodenDoor.rightClosedFirstPart, 4, 9, Location.right);
        }
    }

    private void initializeDoor(int firstWoodenPart, int x, int y, Location location) {
        Door door;
        int tileNumber = map.getMapArray()[x][y];
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

    void openDoors() {
        for (java.util.Map.Entry<Location, Door> doorEntry: doors.entrySet()) {
            if (doorEntry.getValue() instanceof SteelDoor) {
                doorEntry.getValue().open();
                openDoor(doorEntry.getValue());
            }
            else {
                ((WoodenDoor)doorEntry.getValue()).setAvailable(true);
            }
        }
    }

    void openDoor(Door door) {
        switch (door.getLocation()) {
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



    private void changeDoorTile(int firstPart, int secondPart, int x, int y) {
        map.changeMapArray(x, y, firstPart);
        x = x == 4 ? x + 1 : x;
        y = y == 4 ? y + 1 : y;
        map.changeMapArray(x, y, secondPart);
    }

    /**
     * @return Map of room
     */
    public Map getMap() {
        return map;
    }

    /**
     * @return PLayers character
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param canvas Canvas to draw on
     */
    public void draw(Canvas canvas) {
        map.draw(canvas);
        for (GameObject gameObject: gameObjects) {
            gameObject.draw(canvas);
        }
    }

    /**
     * @return All objects in the room
     */
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    Interactable[][] getObjectsOnMap() {
        return objectsOnMap;
    }

    /**
     * @param nextRoom Connected room
     * @param location Location of door to the room
     */
    public void setNextRoom(Room nextRoom, Location location) {
        doors.get(location).setNextRoom(nextRoom);
    }

    boolean checkEnemiesDead() {
        return totalEnemies == 0;
    }

    /**
     * @return All doors in the room
     */
    public HashMap<Location, Door> getDoors() {
        return doors;
    }

    /**
     * @return Number of room
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @return Enemies amount in the room
     */
    public int getTotalEnemies() {
        return totalEnemies;
    }

    /**
     * @return Items amount in the room
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * @return NPC amount in the room
     */
    public int getTotalNPCs() {
        return totalNPCs;
    }

    /**
     * @param roomsNum Amount of rooms in the dungeon
     */
    public static void setRoomsAmount(int roomsNum) {
        roomsAmount = roomsNum;
    }
}
