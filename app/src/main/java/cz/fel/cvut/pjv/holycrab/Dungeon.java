package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

public class Dungeon {
    private GameView gameView;
    private ArrayList<Room> rooms = new ArrayList<>();
    private Room currentRoom;
    private CharacterSprite characterSprite;

    public Dungeon(GameView gameView) {
        this.gameView = gameView;
        //Initialize map tiles
        Bitmap dungeonSpriteSheet = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dungeon);
        MapSprite.initializeTileSprites(dungeonSpriteSheet, 32, 247, 1);
        int[][] mapArrayFirstRoom = new int[][]{
                {3, 57, 57, 57, 152, 153, 57, 57, 57, 4},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {221, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {240, 6, 6, 6, 6, 167, 6, 6, 6, 77},
                {76, 73, 74, 6, 6, 6, 6, 6, 6, 77},
                {76, 92, 93, 6, 6, 6, 6, 6, 6, 77},
                {76, 111, 112, 6, 6, 6, 6, 6, 6, 77},
                {22, 78, 78, 78, 178, 179, 78, 78, 78, 23}};
        int[][] mapArraySecondRoom = new int[][]{
                {3, 57, 57, 57, 155, 156, 57, 57, 57, 4},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 175},
                {76, 6, 6, 6, 6, 167, 6, 6, 6, 194},
                {76, 73, 74, 6, 6, 6, 6, 6, 6, 77},
                {76, 92, 93, 6, 6, 6, 6, 6, 6, 77},
                {76, 111, 112, 6, 6, 6, 6, 6, 6, 77},
                {22, 78, 78, 78, 178, 179, 78, 78, 78, 23}};
        int[][] mapArrayThirdRoom = new int[][]{
                {3, 57, 57, 57, 57, 57, 57, 57, 57, 4},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 167, 6, 6, 6, 77},
                {76, 73, 74, 6, 6, 6, 6, 6, 6, 77},
                {76, 92, 93, 6, 6, 6, 6, 6, 6, 77},
                {76, 111, 112, 6, 6, 6, 6, 6, 6, 77},
                {22, 78, 78, 78, 140, 141, 78, 78, 78, 23}};
        //Create new room
        Room newRoom = new Room(gameView, mapArrayFirstRoom);
        rooms.add(newRoom);
        currentRoom = newRoom;
        //One more room
        newRoom = new Room(gameView, mapArraySecondRoom);
        rooms.add(newRoom);
        newRoom = new Room(gameView, mapArrayThirdRoom);
        rooms.add(newRoom);
        rooms.get(0).setNextRoom(rooms.get(1), Location.left);
        rooms.get(0).setNextRoom(rooms.get(2), Location.top);
        rooms.get(1).setNextRoom(rooms.get(0), Location.right);
        rooms.get(2).setNextRoom(rooms.get(0), Location.bottom);
        characterSprite = gameView.getCharacter();
    }

    public void draw(Canvas canvas) {
        currentRoom.draw(canvas);
    }

    public void update() {
        ArrayList<GameObject> gameObjects = currentRoom.getGameObjects();
        Interactable[][] objectsOnMap = currentRoom.getObjectsOnMap();
        Point characterMove = characterSprite.getCoordinatesAfterUpdate(gameView.getMoveDirection());
        updateEnemies(gameObjects, objectsOnMap, characterMove);
        actualizePositionsOnMap(gameObjects, objectsOnMap);
        if (!characterSprite.isAttacked) {
            updateCharacter(gameObjects, objectsOnMap, characterMove);
        } else {
            characterSprite.setAttacked(false);
        } }

    private void actualizePositionsOnMap(ArrayList<GameObject> gameObjects,
                                         Interactable[][] objectsOnMap) {
        for (GameObject gameObject: gameObjects) {
            Point position = gameObject.getMapCoordinates();
            objectsOnMap[position.x][position.y] = gameObject;
        }
    }

    private void updateEnemies(ArrayList<GameObject> gameObjects,
                               Interactable[][] objectsOnMap, Point characterMove) {
        for (GameObject gameObject: gameObjects) {
            if (gameObject instanceof EnemySprite) {
                EnemySprite enemySprite = (EnemySprite)gameObject;
                Point position = enemySprite.getMapCoordinates();
                objectsOnMap[position.x][position.y] = null;
                enemySprite.update(characterMove);
            }
        }
        if (characterSprite.isDead) {
            gameView.endGame();
        }
    }

    private void updateCharacter(ArrayList<GameObject> gameObjects,
                                 Interactable[][] objectsOnMap, Point characterMove) {
        Interactable objectOnMap = objectsOnMap[characterMove.x][characterMove.y];
        if (objectOnMap != null) {
            if (objectOnMap instanceof EnemySprite) {
                EnemySprite enemySprite = (EnemySprite) objectsOnMap[characterMove.x][characterMove.y];
                characterSprite.attack(enemySprite);
                if (enemySprite.isDead) {
                    currentRoom.reduceEnemiesCount();
                    objectsOnMap[characterMove.x][characterMove.y] = null;
                    gameObjects.remove(enemySprite);
                    if (currentRoom.checkEnemiesDead()) {
                        currentRoom.openDoors();
                    }
                }
                enemySprite.setAttacked(false);
            }
            if (objectOnMap instanceof Door) {
                Door door = (Door)objectOnMap;
                door.interact(characterSprite);
                if (door.checkIsUsed()) {
                    currentRoom = door.getNextRoom();
                    door.setUsed(false);
                    characterSprite.setMapArray(currentRoom.getMap().getMapArray());
                } else if (door.isOpened){
                    currentRoom.openDoor(door);
                }
            }
            if (objectOnMap instanceof Item) {
                Item item = (Item)objectOnMap;
                item.interact(characterSprite);
                objectsOnMap[characterMove.x][characterMove.y] = null;
                gameObjects.remove(item);
                characterSprite.update(gameView.getMoveDirection());
            }
        } else {
            characterSprite.update(gameView.getMoveDirection());
        }
    }

}
