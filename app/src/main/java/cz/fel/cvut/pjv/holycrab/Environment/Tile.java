package cz.fel.cvut.pjv.holycrab.Environment;

import android.graphics.Canvas;


public class Tile {
    private int tileType;

    Tile(int tileType) {
        this.tileType = tileType;
    }

    /**
     * @param canvas Canvas to draw on
     * @param x Vertical coordinate
     * @param y Horizontal coordinate
     */
    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(Map.getTile(tileType), x, y, null);
    }
}
