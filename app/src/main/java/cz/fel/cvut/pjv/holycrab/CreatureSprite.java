package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class CreatureSprite {
    protected Bitmap spriteSheet;
    protected Bitmap creatureFront;
    protected MapSprite map;
    protected int[][] mapArray;
    protected int x, y;
    protected int mapX, mapY;
    protected int stepLength;
    protected int widthpx, heightpx;

    public CreatureSprite(Bitmap spriteSheet, MapSprite map) {
        this.spriteSheet = spriteSheet;
        this.map = map;
        mapArray = map.getMapArray();
        creatureFront = Bitmap.createBitmap(spriteSheet, 24 * 3, 64 * 3, 24 * 3, 32 * 3);
        //Initialization of standard start position
        mapX = mapArray[0].length / 2;
        mapY = mapArray.length / 2;
        widthpx = creatureFront.getWidth() / 3;
        heightpx = creatureFront.getHeight() / 3;
        setPosition(mapX, mapY);
        stepLength = MapSprite.getTileSize();

    }

    public void setPosition(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        int[] tileCoordinates = map.convertCoordinates(mapX, mapY);
        x = tileCoordinates[0] + widthpx / 2;
        y = tileCoordinates[1] + heightpx / 2 - 35; //TODO Find better way

    }

    public void setStepLength(int multiplier) {
        this.stepLength *= multiplier;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(creatureFront, x, y, null);
    }

}
