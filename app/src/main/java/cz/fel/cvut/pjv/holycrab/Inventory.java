package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class Inventory {
    // TODO make it better
    private Bitmap allEmptySlotsSpriteSheet;
    private Bitmap[] emptySlots = new Bitmap[8];
    private Item[] equipment = new Item[8];
    private Point screenCoordinates;


    public Inventory(Point screenCoordinates) {
        this.screenCoordinates = screenCoordinates;
        allEmptySlotsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.empty_slots);
        int i = 0;
        for (int y = 0; y < 64; y+=32) {
            for (int x = 0; x < 128; x+=32) {
                emptySlots[i++] = Bitmap.createBitmap(allEmptySlotsSpriteSheet, x * 3, y * 3,
                        32 * 3, 32 * 3);
            }
        }
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < equipment.length; i++) {
            int shiftX = i * 32 * 3;
            if (equipment[i] == null) {
                canvas.drawBitmap(emptySlots[i], screenCoordinates.x + shiftX, screenCoordinates.y, null);
            } else {
                equipment[i].draw(canvas, screenCoordinates.x + shiftX, screenCoordinates.y);
            }
        }
    }

    public void addItemToEquipment(Item item, int number) {
        equipment[number] = item;
    }
}
