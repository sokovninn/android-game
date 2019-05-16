package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;


import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class Arachne extends Enemy {
    private static Bitmap arachneSpriteSheet;
    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            arachneSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.arachne);
        }

    }
    private Point direction = new Point();

    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     */
    public Arachne(int initialX, int initialY) {
        super(arachneSpriteSheet, null, null, null);
        setMapCoordinates(initialX, initialY);
        maxHitPoints = 10;
        isChangingState = true;
        hitPoints = maxHitPoints;
        reward = 3;
    }
    /**
     * @param room Room in which Enemy is
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     */
    public Arachne(Room room, int initialX, int initialY) {
        super(arachneSpriteSheet, room.getMap(), null, room.getPlayer());
        setMapCoordinates(initialX, initialY);
        maxHitPoints = 10;
        isChangingState = true;
        hitPoints = maxHitPoints;
        reward = 3;
    }

    /**
     * @param characterMove Position of players character after move
     */
    @Override
    public void update(Point characterMove) {
        if (isActive) {
            Point move = getCoordinatesAfterUpdate();
            Point characterPosition = player.getMapCoordinates();
            if (checkCharacterOnTheWay(move, characterPosition, characterMove)) {
                attack(player);
            } else {
                if (move.x == characterMove.x && move.y == characterMove.y) {
                    attack(player);
                    heal();
                }
                changePosition(move, direction);
            }
            if (isChangingState) isActive = false;
        } else {
            isActive = true;
        }
    }

    private void heal() {
        hitPoints = hitPoints + 1 > maxHitPoints ? maxHitPoints : hitPoints + 1;
    }

    /**
     * @return Position after move
     */
    @Override
    public Point getCoordinatesAfterUpdate() {
        //TODO: fix behavior
        Point updatedCoordinates = new Point();
        Point playerCoordinates = player.getMapCoordinates();
        direction.x = playerCoordinates.x - mapCoordinates.x;
        direction.x = direction.x == 0 ? 0 : direction.x / Math.abs(direction.x) * 2;
        direction.y = playerCoordinates.y - mapCoordinates.y;
        direction.y = direction.y == 0 ? 0 : direction.y / Math.abs(direction.y) * 2;
        updatedCoordinates.x = mapCoordinates.x + direction.x;
        updatedCoordinates.y = mapCoordinates.y + direction.y;
        return updatedCoordinates;
    }


}
