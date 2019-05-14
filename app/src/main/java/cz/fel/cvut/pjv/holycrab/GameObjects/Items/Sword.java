package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public class Sword extends Item {
    private static Bitmap swordSprite;
    static {
        swordSprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 0, 0,
                32 * 3, 32 * 3);
    }
    private static int damage = 2;

    public Sword(int initialX, int initialY, Map map) {
        super(initialX, initialY,swordSprite, map);
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
