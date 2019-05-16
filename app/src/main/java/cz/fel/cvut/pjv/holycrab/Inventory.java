package cz.fel.cvut.pjv.holycrab;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class Inventory {
    // TODO make it better
    private Bitmap allEmptySlotsSpriteSheet;
    private Bitmap[] emptySlots = new Bitmap[8];
    private Item[] equipment = new Item[8];


    public Inventory() {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            allEmptySlotsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.empty_slots);
            int i = 0;
            for (int y = 0; y < 64; y += 32) {
                for (int x = 0; x < 128; x += 32) {
                    emptySlots[i++] = Bitmap.createBitmap(allEmptySlotsSpriteSheet, x * 3, y * 3,
                            32 * 3, 32 * 3);
                }
            }
        }
    }

    /**
     * @param canvas Canvas to draw on
     * @param x Horizontal coordinate
     * @param y Vertical coordinate
     */
    public void draw(Canvas canvas, int x, int y) {
        for (int i = 0; i < equipment.length; i++) {
            int shiftX = i * 32 * 3;
            if (equipment[i] == null) {
                canvas.drawBitmap(emptySlots[i], x + shiftX, y, null);
            } else {
                equipment[i].draw(canvas, x + shiftX, y);
            }
        }
    }

    /**
     * @param item Item to be added to inventory
     * @param number Number of item in array
     */
    public void addItemToEquipment(Item item, int number) {
        equipment[number] = item;
    }

    /**
     * @return Full equipment
     */
    public Item[] getEquipment() {
        return equipment;
    }
}
