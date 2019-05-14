package cz.fel.cvut.pjv.holycrab.Environment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Arachne;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Enemy;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.NPC;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Doors.Door;
import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Interactable;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Chest;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Treasure;
import cz.fel.cvut.pjv.holycrab.R;

public class Dungeon {
    private GameView gameView;
    private ArrayList<Room> rooms = new ArrayList<>();
    private Room currentRoom;
    private ArrayList<GameObject> currentGameObjects;
    private Interactable[][] currentObjectsOnMap;
    private Player player;

    /**
     * @param gameView Game view to interact with
     */
    public Dungeon(GameView gameView) {
        this.gameView = gameView;
        //Initialize map tiles
        Bitmap dungeonSpriteSheet = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dungeon);
        Map.initializeTileSprites(dungeonSpriteSheet, 32, 247, 1);
    }

    /**
     * @param canvas Canvas to draw on
     */
    public void draw(Canvas canvas) {
        currentRoom.draw(canvas);
    }

    /**
     * Main part of game loop
     */
    public void update() {
        currentGameObjects = currentRoom.getGameObjects();
        currentObjectsOnMap = currentRoom.getObjectsOnMap();
        Point characterMove = player.getCoordinatesAfterUpdate(gameView.getMoveDirection());
        updateEnemies(characterMove);
        actualizePositionsOnMap();
        if (!player.checkMoveOver()) {
            updateCharacter(characterMove);
        } else {
            player.setMoveOver(false);
        }
    }

    private void actualizePositionsOnMap() {
        Log.i("Information", "Actualize positions of objects");
        for (GameObject gameObject: currentGameObjects) {
            Point position = gameObject.getMapCoordinates();
            currentObjectsOnMap[position.x][position.y] = gameObject;
        }
    }

    private void updateEnemies(Point characterMove) {
        Log.i("Information", "Update enemies");
        for (GameObject gameObject: currentGameObjects) {
            if (gameObject instanceof Enemy) {
                Enemy enemySprite = (Enemy)gameObject;
                Point position = enemySprite.getMapCoordinates();
                currentObjectsOnMap[position.x][position.y] = null;
                enemySprite.update(characterMove);
            }
        }
        if (player.checkDead()) {
            gameView.endGame();
        }
    }

    private void updateCharacter(Point characterMove) {
        Log.i("Information", "Update player");
        Interactable objectOnMap = currentObjectsOnMap[characterMove.x][characterMove.y];
        if (objectOnMap != null) {
            if (objectOnMap instanceof Enemy) {
                processEnemy((Enemy)objectOnMap, characterMove);
            }
            else if (objectOnMap instanceof NPC) {
                objectOnMap.interact(player);
            }
            else if (objectOnMap instanceof Door) {
                processDoor((Door)objectOnMap);
            }
            else if (objectOnMap instanceof Item) {
                processItem((Item)objectOnMap, characterMove);
            }
        }
        if (!player.checkMoveOver()) {
            player.update(gameView.getMoveDirection());
        }
        player.setMoveOver(false);
    }

    private void processEnemy(Enemy enemy, Point characterMove) {
        player.attack(enemy);
        player.setMoveOver(true);
        if (enemy.checkDead()) {
            Log.i("Information", "Enemy killed");
            if (enemy instanceof Arachne) gameView.winGame();
            Treasure treasure = enemy.getReward();
            currentRoom.removeGameObject(enemy, false);
            currentObjectsOnMap[characterMove.x][characterMove.y] = treasure;
            currentGameObjects.add(0, treasure);
            if (currentRoom.checkEnemiesDead()) {
                Log.i("Information", "Room complete");
                currentRoom.openDoors();
            }
        }
    }

    private void processDoor(Door door) {
        Log.i("Information", "Interact with door");
        door.interact(player);
        if (door.checkIsUsed()) {
            Log.i("Information", "Change room");
            setCurrentRoom(door.getNextRoom());
            door.setUsed(false);
            player.setMapArray(currentRoom.getMap().getMapArray());
        } else if (door.checkOpened()){
            currentRoom.openDoor(door);
        }
    }

    private void processItem(Item item, Point characterMove) {
        Log.i("Information", "Interact with item");
        item.interact(player);
        if (item instanceof Chest) {
            Chest chest = (Chest)item;
            if (chest.checkOpened()) {
                Item newItem = chest.getLoot();
                currentRoom.removeGameObject(chest, true);
                currentRoom.addGameObject(newItem, true);
            }
        } else if (item.checkRemoved()) {
            if (item.checkIsForSale()) {
                ((ShopRoom)currentRoom).sellItem();
            }
            currentRoom.removeGameObject(item, true);
        }
    }

    /**
     * @param rooms All rooms in dungeon
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @param currentRoom Room in which player is
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * @param player Players character
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return All rooms in dungeon
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * @return Room in which player is
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @return Players character
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Context
     */
    public Context getContext() {
        return gameView.getContext();
    }
}
