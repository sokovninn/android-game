package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Map;

public abstract class CreatureSprite extends GameObject {
    protected Bitmap[] creatureFrontFrames;
    protected int currentFrame;
    protected long previousFrameChangeTime, frameLength;
    protected int hitPoints;
    protected Bitmap hitPointImage;
    protected int hitPointSize;
    protected int strength;
    protected boolean isDead;
    protected boolean isAttacked;

    public CreatureSprite(Bitmap spriteSheet, MapSprite map, Bitmap hitPointImage) {
        super(spriteSheet, map);
        setCreatureFrontFrames(0,64,24,32);
        currentFrame = 0;
        previousFrameChangeTime = 0;
        frameLength = 500;
        widthpx = creatureFrontFrames[0].getWidth();
        heightpx = creatureFrontFrames[0].getHeight();
        setMapCoordinates(mapArray[0].length / 2, mapArray.length / 2);
        hitPoints = 3;
        strength = 1;
        this.hitPointImage = hitPointImage;
        hitPointSize = hitPointImage.getWidth();
        isDead = false;
        isAttacked = false;

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
        if (hitPoints < 1) isDead = true;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void attack(CreatureSprite creatureSprite) {
        creatureSprite.updateHitPoints(-strength);
        creatureSprite.setAttacked(true);
    }

    public void setCreatureFrontFrames(int x, int y, int width, int height) {
        creatureFrontFrames = new Bitmap[3];
        for (int i = 0; i < 3; i++) {
            creatureFrontFrames[i] = Bitmap.createBitmap(spriteSheet, x * 3, y * 3, width * 3, height * 3);
            x += width;
        }
    }

    public void setAttacked(boolean isAttacked) {
        this.isAttacked = isAttacked;
    }
}
