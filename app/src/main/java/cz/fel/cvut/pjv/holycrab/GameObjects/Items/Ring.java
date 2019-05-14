package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;

public class Ring extends Item {
    private static Bitmap ringSprite;
    static {
        ringSprite = Bitmap.createBitmap(Item.extendedItemsSpriteSheet, 96 * 3, 64 * 3,
                32 * 3, 32 * 3);
    }
    private int damage = 2;

    public Ring(int initialX, int initialY, Map map) {
        super(initialX, initialY, ringSprite, map);
    }

    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!player.checkMoveOver()) {
            player.getRing(this);
            isRemoved = true;
        }
    }
}
