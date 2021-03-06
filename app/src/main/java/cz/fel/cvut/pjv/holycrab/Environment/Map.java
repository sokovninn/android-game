package cz.fel.cvut.pjv.holycrab.Environment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import cz.fel.cvut.pjv.holycrab.Views.GameView;


public class Map {

    private static Bitmap[] allTileSprites;
    private static int tileSize;
    private static int tilesCount;
    private static int scaleFactor;

    private int[][] mapArray;
    private Tile[][] tiles;
    private int mapHeight, mapWidth;
    private int startCornerX;
    private int startCornerY;


    /**
     * @param mapArray Array with numbers of tiles
     * @param mapHeight Height in tiles
     * @param mapWidth Width in tiles
     */
    public Map(int[][] mapArray, int mapHeight, int mapWidth) {
        this.mapArray = mapArray;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        startCornerX = (GameView.screenWidth - mapWidth * tileSize) / 2 ;
        startCornerY = (GameView.screenHeight - mapHeight * tileSize) / 2;


        initializeMap();
    }

    private void initializeMap() {
        tiles = new Tile[mapHeight][mapWidth];
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                tiles[r][c] = new Tile(mapArray[r][c]);
            }
        }
    }

    /**
     * @param canvas Canvas to draw on
     */
    public void draw(Canvas canvas) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                tiles[row][col].draw(canvas,startCornerX + col * tileSize,startCornerY + row * tileSize);
            }
        }
    }

    /**
     * @param mapCoordinates Coordinates in map in tiles
     * @return Coordinates on screen
     */
    public Point convertMapToScreenCoordinates(Point mapCoordinates) {
        Point screenCoordinates = new Point();
        screenCoordinates.x = startCornerX + tileSize * mapCoordinates.x;
        screenCoordinates.y = startCornerY + tileSize * mapCoordinates.y;

        return screenCoordinates;
    }

    static void initializeTileSprites(Bitmap image, int tSize, int tCount, int sFactor) {
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

    static Bitmap getTile(int tileType) {
        return allTileSprites[tileType];
    }

    /**
     * @return Sile of tile in px
     */
    public static int getTileSize() {
        return tileSize;
    }

    /**
     * @return Array with numbers of tiles
     */
    public int[][] getMapArray() {
        return mapArray;
    }

    void changeMapArray(int x, int y, int newValue) {
        mapArray[x][y] = newValue;
        tiles[x][y] = new Tile(newValue);
    }
}
