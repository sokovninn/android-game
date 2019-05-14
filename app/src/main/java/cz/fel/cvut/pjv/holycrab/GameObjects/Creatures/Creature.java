package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public abstract class Creature extends GameObject {
    private Bitmap[] creatureFrontFrames;
    private int currentFrame;
    private long previousFrameChangeTime, frameLength;
    protected int maxHitPoints;
    protected int hitPoints;
    private int strength;
    private boolean isDead;
    private boolean isMoveOver;

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

    private void manageCurrentFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > previousFrameChangeTime + frameLength) {
            currentFrame = (currentFrame + 1) % 3;
            previousFrameChangeTime = currentTime;
        }
    }

    /**
     * @param canvas Canvas to draw on
     */
    public void draw(Canvas canvas) {
        manageCurrentFrame();
        canvas.drawBitmap(creatureFrontFrames[currentFrame], screenCoordinates.x, screenCoordinates.y, null);
    }

    private void updateHitPoints(int change) {
        hitPoints += change;
        if (hitPoints < 1) isDead = true;
    }

    /**
     * @return Amount of hp
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @return Maximum amount of hp value
     */
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    /**
     * @return Strength of creature
     */
    public int getStrength() {
        return strength;
    }

    public void attack(Creature creature) {
        creature.updateHitPoints(-strength);
        creature.setMoveOver(true);
    }

    protected void setCreatureFrontFrames(int x, int y, int width, int height) {
        creatureFrontFrames = new Bitmap[3];
        for (int i = 0; i < 3; i++) {
            creatureFrontFrames[i] = Bitmap.createBitmap(spriteSheet, x * 3, y * 3, width * 3, height * 3);
            x += width;
        }
    }

    /**
     * @param isMoveOver True if move is over
     */
    public void setMoveOver(boolean isMoveOver) {
        this.isMoveOver = isMoveOver;
    }

    /**
     * @return True if move is over
     */
    public boolean checkMoveOver() {
        return isMoveOver;
    }

    /**
     * @return True if creature is dead
     */
    public boolean checkDead() {
        return isDead;
    }

    /**
     * @param hitPoints Amount of hp
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * @param hitPoints Amount of hp
     * @param maxHitPoints Maximum amount of hp
     */
    public void setHitPoints(int hitPoints, int maxHitPoints) {
        this.hitPoints = hitPoints;
        this.maxHitPoints = maxHitPoints;
    }

    /**
     * @param strength Strength of creature
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }
}
