package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Key;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class UserInterface {
    private int hitPoints;
    private Bitmap hitPointImage;
    private Bitmap[] coinFrames;
    private int hitPointSize;
    private int keysAmount;
    private int goldAmount;
    private Inventory inventory;
    private int currentFrame;
    private long previousFrameChangeTime, frameLength;
    private Paint goldAmountText;

    public UserInterface() {
        hitPoints = 0;
        hitPointImage = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.heart_48x48);
        Bitmap coinSpriteSheet =  BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.coin);
        coinFrames = new Bitmap[8];
        for (int i = 0; i < 8; i++) {
            coinFrames[i] = Bitmap.createBitmap(coinSpriteSheet, i * 32 * 3, 0,
                    32 * 3, 32 * 3);
        }
        setGoldAmountText(Color.WHITE, 50);
        hitPointSize = hitPointImage.getWidth();
        keysAmount = 0;
        goldAmount = 0;
        frameLength = 100;
    }

    public void update(Player player) {
        hitPoints = player.getHitPoints();
        keysAmount = player.getKeysAmount();
        goldAmount = player.getGoldAmount();
        inventory = player.getInventory();
    }

    public void draw(Canvas canvas) {
        drawHitPoints(canvas);
        drawKeys(canvas);
        drawGold(canvas);
        if (!(inventory == null)) {
            inventory.draw(canvas, 50, 500);
        }

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

    public void drawKeys(Canvas canvas) {
        Point coordinates = new Point();
        coordinates.x = GameView.screenWidth - 150;
        coordinates.y = 20;
        for (int i = 0; i < keysAmount; i++) {
            canvas.drawBitmap(Key.getKeySprite(), coordinates.x, coordinates.y, null);
            coordinates.y += 20;
        }
    }

    public void manageCurrentCoinFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > previousFrameChangeTime + frameLength) {
            currentFrame = (currentFrame + 1) % 8;
            previousFrameChangeTime = currentTime;
        }
    }

    public void drawGold(Canvas canvas) {
        manageCurrentCoinFrame();
        canvas.drawBitmap(coinFrames[currentFrame], 20, 200, null);
        canvas.drawText(String.valueOf(goldAmount), 110, 265, goldAmountText);
    }

    public void setGoldAmountText(int color, int textSize) {
        goldAmountText = new Paint();
        goldAmountText.setColor(color);
        goldAmountText.setTextSize(textSize);
    }
}
