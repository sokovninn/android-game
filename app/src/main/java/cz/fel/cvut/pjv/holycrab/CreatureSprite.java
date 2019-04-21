package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public abstract class CreatureSprite {
    protected Bitmap spriteSheet;
    protected Bitmap creatureFront;
    protected MapSprite map;
    protected int[][] mapArray;
    protected Point screenCoordinates;
    protected Point mapCoordinates;
    protected int stepLength;
    protected int widthpx, heightpx;

    public CreatureSprite(Bitmap spriteSheet, MapSprite map) {
        this.spriteSheet = spriteSheet;
        this.map = map;
        mapArray = map.getMapArray();
        creatureFront = Bitmap.createBitmap(spriteSheet, 24 * 3, 64 * 3, 24 * 3, 32 * 3);
        //Initialization of standard start position
        mapCoordinates = new Point();
        screenCoordinates = new Point();
        widthpx = creatureFront.getWidth() / 3;
        heightpx = creatureFront.getHeight() / 3;
        setMapCoordinates(mapArray[0].length / 2, mapArray.length / 2);
        stepLength = MapSprite.getTileSize();

    }

    public void setMapCoordinates(int mapX, int mapY) {
        this.mapCoordinates.x = mapX;
        this.mapCoordinates.y = mapY;
        Point tileCoordinates = map.convertMapToScreenCoordinates(new Point(mapX, mapY));
        screenCoordinates.x = tileCoordinates.x + widthpx / 2;
        screenCoordinates.y = tileCoordinates.y + heightpx / 2 - 35; //TODO Find better way

    }

    public Point getMapCoordinates() {
        return mapCoordinates;
    }

    public void setStepLength(int multiplier) {
        this.stepLength *= MapSprite.getTileSize() * multiplier;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(creatureFront, screenCoordinates.x, screenCoordinates.y, null);
    }

}
