package cz.fel.cvut.pjv.holycrab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;


public class MapSprite {

    private static Bitmap[] allTileSprites;
    private static int tileSize;
    private static int tilesCount;
    private static int scaleFactor;

    private int[][] mapArray;
    private TileSprite[][] tileSprites;
    private int mapHeight, mapWidth;
    private int startCornerX;
    private int startCornerY;


    public MapSprite(int[][] mapArray, int mapHeight, int mapWidth) {
        this.mapArray = mapArray;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        startCornerX = (GameView.screenWidth - mapWidth * tileSize + tileSize) / 2 ;
        startCornerY = (GameView.screenHeight - mapHeight * tileSize + tileSize) / 2;


        initializeMap();
    }

    public void initializeMap() {
        tileSprites = new TileSprite[mapHeight][mapWidth];
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                tileSprites[r][c] = new TileSprite(mapArray[r][c]);
            }
        }
    }

    public void draw(Canvas canvas) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                tileSprites[row][col].draw(canvas,startCornerX + col * tileSize,startCornerY + row * tileSize);
            }
        }
    }

    public Point convertMapToScreenCoordinates(Point mapCoordinates) {
        Point screenCoordinates = new Point();
        screenCoordinates.x = startCornerX + tileSize * mapCoordinates.x;
        screenCoordinates.y = startCornerY + tileSize * mapCoordinates.y;

        return screenCoordinates;
    }

    public static void initializeTileSprites(Bitmap image, int tSize, int tCount, int sFactor) {
        tileSize = tSize * 3;
        tilesCount = tCount;
        scaleFactor = sFactor;
        allTileSprites = new Bitmap[tCount];
        int columnsCount = image.getWidth() / tileSize;
        int row, col;

        for (int currentTile = 0; currentTile < tilesCount; currentTile++) {
            col = currentTile % columnsCount;
            row = currentTile / columnsCount;
            allTileSprites[currentTile] = Bitmap.createBitmap(image, tileSize * col,
                    tileSize * row, tileSize, tileSize);
            allTileSprites[currentTile] = Bitmap.createScaledBitmap(allTileSprites[currentTile],
                    tileSize * scaleFactor , tileSize * scaleFactor, true);
        }

        tileSize *= scaleFactor;
    }

    public static Bitmap getTile(int tileType) {
        return allTileSprites[tileType];
    }

    public static int getTileSize() {
        return tileSize;
    }

    public int[][] getMapArray() {
        return mapArray;
    }
}
