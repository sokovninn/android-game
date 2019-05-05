package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class CharacterSprite extends CreatureSprite {
    private int stepLength;
    private static Bitmap characterHitPointImage;
    private static Bitmap chatacterSpriteSheet;
    private int keysAmount = 0;
    private Inventory inventory;

    static {
        characterHitPointImage = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.heart_48x48);
        chatacterSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.dwarf);
    }

    public CharacterSprite(Room room, int initialX, int initialY) {
        super(CharacterSprite.chatacterSpriteSheet, room.getMap(), CharacterSprite.characterHitPointImage);
        stepLength = MapSprite.getTileSize();
        setMapCoordinates(initialX, initialY);
        inventory = new Inventory(new Point(50, 1560));
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
        int updatedMapY = mapCoordinates.y + moveDirection.y;
        if (updatedMapX >= 0 && updatedMapX < mapArray[0].length && updatedMapY >= 0 && updatedMapY < mapArray.length) {
            if (mapArray[updatedMapY][updatedMapX] != 57 && mapArray[updatedMapY][updatedMapX] != 76
                    && mapArray[updatedMapY][updatedMapX] != 77 && mapArray[updatedMapY][updatedMapX] != 78) {
                updatedCoordinates.x = updatedMapX;
                updatedCoordinates.y = updatedMapY;
            }
        }
        return updatedCoordinates;

    }

    public void getKey() {
        keysAmount++;
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
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawKeys(canvas);
        inventory.draw(canvas);

    }
    // TODO: move it to UI
    public void drawHitPoints(Canvas canvas) {
        Point coordinates = new Point();
        coordinates.x = 20;
        coordinates.y = 20;
        for (int i = 0; i < hitPoints; i++) {
            canvas.drawBitmap(hitPointImage, coordinates.x, coordinates.y, null);
            coordinates.x += hitPointSize;
        }
    }

    public void drawKeys(Canvas canvas) {
        Point coordinates = new Point();
        coordinates.x = GameView.screenWidth - 150;
        coordinates.y = 20;
        for (int i = 0; i < keysAmount; i++) {
            canvas.drawBitmap(Key.keySprite, coordinates.x, coordinates.y, null);
            coordinates.y += 20;
        }
    }


    @Override
    public void interact(CharacterSprite characterSprite) {

    }

    public boolean hasKey() {
        return keysAmount > 0;
    }

    public void useKey() {
        keysAmount--;
    }
    //TODO make common way to get items
    public void getSword(Sword sword) {
        inventory.addItemToEquipment(sword, 4);
        strength = sword.getDamage();
    }
}

