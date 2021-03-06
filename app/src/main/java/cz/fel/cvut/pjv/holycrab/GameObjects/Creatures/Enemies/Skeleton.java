package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Environment.Room;

public class Skeleton extends Enemy {
    private static Bitmap skeletonSpriteSheet;
    private static ArrayList<Point> behavior;
    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            skeletonSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.tiny_skelly);
        }
        behavior = new ArrayList<>();
        behavior.add(new Point(1, 0));
        behavior.add(new Point(0, 1));
        behavior.add(new Point(-1, 0));
        behavior.add(new Point(0, -1));
    }

    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     */
    public Skeleton(int initialX, int initialY) {
        super(Skeleton.skeletonSpriteSheet, null, behavior, null);
        setMapCoordinates(initialX, initialY);
        isChangingState = true;
        reward = 0;
        hitPoints = 2;
    }

    /**
     * @param room Room in which enemy is
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     */
    public Skeleton(Room room, int initialX, int initialY) {
        super(Skeleton.skeletonSpriteSheet, room.getMap(), behavior, room.getPlayer());
        setMapCoordinates(initialX, initialY);
        isChangingState = true;
        reward = 0;
        hitPoints = 2;
    }
}
