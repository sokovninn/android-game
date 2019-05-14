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

    public final static int topWall = 57;
    public final static int bottomWall = 78;
    public final static int leftWall = 76;
    public final static int rightWall = 77;


    private static int roomsAmount = 0;
    private Map map;
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();

    protected Interactable[][] objectsOnMap;
    protected Player player;
    private HashMap<Location, Door> doors = new HashMap<>();
    private int totalEnemies;
    private int totalItems;
    private int totalNPCs;
    private int roomNumber;

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

    public void initializeGameObjects() {
        addGameObject(new Skeleton(this, 2, 1), false);
        //Create another enemy
        addGameObject(new Mage(this, 6, 5), false);

        addGameObject(new Chest(7, 2, map), true);
    }

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

    public void removeGameObject(GameObject gameObject, boolean isItem) {
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

    public void openDoors() {
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

    public void openDoor(Door door) {
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



    public void changeDoorTile(int firstPart, int secondPart, int x, int y) {
        map.changeMapArray(x, y, firstPart);
        x = x == 4 ? x + 1 : x;
        y = y == 4 ? y + 1 : y;
        map.changeMapArray(x, y, secondPart);
    }

    public Map getMap() {
        return map;
    }

    public Player getCharacter() {
        return player;
    }

    public void draw(Canvas canvas) {
        map.draw(canvas);
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

    public HashMap<Location, Door> getDoors() {
        return doors;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getTotalEnemies() {
        return totalEnemies;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalNPCs() {
        return totalNPCs;
    }

    public static void setRoomsAmount(int roomsNum) {
        roomsAmount = roomsNum;
    }
}
