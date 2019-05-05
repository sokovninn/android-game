package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;

public class Sword extends Item {
    static Bitmap swordSprite;
    static {
        swordSprite = Bitmap.createBitmap(Item.allItemsSpriteSheet, 0, 0,
                32 * 3, 32 * 3);
    }
    private int damage = 3;

    public Sword(int initialX, int initialY, MapSprite map) {
        super(initialX, initialY, Sword.swordSprite, map);
    }

    @Override
    public void interact(CharacterSprite characterSprite) {
        characterSprite.getSword(this);
    }

    public int getDamage() {
        return damage;
    }
}
