package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public abstract class Creature extends GameObject {
    private Bitmap[] creatureFrontFrames;
    private int currentFrame = 0;
    private long previousFrameChangeTime = 0;
    private long frameLength = 500;
    protected int maxHitPoints = 3;
    protected int hitPoints = 3;
    private int strength = 1;
    private boolean isDead = false;
    private boolean isMoveOver = false;

    public Creature(Bitmap spriteSheet, Map map) {
        super(spriteSheet, map);
        if (spriteSheet != null) {
            setCreatureFrontFrames(0, 64, 24, 32);
            widthpx = creatureFrontFrames[0].getWidth();
            heightpx = creatureFrontFrames[0].getHeight();
        }
        if (map != null) {
            setMapCoordinates(mapArray[0].length / 2, mapArray.length / 2);
        }

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
