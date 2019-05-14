package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Axe;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Chest;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.HealthPotion;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Key;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Ring;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Sword;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Treasure;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Inventory;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Environment.Room;

public class Player extends Creature {
    private int stepLength;
    private static Bitmap playerSpriteSheet;
    private int keysAmount = 0;
    private int goldAmount = 10;

    private Inventory inventory;

    static {
        playerSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.dwarf);
    }

    public Player(Room room, int initialX, int initialY) {
        super(Player.playerSpriteSheet, room.getMap());
        stepLength = Map.getTileSize();
        setMapCoordinates(initialX, initialY);
        inventory = new Inventory();
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

    public void takeKey() {
        keysAmount++;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    @Override
    public void interact(Player player) {

    }

    public boolean hasKey() {
        return keysAmount > 0;
    }

    public void useKey() {
        keysAmount--;
    }
    //TODO make common way to get items
    public void getWeapon(Item weapon, int damage) {
        inventory.addItemToEquipment(weapon, 4);
        strength = damage;
    }
    public void getRing(Ring ring) {
        inventory.addItemToEquipment(ring, 7);
        hitPoints++;
        maxHitPoints++;
    }

    public void takeItem(String itemName) {
        switch (itemName) {
            case "Axe":
                getWeapon(new Axe(0,0, map), Axe.getDamage());
                break;
            case "Ring":
                getRing(new Ring(0, 0, map));
                break;
            case "Sword":
                getWeapon(new Sword(0,0, map), Axe.getDamage());
                break;
        }
    }

    public void heal(int healPower) {
        int updatedHitPoints = hitPoints + healPower;
        hitPoints = updatedHitPoints < maxHitPoints ? updatedHitPoints : maxHitPoints;
    }

    public void getTreasure(int value) {
        goldAmount += value;
    }

    public int getKeysAmount() {
        return keysAmount;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void buyItem(int cost) {
        goldAmount -= cost;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void setKeysAmount(int keysAmount) {
        this.keysAmount = keysAmount;
    }
}

