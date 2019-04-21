package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;

public class EnemySprite extends CreatureSprite {
    private ArrayList<Point> behavior;
    private int behaviorDuration;
    private int currentMove;
    private boolean is_moving = true;

    public EnemySprite(Bitmap spriteSheet, MapSprite map, ArrayList<Point> behavior) {
        super(spriteSheet, map);
        this.behavior = behavior;
        behaviorDuration = behavior.size();
    }

    public void setBehavior(ArrayList<Point> behavior) {
        this.behavior = behavior;
    }

    public void update() {
        mapCoordinates = getCoordinatesAfterUpdate();
        if (is_moving) {
            screenCoordinates.x += behavior.get(currentMove).x * MapSprite.getTileSize();
            screenCoordinates.y += behavior.get(currentMove).y * MapSprite.getTileSize();
            currentMove++;
            currentMove = (currentMove == behaviorDuration) ? 0 : currentMove;
            is_moving = false;
        } else {
            is_moving = true;
        }
    }

    public Point getCoordinatesAfterUpdate() {
        Point updatedCoordinates = new Point();
        updatedCoordinates.x = mapCoordinates.x;
        updatedCoordinates.y = mapCoordinates.y;
        if (is_moving) {
            Point nextMove = behavior.get(currentMove);
            updatedCoordinates.x += nextMove.x;
            updatedCoordinates.y += nextMove.y;
        }
        return updatedCoordinates;
    }
}
