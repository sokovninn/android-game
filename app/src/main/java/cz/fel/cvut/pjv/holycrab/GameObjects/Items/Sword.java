package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.content.res.Resources;
import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class Sword extends Item {
    private static Bitmap swordSprite;
    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            swordSprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 0, 0,
                    32 * 3, 32 * 3);
        }
    }
    private static int damage = 2;
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param map Map of the room
     */
    public Sword(int initialX, int initialY, Map map) {
        super(initialX, initialY,swordSprite, map);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!player.checkMoveOver()) {
            player.takeWeapon(this, damage);
            isRemoved = true;
        }
    }
}
