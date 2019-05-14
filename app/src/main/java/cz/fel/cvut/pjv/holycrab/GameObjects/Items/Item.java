package cz.fel.cvut.pjv.holycrab.GameObjects.Items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.R;

public abstract class Item extends GameObject {
    static Bitmap allItemsSpriteSheet;
    static Bitmap extendedItemsSpriteSheet;
    static Bitmap allChestsSpriteSheet;
    static Bitmap treasureSpriteSheet;
    private Bitmap itemSprite;
    boolean isForSale = false;
    protected int cost;
    private Paint costText;
    private Point costTextCoordinates;
    protected boolean isRemoved = false;
    static {
        allItemsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.items);
        allChestsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.chests);
        treasureSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.treasure_icons);
        extendedItemsSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.items_extended);

    }
    public Item(int initialX, int initialY, Bitmap itemSprite, Map map) {
        super(itemSprite, map);
        this.itemSprite = itemSprite;
        setMapCoordinates(initialX, initialY);
        //correctScreenCoordinates(0, );
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(itemSprite, screenCoordinates.x, screenCoordinates.y, null);
        if (isForSale) {
            canvas.drawText(String.valueOf(cost), costTextCoordinates.x, costTextCoordinates.y, costText);
        }
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(itemSprite, x, y, null);
    }

    public void interact(Player player) {
        if (isForSale) {
            if (player.getGoldAmount() > cost) {
                player.buyItem(cost);
                isForSale = false;
            } else {
                player.setMoveOver(true);
            }
        }
    }

    public void setItemForSale(int cost) {
        this.cost = cost;
        isForSale = true;
        setCostText(Color.WHITE, 30);
    }

    public void setCostText(int color, int textSize) {
        costText = new Paint();
        costText.setColor(color);
        costText.setTextSize(textSize);
        costTextCoordinates = new Point(screenCoordinates.x, screenCoordinates.y + 90);
    }

    public boolean checkRemoved() {
        return isRemoved;
    }

    public int getCost() {
        return cost;
    }

    public boolean checkIsForSale() {
        return isForSale;
    }


}
