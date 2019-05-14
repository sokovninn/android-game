package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public class Chest extends Item {
    static Bitmap chestSprite;
    static {
        chestSprite = Bitmap.createBitmap(Item.allChestsSpriteSheet, 0, 0,
                32 * 3, 32 * 3);
    }
    private Item loot;
    private boolean isOpened = false;
    private Map map;

    public Chest(int initialX, int initialY, Map map) {
        super(initialX, initialY, chestSprite, map);
        this.map = map;
        loot = new Sword(mapCoordinates.x, mapCoordinates.y, map);
    }

    @Override
    public void interact(Player player) {
        player.setMoveOver(true);
        open();
        isRemoved = true;
    }

    public void addLoot(String lootName) {
        switch (lootName) {
            case "Axe":
                loot = new Axe(mapCoordinates.x, mapCoordinates.y, map);
                break;
            case "Chest":
                loot = new Chest(mapCoordinates.x, mapCoordinates.y, map);
                break;
            case "HealthPotion":
                loot = new HealthPotion(mapCoordinates.x, mapCoordinates.y, map);
                break;
            case "Key":
                loot = new Key(mapCoordinates.x, mapCoordinates.y, map);
                break;
            case "Ring":
                loot = new Ring(mapCoordinates.x, mapCoordinates.y, map);
                break;
            case "Sword":
                loot = new Sword(mapCoordinates.x, mapCoordinates.y, map);
                break;
            case "Treasure":
                loot = new Treasure(mapCoordinates.x, mapCoordinates.y, 0, map);
                break;
        }

    }

    public void open() {
        isOpened = true;
    }

    public Item getLoot() {
        return loot;
    }

    public boolean checkOpened() {
        return isOpened;
    }
}
