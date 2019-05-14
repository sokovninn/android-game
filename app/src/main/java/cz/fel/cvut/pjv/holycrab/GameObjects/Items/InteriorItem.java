package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.R;

public class InteriorItem extends Item {
    static Bitmap allInteriorItemsSpriteSheet;
    static Bitmap[] interiorItemSprites;
    static {
        allInteriorItemsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.interior);
        interiorItemSprites = new Bitmap[10];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                interiorItemSprites[i * 5 + j] = Bitmap.createBitmap(allInteriorItemsSpriteSheet, j * 32 * 3, i * 32 * 3,
                        32 * 3, 32 * 3);
            }
        }

    }
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param numItem Number of interior item
     * @param map Map of the room
     */
    public InteriorItem(int initialX, int initialY, int numItem, Map map) {
        super(initialX, initialY, interiorItemSprites[numItem], map);
    }

    @Override
    public void interact(Player player) {
        player.setMoveOver(true);
    }
}
