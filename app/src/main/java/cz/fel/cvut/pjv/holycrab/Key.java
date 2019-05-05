package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Key extends Item {
    static Bitmap keySprite;
    static {
        //TODO do something with key image
        keySprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 32 * 3, 129 * 3,
                32 * 3, 31 * 3);
    }
    public Key(int initialX, int initialY, MapSprite map) {
        super(initialX, initialY, keySprite, map);
    }

    @Override
    public void interact(CharacterSprite characterSprite) {
        characterSprite.getKey();
    }
}
