package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Axe;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Ring;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Sword;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Inventory;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Environment.Room;

public class Player extends Creature {
    private int stepLength = 96;
    private static Bitmap playerSpriteSheet;
    private int keysAmount = 0;
    private int goldAmount = 10;

    private Inventory inventory;

    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            playerSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.dwarf);
        }
    }

    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     */
    public Player(int initialX, int initialY) {
        super(Player.playerSpriteSheet, null);
        setMapCoordinates(initialX, initialY);
        inventory = new Inventory();
    }

    /**
     * @param room Room in which player yes
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     */
    public Player(Room room, int initialX, int initialY) {
        super(Player.playerSpriteSheet, room.getMap());
        setMapCoordinates(initialX, initialY);
        inventory = new Inventory();
    }

    /**
     * @param moveDirection Direction of move
     */
    public void update(Point moveDirection) {
       Point updatedCoordinates = getCoordinatesAfterUpdate(moveDirection);
       screenCoordinates.x += ((updatedCoordinates.x - mapCoordinates.x) * stepLength);
       screenCoordinates.y += ((updatedCoordinates.y - mapCoordinates.y) * stepLength);
       mapCoordinates = updatedCoordinates;
    }

    /**
     * @param moveDirection Direction of move
     * @return Position after update
     */
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

    /**
     * Increase amount of keys
     */
    public void takeKey() {
        keysAmount++;
    }

    /**
     * @param canvas Canvas to draw on
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        setMoveOver(true);
    }

    /**
     * @return True if have at least 1 key
     */
    public boolean hasKey() {
        return keysAmount > 0;
    }

    /**
     * Use key to open the door
     */
    public void useKey() {
        keysAmount--;
    }

    /**
     * @param weapon Weapon to be taken
     * @param damage Damage of weapon
     */
    //TODO make common way to get items
    public void takeWeapon(Item weapon, int damage) {
        inventory.addItemToEquipment(weapon, 4);
        super.setStrength(damage);
    }

    /**
     * @param ring Ring to be taken
     */
    public void takeRing(Ring ring) {
        inventory.addItemToEquipment(ring, 7);
        hitPoints++;
        maxHitPoints++;
    }

    /**
     * @param itemName Name of the item
     */
    public void takeItem(String itemName) {
        switch (itemName) {
            case "Axe":
                takeWeapon(new Axe(0,0, map), Axe.getDamage());
                break;
            case "Ring":
                takeRing(new Ring(0, 0, map));
                break;
            case "Sword":
                takeWeapon(new Sword(0,0, map), Axe.getDamage());
                break;
        }
    }

    /**
     * @param healPower Amount of hp, that can be restored
     */
    public void heal(int healPower) {
        int updatedHitPoints = hitPoints + healPower;
        hitPoints = updatedHitPoints < maxHitPoints ? updatedHitPoints : maxHitPoints;
    }

    /**
     * @param value Value of treasure
     */
    public void takeTreasure(int value) {
        goldAmount += value;
    }

    /**
     * @return Amount of keys
     */
    public int getKeysAmount() {
        return keysAmount;
    }

    /**
     * @return Amount of Gold
     */
    public int getGoldAmount() {
        return goldAmount;
    }

    /**
     * @return Players inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param cost Cost of the item
     */
    public void buyItem(int cost) {
        goldAmount -= cost;
    }

    /**
     * @param goldAmount Amount of gold
     */
    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    /**
     * @param keysAmount Amount of keys
     */
    public void setKeysAmount(int keysAmount) {
        this.keysAmount = keysAmount;
    }
}

