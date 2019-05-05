package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;

public abstract class EnemySprite extends CreatureSprite {
    private ArrayList<Point> behavior;
    private int behaviorDuration;
    private int currentMove;
    private boolean isActive = true;
    protected boolean isChangingState = false;
    private CharacterSprite characterSprite;

    static Bitmap hitPointImage;

    static {
        hitPointImage = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.heart_16x16);
    }


    public EnemySprite(Bitmap spriteSheet, MapSprite map, ArrayList<Point> behavior, CharacterSprite characterSprite) {
        super(spriteSheet, map, hitPointImage);
        this.behavior = behavior;
        this.characterSprite = characterSprite;
        behaviorDuration = behavior.size();

    }

    public void update(Point characterMove) {
        if (isActive) {
            Point move = getCoordinatesAfterUpdate();
            Point characterPosition = characterSprite.getMapCoordinates();
            if (checkCharacterOnTheWay(move, characterPosition, characterMove)) {
                attack(characterSprite);
            } else {
                if (move.x == characterMove.x && move.y == characterMove.y) {
                    attack(characterSprite);
                }
                changePosition(move);
            }
            if (isChangingState) isActive = false;
        } else {
            isActive = true;
        }
    }

    public Point getCoordinatesAfterUpdate() {
        Point updatedCoordinates = new Point();
        updatedCoordinates.x = mapCoordinates.x;
        updatedCoordinates.y = mapCoordinates.y;
        Point nextMove = behavior.get(currentMove);
        updatedCoordinates.x += nextMove.x;
        updatedCoordinates.y += nextMove.y;
        return updatedCoordinates;
    }

    public void drawHitPoints(Canvas canvas) {
        Point coordinates = new Point();
        coordinates.x = screenCoordinates.x + widthpx / 2 - (hitPoints * hitPointSize) / 2;
        coordinates.y = screenCoordinates.y - hitPointSize / 3;
        for (int i = 0; i < hitPoints; i++) {
            canvas.drawBitmap(hitPointImage, coordinates.x, coordinates.y, null);
            coordinates.x += hitPointSize;
        }
    }

    private boolean checkCharacterOnTheWay(Point move, Point characterPosition, Point characterMove) {
        return ((characterPosition.x == move.x) && (characterPosition.y == move.y)
                && ((characterMove.x == mapCoordinates.x) && (characterMove.y == mapCoordinates.y)
                || (characterMove.x == characterPosition.x) && (characterMove.y == characterPosition.y)));
    }

    private void changePosition(Point move) {
        mapCoordinates = move;
        screenCoordinates.x += behavior.get(currentMove).x * MapSprite.getTileSize();
        screenCoordinates.y += behavior.get(currentMove).y * MapSprite.getTileSize();
        currentMove++;
        currentMove = (currentMove == behaviorDuration) ? 0 : currentMove;
    }

    @Override
    public void interact(CharacterSprite characterSprite) {

    }
}
