package cz.fel.cvut.pjv.holycrab;

import android.graphics.Canvas;

public class TileSprite {
    private int tileType;

    public TileSprite(int tileType) {
        this.tileType = tileType;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(MapSprite.getTile(tileType), x, y, null);
    }
}
