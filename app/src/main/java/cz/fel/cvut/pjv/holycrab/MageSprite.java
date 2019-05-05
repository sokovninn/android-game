package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

public class MageSprite extends EnemySprite {
    private static Bitmap mageSpriteSheet;
    private static ArrayList<Point> behavior;
    static {
        mageSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.mage_dark);
        behavior = new ArrayList<>();
        behavior.add(new Point(-1, 1));
        behavior.add(new Point(0, -1));
        behavior.add(new Point(-1, 1));
        behavior.add(new Point(0, -1));
        behavior.add(new Point(1, -1));
        behavior.add(new Point(0, 1));
        behavior.add(new Point(1, -1));
        behavior.add(new Point(0, 1));

    }

    public MageSprite(Room room, int initialX, int initialY) {
        super(MageSprite.mageSpriteSheet, room.getMap(), behavior, room.getCharacter());
        setMapCoordinates(initialX, initialY);
        isChangingState = true;
    }
}