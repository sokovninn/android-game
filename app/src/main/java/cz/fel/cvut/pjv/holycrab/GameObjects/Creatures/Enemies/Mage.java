package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.ArrayList;

import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Environment.Room;

public class Mage extends Enemy {
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

    public Mage(Room room, int initialX, int initialY) {
        super(Mage.mageSpriteSheet, room.getMap(), behavior, room.getCharacter());
        setMapCoordinates(initialX, initialY);
        isChangingState = true;
        reward = 1;
        hitPoints = 4;
    }
}