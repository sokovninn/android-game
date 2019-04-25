package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class GameObject {
    protected Bitmap spriteSheet;
    protected MapSprite map;
    protected int[][] mapArray;
    protected Point screenCoordinates;
    protected Point mapCoordinates;
    protected int widthpx, heightpx;

    public GameObject(Bitmap spriteSheet, MapSprite map) {
        this.spriteSheet = spriteSheet;
        this.map = map;
        mapArray = map.getMapArray();
        //Initialization of standard start position
        mapCoordinates = new Point();
        screenCoordinates = new Point();
        widthpx = spriteSheet.getWidth();
        heightpx = spriteSheet.getHeight();
    }

    public void setMapCoordinates(int mapX, int mapY) {
        this.mapCoordinates.x = mapX;
        this.mapCoordinates.y = mapY;
        Point tileCoordinates = map.convertMapToScreenCoordinates(new Point(mapX, mapY));
        screenCoordinates.x = tileCoordinates.x + (MapSprite.getTileSize()  - widthpx) / 2;
        screenCoordinates.y = tileCoordinates.y - 20; //TODO Find better way

    }

    public Point getMapCoordinates() {
        return mapCoordinates;
    }

    public abstract void draw(Canvas canvas);

}
