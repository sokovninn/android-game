package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class Axe extends Item {
    private static Bitmap axeSprite;
    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            axeSprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 96 * 3, 0,
                    32 * 3, 32 * 3);
        }

    }
    private static int damage = 3;

    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param map Map of the room
     */
    public Axe(int initialX, int initialY, Map map) {
        super(initialX, initialY, axeSprite, map);
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

    /**
     * @return Damage of weapon
     */
    public static int getDamage() {
        return damage;
    }
}
