package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Map;

public abstract class CreatureSprite {
    protected Bitmap spriteSheet;
    protected Bitmap[] creatureFrontFrames;
    protected MapSprite map;
    protected int[][] mapArray;
    protected Point screenCoordinates;
    protected Point mapCoordinates;
    protected int stepLength;
    protected int widthpx, heightpx;
    protected int currentFrame;
    protected long previousFrameChangeTime, frameLength;
    protected int hitPoints;
    protected Bitmap hitPointImage;
    protected int hitPointSize;

    public CreatureSprite(Bitmap spriteSheet, MapSprite map, Bitmap hitPointImage) {
        this.spriteSheet = spriteSheet;
        this.map = map;
        mapArray = map.getMapArray();
        creatureFrontFrames = new Bitmap[3];
        for (int i = 0; i < 3; i++) {
            creatureFrontFrames[i] = Bitmap.createBitmap(spriteSheet, 24 * i * 3, 64 * 3, 24 * 3, 32 * 3);
        }
        currentFrame = 0;
        previousFrameChangeTime = 0;
        frameLength = 500;
        //Initialization of standard start position
        mapCoordinates = new Point();
        screenCoordinates = new Point();
        widthpx = creatureFrontFrames[0].getWidth();
        heightpx = creatureFrontFrames[0].getHeight();
        setMapCoordinates(mapArray[0].length / 2, mapArray.length / 2);
        stepLength = MapSprite.getTileSize();
        hitPoints = 3;
        this.hitPointImage = hitPointImage;
        hitPointSize = hitPointImage.getWidth();

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

    public void setStepLength(int multiplier) {
        this.stepLength *= MapSprite.getTileSize() * multiplier;
    }

    public void manageCurrentFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > previousFrameChangeTime + frameLength) {
            currentFrame = (currentFrame + 1) % 3;
            previousFrameChangeTime = currentTime;
        }
    }

    public abstract void drawHitPoints(Canvas canvas);

    public void draw(Canvas canvas) {
        manageCurrentFrame();
        canvas.drawBitmap(creatureFrontFrames[currentFrame], screenCoordinates.x, screenCoordinates.y, null);
        drawHitPoints(canvas);
    }

    public void updateHitPoints(int change) {
        hitPoints += change;
    }

    public int getHitPoints() {
        return hitPoints;
    }

}
