package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Point;

public class CharacterSprite extends CreatureSprite {

    public CharacterSprite(Bitmap bmp, MapSprite map) {
        super(bmp, map);
    }

    public void update(Point point) {
       int offsetX = point.x - x;
       int offsetY = point.y - y;
       if (Math.abs(offsetX) > Math.abs(offsetY)) {
           if (offsetX > 0) {
               moveHorizontally(1);
           }
           else {
               moveHorizontally(-1);
           }
       } else {
           if (offsetY > 0) {
               moveVertically(1);
           }
           else {
               moveVertically(-1);
           }
       }
    }

    public void moveHorizontally(int direction) {
        int updatedMapX = mapX + direction;
        if (updatedMapX >= 0 && updatedMapX < mapArray[0].length && mapArray[mapY][updatedMapX] != 83) {
            mapX = updatedMapX;
            x += stepLength * direction;
        }
    }

    public void moveVertically(int direction) {
        int updatedMapY = mapY + direction;
        if (updatedMapY >= 0 && updatedMapY < mapArray.length && mapArray[updatedMapY][mapX] != 83) {
            mapY = updatedMapY;
            y += stepLength * direction;
        }
    }
}

