package cz.fel.cvut.pjv.holycrab;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Room {
    private MapSprite mapSprite;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private GameObject[][] objectsOnMap;
    private CharacterSprite characterSprite;
    private Door[] doors;

    public Room(GameView gameView, int[][] mapArray) {
        mapSprite = new MapSprite(mapArray, mapArray.length, mapArray[0].length);
        if (gameView.getCharacter() == null) {
            gameView.setCharacterSprite(new CharacterSprite(this, 4, 4));
        }
        characterSprite = gameView.getCharacter();
        objectsOnMap = new GameObject[mapArray.length][mapArray[0].length];
        SkeletonSprite skeletonSprite = new SkeletonSprite(this, 2, 1);
        gameObjects.add(skeletonSprite);
        objectsOnMap[2][1] = skeletonSprite;
        //Create another enemy
        MageSprite mageSprite = new MageSprite(this, 5, 4);
        gameObjects.add(mageSprite);
        objectsOnMap[5][4] = mageSprite;
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

    public GameObject[][] getObjectsOnMap() {
        return objectsOnMap;
    }
}
