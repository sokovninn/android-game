package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class CharacterSprite extends CreatureSprite {
    private int stepLength;

    public CharacterSprite(Bitmap bmp, MapSprite map, Bitmap hitPointImage) {
        super(bmp, map, hitPointImage);
        stepLength = MapSprite.getTileSize();
    }

    public void update(Point point) {
       Point updatedCoordinates = getCoordinatesAfterUpdate(point);
       screenCoordinates.x += ((updatedCoordinates.x - mapCoordinates.x) * stepLength);
       screenCoordinates.y += ((updatedCoordinates.y - mapCoordinates.y) * stepLength);
       mapCoordinates = updatedCoordinates;

    }

    public Point getCoordinatesAfterUpdate(Point point) {
        int offsetX = point.x - screenCoordinates.x;
        int offsetY = point.y - screenCoordinates.y;
        if (Math.abs(offsetX) > Math.abs(offsetY)) {
            if (offsetX > 0) {
                return moveHorizontally( 1);
            }
            else {
                return moveHorizontally(-1);
            }
        } else {
            if (offsetY > 0) {
                return moveVertically(1);
            }
            else {
                return moveVertically(-1);
            }
        }

    }

    public Point moveHorizontally(int direction) {
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
    }

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

