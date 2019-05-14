package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;

public class Axe extends Item {
    private static Bitmap axeSprite;
    static {
        axeSprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 96 * 3, 0,
                32 * 3, 32 * 3);
    }
    private static int damage = 3;

    public Axe(int initialX, int initialY, Map map) {
        super(initialX, initialY, axeSprite, map);
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!player.checkMoveOver()) {
            player.getWeapon(this, damage);
            isRemoved = true;
        }
    }

    public static int getDamage() {
        return damage;
    }
}
