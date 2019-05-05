package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public abstract class Item extends GameObject {
    static Bitmap allItemsSpriteSheet;
    private Bitmap itemSprite;
    static {
        allItemsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.items);
    }
    public Item(int initialX, int initialY, Bitmap itemSprite, MapSprite map) {
        super(itemSprite, map);
        this.itemSprite = itemSprite;
        setMapCoordinates(initialX, initialY);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(itemSprite, screenCoordinates.x, screenCoordinates.y, null);
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(itemSprite, x, y, null);
    }
}
