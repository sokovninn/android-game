package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.content.res.Resources;
import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class Ring extends Item {
    private static Bitmap ringSprite;
    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            ringSprite = Bitmap.createBitmap(Item.extendedItemsSpriteSheet, 96 * 3, 64 * 3,
                    32 * 3, 32 * 3);
        }
    }
    private int damage = 2;
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param map Map of the room
     */
    public Ring(int initialX, int initialY, Map map) {
        super(initialX, initialY, ringSprite, map);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!player.checkMoveOver()) {
            player.takeRing(this);
            isRemoved = true;
        }
    }
}
