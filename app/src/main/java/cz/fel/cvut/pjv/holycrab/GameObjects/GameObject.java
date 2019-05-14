package cz.fel.cvut.pjv.holycrab.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.Interactable;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public abstract class GameObject implements Interactable {
    protected Bitmap spriteSheet;
    protected Map map;
    protected int[][] mapArray;
    protected Point screenCoordinates;
    protected Point mapCoordinates;
    protected int widthpx, heightpx;

    /**
     * @param spriteSheet Image of game object
     * @param map Map of the room
     */
    public GameObject(Bitmap spriteSheet, Map map) {
        this.spriteSheet = spriteSheet;
        this.map = map;
        mapArray = map.getMapArray();
        //Initialization of standard start position
        mapCoordinates = new Point();
        screenCoordinates = new Point();
        widthpx = spriteSheet.getWidth();
        heightpx = spriteSheet.getHeight();
    }

    /**
     * @param mapX Map X coordinate
     * @param mapY Map Y coordinate
     */
    public void setMapCoordinates(int mapX, int mapY) {
        this.mapCoordinates.x = mapX;
        this.mapCoordinates.y = mapY;
        Point tileCoordinates = map.convertMapToScreenCoordinates(new Point(mapX, mapY));
        screenCoordinates.x = tileCoordinates.x + (Map.getTileSize()  - widthpx) / 2;
        screenCoordinates.y = tileCoordinates.y; //TODO Find better way

    }

    /**
     * @param x New value of x screen coordinate
     * @param y New value of y screen coordinate
     */
    public void correctScreenCoordinates(int x, int y) {
        screenCoordinates.x += x;
        screenCoordinates.y += y;
    }

    /**
     * @return Map coordinates of game object
     */
    public Point getMapCoordinates() {
        return mapCoordinates;
    }

    /**
     * @param mapArray Array with numbers of tiles
     */
    public void setMapArray(int[][] mapArray) {
        this.mapArray = mapArray;
    }

    /**
     * @param canvas Canvas to draw on
     */
    public abstract void draw(Canvas canvas);

}
