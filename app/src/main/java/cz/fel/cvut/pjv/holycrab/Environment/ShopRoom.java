package cz.fel.cvut.pjv.holycrab.Environment;

import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Axe;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Chest;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.HealthPotion;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Key;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Ring;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Sword;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.InteriorItem;


public class ShopRoom extends Room {
    private int itemPosition = 0;
    private int itemsForSaleAmount = 0;
    private Point firstItemCoordinates = new Point(3, 3);

    /**
     * @param gameView Game view
     * @param mapArray Array with numbers of tiles
     */
    public ShopRoom(GameView gameView, int[][] mapArray) {
        super(gameView, mapArray);
        initialize();
    }

    private void initialize() {
        addGameObject(new InteriorItem(1,1,3, getMap()), true);
        addGameObject(new InteriorItem(3,2,3, getMap()), true);
        addGameObject(new InteriorItem(6,2,3, getMap()), true);
        addGameObject(new InteriorItem(3,1,4, getMap()), true);
        addGameObject(new InteriorItem(6,1,4, getMap()), true);
        addGameObject(new InteriorItem(4,2,4, getMap()), true);
        addGameObject(new InteriorItem(5,2,4, getMap()), true);
        addGameObject(new InteriorItem(8,8,8, getMap()), true);
        addGameObject(new InteriorItem(8,7,9, getMap()), true);
        addGameObject(new InteriorItem(4,1,2, getMap()), true);
    }

    /**
     * @param itemName Item name to sell
     * @param cost Cost of the item
     */
    public void addItemForSale(String itemName, int cost) {
        itemsForSaleAmount++;
        Item item = null;
        int x = firstItemCoordinates.x + itemPosition++;
        itemPosition %= 4;
        int y = firstItemCoordinates.y;
        switch (itemName) {
            case "Axe":
                item = new Axe(x, y, super.getMap());
                break;
            case "Chest":
                item = new Chest(x, y, super.getMap());
                break;
            case "HealthPotion":
                item = new HealthPotion(x, y, super.getMap());
                break;
            case "Key":
                item = new Key(x, y, super.getMap());
                break;
            case "Ring":
                item = new Ring(x, y, super.getMap());
                break;
            case "Sword":
                item = new Sword(x, y, super.getMap());
                break;
        }
        item.setItemForSale(cost);
        addGameObject(item, true);

    }

    /**
     * @return Amount of items on sale
     */
    public int getItemsForSaleAmount() {
        return itemsForSaleAmount;
    }

    public void sellItem() {
        itemsForSaleAmount--;
    }
}
