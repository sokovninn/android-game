package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.content.res.Resources;
import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class Treasure extends Item {
    static Bitmap[] treasureSprites;
    static int[] values;
    static {
        treasureSprites = new Bitmap[4];
        values = new int[] {1, 3, 5, 25};
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            for (int i = 0; i < 4; i++) {
                treasureSprites[i] = Bitmap.createBitmap(Item.treasureSpriteSheet, i * 32 * 3, 64 * 3,
                        32 * 3, 32 * 3);
            }
        }

    }
    private int value;
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param numValue Number of value in array values[]
     * @param map Map of the room
     */
    public Treasure(int initialX, int initialY, int numValue, Map map) {
        super(initialX, initialY, treasureSprites[numValue], map);
        //correctScreenCoordinates(0, 10);
        value = values[numValue];
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        player.takeTreasure(value);
        isRemoved = true;
    }
}
