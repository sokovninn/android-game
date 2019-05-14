package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Creature;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Treasure;

public abstract class Enemy extends Creature {
    private ArrayList<Point> behavior;
    private int behaviorDuration;
    private int currentMove;
    protected boolean isActive = true;
    protected boolean isChangingState = false;
    protected Player player;
    protected int reward;
    static Bitmap hitPointImage;
    static int hitPointSize;

    static {
        hitPointImage = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.heart_16x16);
        hitPointSize = hitPointImage.getWidth();
    }


    public Enemy(Bitmap spriteSheet, Map map, ArrayList<Point> behavior, Player player) {
        super(spriteSheet, map);
        this.behavior = behavior;
        this.player = player;
        if (behavior != null) {
            behaviorDuration = behavior.size();
        }

    }

    public void update(Point characterMove) {
        if (isActive) {
            Point move = getCoordinatesAfterUpdate();
            Point characterPosition = player.getMapCoordinates();
            if (checkCharacterOnTheWay(move, characterPosition, characterMove)) {
                attack(player);
            } else {
                if (move.x == characterMove.x && move.y == characterMove.y) {
                    attack(player);
                }
                changePosition(move, behavior.get(currentMove));
            }
            if (isChangingState) isActive = false;
        } else {
            isActive = true;
        }
    }

    public Point getCoordinatesAfterUpdate() {
        Point updatedCoordinates = new Point();
        Point nextMove = behavior.get(currentMove);
        updatedCoordinates.x = mapCoordinates.x + nextMove.x;
        updatedCoordinates.y = mapCoordinates.y + nextMove.y;
        return updatedCoordinates;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawHitPoints(canvas);
    }

    public void drawHitPoints(Canvas canvas) {
        Point coordinates = new Point();
        coordinates.x = screenCoordinates.x + widthpx / 2 - (hitPoints * hitPointSize) / 2;
        coordinates.y = screenCoordinates.y - hitPointSize / 2;
        if (hitPoints > 5) {
            coordinates.y -= hitPointSize;
            coordinates.x = screenCoordinates.x + widthpx / 2 - (5 * hitPointSize) / 2;
        }
        for (int i = 0; i < hitPoints; i++) {
            canvas.drawBitmap(hitPointImage, coordinates.x + i % 5 * hitPointSize, coordinates.y + i / 5 * hitPointSize, null);
        }
    }

    protected boolean checkCharacterOnTheWay(Point move, Point characterPosition, Point characterMove) {
        return ((characterPosition.x == move.x) && (characterPosition.y == move.y)
                && ((characterMove.x == mapCoordinates.x) && (characterMove.y == mapCoordinates.y)
                || (characterMove.x == characterPosition.x) && (characterMove.y == characterPosition.y)));
    }

    protected void changePosition(Point move, Point direction) {
        mapCoordinates = move;
        screenCoordinates.x += direction.x * Map.getTileSize();
        screenCoordinates.y += direction.y * Map.getTileSize();
        currentMove++;
        currentMove = (currentMove == behaviorDuration) ? 0 : currentMove;
    }

    @Override
    public void interact(Player player) {

    }

    public Treasure getReward() {
        return new Treasure(mapCoordinates.x, mapCoordinates.y, reward, map);
    }
}
