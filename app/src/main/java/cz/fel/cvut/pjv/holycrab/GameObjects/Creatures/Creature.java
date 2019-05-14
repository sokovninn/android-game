package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public abstract class Creature extends GameObject {
    protected Bitmap[] creatureFrontFrames;
    protected int currentFrame;
    protected long previousFrameChangeTime, frameLength;
    protected int maxHitPoints;
    protected int hitPoints;
    protected int strength;
    protected boolean isDead;
    protected boolean isMoveOver;

    public Creature(Bitmap spriteSheet, Map map) {
        super(spriteSheet, map);
        setCreatureFrontFrames(0,64,24,32);
        currentFrame = 0;
        previousFrameChangeTime = 0;
        frameLength = 500;
        widthpx = creatureFrontFrames[0].getWidth();
        heightpx = creatureFrontFrames[0].getHeight();
        setMapCoordinates(mapArray[0].length / 2, mapArray.length / 2);
        maxHitPoints = 3;
        hitPoints = maxHitPoints;
        strength = 1;
        isDead = false;
        isMoveOver = false;

    }

    public void manageCurrentFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > previousFrameChangeTime + frameLength) {
            currentFrame = (currentFrame + 1) % 3;
            previousFrameChangeTime = currentTime;
        }
    }

    public void draw(Canvas canvas) {
        manageCurrentFrame();
        canvas.drawBitmap(creatureFrontFrames[currentFrame], screenCoordinates.x, screenCoordinates.y, null);
    }

    public void updateHitPoints(int change) {
        hitPoints += change;
        if (hitPoints < 1) isDead = true;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void attack(Creature creature) {
        creature.updateHitPoints(-strength);
        creature.setMoveOver(true);
    }

    public void setCreatureFrontFrames(int x, int y, int width, int height) {
        creatureFrontFrames = new Bitmap[3];
        for (int i = 0; i < 3; i++) {
            creatureFrontFrames[i] = Bitmap.createBitmap(spriteSheet, x * 3, y * 3, width * 3, height * 3);
            x += width;
        }
    }

    public void setMoveOver(boolean isMoveOver) {
        this.isMoveOver = isMoveOver;
    }

    public boolean checkMoveOver() {
        return isMoveOver;
    }

    public boolean checkDead() {
        return isDead;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setHitPoints(int hitPoints, int maxHitPoints) {
        this.hitPoints = hitPoints;
        this.maxHitPoints = maxHitPoints;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
