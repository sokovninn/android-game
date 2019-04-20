package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Vector;

public class EnemySprite extends CreatureSprite {
    private ArrayList<Point> behavior;
    private int behaviorDuration;
    private int currentMove;

    public EnemySprite(Bitmap spriteSheet, MapSprite map, ArrayList<Point> behavior) {
        super(spriteSheet, map);
        this.behavior = behavior;
        behaviorDuration = behavior.size();
    }

    public void setBehavior(ArrayList<Point> behavior) {
        this.behavior = behavior;
    }

    public void update() {
        Point currentPoint = behavior.get(currentMove++);
        currentMove = currentMove == behaviorDuration ? 0 : currentMove;
        mapX += currentPoint.x;
        mapY += currentPoint.y;
        x += stepLength * currentPoint.x;
        y += stepLength * currentPoint.y;
    }

}
