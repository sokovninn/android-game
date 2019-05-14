package cz.fel.cvut.pjv.holycrab.Environment;

import android.graphics.Canvas;


public class Tile {
    private int tileType;

    public Tile(int tileType) {
        this.tileType = tileType;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(Map.getTile(tileType), x, y, null);
    }
}
