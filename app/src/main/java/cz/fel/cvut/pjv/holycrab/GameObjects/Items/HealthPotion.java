package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;

public class HealthPotion extends Item {
    private static Bitmap healthPotionSpite;
    static {
        healthPotionSpite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 32 * 3, 64 * 3,
                32 * 3, 32 * 3);
    }
    private int healPower = 1;
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param map Map of the room
     */
    public HealthPotion(int initialX, int initialY, Map map) {
        super(initialX, initialY, healthPotionSpite, map);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        super.interact(player);
        if (!player.checkMoveOver()) {
            player.heal(healPower);
            isRemoved = true;
        }
    }

}
