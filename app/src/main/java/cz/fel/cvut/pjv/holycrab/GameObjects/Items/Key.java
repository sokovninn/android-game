package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public class Key extends Item {
    private static Bitmap keySprite;
    static {
        //TODO do something with key image
        keySprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 32 * 3, 129 * 3,
                32 * 3, 31 * 3);
    }
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param map Map of the room
     */
    public Key(int initialX, int initialY, Map map) {
        super(initialX, initialY, keySprite, map);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!player.checkMoveOver()) {
            player.takeKey();
            isRemoved = true;
        }
    }

    /**
     * @return Image of key
     */
    public static Bitmap getKeySprite() {
        return keySprite;
    }
}
