package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

public class SkeletonSprite extends EnemySprite {
    private static Bitmap skeletonSpriteSheet;
    private static ArrayList<Point> behavior;
    static {
        skeletonSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.tiny_skelly);
        behavior = new ArrayList<>();
        behavior.add(new Point(1, 0));
        behavior.add(new Point(0, 1));
        behavior.add(new Point(-1, 0));
        behavior.add(new Point(0, -1));
    }

    public SkeletonSprite(GameView gameView, int initialX, int initialY) {
        super(SkeletonSprite.skeletonSpriteSheet, gameView.getMap(), behavior, gameView.getCharacter());
        setMapCoordinates(initialX, initialY);
    }
}
