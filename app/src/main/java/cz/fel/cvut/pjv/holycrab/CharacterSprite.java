package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class CharacterSprite extends CreatureSprite {
    private int stepLength;
    private static Bitmap characterHitPointImage;
    private static Bitmap chatacterSpriteSheet;

    static {
        characterHitPointImage = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.heart_48x48);
        chatacterSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.dwarf);

    }

    public CharacterSprite(GameView gameView, int initialX, int initialY) {
        super(CharacterSprite.chatacterSpriteSheet, gameView.getMap(), CharacterSprite.characterHitPointImage);
        stepLength = MapSprite.getTileSize();
        setMapCoordinates(initialX, initialY);
    }

    public void update(Point moveDirection) {
       Point updatedCoordinates = getCoordinatesAfterUpdate(moveDirection);
       screenCoordinates.x += ((updatedCoordinates.x - mapCoordinates.x) * stepLength);
       screenCoordinates.y += ((updatedCoordinates.y - mapCoordinates.y) * stepLength);
       mapCoordinates = updatedCoordinates;

    }

    public Point getCoordinatesAfterUpdate(Point moveDirection) {
        Point updatedCoordinates = new Point(mapCoordinates.x, mapCoordinates.y);
        int updatedMapX = mapCoordinates.x + moveDirection.x;
        if (updatedMapX >= 0 && updatedMapX < mapArray[0].length && mapArray[mapCoordinates.y][updatedMapX] != 83) {
            updatedCoordinates.x = updatedMapX;
        }
        int updatedMapY = mapCoordinates.y + moveDirection.y;
        if (updatedMapY >= 0 && updatedMapY < mapArray.length && mapArray[updatedMapY][mapCoordinates.x] != 83) {
            updatedCoordinates.y = updatedMapY;
        }
        return updatedCoordinates;

    }

    /*public Point moveHorizontally(int direction) {
        Point updatedCoordinates = new Point(mapCoordinates.x, mapCoordinates.y);
        int updatedMapX = mapCoordinates.x + direction;
        if (updatedMapX >= 0 && updatedMapX < mapArray[0].length && mapArray[mapCoordinates.y][updatedMapX] != 83) {
            updatedCoordinates.x = updatedMapX;
        }
        return updatedCoordinates;
    }

    public Point moveVertically(int direction) {
        Point updatedCoordinates = new Point(mapCoordinates.x, mapCoordinates.y);
        int updatedMapY = mapCoordinates.y + direction;
        if (updatedMapY >= 0 && updatedMapY < mapArray.length && mapArray[updatedMapY][mapCoordinates.x] != 83) {
            updatedCoordinates.y = updatedMapY;
        }
        return updatedCoordinates;
    }*/

    public void drawHitPoints(Canvas canvas) {
        Point coordinates = new Point();
        coordinates.x = 20;
        coordinates.y = 20;
        for (int i = 0; i < hitPoints; i++) {
            canvas.drawBitmap(hitPointImage, coordinates.x, coordinates.y, null);
            coordinates.x += hitPointSize;
        }
    }


}

